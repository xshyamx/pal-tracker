package io.pivotal.pal.tracker

import java.util.*

class InMemoryTimeEntryRepository : TimeEntryRepository {
    private var id:Long = 1
    private val db:MutableMap<Long, TimeEntry> = HashMap()

    override fun create(timeEntry: TimeEntry): TimeEntry {
        timeEntry.id = id++
        db.put(timeEntry.id, timeEntry)
        return timeEntry
    }

    override fun find(id: Long): TimeEntry? {
        return db.get(id)
    }

    override fun list(): List<TimeEntry> {
        return db.values.toList()
    }

    override fun update(id: Long, timeEntry: TimeEntry): TimeEntry? {
        val update = find(id)
        if (update != null) {
            update.projectId = timeEntry.projectId
            update.userId = timeEntry.userId
            update.date = timeEntry.date
            update.hours = timeEntry.hours
            return update
        }
        return null
    }

    override fun delete(id: Long): TimeEntry? {
        return db.remove(id)
    }
}