import monocle.Iso

case class Meter(whole: Int, fraction: Int)
case class Centimeter(whole: Int)

val centimeterToMeterIso = Iso[Centimeter, Meter] { cm =>
  Meter(cm.whole / 100, cm.whole % 100)
}{ m =>
  Centimeter(m.whole * 100 + m.fraction)
}

centimeterToMeterIso.modify(m => m.copy(m.whole + 3))(Centimeter(155))