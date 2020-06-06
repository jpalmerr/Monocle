import monocle.{Lens, Optional, Prism}

sealed trait Error

case class ErrorA(message: String, details: DetailedErrorA) extends Error
case object ErrorB extends Error

case class DetailedErrorA(detailedMessage: String)

object ErrorOptics {
  // That's straightforward approach, not recommended but shows the essence of Optional:
  val detailedErrorA = Optional[Error, String]{
    case err: ErrorA => Some(err.details.detailedMessage)
    case _ => None
  }{ newDetailedMsg => {
    case err: ErrorA => err.copy(details = err.details.copy(newDetailedMsg))
    case from => from
  }
  }

  // Better approach is to get `Optional[OperationError, String]` by composing Prism and Lens:
  val errorA = Prism.partial[Error, ErrorA] {
    case err: ErrorA => err
  }(identity)

  val detailedError =
    Lens[ErrorA, DetailedErrorA](_.details)(newDetails => from => from.copy(details = newDetails))

  val detailedErrorMsg =
    Lens[DetailedErrorA, String](_.detailedMessage)(newMsg => from => from.copy(detailedMessage = newMsg))

  val composedDetailedErrorA: Optional[Error, String] =
    errorA.composeLens(detailedError.composeLens(detailedErrorMsg))
}