# Demo

A quick end-to-end run of the Task Tracker, built and packaged by Gradle.

## Build & test
```text
$ ./gradlew build

> Task :test
TaskRepositoryTest > returnsEmptyListWhenFileMissing(Path) PASSED
TaskRepositoryTest > writesPrettyPrintedJson(Path) PASSED
TaskRepositoryTest > savesAndLoadsTasksRoundTrip(Path) PASSED
TaskServiceTest > removesTaskById() PASSED
TaskServiceTest > trimsTitleWhitespace() PASSED
TaskServiceTest > addsTasksWithIncrementingIds() PASSED
TaskServiceTest > separatesPendingAndCompleted() PASSED
TaskServiceTest > continuesIdSequenceFromExistingTasks() PASSED
TaskServiceTest > rejectsBlankTitle() PASSED
TaskServiceTest > completesExistingTaskOnly() PASSED
TaskServiceTest > clearsCompletedTasks() PASSED

BUILD SUCCESSFUL
```

## Run the app
```text
$ app add "Finish the Docker web server task"
Added [ ] #1 Finish the Docker web server task

$ app add "Record the LinkedIn demo video"
Added [ ] #2 Record the LinkedIn demo video

$ app done 1
Completed task #1

$ app list
Tasks:
  [x] #1 Finish the Docker web server task
  [ ] #2 Record the LinkedIn demo video
```

## What gets persisted
Tasks are stored as JSON (via Gson) at `~/.codealpha-tasks.json`:
```json
[
  { "id": 1, "title": "Finish the Docker web server task", "done": true },
  { "id": 2, "title": "Record the LinkedIn demo video", "done": false }
]
```
