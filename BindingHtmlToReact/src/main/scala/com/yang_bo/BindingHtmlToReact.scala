package com.yang_bo

import com.thoughtworks.binding.Binding
import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.Binding.BindingSeq
import com.thoughtworks.binding.bindable.Bindable
import com.thoughtworks.binding.bindable.BindableSeq
import org.scalajs.dom._
import org.scalajs.dom.raw._
import slinky.core._
import slinky.core.facade._
import slinky.web.ReactDOM
import slinky.web.html._

import scala.language.implicitConversions
import scala.scalajs.js
import scala.annotation.tailrec

/** A React component that wraps an
  * [[https://github.com/Atry/html.scala html.scala]] node sequence.
  * @see
  *   [[Implicits.bindingSeqToReactElement]] for inserting an `@html` literal or
  *   `html"..."` interpolation into React virtual DOM.
  */
object BindingHtmlToReact extends ComponentWrapper {
  final case class Props(
      bindingSeq: BindingSeq[Node],
      wrapper: ReactRef[Node with ParentNode] => ReactElement
  )
  type State = Unit

  final class Def(jsProps: js.Object) extends Definition(jsProps) with js.Any {
    private val wrapperVar = Var[Option[Node with ParentNode]](None)
    private val mountPoint = Binding {
      wrapperVar.bind match {
        case Some(wrapperElement) =>
          new HtmlMountPoint(wrapperElement, props.bindingSeq).bind
        case None =>
      }
    }

    def setter: js.Function1[Element, Unit] = { wrapperElement =>
      wrapperVar.value = Some(wrapperElement)
    }

    def initialState = ()

    def render() =
      props.wrapper(setter.asInstanceOf[ReactRef[Node with ParentNode]])

    override def componentDidMount(): Unit = {
      super.componentDidMount()
      mountPoint.watch()
    }

    override def componentWillUnmount(): Unit = {
      mountPoint.unwatch()
      super.componentWillUnmount()
    }

  }

  private[BindingHtmlToReact] trait LowPriorityImplicits1024 {
    @inline implicit def bindableSeqToReactElement[From](
        from: From
    )(implicit bindableSeq: BindableSeq.Lt[From, Node]): ReactElement =
      BindingHtmlToReact(
        Props(
          bindableSeq.toBindingSeq(from),
          wrapperRef => span(ref := wrapperRef)
        )
      )
  }

  object Implicits
      extends LowPriorityImplicits1024
      with BindingHtmlToReactImplicits2Or3 {

    @inline implicit def bindingSeqToReactElement(
        bindingSeq: BindingSeq[Node]
    ): ReactElement =
      BindingHtmlToReact(
        Props(bindingSeq, wrapperRef => span(ref := wrapperRef))
      )
  }
}
