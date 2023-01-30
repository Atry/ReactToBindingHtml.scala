package com.yang_bo
import com.thoughtworks.binding.Binding
import slinky.core._
import slinky.core.facade._

import scala.language.implicitConversions
import scala.scalajs.js
object ReactBinding extends ComponentWrapper {
  type Props = Binding[ReactElement]
  type State = ReactElement

  final class Def(jsProps: js.Object) extends Definition(jsProps) {
    private val setterBinding = props.map(setState)

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

  object ImplicitConversions {
    @inline implicit def bindingReactElementToReactElement(
        binding: Binding[ReactElement]
    ): ReactElement = ReactBinding(binding)
  }

}
