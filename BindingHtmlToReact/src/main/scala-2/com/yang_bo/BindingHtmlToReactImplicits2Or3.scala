package com.yang_bo

import com.thoughtworks.binding.Binding.BindingSeq
import org.scalajs.dom._
import org.scalajs.dom.raw._
import slinky.core.facade._

private[yang_bo] trait BindingHtmlToReactImplicits2Or3 {

  /** Implicitly convents an [[https://github.com/Atry/html.scala html.scala]]
    * `@html` literal into a React component.
    * @example
    *   The following code creates a spinner from the `@html` literal:
    *   {{{
    *   import com.thoughtworks.binding.Binding
    *   import com.thoughtworks.binding.Binding.Var
    *   import org.lrng.binding.html
    *   import org.scalajs.dom._
    *   import org.scalajs.dom.raw._
    *
    *   @html def spinner(currentNumber: Var[Int]) = {
    *     <button
    *       id="minus"
    *       onclick={ (e: Event) => currentNumber.value -= 1 }
    *     >-</button>
    *     <label>{currentNumber.bind.toString}</label>
    *     <button
    *       id="plus"
    *       onclick={ (e: Event) => currentNumber.value += 1 }
    *     >+</button>
    *   }
    *   }}}
    *   The `@html` literal can be then used as a React component with the help
    *   of [[BindingHtmlToReact.Implicits]]:
    *   {{{
    *   import com.yang_bo.BindingHtmlToReact.Implicits._
    *   import slinky.web.html._
    *   val currentNumber = Var(50)
    *   def reactRoot = fieldset(
    *     legend("I am a React component that contains an `@html` literal"),
    *     spinner(currentNumber)
    *   )
    *   }}}
    *   Then, the `@html` literal can be
    *   [[org.lrng.binding.html.render render]]ed into the html document,
    *   {{{
    *   import slinky.web.ReactDOM
    *   import slinky.testrenderer.TestRenderer
    *   import org.scalajs.dom.document
    *   TestRenderer.act { () =>
    *     ReactDOM.render(reactRoot, document.body)
    *   }
    *   currentNumber.value should be(50)
    *   document.body.innerHTML should be(
    *     """<fieldset><legend>I am a React component that contains an `@html` literal</legend><span><button id="minus">-</button><label>50</label><button id="plus">+</button></span></fieldset>"""
    *   )
    *   }}}
    *   and it can respond to UI events
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
    *     """<fieldset><legend>I am a React component that contains an `@html` literal</legend><span><button id="minus">-</button><label>49</label><button id="plus">+</button></span></fieldset>"""
    *   )
    *   }}}
    */
  def bindingSeqToReactElement(
      bindingSeq: BindingSeq[Node]
  ): ReactElement
}
