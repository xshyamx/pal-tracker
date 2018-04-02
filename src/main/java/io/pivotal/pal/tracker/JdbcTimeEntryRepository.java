package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    JdbcTemplate template;

    public JdbcTimeEntryRepository(DataSource ds) {
        template = new JdbcTemplate(ds);
    }

    @Override
    public TimeEntry create(TimeEntry any) {
        KeyHolder holder = new GeneratedKeyHolder();
        int update = template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement("insert into time_entries(project_id,user_id,date,hours) values(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                int idx = 1;
                ps.setLong(idx++, any.getProjectId());
                ps.setLong(idx++, any.getUserId());
                ps.setDate(idx++, Date.valueOf(any.getDate()));
                ps.setInt(idx++, any.getHours());
                return ps;
            }
        }, holder);
        if ( update > 0 ) {
            return find(holder.getKey().longValue());
        } else {
            throw new RuntimeException("Failed to insert into time_entries");
        }
    }

    @Override
    public TimeEntry find(long id) {
        String queryStr = "SELECT id,project_id,user_id,date,hours FROM time_entries WHERE id = ?";
        TimeEntry entry = template.query(queryStr,new Object[]{id},extractor);
        return entry;
    }

    @Override
    public List<TimeEntry> list() {
        return template.query("SELECT id,project_id,user_id,date,hours FROM time_entries", mapper);
    }

    @Override
    public TimeEntry update(long id, TimeEntry any) {
        TimeEntry toUpdate = find(id);
        if ( toUpdate != null ) {
            toUpdate.setProjectId(any.getProjectId());
            toUpdate.setUserId(any.getUserId());
            toUpdate.setDate(any.getDate());
            toUpdate.setHours(any.getHours());
            int updated = template.update(
                    "update time_entries set project_id=?, user_id=?, date=?, hours=? where id=?",
                    new PreparedStatementSetter() {
                        @Override
                        public void setValues(PreparedStatement ps) throws SQLException {
                            int idx = 1;
                            ps.setLong(idx++, toUpdate.getProjectId());
                            ps.setLong(idx++, toUpdate.getUserId());
                            ps.setDate(idx++, Date.valueOf(toUpdate.getDate()));
                            ps.setInt(idx++, toUpdate.getHours());
                            ps.setLong(idx++, id);
                        }
                    }
            );
            if ( updated > 0 ) {
                return toUpdate;
            } else {
                throw new RuntimeException("Failed to update time entry");
            }
        }
        return toUpdate;
    }

    @Override
    public TimeEntry delete(long id) {
        TimeEntry toDelete = find(id);
        if ( toDelete != null ) {
            int deleted = template.update("delete from time_entries where id=?", id);
            if ( deleted > 0 ) {
                return  toDelete;
            } else {
                throw new RuntimeException("Failed to delete time entry");
            }
        }
        return toDelete;
    }

    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor = (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;
}
