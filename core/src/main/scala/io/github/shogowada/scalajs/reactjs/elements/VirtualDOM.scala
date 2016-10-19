package io.github.shogowada.scalajs.reactjs.elements

import io.github.shogowada.scalajs.reactjs.React
import io.github.shogowada.scalajs.reactjs.components.ReactInputComponent
import io.github.shogowada.statictags.StaticTags.{Attributes, Elements}
import io.github.shogowada.statictags.{Attribute, AttributeSpec, Element}

import scala.language.implicitConversions
import scala.scalajs.js
import scala.scalajs.js.JSConverters._

object VirtualDOM {

  case class OnChangeAttributeSpec(name: String) extends AttributeSpec {
    def :=(callback: js.Function0[Unit]): Attribute[js.Function0[Unit]] = {
      Attribute[js.Function0[Unit]](name = name, value = callback)
    }
  }

  case class RefAttributeSpec(name: String) extends AttributeSpec {
    def :=(callback: js.Function1[ReactInputComponent, Unit]): Attribute[js.Function1[ReactInputComponent, Unit]] = {
      Attribute[js.Function1[ReactInputComponent, Unit]](name = name, value = callback)
    }
  }

  class VirtualDOMAttributes extends Attributes {
    lazy val onChange = new OnChangeAttributeSpec("onChange")
    lazy val ref = new RefAttributeSpec("ref")
  }

  val < = new Elements()
  val ^ = new VirtualDOMAttributes()

  implicit def asReactElement(element: Element): ReactElement = {
    React.createElement(
      element.name,
      toReactAttributes(element.attributes),
      toReactContents(element.contents): _*
    )
  }

  private def toReactAttributes(attributes: Iterable[Attribute[_]]): js.Dictionary[Any] = {
    attributes.map(attribute => (attribute.name, attribute.value))
        .toMap
        .toJSDictionary
  }

  private def toReactContents(contents: Seq[Any]): Seq[js.Any] = {
    contents.map(toReactContent)
  }

  private def toReactContent(content: Any): js.Any = {
    content match {
      case element@Element(_, _, _) => asReactElement(element)
      case _ => content.toString
    }
  }
}