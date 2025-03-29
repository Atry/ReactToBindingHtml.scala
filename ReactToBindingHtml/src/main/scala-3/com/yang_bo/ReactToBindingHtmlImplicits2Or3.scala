package com.yang_bo

import com.thoughtworks.binding.Binding.BindingSeq
import com.thoughtworks.binding.bindable.*
import org.scalajs.dom.*
import slinky.core.facade.*

private[yang_bo] trait ReactToBindingHtmlImplicits2Or3 {

  /** Provides the [[com.thoughtworks.binding.bindable.Bindable Bindable]] and
    * [[com.thoughtworks.binding.bindable.BindableSeq BindableSeq]] instances
    * that allow for inserting React virtual DOM into an `html"..."`
    * interpolation.
    * @example
    *   The following code creates a React virtual DOM tree of a spinner. With
    *   the help of [[BindingReactToReact.Implicits]], it can use states stored
    *   in [[com.thoughtworks.binding.Binding.Var Binding.Var]]s:
    *   {{{
    *   import com.thoughtworks.binding.Binding
    *   import com.thoughtworks.binding.Binding.Var
    *   import com.yang_bo.BindingReactToReact.Implicits._
    *   import slinky.web.html._
    *
    *   def spinner(currentNumber: Var[Int]) = {
    *     span(
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
    *   The virtual DOM can be inserted into a `html"..."` interpolation with
    *   the help of [[ReactToBindingHtml.Implicits]]:
    *   {{{
    *   import com.yang_bo.ReactToBindingHtml.Implicits.*
    *   import com.yang_bo.html.*
    *   val currentNumber = Var(50)
    *   def bindingHtmlRoot = html"""<fieldset>
    *     <legend>I am an `html"..."` interpolation that contains a React component</legend>
    *     ${spinner(currentNumber)}
    *   </fieldset>"""
    *   }}}
    *   Then, the `html"..."` interpolation can be
    *   [[com.yang_bo.html#render render]]ed into the html document,
    *   {{{
    *   import slinky.web.ReactDOM
    *   import slinky.testrenderer.TestRenderer
    *   import org.scalajs.dom.*
    *   TestRenderer.act { () =>
    *     render(document.body, bindingHtmlRoot)
    *   }
    *   currentNumber.value should be(50)
    *   document.body.innerHTML should be(
    *     """<fieldset>
    *     <legend>I am an `html"..."` interpolation that contains a React component</legend>
    *     <span><span><button id="minus">-</button><label>50</label><button id="plus">+</button></span></span>
    *     </fieldset>"""
    *   )
    *   }}}
    *   and it can respond to UI events
    *   {{{
    *   TestRenderer.act { () =>
    *     document
    *       .getElementById("minus")
    *       .dispatchEvent(
    *         new MouseEvent("click", new MouseEventInit {
    *           bubbles = true
    *           cancelable = true
    *         })
    *       )
    *   }
    *   currentNumber.value should be(49)
    *   document.body.innerHTML should be(
    *     """<fieldset>
    *     <legend>I am an `html"..."` interpolation that contains a React component</legend>
    *     <span><span><button id="minus">-</button><label>49</label><button id="plus">+</button></span></span>
    *     </fieldset>"""
    *   )
    *   }}}
    */
  implicit def reactElementBindable[From](implicit
      toReactElement: From => ReactElement
  ): Bindable.Aux[From, Element] with BindableSeq.Aux[From, Element]
}
