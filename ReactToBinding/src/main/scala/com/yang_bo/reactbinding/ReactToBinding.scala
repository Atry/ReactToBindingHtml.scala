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

final class ReactToBinding[WrapperElement <: Element](
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

object ReactToBinding {
  object Implicits {
    implicit val reactElementBindable: Bindable.Aux[ReactElement, Element]
      with BindableSeq.Aux[ReactElement, Element] = new Bindable[ReactElement]
      with BindableSeq[ReactElement] {
      type Value = Element

      def toBinding(from: ReactElement): Binding[Value] =
        new ReactToBinding(from, document.createElement("span"))

      def toBindingSeq(
          from: ReactElement
      ): BindingSeq[Value] = Binding.SingletonBindingSeq(toBinding(from))
    }
  }
}
