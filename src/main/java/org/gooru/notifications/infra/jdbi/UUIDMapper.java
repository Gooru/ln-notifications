package org.gooru.notifications.infra.jdbi;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @author ashish.
 */
public class UUIDMapper implements ResultSetMapper<UUID> {

    @Override
    public UUID map(final int index, final ResultSet resultSet, final StatementContext statementContext)
        throws SQLException {

        final String uuid = resultSet.getString(1);
        return uuid != null ? UUID.fromString(uuid) : null;
    }

}
