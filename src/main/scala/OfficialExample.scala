import models._
import monocle.Lens
import monocle.macros.GenLens
import monocle.function.Cons.headOption
import monocle.macros.syntax.lens._

object OfficialExample extends App {

  val employee = Employee("john", Company("awesome inc", Address("london", Street(23, "high street"))))

  val company: Lens[Employee, Company] = GenLens[Employee](_.company)
  val address: Lens[Company, Address] = GenLens[Company](_.address)
  val street: Lens[Address , Street]  = GenLens[Address](_.street)
  val streetName: Lens[Street  , String]  = GenLens[Street](_.name)

  /*
  composeLens takes two Lenses, one from A to B and another one from B to C and creates a third Lens from A to C
   */
  company composeLens address composeLens street composeLens streetName

  println((company composeLens address composeLens street composeLens streetName).modify(_.capitalize)(employee))
  //  we cannot write such a Lens because Lenses require the field they are directed at to be mandatory ^

  println(
    (company composeLens address
    composeLens street
    composeLens streetName
    composeOptional headOption).modify(_.toUpper)(employee)
  )

  // even less boiler plate

  println(employee.lens(_.company.address.street.name).composeOptional(headOption).modify(_.toUpper))
}
