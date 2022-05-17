package dsl.meeting.v1

class Meeting(val title: String) {
    var startTime = ""
    var endTime = ""
    val start = this
    val end = this
    infix fun at(time: Double) {
        startTime = convertToString(time)
    }

    infix fun by(time: Double) {
        endTime = convertToString(time)
    }

    private fun convertToString(time: Double): String = String.format("%.02f", time)
    override fun toString(): String {
        return "Meeting(title='$title', startTime='$startTime', endTime='$endTime')"
    }


}

infix fun String.meeting(block: Meeting.() -> Unit) {
    val meeting = Meeting(this)
    meeting.block()
    println(meeting)
}

/**
 * 无法限制at by的调用方
 * start at(start by:error)
 * end by(end at:error)
 */
fun testMeetingDslV1() {
    "Release planning" meeting {
        start at 14.30
        end by 15.2
    }
}