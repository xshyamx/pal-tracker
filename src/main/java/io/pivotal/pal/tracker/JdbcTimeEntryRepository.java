package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    JdbcTemplate template;

    public JdbcTimeEntryRepository(DataSource ds) {
        template = new JdbcTemplate(ds);
    }

    @Override
    public TimeEntry create(TimeEntry any) {
        KeyHolder holder = new GeneratedKeyHolder();
        template.update(
                "insert into time_entries(project_id,user_id,date,hours) values(?,?,?,?)",
                new PreparedStatementSetter() {

                    @Override
                    public void setValues(PreparedStatement ps) throws SQLException {
                        int idx = 1;
                        ps.setLong(idx++, any.getProjectId());
                        ps.setLong(idx++, any.getUserId());
                        ps.setDate(idx++, Date.valueOf(any.getDate()));
                        ps.setInt(idx++, any.getHours());
                    }
                }, holder
        );
        return find(holder.getKey().longValue());
    }

    @Override
    public TimeEntry find(long id) {
        String queryStr = "SELECT id,project_id,user_id,date,hours FROM time_entries WHERE id = ?";
        TimeEntry entry = template.query(queryStr,new Object[]{id},extractor);
        return entry;
    }

    @Override
    public List<TimeEntry> list() {
        return template.queryForList("SELECT id,project_id,user_id,date,hours FROM time_entries", TimeEntry.class);
    }

    @Override
    public TimeEntry update(long eq, TimeEntry any) {
        return null;
    }

    @Override
    public TimeEntry delete(long l) {
        return null;
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
