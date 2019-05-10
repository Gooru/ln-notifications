package org.gooru.notifications.writers.milestones;

import java.util.UUID;

/**
 * @author ashish.
 */

public class MilestoneFinderContext {
    private final UUID classId;
    private final UUID courseId;
    private final UUID unitId;
    private final UUID lessonId;


    public MilestoneFinderContext(UUID classId, UUID courseId, UUID unitId, UUID lessonId) {
        this.classId = classId;
        this.courseId = courseId;
        this.unitId = unitId;
        this.lessonId = lessonId;
    }

    public UUID getClassId() {
        return classId;
    }

    public UUID getCourseId() {
        return courseId;
    }

    public UUID getUnitId() {
        return unitId;
    }

    public UUID getLessonId() {
        return lessonId;
    }
}
