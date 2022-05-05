package typesafe

class Animal(val age: Int) {
    override fun equals(other: Any?): Boolean {
        return other is Animal && other.age == age
    }
}