# React / Binding.scala / html.scala Interoperability
[![Scala CI](https://github.com/Atry/ReactToBindingHtml.scala/actions/workflows/scala.yml/badge.svg)](https://github.com/Atry/ReactToBindingHtml.scala/actions/workflows/scala.yml)
[![reacttobindinghtml Scala version support](https://index.scala-lang.org/atry/reacttobindinghtml.scala/reacttobindinghtml/latest.svg)](https://index.scala-lang.org/atry/reacttobindinghtml.scala/reacttobindinghtml)
[![bindinghtmltoreact Scala version support](https://index.scala-lang.org/atry/reacttobindinghtml.scala/bindinghtmltoreact/latest.svg)](https://index.scala-lang.org/atry/reacttobindinghtml.scala/bindinghtmltoreact)
[![bindingreacttoreact Scala version support](https://index.scala-lang.org/atry/reacttobindinghtml.scala/bindingreacttoreact/latest.svg)](https://index.scala-lang.org/atry/reacttobindinghtml.scala/bindingreacttoreact)
[![Scaladoc](https://javadoc.io/badge/com.yang-bo/reacttobindinghtml_sjs1_3.svg?label=Scaladoc)](https://javadoc.io/page/com.yang-bo/reacttobindinghtml_sjs1_3/latest/com/yang_bo.html)

This repository includes adapters for [React](https://reactjs.org/) / [Binding.scala](https://github.com/ThoughtWorksInc/Binding.scala/) / [html.scala](https://github.com/Atry/html.scala) interoperability.

## Motivation

The rendering process in React components is unpredictable, resulting in unnecessary reevaluation and, even worse, unexpected reevaluation of side effects, if any. Precise data-binding in [Binding.scala](https://github.com/ThoughtWorksInc/Binding.scala) is a fast and predictable alternative to React's repeatedly re-rendering approach.

However, currently there are more third-party components in the React ecosystem than Binding.scala. It would be nice if a web developer could reuse React components while taking advantage of Binding.scala's precise data-binding. This repository includes the following adapters for reusing React components in Binding.scala + html.scala web apps:

- [ReactToBindingHtml](https://www.javadoc.io/page/com.yang-bo/reacttobindinghtml_sjs1_3/latest/com/yang_bo/ReactToBindingHtml.html) - an adapter for inserting React virtual DOM into an `@html` literal or an `html"..."` interpolation.
- [BindingHtmlToReact](https://www.javadoc.io/page/com.yang-bo/bindinghtmltoreact_sjs1_3/latest/com/yang_bo/BindingHtmlToReact$.html) - an adapter for inserting an `@html` literal or an `html"..."` interpolation into a React component.
- [BindingReactToReact](https://www.javadoc.io/page/com.yang-bo/bindingreacttoreact_sjs1_3/latest/com/yang_bo/BindingReactToReact$.html) - an adapter for using [Binding.scala](https://github.com/ThoughtWorksInc/Binding.scala)'s `.bind` data-binding in React virtual DOM.

With the help of the adapters, you will be able to reuse existing React components while getting rid of React hooks or `setState` by managing your app's states in Binding.scala.

## Getting Started

```sbt
// build.sbt
libraryDependencies ++= Seq(
  "com.yang-bo" %%% "html" % (if (scalaBinaryVersion.value == "3") "3+" else "2+"),
  "com.yang-bo" %%% "bindingreacttoreact" % "1+",
  "com.yang-bo" %%% "reacttobindinghtml" % "1+",
  "com.yang-bo" %%% "bindinghtmltoreact" % "1+",
)
```

The following example demonstrates how to use React components with [html.scala](https://github.com/Atry/html.scala)'s `@html` literal in Scala 2

```scala
import com.thoughtworks.binding.Binding
import com.thoughtworks.binding.Binding._
import com.yang_bo.ReactToBindingHtml.Implicits._
import com.yang_bo.BindingHtmlToReact.Implicits._
import com.yang_bo.BindingReactToReact.Implicits._
import org.scalajs.dom._
import org.lrng.binding.html
import slinky.web._
import slinky.web.html._
import slinky.core.facade._

@html def spinner(currentNumber: Var[Int]): ReactElement = {
  // virtual DOM span element
  span(

    // real DOM button element
    <button
      id="minus"
      onclick={ (event: MouseEvent) => currentNumber.value -= 1 }
    ></button>,

    // virtual DOM label element with Binding.scala's `.bind` magic
    Binding {
      label(currentNumber.bind.toString)
    },

    // virtual DOM button element
    button(
      id := "plus",
      onClick := { (event: Any) =>
        currentNumber.value += 1
      }
    )(
      "+"
    )

  )
}

val currentNumber = Var(50)

@html val rootView = {
  // real DOM fieldset element
  <fieldset>
    <legend>
      I am an `@html` literal that contains a React component
    </legend>
    { spinner(currentNumber) }
  </fieldset>
}

html.render(documet.body, rootView)
```

For Scala 3 users, use [html.scala](https://github.com/Atry/html.scala)'s `html"..."` interpolation instead, as shown below:

```scala
import com.thoughtworks.binding.Binding
import com.thoughtworks.binding.Binding._
import com.yang_bo.ReactToBindingHtml.Implicits._
import com.yang_bo.BindingHtmlToReact.Implicits._
import com.yang_bo.BindingReactToReact.Implicits._
import com.yang_bo.html._
import org.scalajs.dom._
import slinky.web._
import slinky.web.html._
import slinky.core.facade._

def spinner(currentNumber: Var[Int]): ReactElement = {
  // virtual DOM span element
  span(

    // real DOM button element
    html"""<button
      id="minus"
      onclick=${ (event: MouseEvent) => currentNumber.value -= 1 }
    ></button>""",

    // virtual DOM label element with Binding.scala's `.bind` magic
    Binding {
      label(currentNumber.bind.toString)
    },

    // virtual DOM button element
    button(
      id := "plus",
      onClick := { (event: Any) =>
        currentNumber.value += 1
      }
    )(
      "+"
    )

  )
}

val currentNumber = Var(50)

val rootView = {
  // real DOM fieldset element
  html"""<fieldset>
    <legend>
      I am an html interpolation that contains a React component
    </legend>
    ${ spinner(currentNumber) }
  </fieldset>"""
}

render(documet.body, rootView)
```

Even though adapters from this repository provide the flexibility to choose to create UI in either React or real DOM, to minimize the cost due to React rerendering, `BindingReactToReact` users are recommended to neither define any React components nor use any React hooks. Instead, to take the advantage of Binding.scala's precise data-binding, manage the application states in Binding.scala, and let the `BindingReactToReact` React components be instantiated implicitly, when using existing React components.

Because React's virtual DOM does not support partial update provided by Binding.scala's `BindingSeq`, create your own HTML UI as `@html` literals or `html"..."` interpolations, if the overhead due to React's virtual DOM differentiation matters.

## Related tools

Adapters from this repository work with React types defined in [Slinky](https://slinky.dev/) by default. Other sources of React components need to be converted to Slinky types first.

- React components defined in [scalajs-react](https://github.com/japgolly/scalajs-react) can be converted into Slinky types via [toSlinky](https://slinky.dev/docs/scalajs-react-interop), in order to use them in Binding.scala apps with the help of adapters from this repository.
- React components defined in TypeScript can be converted to Slinky types via [ScalablyTyped](https://scalablytyped.org/) with [Flavour.Slinky](https://scalablytyped.org/docs/flavour), in order to use them in Binding.scala apps with the help of adapters from this repository.

## Links

- [API Documentation](https://www.javadoc.io/page/com.yang-bo/reacttobindinghtml_sjs1_3/latest/com/yang_bo.html)
- [Binding.scala](https://github.com/ThoughtWorksInc/Binding.scala)
- [html.scala](https://github.com/Atry/html.scala)
- [Slinky](https://slinky.dev/)
- [ScalablyTyped](https://scalablytyped.org)
- [scalajs-react](https://github.com/japgolly/scalajs-react)
