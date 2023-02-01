package com.yang_bo.reactbinding

import com.thoughtworks.binding.Binding.BindingSeq
import org.scalajs.dom._
import org.scalajs.dom.raw._
import slinky.core.facade._

private[reactbinding] trait BindingHtmlToReactImplicits2Or3 {

  /** Implicitly convents a Binding.scala HTML template as a React component.
    * @note the code examples in the Scaladoc is for Scala 3 only
    * @example
    *   The following code creates a spinner from the `html"..."` interpolation
    *   provided by [[com.yang_bo.html]]:
    *   {{{
    *   import com.thoughtworks.binding.Binding
    *   import com.thoughtworks.binding.Binding.Var
    *   import com.yang_bo.html.*
    *   import org.scalajs.dom.*
    *
    *   def spinner(currentNumber: Var[Int]) = html"""
    *     <button
    *       id="minus"
    *       onclick=${ (e: Event) => currentNumber.value -= 1 }
    *     >-</button>
    *     <label>${currentNumber.bind.toString}</label>
    *     <button
    *       id="plus"
    *       onclick=${ (e: Event) => currentNumber.value += 1 }
    *     >+</button>
    *   """
    *   }}}
    *   The HTML interpolation can be then used as a React component with the
    *   help of [[BindingHtmlToReact.Implicits]]:
    *   {{{
    *   import com.yang_bo.reactbinding.BindingHtmlToReact.Implicits.*
    *   import slinky.web.html.*
    *   val currentNumber = Var(50)
    *   def reactRoot = fieldset(
    *     legend("I am a React component that contains an HTML interpolation"),
    *     spinner(currentNumber)
    *   )
    *   }}}
    *   Then, the component can be rendered.
    *   {{{
    *   import slinky.web.ReactDOM
    *   import slinky.testrenderer.TestRenderer
    *   import org.scalajs.dom.document
    *   TestRenderer.act { () =>
    *     ReactDOM.render(reactRoot, document.body)
    *   }
    *   currentNumber.value should be(50)
    *   document.body.innerHTML should be(
    *     """<fieldset><legend>I am a React component that contains an HTML interpolation</legend><span>
    *     <button id="minus">-</button>
    *     <label>50</label>
    *     <button id="plus">+</button>
    *     </span></fieldset>"""
    *   )
    *   }}}
    *   And, the component can respond to UI events
    *   {{{
    *   document
    *     .getElementById("minus")
    *     .asInstanceOf[HTMLButtonElement]
    *     .onclick(
    *       new MouseEvent("click", new MouseEventInit {
    *         bubbles = true
    *         cancelable = true
    *       })
    *     )
    *   currentNumber.value should be(49)
    *   document.body.innerHTML should be(
    *     """<fieldset><legend>I am a React component that contains an HTML interpolation</legend><span>
    *     <button id="minus">-</button>
    *     <label>49</label>
    *     <button id="plus">+</button>
    *     </span></fieldset>"""
    *   )
    *   }}}
    */
  def bindingSeqToReactElement(
      bindingSeq: BindingSeq[Node]
  ): ReactElement
}
