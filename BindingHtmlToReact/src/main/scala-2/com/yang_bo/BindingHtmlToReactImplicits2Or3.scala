package com.yang_bo

import com.thoughtworks.binding.Binding.BindingSeq
import org.scalajs.dom._
import org.scalajs.dom.raw._
import slinky.core.facade._

private[yang_bo] trait BindingHtmlToReactImplicits2Or3 {

  /** Implicitly convents an [[https://github.com/Atry/html.scala html.scala]]
    * node sequence into a React component.
    * 
    * @todo examples for Scala 2
    */
  def bindingSeqToReactElement(
      bindingSeq: BindingSeq[Node]
  ): ReactElement
}
