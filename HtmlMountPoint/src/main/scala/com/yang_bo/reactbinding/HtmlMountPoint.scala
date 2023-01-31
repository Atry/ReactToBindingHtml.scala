package com.yang_bo.reactbinding
import com.thoughtworks.binding.Binding
import com.thoughtworks.binding.Binding.Var
import com.thoughtworks.binding.Binding.BindingSeq
import org.scalajs.dom._
import org.scalajs.dom.raw._

import scala.scalajs.js
import scala.annotation.tailrec

private[reactbinding] object HtmlMountPoint {
  @inline
  @tailrec
  def removeAll(parent: Node): Unit = {
    val firstChild = parent.firstChild
    if (firstChild != null) {
      parent.removeChild(firstChild)
      removeAll(parent)
    }
  }
}

final class HtmlMountPoint(
    parent: Node with ParentNode,
    childrenBinding: BindingSeq[Node]
) extends Binding.MultiMountPoint[Node](childrenBinding) {

  protected def set(children: Seq[Node]): Unit = {
    set(children: Iterable[Node])
  }
  protected def set(children: Iterable[Node]): Unit = {
    HtmlMountPoint.removeAll(parent)
    for (child <- children) {
      if (child.parentNode != null) {
        throw new IllegalStateException(
          raw"""Cannot insert ${child.toString} twice!"""
        )
      }
      parent.appendChild(child)
    }
  }

  protected def splice(from: Int, that: Iterable[Node], replaced: Int): Unit = {
    spliceGenIterable(from, that, replaced)
  }

  protected def splice(
      from: Int,
      that: collection.GenSeq[Node],
      replaced: Int
  ): Unit = {
    spliceGenIterable(from, that, replaced)
  }

  private def spliceGenIterable(
      from: Int,
      that: collection.GenIterable[Node],
      replaced: Int
  ): Unit = {
    @inline
    @tailrec
    def removeChildren(child: Node, n: Int): Node = {
      if (n == 0) {
        child
      } else {
        val nextSibling = child.nextSibling
        parent.removeChild(child)
        removeChildren(nextSibling, n - 1)
      }
    }

    val child = removeChildren(parent.childNodes(from), replaced)
    if (child == null) {
      for (newChild <- that) {
        if (newChild.parentNode != null) {
          throw new IllegalStateException(
            raw"""Cannot insert a ${newChild.toString} element twice!"""
          )
        }
        parent.appendChild(newChild)
      }
    } else {
      for (newChild <- that) {
        if (newChild.parentNode != null) {
          throw new IllegalStateException(
            raw"""Cannot insert a ${newChild.toString} element twice!"""
          )
        }
        parent.insertBefore(newChild, child)
      }
    }
  }
}
