import monocle.Lens
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers

object ClassicExampleSpec extends AnyFlatSpecLike with Matchers {

  import PersonLenses._

  "composed Lenses" should "works as nested copy" in {
    val bob = Person("Bob Dylan", Address("New York", Street("some", 67)))

    upperCaseWithCopy(bob) should equal(upperCaseWithLens(bob))
  }

  def upperCaseWithCopy(person: Person): Person =
    person.copy(address = person.address.copy(
      street = person.address.street.copy(
        name = person.address.street.name.toUpperCase
      )
    ))

  // Same thing as above but with Lenses
  def upperCaseWithLens(person: Person): Person = {
    val streetName: Lens[Person, String] = addressLens.composeLens(streetLens).composeLens(nameLens)
    streetName.modify(_.toUpperCase)(person)
  }

}