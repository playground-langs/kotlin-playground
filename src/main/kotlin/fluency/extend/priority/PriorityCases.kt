package fluency.extend.priority

fun String.isPalindrome(): Boolean {
    println("version 2")
    return reversed() == this
}


fun testPriorityExtend() {
    //should print "version 2"
    //use the nearest
    "abccba".isPalindrome()
}