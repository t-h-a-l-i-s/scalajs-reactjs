package io.github.shogowada.scalajs.reactjs.example.customvirtualdom

import io.github.shogowada.scalajs.reactjs.ReactDOM
import io.github.shogowada.scalajs.reactjs.elements.ReactElement
import io.github.shogowada.scalajs.reactjs.example.customvirtualdom.CustomVirtualDOM._
import org.scalajs.dom

object HelloWorld {
  def apply(): ReactElement = E.div(A.id := "hello-world")("Hello, World!")
}

object Main {
  def main(args: Array[String]): Unit = {
    val mountNode = dom.document.getElementById("mount-node")
    ReactDOM.render(HelloWorld(), mountNode)
  }
}
