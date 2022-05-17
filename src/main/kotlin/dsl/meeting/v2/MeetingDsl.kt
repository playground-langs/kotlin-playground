package dsl.meeting.v2

sealed class MeetingTime(var time: String = "") {
    protected fun convertToString(time: Double): String = String.format("%.02f", time)
}

class StartTime : MeetingTime() {
    infix fun at(time: Double) {
        this.time = convertToString(time)
    }
}

class EndTime : MeetingTime() {
    infix fun by(time: Double) {
        this.time = convertToString(time)
    }
}

class Meeting(val title: String) {
    val start = StartTime()
    val end = EndTime()

    override fun toString(): String {
        return "Meeting(title='$title', startTime='${start.time}', endTime='${end.time}')"
    }


}

infix fun String.meeting(block: Meeting.() -> Unit) {
    val meeting = Meeting(this)
    meeting.block()
    println(meeting)
}

fun testMeetingDslV2() {
    "Release planning" meeting {
        start at 14.30
        end by 15.25
    }
}