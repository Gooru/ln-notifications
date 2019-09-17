package org.gooru.notifications.infra.jdbi;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;
import java.sql.Array;

/**
 * @author ashish.
 */
public class PostgresIntegerArrayArgumentFactory implements ArgumentFactory<PGArray<Integer>> {

  @SuppressWarnings("unchecked")
  public boolean accepts(Class<?> expectedType, Object value, StatementContext ctx) {
    return value instanceof PGArray && ((PGArray) value).getType().isAssignableFrom(Integer.class);
  }

  public Argument build(Class<?> expectedType, final PGArray<Integer> value, StatementContext ctx) {
    return (position, statement, ctx1) -> {
      Array ary = ctx1.getConnection().createArrayOf("integer", value.getElements());
      statement.setArray(position, ary);
    };
  }
}

