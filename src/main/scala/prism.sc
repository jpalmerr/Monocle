import monocle.Prism

import scala.util.Try

sealed trait Json
case object JNull extends Json
case class JStr(v: String) extends Json
case class JNum(v: Double) extends Json
case class JObj(v: Map[String, Json]) extends Json

val verboseStringPrism = Prism[Json, String]{
  case JStr(v) => Some(v)
  case _       => None
}(str => JStr(str))

val stringPrism = Prism.partial[Json, String]{case JStr(v) => v}(JStr)

stringPrism.getOption(JStr("someString"))
stringPrism.reverseGet("someString")

val someJson: Json = JStr("someString")
someJson match {
  case JStr(s)      => JStr(s.toUpperCase)
  case anythingElse => anythingElse
}

val withPrism = stringPrism.modify(_.toUpperCase)(someJson)

stringPrism.modifyOption(_.toUpperCase)(JStr("some"))
// Some(JStr(SOME))
stringPrism.modifyOption(_.toUpperCase)(JNull)
// None

val stringToIntPrism = Prism[String, Int](str => Try(str.toInt).toOption)(_.toString)

// --------------------------------------

case class Percent private (value: Int) {
  require(value >= 0)
  require(value <= 100)
}

object Percent {
  def fromInt(input: Int): Option[Percent] =
    if(input >= 0 && input <= 100) {
      Some(Percent(input))
    } else {
      None
    }
}

val intToPercentPrism = Prism[Int, Percent](i => Percent.fromInt(i))(_.value)
val stringToPercent = stringToIntPrism.composePrism(intToPercentPrism)


// successful
stringToPercent.modifyOption(p => p.copy(p.value * 2))("20")
// res: Option[String] = Some(40)

// String => Int conversion fails
stringToPercent.modifyOption(p => p.copy(p.value * 2))("abc")
// res: Option[String] = None

// Int => Percent conversion fails
stringToPercent.modifyOption(p => p.copy(p.value * 2))("200")
// res: Option[String] = None
