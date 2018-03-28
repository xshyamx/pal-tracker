package io.pivotal.pal.tracker

import java.time.LocalDate

data class TimeEntry constructor(
        var id:Long,
        var projectId:Long,
        var userId:Long,
        var date: LocalDate,
        var hours:Int
) {

    constructor(projectId: Long, userId: Long, date: LocalDate, hours: Int) :
            this(0, projectId, userId, date, hours)
    constructor() : this(0, 0, 0, LocalDate.now(), 0)


}