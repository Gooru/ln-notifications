package org.gooru.notifications.writers.milestones;

import org.skife.jdbi.v2.DBI;

/**
 * @author ashish.
 */

class MilestoneFinderImpl implements MilestoneFinder {

  private final DBI dbi;
  private final MilestoneFinderContext context;
  private MilestoneFinderDao dao;

  MilestoneFinderImpl(DBI dbi, MilestoneFinderContext context) {
    this.dbi = dbi;
    this.context = context;
  }

  /*
   * Iff, class is not deleted and class has course which is premium and class has current grade set
   * then fetch fw from current grade fetch milestone with CUL and fw_code return fw_code else no
   * milestone id present
   */


  @Override
  public String findMilestone() {
    Long destination = fetchDao().fetchCurrentGrade(context.getClassId(), context.getCourseId());
    if (destination != null) {
      return fetchDao().fetchMilestoneId(context.getCourseId(), context.getUnitId(),
          context.getLessonId(), destination);
    }
    return null;
  }

  private MilestoneFinderDao fetchDao() {
    if (dao == null) {
      dao = dbi.onDemand(MilestoneFinderDao.class);
    }
    return dao;
  }
}
