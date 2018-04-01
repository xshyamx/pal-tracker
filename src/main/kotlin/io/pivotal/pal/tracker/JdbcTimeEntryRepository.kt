package io.pivotal.pal.tracker

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.PreparedStatementCreator
import org.springframework.jdbc.core.PreparedStatementSetter
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import java.sql.*
import java.sql.Statement.RETURN_GENERATED_KEYS
import java.time.LocalDate
import javax.sql.DataSource

class JdbcTimeEntryRepository : TimeEntryRepository {
    private val template: JdbcTemplate

    constructor(ds:DataSource) {
        template = JdbcTemplate(ds)
    }

    override fun create(timeEntry: TimeEntry): TimeEntry {
        val holder = GeneratedKeyHolder()
        val count = template.update({ con ->
            val ps = con.prepareStatement(
                    "insert into time_entries (project_id, user_id, date, hours) values (?,?,?,?)",
                    RETURN_GENERATED_KEYS)
            var id:Int = 1
            ps.setLong(id++, timeEntry.projectId)
            ps.setLong(id++, timeEntry.userId)
            ps.setDate(id++, Date.valueOf(timeEntry.date))
            ps.setInt(id, timeEntry.hours)
            ps
        }
        , holder)
        when (count) {
            1 -> timeEntry.id = holder.key!!.toLong()
            else -> throw SQLException("Failed to insert time entry")
        }
        return timeEntry
    }

    override fun find(id: Long): TimeEntry? {
        return template.query(PreparedStatementCreator{
            con:Connection ->
          val ps = con.prepareStatement("select project_id, user_id, date, hours from time_entries where id = ?")
            ps.setLong(1, id)
            ps
        }, ResultSetExtractor{

            rs:ResultSet ->
            when ( rs.next() ) {
                true -> TimeEntry(
                        id,
                        rs.getLong("project_id"),
                        rs.getLong("user_id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getInt("hours")
                )
                false -> null
            }
        })
    }

    override fun list(): List<TimeEntry> {
        return template.query(
                "select id, project_id, user_id, date, hours from time_entries",
                RowMapper { rs, _ ->
                    TimeEntry(
                            rs.getLong("id"),
                            rs.getLong("project_id"),
                            rs.getLong("user_id"),
                            rs.getDate("date").toLocalDate(),
                            rs.getInt("hours")
                    )
                })
    }

    override fun update(id: Long, timeEntry: TimeEntry): TimeEntry? {
        val te = find(id)
        if ( te == null ) {
            return null
        }
        te.id = id
        te.projectId = timeEntry.projectId
        te.userId = timeEntry.userId
        te.date = timeEntry.date
        te.hours = timeEntry.hours
        val count = template.update("update time_entries set project_id=?, user_id=?, date=?, hours=? where id=?", PreparedStatementSetter {
          ps ->
            var idx:Int = 1
            ps.setLong(idx++, te.projectId)
            ps.setLong(idx++, te.userId)
            ps.setDate(idx++, Date.valueOf(te.date))
            ps.setInt(idx++, te.hours)
            ps.setLong(idx, te.id)
        })
        return when (count) {
            1 -> te
            else -> null
        }
    }

    override fun delete(id: Long): TimeEntry? {
        val deleted = find(id)
        if ( deleted == null ) {
            return null
        }
        val count = template.update(PreparedStatementCreator {
            con ->
            val ps = con.prepareStatement("delete from time_entries where id = ?")
            ps.setLong(1, id)
            ps
        })
        return when ( count ) {
            1 -> deleted
            else -> null
        }
    }
}