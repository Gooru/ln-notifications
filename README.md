# Notifications for User 

This component is responsible for creation and serving of notifications created for users. 

### Scope

This component will serve two purposes:
1. For the caller, given a context of user, user role and optionally class, provide a list of notifications available
2. Provide a listener, which can listen to different events, coming from different sub systems to generate the 
notifications
 
### Build and Run

To create the shadow (fat) jar:

    ./gradlew

To run the binary which would be fat jar:

    java -jar notifications.jar src/main/resources/notifications.json

### Event Structure
Following notification types are supported:

#### For student
- teacher.override
- teacher.suggestion
- teacher.grading.complete

#### For teacher
- student.self.report
- student.gradable.submission

The event is a Json packet which contains following fields:


<pre>
{
    "notificationType": "one-of-type-names-defined-above",
    "userId": "uuid-of-user",
    "classId": "uuid-of-class-where-applicable",
    "courseId": "uuid-of-course-mandatory",
    "unitId": "uuid-of-unit-mandatory",
    "lessonId": "uuid-of-lesson-mandatory",
    "collectionId": "uuid-of-collection-mandatory",
    "currentItemId": "uuid-of-current-item-on-main-path-same-as-collection",
    "currentItemType": "type-of-current-item-coll/asmt/asmt-ext/coll-ext",
    "pathId": "id-of-path-if-not-on-main-path-BigInt-type-else-null",
    "action": "initiate/complete",
    "pathType": "one-of-system/teacher/route0-mandatory-if-pathid-is-present"
}
</pre>

### Pending task items
- Complete write paths
- Create new APIs
    - List all supported notifications
    - Allow delete of notifications only where the notifications