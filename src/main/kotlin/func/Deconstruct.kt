package func

fun main() {
    val fullName = getFullName()
    val first = fullName.first
    val second = fullName.second
    val third = fullName.third
    println("$first $second $third")

    val (f, s, t) = fullName
    println("$f $s $t")
}

fun getFullName() = Triple("John", "Quincy", "Adams")