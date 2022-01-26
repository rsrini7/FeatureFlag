# Feature Toggles

### Run backend

1. Start Redis cache

```bash
docker run -p 6379:6379 -d redis:5.0.6
```

2. Build the app

```
./gradlew clean build
```

3. Run the app - `com.srini.feature.FeatureApplication` from Intellij IDEA
   or run

```
./gradlew bootRun
```

### Feature flags

Based on [FF4J](https://github.com/ff4j/ff4j)
DB table - `ff4j_features` </br>
Feature RBAC table - `ff4j_roles` </br>
NOTE: role name is a privilige, not role </br>
For more details about roles and priviliges please take a look at section `Permissions` below </br>

How to check if a feature flag is enabled/disabled: </br>
`curl --request GET 'http://localhost:8080/api/ff4j/check/fflag-quality-check-1'`
</br>
How to enable a feature flag: </br>
`curl --request POST 'http://localhost:8080/api/ff4j/store/features/fflag-quality-check-1/enable' --header 'Content-Type: application/json'`
</br>
How to disable a feature flag: </br>
`curl --request POST 'http://localhost:8080/api/ff4j/store/features/fflag-quality-check-1/disable' --header 'Content-Type: application/json'`

http://localhost:8080/ff4j-console/
http://localhost:8080/h2-console
http://localhost:8080/message
http://localhost:8080/message/vasan
http://localhost:8080/api/ff4j/
