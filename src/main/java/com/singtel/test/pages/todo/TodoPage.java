package com.singtel.test.pages.todo;

import com.singtel.test.utils.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TodoPage {

    private static final Logger LOGGER = LoggerFactory.getLogger(TodoPage.class);

    public static final String TODOMVC_URL = "https://todomvc.com/examples/vue/#/all";

    private static final String NEW_TODO_LOCATOR = "xpath://input[@placeholder='What needs to be done?']";
    private static final String LIST_OF_TODOS_LOCATOR = "xpath://ul[@class='todo-list']";
    private static final String TODO_COUNT_LOCATOR = "xpath://span[@class='todo-count']";
    private static final String TODO_FILTERS_LOCATOR = "xpath://ul[@class='filters']//a[text()='%s']";

    private static final String TASK_LOCATOR = "xpath://ul[@class='todo-list']//label[text()='%s']";
    private static final String EDIT_TASK_LOCATOR = "xpath://ul[@class='todo-list']//label[text()='%s']/../../input[@class='edit']";
    private static final String TASK_DELETE_LOCATOR = "xpath://ul[@class='todo-list']//label[text()='%s']/../button[@class='destroy']";
    private static final String TASK_COMPLETE_LOCATOR = "xpath://ul[@class='todo-list']//label[text()='%s']/../input[@class='toggle']";
    private static final String CLEAR_COMPLETED_LOCATOR = "xpath://button[@class='clear-completed']";
    private static final String MARK_ALL_TASKS_AS_COMPLETE = "xpath://label[text()='Mark all as complete']";

    @Autowired
    private DriverUtils driverUtils;

    @Autowired
    private WebElementUtils webElementUtils;

    @Autowired
    private WebTasksUtils webTasksUtils;

    @Autowired
    private FormatterUtils formatterUtils;

    @Autowired
    private ThreadUtils threadUtils;

    public void launchTodoApp() {
        driverUtils.launchURL(TODOMVC_URL);
        By by = webElementUtils.getByReference(NEW_TODO_LOCATOR);
        driverUtils.waitTillVisible(by, DriverUtils.DEFAULT_EXPLICIT_WAIT_SECONDS);
    }

    public void addTask(String title) {
        LOGGER.debug("Adding task [{}]", title);
        By by = webElementUtils.getByReference(NEW_TODO_LOCATOR);
        WebElement webElement = driverUtils.findElement(by, DriverUtils.DEFAULT_EXPLICIT_WAIT_SECONDS);
        webTasksUtils.enterText(webElement, title, "ENTER");
    }

    public void updateTask(final String oldTitle, final String newTitle) {
        LOGGER.debug("Updating task [{}] with [{}]", oldTitle, newTitle);

        final String taskLocator = formatterUtils.format(TASK_LOCATOR, oldTitle);
        By taskBy = webElementUtils.getByReference(taskLocator);
        WebElement taskElement = driverUtils.findElement(taskBy, DriverUtils.DEFAULT_EXPLICIT_WAIT_SECONDS);

        Actions actions = new Actions(driverUtils.getDriver());
        actions.doubleClick(taskElement).build().perform();

        threadUtils.sleepSeconds(1);

        By taskEditBy = webElementUtils.getByReference("xpath://input[@type='text']");
        WebElement taskEditElement = driverUtils.findElement(taskEditBy, DriverUtils.DEFAULT_EXPLICIT_WAIT_SECONDS);

        actions.click(taskEditElement)
                .doubleClick(taskEditElement)
                .build()
                .perform();

        threadUtils.sleepMillis(100);
        taskEditElement.sendKeys(newTitle);
        taskEditElement.sendKeys(Keys.ENTER);
    }

    public List<String> getListOfTasks() {
        By listOfTodos = webElementUtils.getByReference(LIST_OF_TODOS_LOCATOR);
        WebElement todoParentElement = driverUtils.findElement(listOfTodos, 1);
        List<WebElement> todos = todoParentElement.findElements(By.xpath("//li[@class='todo']"));

        List<String> result = todos.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        LOGGER.debug("List of tasks [{}]", result);
        return result;
    }

    public List<String> getListOfCompletedTasks() {
        By listOfTodos = webElementUtils.getByReference(LIST_OF_TODOS_LOCATOR);
        WebElement todoParentElement = driverUtils.findElement(listOfTodos, 1);
        List<WebElement> todos = todoParentElement.findElements(By.xpath("//li[@class='todo completed']"));

        List<String> result = todos.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());

        LOGGER.debug("List of tasks [{}]", result);
        return result;
    }


    public String getTodoCountLabel() {
        By todoCountBy = webElementUtils.getByReference(TODO_COUNT_LOCATOR);
        List<WebElement> todoCountLabel = driverUtils.findElements(todoCountBy, DriverUtils.DEFAULT_EXPLICIT_WAIT_SECONDS);

        return todoCountLabel.size() > 0
                ? todoCountLabel.get(0).getAttribute("innerText")
                : "";
    }

    public boolean isFilterVisible(final String filter) {
        LOGGER.debug("Checking for [{}] filter on the Todo page", filter);
        final String allXpath = formatterUtils.format(TODO_FILTERS_LOCATOR, filter);
        By by = webElementUtils.getByReference(allXpath);
        List<WebElement> elements = driverUtils.findElements(by, DriverUtils.DEFAULT_EXPLICIT_WAIT_SECONDS);
        return elements.size() > 0 && elements.get(0).isDisplayed();
    }

    private WebElement getClearCompletedElement() {
        By by = webElementUtils.getByReference(CLEAR_COMPLETED_LOCATOR);
        return driverUtils.findElement(by, DriverUtils.DEFAULT_EXPLICIT_WAIT_SECONDS);
    }

    public boolean isClearCompletedVisible() {
        return getClearCompletedElement().isDisplayed();
    }

    public void clearCompletedTasks() {
        getClearCompletedElement().click();
    }

    public void markAllTasksAsComplete() {
        By by = webElementUtils.getByReference(MARK_ALL_TASKS_AS_COMPLETE);
        WebElement element = driverUtils.findElement(by, DriverUtils.DEFAULT_EXPLICIT_WAIT_SECONDS);
        element.click();
    }

    public boolean isAllFilterSelected() {
        final String allXpath = formatterUtils.format(TODO_FILTERS_LOCATOR, "All");
        By allBy = webElementUtils.getByReference(allXpath);
        WebElement element = driverUtils.findElement(allBy, DriverUtils.DEFAULT_EXPLICIT_WAIT_SECONDS);
        String classAttribute = element.getAttribute("class");
        LOGGER.debug("All filter status [{}]", classAttribute);
        return classAttribute.equalsIgnoreCase("selected");
    }


    public void selectFilter(final String filter) {
        LOGGER.debug("Selecting [{}] filter on the Todo page", filter);
        final String allXpath = formatterUtils.format(TODO_FILTERS_LOCATOR, filter);
        By by = webElementUtils.getByReference(allXpath);
        WebElement element = driverUtils.findElement(by, DriverUtils.DEFAULT_EXPLICIT_WAIT_SECONDS);
        element.click();
    }

    public void deleteTask(final String title) {
        LOGGER.debug("Deleting [{}] task", title);
        final String taskDeleteLocator = formatterUtils.format(TASK_DELETE_LOCATOR, title);
        final String taskLocator = formatterUtils.format(TASK_LOCATOR, title);

        By taskBy = webElementUtils.getByReference(taskLocator);
        WebElement taskElement = driverUtils.findElement(taskBy, DriverUtils.DEFAULT_EXPLICIT_WAIT_SECONDS);
        Actions actions = new Actions(driverUtils.getDriver());
        actions.moveToElement(taskElement).build().perform();

        threadUtils.sleepMillis(500L);

        By deleteBy = webElementUtils.getByReference(taskDeleteLocator);
        WebElement deleteElement = driverUtils.findElement(deleteBy, DriverUtils.DEFAULT_EXPLICIT_WAIT_SECONDS);
        deleteElement.click();
    }


    private void clickOnCompleteTaskCheckBox(final String title) {
        final String taskCompleteLocator = formatterUtils.format(TASK_COMPLETE_LOCATOR, title);
        By completeBy = webElementUtils.getByReference(taskCompleteLocator);
        WebElement completeElement = driverUtils.findElement(completeBy, DriverUtils.DEFAULT_EXPLICIT_WAIT_SECONDS);
        completeElement.click();
    }

    public void completeTask(final String title) {
        LOGGER.debug("Completing [{}] task", title);
        clickOnCompleteTaskCheckBox(title);
    }

    public void undoCompletedTask(final String title) {
        LOGGER.debug("Undo completed [{}] task", title);
        clickOnCompleteTaskCheckBox(title);
    }


}
