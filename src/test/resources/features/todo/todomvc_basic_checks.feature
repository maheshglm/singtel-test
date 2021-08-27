@todo_management
Feature: Manage Todo List with TodoMVC App

  In order to remember my daily tasks,
  As a ToDo MVC user,
  I want to manage my todo list using TodoMVC app

  Background: Open the Todo application

    Given TodoApp is open

  Scenario: User should be able to add a task

    When I add below task
      | task_title     |
      | Coffee at 4 am |

    Then I expect below tasks should get added successfully
      | task_title     |
      | Coffee at 4 am |

    And I expect todo count label "1 item left" is visible
    And I expect "All" filter is visible
    And I expect "Active" filter is visible
    And I expect "Completed" filter is visible

    And I expect All filter is selected by default

    But No tasks should be displayed in "Completed" section

  Scenario: User should be able to edit a task

    When I add below task
      | task_title        |
      | Breakfast at 8 am |

    Then I update task title "Breakfast at 8 am" as "Breakfast at 8:30 am"

    Then I expect below tasks should get updated successfully
      | task_title           |
      | Breakfast at 8:30 am |

    And below tasks should display in "Active" section
      | task_title           |
      | Breakfast at 8:30 am |

  Scenario: User should be able to delete a task

    When I add below task
      | task_title |
      | Tasks1     |

    Then I delete below task
      | task_title |
      | Tasks1     |

    And I expect todo count label "1 item left" is not visible
    And I expect "Active" filter is not visible
    And I expect "Completed" filter is not visible

  Scenario: User should be able to add multiple tasks

    When I add below tasks
      | task_title |
      | Task2      |
      | Task3      |

    Then I expect below tasks should get added successfully
      | task_title |
      | Task2      |
      | Task3      |

    And I expect todo count label "2 items left" is visible
    And I expect All filter is selected by default

    And below tasks should display in "Active" section
      | task_title |
      | Task2      |
      | Task3      |

    #This verifies after selecting "Active" section
    And I expect todo count label "2 items left" is visible

  Scenario: User should be able to complete a task

    When I add below tasks
      | task_title |
      | Task4      |

    And I complete below tasks
      | task_title |
      | Task4      |

    Then I expect todo count label "0 items left" is visible
    And I expect Clear completed is visible

    And No tasks should be displayed in "Active" section
    And below tasks should display in "Completed" section
      | task_title |
      | Task4      |

  Scenario: Clear Completed action when single task exist

    When I add below tasks
      | task_title |
      | Task4      |

    And I complete below tasks
      | task_title |
      | Task4      |

    And I clear completed tasks

    And I expect todo count label "1 item left" is not visible
    And I expect "Active" filter is not visible
    And I expect "Completed" filter is not visible

  Scenario: Clear Completed action when multiple task exist

    When I add below tasks
      | task_title |
      | Task4      |
      | Task5      |

    And I complete below tasks
      | task_title |
      | Task4      |

    And I clear completed tasks

    Then I expect todo count label "1 item left" is visible

    And below tasks should display in "Active" section
      | task_title |
      | Task5      |

    And No tasks should be displayed in "Completed" section

  Scenario: Complete All tasks (Mark all as complete)

    When I add below tasks
      | task_title |
      | Task4      |
      | Task5      |

    Then I mark all tasks as complete

    Then I expect todo count label "0 items left" is visible

    And No tasks should be displayed in "Active" section

    And below tasks should display in "Completed" section
      | task_title |
      | Task4      |
      | Task5      |

  Scenario: Undo Completed task

    When I add below tasks
      | task_title |
      | Task4      |

    And I complete below tasks
      | task_title |
      | Task4      |

    Then I expect todo count label "0 items left" is visible

    And No tasks should be displayed in "Active" section

    And below tasks should display in "Completed" section
      | task_title |
      | Task4      |

    When I undo below completed tasks
      | task_title |
      | Task4      |

    Then I expect todo count label "1 item left" is visible

    And No tasks should be displayed in "Completed" section

    And below tasks should display in "All" section
      | task_title |
      | Task4      |

    And below tasks should display in "Active" section
      | task_title |
      | Task4      |