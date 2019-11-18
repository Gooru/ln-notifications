package org.gooru.notifications.writers.milestones;

import org.gooru.notifications.infra.jdbi.DBICreator;
import org.skife.jdbi.v2.DBI;

/**
 * @author ashish.
 */

public interface MilestoneFinder {

  String findMilestone();

  static MilestoneFinder build(DBI dbi, MilestoneFinderContext context) {
    return new MilestoneFinderImpl(dbi, context);
  }

  static MilestoneFinder build(MilestoneFinderContext context) {
    return new MilestoneFinderImpl(DBICreator.getDbiForDefaultDS(), context);
  }

}
