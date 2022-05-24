package designpatterns.behavioral.template.v2

class CivicCenterTask {
    fun execute(askForHelp: () -> Unit) {
        lineUp()
        askForHelp()
        evaluate()
    }

    private fun lineUp() {
        println("line up to take a number")
    }

    private fun evaluate() {
        println("evaluate service attitude")
    }
}

fun applyForCitizenCard() {
    println("apply for a citizen card")
}

fun pullSocialSecurity() {
    println("ask for pulling the social security")
}

fun main() {
    val task1 = CivicCenterTask()
    task1.execute(::applyForCitizenCard)
    val task2 = CivicCenterTask()
    task2.execute(::pullSocialSecurity)
}