package com.yang_bo.reactbinding

import com.thoughtworks.binding.Binding.BindingSeq
import org.scalajs.dom._
import org.scalajs.dom.raw._
import slinky.core.facade._

private[reactbinding] trait BindingHtmlToReactImplicits2Or3 {

  /** The implicit convention to treat a Binding.scala HTML template as a React
    * component.
    * 
    * @todo examples for Scala 2
    */
  def bindingSeqToReactElement(
      bindingSeq: BindingSeq[Node]
  ): ReactElement
}
