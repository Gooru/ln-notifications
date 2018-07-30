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

