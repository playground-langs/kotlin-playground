package designpatterns.behavioral.chain.v1

data class ApplyEvent(val money: Int, val title: String)

interface ApplyHandler {
    val successor: ApplyHandler?
    fun handleEvent(event: ApplyEvent)
}

class GroupLeader(override val successor: ApplyHandler?) : ApplyHandler {
    override fun handleEvent(event: ApplyEvent) {
        when {
            event.money <= 100 -> println("group leader handled application:${event.title}")
            else -> when (successor) {
                is ApplyHandler -> successor.handleEvent(event)
                else -> println("group leader:${event.money} can't be handled")
            }
        }
    }
}

class President(override val successor: ApplyHandler?) : ApplyHandler {
    override fun handleEvent(event: ApplyEvent) {
        when {
            event.money <= 500 -> println("president handled application:${event.title}")
            else -> when (successor) {
                is ApplyHandler -> successor.handleEvent(event)
                else -> println("president:${event.money} can't be handled")
            }
        }
    }
}

class College(override val successor: ApplyHandler?) : ApplyHandler {
    override fun handleEvent(event: ApplyEvent) {
        when {
            event.money > 1000 -> println("college:application is refused")
            else -> println("president handled application:${event.title}")
        }
    }
}

fun main() {
    val college = College(null)
    val president = President(college)
    val groupLeader = GroupLeader(president)
    groupLeader.handleEvent(ApplyEvent(10, "buy a pen"))
    groupLeader.handleEvent(ApplyEvent(200, "team building"))
    groupLeader.handleEvent(ApplyEvent(600, "hold a match"))
    groupLeader.handleEvent(ApplyEvent(1200, "meeting"))
}