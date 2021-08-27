package com.singtel.test.steps;

import com.singtel.test.CustomException;
import com.singtel.test.mdl.CustomExceptionType;
import com.singtel.test.pages.todo.TodoPage;
import com.singtel.test.utils.ThreadUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoTaskSteps {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoTaskSteps.class);

    @Autowired
    private TodoPage todoPage;

    @Autowired
    private ThreadUtils threadUtils;

    public void launchTodoApplication() {
        todoPage.launchTodoApp();
    }

    public void addTasks(List<String> tasks) {
        tasks.stream()
                .filter(t -> !t.equalsIgnoreCase("task_title"))
                .forEach(t -> todoPage.addTask(t));
    }

    public void deleteTasks(List<String> tasks) {
        tasks.stream()
                .filter(t -> !t.equalsIgnoreCase("task_title"))
                .forEach(t -> todoPage.deleteTask(t));
    }

    public void completeTasks(List<String> tasks) {
        tasks.stream()
                .filter(t -> !t.equalsIgnoreCase("task_title"))
                .forEach(t -> todoPage.completeTask(t));
    }

    public void undoCompletedTasks(List<String> tasks) {
        todoPage.selectFilter("Completed");
        tasks.stream()
                .filter(t -> !t.equalsIgnoreCase("task_title"))
                .forEach(t -> todoPage.undoCompletedTask(t));
    }

    public void updateTaskTitle(final String oldTitle, final String newTitle) {
        todoPage.updateTask(oldTitle, newTitle);
    }


    public void clearCompletedTasks() {
        todoPage.clearCompletedTasks();
    }

    public void verifyAllTasks(List<String> tasks) {
        List<String> expectedTasks = tasks.stream()
                .filter(t -> !t.equalsIgnoreCase("task_title"))
                .collect(Collectors.toList());
        threadUtils.sleepMillis(500L);
        List<String> listOfTasks = todoPage.getListOfTasks();
        if (!expectedTasks.equals(listOfTasks)) {
            LOGGER.error("Expected tasks [{}] are not visible", expectedTasks);
            throw new CustomException(CustomExceptionType.VERIFICATION_FAILED,
                    "Expected tasks [{}] are not visible", expectedTasks);
        }
    }

    public void verifyTodoCountLabelVisible(String labelIdentifier) {
        final String actualTodoCountLabel = todoPage.getTodoCountLabel();
        if (!labelIdentifier.equalsIgnoreCase(actualTodoCountLabel)) {
            LOGGER.error("Todo count verification is failed, expected is [{}], but actual [{}]",
                    labelIdentifier, actualTodoCountLabel);
            throw new CustomException(CustomExceptionType.VERIFICATION_FAILED,
                    "Todo count verification is failed, expected is [{}], but actual [{}]",
                    labelIdentifier, actualTodoCountLabel);
        }
    }

    public void verifyTodoCountLabelNotVisible(String labelIdentifier) {
        final String actualTodoCountLabel = todoPage.getTodoCountLabel();
        if (actualTodoCountLabel.equalsIgnoreCase(labelIdentifier)) {
            LOGGER.error("Todo count verification is failed, expected is [{}], but actual [{}]",
                    labelIdentifier, actualTodoCountLabel);
            throw new CustomException(CustomExceptionType.VERIFICATION_FAILED,
                    "Todo count verification is failed, expected is [{}], but actual [{}]",
                    labelIdentifier, actualTodoCountLabel);
        }
    }

    public void isAllFilterSelectedByDefault() {
        if (!todoPage.isAllFilterSelected()) {
            LOGGER.error("All filter is not selected by default");
            throw new CustomException(CustomExceptionType.VERIFICATION_FAILED,
                    "All filter is not selected by default");
        }
    }

    public void verifyFilterIsVisible(final String filter) {
        if (!todoPage.isFilterVisible(filter)) {
            LOGGER.error("Filter [{}] is not visible on the Todo page", filter);
            throw new CustomException(CustomExceptionType.VERIFICATION_FAILED,
                    "Filter [{}] is not visible on the Todo page", filter);
        }
    }

    public void verifyFilterIsNotVisible(final String filter) {
        if (todoPage.isFilterVisible(filter)) {
            LOGGER.error("Filter [{}] is visible on the Todo page", filter);
            throw new CustomException(CustomExceptionType.VERIFICATION_FAILED,
                    "Filter [{}] is visible on the Todo page", filter);
        }
    }

    public void noTasksShouldDisplay(final String filter) {
        todoPage.selectFilter(filter);
        List<String> listOfTasks = todoPage.getListOfTasks();
        if (listOfTasks.size() != 0) {
            LOGGER.error("No Tasks should display under [{}], but [{}] are displaying", filter, listOfTasks);
            throw new CustomException(CustomExceptionType.VERIFICATION_FAILED,
                    "No Tasks should display under [{}], but [{}] are displaying", filter, listOfTasks);
        }
    }

    public void tasksShouldDisplay(final String filter, List<String> tasks) {
        List<String> expectedTasks = tasks.stream()
                .filter(t -> !t.equalsIgnoreCase("task_title"))
                .collect(Collectors.toList());

        todoPage.selectFilter(filter);
        List<String> listOfTasks;

        if ("completed".equalsIgnoreCase(filter)) {
            listOfTasks = todoPage.getListOfCompletedTasks();
        } else {
            listOfTasks = todoPage.getListOfTasks();
        }

        if (!expectedTasks.equals(listOfTasks)) {
            LOGGER.error("All Tasks [{}] should display under [{}], but [{}] are displaying",
                    expectedTasks, filter, listOfTasks);
            throw new CustomException(CustomExceptionType.VERIFICATION_FAILED,
                    "All Tasks [{}] should display under [{}], but [{}] are displaying",
                    expectedTasks, filter, listOfTasks);
        }
    }

    public void verifyClearCompleted() {
        if (!todoPage.isClearCompletedVisible()) {
            LOGGER.error("Clear Completed is not visible on the Todo page");
            throw new CustomException(CustomExceptionType.VERIFICATION_FAILED,
                    "Clear Completed is not visible on the Todo page");
        }
    }

    public void markAllTasksAsComplete() {
        todoPage.markAllTasksAsComplete();
    }


}
