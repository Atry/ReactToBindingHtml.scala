package com.yang_bo.reactbinding

import com.thoughtworks.binding.Binding
import slinky.core._
import slinky.core.facade._

import scala.language.implicitConversions
import scala.scalajs.js

/** A React Component backed by a `Binding[ReactElement]`.
  *
  * @example
  *   With the help of [[Implicits]],
  *   [[com.thoughtworks.binding.Binding]] can be used as an React element. The
  *   following code create a spinner, which includes a label whose value is
  *   automatically updated according to `currentNumber`:
  *   {{{
  *   import com.thoughtworks.binding.Binding
  *   import com.thoughtworks.binding.Binding.Var
  *   import com.yang_bo.reactbinding.BindingReactToReact.Implicits._
  *   import slinky.web.html._
  *   import slinky.core.facade._
  *
  *   def spinner(currentNumber: Var[Int]) = {
  *     div(
  *       button(id := "minus", onClick := { e => currentNumber.value -= 1 })(
  *         "-"
  *       ),
  *       // With the help of `BindingReactToReact.Implicits`,
  *       // a `Binding` block can be used as a React element
  *       Binding {
  *         // `.bind` is allowed in the Binding block
  *         label(currentNumber.bind.toString)
  *       },
  *       button(id := "plus", onClick := { e => currentNumber.value += 1 })(
  *         "+"
  *       ),
  *     )
  *   }
  *   }}}
  *   The component can be rendered and respond to UI events
  *   {{{
  *
  *   import slinky.web.ReactDOM
  *   import slinky.testrenderer.TestRenderer
  *   import org.scalajs.dom._
  *   import org.scalajs.dom.raw._
  *   val currentNumber = Var(50)
  *   TestRenderer.act { () =>
  *     ReactDOM.render(spinner(currentNumber), document.body)
  *   }
  *   currentNumber.value should be(50)
  *   document.body.innerHTML should be(
  *     """<div><button id="minus">-</button><label>50</label><button id="plus">+</button></div>"""
  *   )
  *
  *   TestRenderer.act { () =>
  *     document.getElementById("minus").dispatchEvent(new Event("click", new EventInit {
  *       bubbles = true
  *       cancelable = true
  *     }))
  *   }
  *   currentNumber.value should be(49)
  *   document.body.innerHTML should be(
  *     """<div><button id="minus">-</button><label>49</label><button id="plus">+</button></div>"""
  *   )
  *   }}}
  */
object BindingReactToReact extends ComponentWrapper {
  type Props = Binding[ReactElement]
  type State = ReactElement

  final class Def(jsProps: js.Object) extends Definition(jsProps) {
    private val setterBinding =
      new Binding.Map[ReactElement, Unit](props, setState)

    def initialState = null

    def render(): ReactElement = state

    override def componentWillMount(): Unit = {
      setterBinding.watch()
      super.componentWillMount()
    }

    override def componentWillUnmount(): Unit = {
      setterBinding.unwatch()
      super.componentWillUnmount()
    }
  }

  private[BindingReactToReact] trait LowPriorityImplicits1024 {
    @inline implicit def bindingToReactElement[From](
        binding: Binding[From]
    )(implicit toReactElement: From => ReactElement): ReactElement =
      BindingReactToReact(
        new Binding.Map[From, ReactElement](binding, toReactElement)
      )
  }

  object Implicits extends LowPriorityImplicits1024 {
    @inline implicit def bindingReactElementToReactElement(
        binding: Binding[ReactElement]
    ): ReactElement = BindingReactToReact(binding)
  }

}
