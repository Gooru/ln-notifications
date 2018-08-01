package org.gooru.notifications.infra.jdbi;

import org.gooru.notifications.infra.components.DataSourceRegistry;
import org.skife.jdbi.v2.DBI;

import javax.sql.DataSource;

/**
 * @author ashish.
 */
public final class DBICreator {

    private DBICreator() {
        throw new AssertionError();
    }

    private static DBI createDBI(DataSource dataSource) {
        DBI dbi = new DBI(dataSource);
        dbi.registerArgumentFactory(new PostgresIntegerArrayArgumentFactory());
        dbi.registerArgumentFactory(new PostgresStringArrayArgumentFactory());
        dbi.registerArgumentFactory(new PostgresUUIDArrayArgumentFactory());
        return dbi;
    }

    public static DBI getDbiForDefaultDS() {
        return createDBI(DataSourceRegistry.getInstance().getDefaultDataSource());
    }
}
