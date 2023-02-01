package com.yang_bo.reactbinding

import com.thoughtworks.binding.Binding
import com.thoughtworks.binding.Binding.BindingSeq
import com.thoughtworks.binding.bindable.Bindable
import com.thoughtworks.binding.bindable.BindableSeq
import org.scalajs.dom._
import org.scalajs.dom.raw._
import slinky.core._
import slinky.core.facade._

import scala.scalajs.js
import slinky.web.ReactDOM

/** The [[https://github.com/ThoughtWorksInc/Binding.scala/ Binding.scala]]
  * wrapper of React virtual DOM element.
  *
  * @see
  *   [[ReactToBindingHtml.Implicits.reactElementBindable]] for using React
  *   virtual DOM in [[https://github.com/Atry/html.scala html.scala]]
  *   templates.
  */
final class ReactToBindingHtml[WrapperElement <: Element](
    reactElement: ReactElement,
    wrapperElement: WrapperElement
) extends Binding[WrapperElement] {
  protected def value = wrapperElement
  private var listenerCount = 0

  protected def addChangedListener(
      listener: Binding.ChangedListener[WrapperElement]
  ): Unit = {
    try {
      if (listenerCount == 0) {
        ReactDOM.render(reactElement, wrapperElement)
      }
    } finally {
      listenerCount += 1
    }
  }
  protected def removeChangedListener(
      listener: Binding.ChangedListener[WrapperElement]
  ): Unit = {
    listenerCount -= 1
    if (listenerCount == 0) {
      ReactDOM.unmountComponentAtNode(value)
    }
  }

}

object ReactToBindingHtml {
  private[ReactToBindingHtml] trait LowPriorityImplicits1024 {}

  object Implicits
      extends ReactToBindingHtmlImplicits2Or3
      with LowPriorityImplicits1024 {
    implicit def reactElementBindable[From](implicit
        toReactElement: From => ReactElement
    ): Bindable.Aux[From, Element] with BindableSeq.Aux[From, Element] =
      new Bindable[From] with BindableSeq[From] {
        type Value = Element

        def toBinding(from: From): Binding[Value] =
          new ReactToBindingHtml(
            toReactElement(from),
            document.createElement("span")
          )

        def toBindingSeq(
            from: From
        ): BindingSeq[Value] = Binding.SingletonBindingSeq(toBinding(from))
      }
  }
}
