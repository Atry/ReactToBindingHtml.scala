package com.yang_bo

import com.thoughtworks.binding.Binding.BindingSeq
import com.thoughtworks.binding.bindable._
import org.scalajs.dom._
import org.scalajs.dom.raw._
import slinky.core.facade._

private[yang_bo] trait ReactToBindingHtmlImplicits2Or3 {

  /** Implicitly returns the [[com.thoughtworks.binding.bindable.Bindable]] and
    * [[com.thoughtworks.binding.bindable.BindableSeq]] instances, which allows
    * for using a React component inside an
    * [[https://github.com/Atry/html.scala html.scala]] literal / interpolation.
    *
    * @todo
    *   examples for Scala 2
    */
  def reactElementBindable[From](implicit
      toReactElement: From => ReactElement
  ): Bindable.Aux[From, Element] with BindableSeq.Aux[From, Element]

}
