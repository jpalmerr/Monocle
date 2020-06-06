import monocle.Lens

// business logic
case class Person(fullName: String, address: Address)
case class Address(city: String, street: Street)
case class Street(name: String, number: Int)

// Lens definitions
object PersonLenses {
  val addressLens = Lens.apply[Person, Address](person => person.address)(newAddress => person => person.copy(address = newAddress))
  val streetLens = Lens.apply[Address, Street](address => address.street)(newStreet => address => address.copy(street = newStreet))
  val nameLens = Lens.apply[Street, String](street => street.name)(newName => street => street.copy(name = newName))
}

object ClassicExample extends App {

  import PersonLenses._

  val bob = Person("Bob Dylan", Address("New York", Street("some", 67)))

  println("with copy")
  println(upperCaseWithCopy(bob))
  println("with lens")
  println(upperCaseWithLens(bob))



  // Let's capitalize street name without Lenses to have some reference point
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
