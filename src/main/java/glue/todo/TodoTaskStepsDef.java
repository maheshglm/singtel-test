package glue.todo;


import com.singtel.test.Bootstrap;
import com.singtel.test.steps.TodoTaskSteps;
import cucumber.api.java.en.But;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java8.En;

import java.util.List;

public class TodoTaskStepsDef implements En {

    private TodoTaskSteps todoSteps = (TodoTaskSteps) Bootstrap.getBean(TodoTaskSteps.class);

    @Given("TodoApp is open")
    public void launchApp() {
        todoSteps.launchTodoApplication();
    }

    @When("I add below task(s)")
    public void addTasks(List<String> tasks) {
        todoSteps.addTasks(tasks);
    }

    @When("I delete below task(s)")
    public void deleteTasks(List<String> tasks) {
        todoSteps.deleteTasks(tasks);
    }

    @When("I complete below task(s)")
    public void completeTasks(List<String> tasks) {
        todoSteps.completeTasks(tasks);
    }

    @When("I undo below completed tasks")
    public void undoCompletedTasks(List<String> tasks) {
        todoSteps.undoCompletedTasks(tasks);
    }

    @When("I clear completed tasks")
    public void clearCompletedTasks() {
        todoSteps.clearCompletedTasks();
    }

    @Then("I expect below tasks should get (added/updated) successfully")
    public void verifyTasks(List<String> tasks) {
        todoSteps.verifyAllTasks(tasks);
    }

    @Then("I update task title {string} as {string}")
    public void updatedTaskTitle(final String oldTitle, final String newTitle) {
        todoSteps.updateTaskTitle(oldTitle, newTitle);
    }

    @Then("I expect todo count label {string} is visible")
    public void todoCountVisibleVerification(String labelIdentifier) {
        todoSteps.verifyTodoCountLabelVisible(labelIdentifier);
    }

    @Then("I expect todo count label {string} is not visible")
    public void todoCountNotVisibleVerification(String labelIdentifier) {
        todoSteps.verifyTodoCountLabelNotVisible(labelIdentifier);
    }

    @Then("I expect All filter is selected by default")
    public void isAllFilterSelectedByDefault() {
        todoSteps.isAllFilterSelectedByDefault();
    }

    @Then("I expect {string} filter is visible")
    public void isFilterVisible(final String filter) {
        todoSteps.verifyFilterIsVisible(filter);
    }

    @Then("I expect {string} filter is not visible")
    public void isFilterNotVisible(final String filter) {
        todoSteps.verifyFilterIsNotVisible(filter);
    }

    @But("No tasks should be displayed in {string} section")
    public void noTasksShouldDisplay(final String filter) {
        todoSteps.noTasksShouldDisplay(filter);
    }

    @Then("below tasks should display in {string} section")
    public void tasksShouldDisplay(final String filter, List<String> tasks) {
        todoSteps.tasksShouldDisplay(filter, tasks);
    }

    @Then("I expect Clear completed is visible")
    public void verifyClearCompleted() {
        todoSteps.verifyClearCompleted();
    }

    @Then("I mark all tasks as complete")
    public void markAllTasksAsComplete() {
        todoSteps.markAllTasksAsComplete();
    }


}
