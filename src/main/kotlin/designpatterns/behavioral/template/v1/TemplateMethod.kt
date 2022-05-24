package designpatterns.behavioral.template.v1

abstract class CivicCenterTask {
    fun execute() {
        lineUp()
        askForHelp()
        evaluate()
    }

    private fun lineUp() {
        println("line up to take a number")
    }

    abstract fun askForHelp()

    private fun evaluate() {
        println("evaluate service attitude")
    }
}

class PullSocialSecurity : CivicCenterTask() {
    override fun askForHelp() {
        println("ask for pulling the social security")
    }
}

class ApplyForCitizenCard : CivicCenterTask() {
    override fun askForHelp() {
        println("apply for a citizen card")
    }
}

fun main() {
    val pss = PullSocialSecurity()
    pss.execute()
    val afcc = ApplyForCitizenCard()
    afcc.execute()
}