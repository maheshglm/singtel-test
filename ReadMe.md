# Coding Test

Coding Test project demonstrates the automation of use case scenarios for todomvc
application https://todomvc.com/examples/vue/#

Tech stack used
---

* Java 8
* Spring (Dependency Injection)
* Cucumber 4.X
* Junit 4.X
* Maven 3.X (Build tool)

Feature files
---

Feature file for all the TODO management scenario are captured under `test/resources/features/todo`

Below scenarios are covered for TODO Management for coding test:

1. User should be able to add a task
2. User should be able to edit a task
3. User should be able to delete a task
4. User should be able to add multiple tasks
5. User should be able to complete a task
6. Clear Completed action when single task exist
7. Clear Completed action when multiple task exist
8. Complete All tasks (Mark all as complete)
9. Undo Completed task

Executing scenarios from Cucumber Runner
---

We can use cucumber runner to execute user scenarios Runner is configured

1. Identify a tag of a specific Feature or Scenario
2. Update `com/singtel/test/CucumberRunner.java` with the tag that captured in above step
3. Run `CucumberRunner.java` class

Executing scenarios using Maven
---

1. Run `mvn clean test` from Terminal (it will trigger Tests with predefined tag (`@todo_management`) in the Cucumber
   Runner)
2. or `mvn clean test "-Dcucumber.options=--tags @tag1, @tag2..."` (to trigger specific tags)

Reports
---

This project leverages net master thought reports library. After the execution, the html reports are available in
`testout/reports` folder.

Assumptions
---

For the simplicity & time constraints, below are assumptions were made:

1. Considered `chrome` is the default browser. Cross browser can be enabled with Webdriver manager library and passing
   browser name from System properties.
2. Parallel execution is not implemented (though it can be done with cucumber-jvm parallel plugin)
3. Framework & Tests are built in same repo (but in enterprise automation design, core framework can be an independent
   entity/repo to each project can add the core framework as a dependency and continue with test automation)
