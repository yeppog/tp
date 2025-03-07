---
layout: page
title: Developer Guide
---

- Table of Contents
{:toc}

---

## **Acknowledgements**

- {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

---

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/se-edu/addressbook-level3/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes called [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java). It is responsible for,

- At app launch: Initializes the components in the correct sequence, and connects them up with each other.
- At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it uses the `AddressBookParser` class to parse the user command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

- When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
- All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />

The `Model` component,

- stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
- stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,

- can save both address book data and user preference data in json format, and read them back into corresponding objects.
- inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

---

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

- `VersionedAddressBook#commit()` — Saves the current address book state in its history.
- `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
- `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

- **Alternative 1 (current choice):** Saves the entire address book.

  - Pros: Easy to implement.
  - Cons: May have performance issues in terms of memory usage.

- **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  - Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  - Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_

---

## **Documentation, logging, testing, configuration, dev-ops**

- [Documentation guide](Documentation.md)
- [Testing guide](Testing.md)
- [Logging guide](Logging.md)
- [Configuration guide](Configuration.md)
- [DevOps guide](DevOps.md)

---

## **Appendix: Requirements**

### Product scope

**Target user profile**:
Comprehensive user narrative [here](https://docs.google.com/document/d/1B5kaY-Trws5eFxNEJdF_qdfHmCMzlpPUrZl-F4lkFSo/edit?usp=sharing)

- has a need to manage a significant number of contacts
- prefer desktop apps over other types
- is reasonably comfortable using CLI apps
- is a student
- has a need to manage tasks quickly
- prefers to have a visualization of their productivity

**Value proposition**: allow students to easily keep track of their tasks for modules, school calenders, and meetings in a strealined manner and allow them to use either GUI/CLI options depending on whichever suits them better

### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                                          | I want to …​                                                       | So that I can…​                                         |
| -------- | ---------------------------------------------------------------- | ------------------------------------------------------------------ | ------------------------------------------------------- |
| `* * *`  | student                                                          | add tasks into a tasklist                                          | keep a list of tasks I need to do                       |
| `* * *`  | student                                                          | delete tasks                                                       | remove tasks that are no longer applicable              |
| `* * *`  | student with one laptop                                          | access my data locally                                             | use the application without an internet connection      |
| `* * *`  | new user                                                         | use the GUI                                                        | use the app easily from the desktop                     |
| `* * *`  | student who often makes typos                                    | edit tasks                                                         | correct tasks that were entered wrongly                 |
| `* * *`  | student with similar tasks                                       | create skeleton templates                                          | add tasks without repeating actions                     |
| `* * *`  | student with different commitments                               | categorise tasks using tags                                        | differentiate whcih tasks belongs to which commitment   |
| `* * *`  | student with different commitments                               | overview of my tasks by workload                                   | understand which tasks I need to prioritise             |
| `* * *`  | student who is forgetful                                         | receive reminders                                                  | be reminded for upcoming tasks                          |
| `* * *`  | student who receives periodic assignments                        | create recurring tasks that repeat at a set interval               | save time                                               |
| `* *`    | student with varying type of tasks                               | attach various files or messages                                   | increase convenience of access                          |
| `* *`    | a veteran user                                                   | have CLI to organise my tasks                                      | save time from not having to point and click on the GUI |
| `* *`    | busy student                                                     | be able to add tasks using CLI                                     | save time from not needing to sort my schedule          |
| `* *`    | student that manages multiple projects across different modules  | tag tasks with contacts                                            | know who is participating in which meeting or task      |
| `* *`    | a student who love data and visualisations                       | view previously completed tasks over time                          | gain insights into my producitvity                      |
| `*`      | student with similar tasks                                       | create custom macros                                               | save time                                               |
| `*`      | student that is finishing their semester                         | purge data on tasks and templates                                  | remove clutter on my app                                |
| `*`      | student with many tasks                                          | track progress for tasks                                           | visualise the time required for each task               |
| `*`      | student who has to manage projects with peers                    | create tasks that can be emailed to individuals on my contact list | facilitate quick communication                          |
| `*`      | student who easily gets bored focusing too much on a single task | shuffle the arrangement of upcoming tasks                          | work on another task with similar priority              |
| `*`      | student who may make mistakes                                    | undo my changes                                                    | revert any changes if needed                            |
| `*`      | student who has some tasks of utmost importance                  | designate special tasks that block out a certain time period       | avoid scheduling other tasks that clash with them       |

### Use cases

(For all use cases below, the **System** is the `TaskMaster2103` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: UCP01 - Add a person

##### MSS

1. User requests add a person.

2. TaskMaster2103 shows a list of updated tasks.

   Use case ends.

##### Extensions

- 1a. An invalid field is specified

  - 1a1. TaskMaster2103 shows an error message.

    Use case resumes at step 1.

#### Use case: UCP02 - Edit a person

##### MSS

1. User requests to list persons.

2. TaskMaster2103 shows a list of persons.

3. User requests to edit a specific person in the list with the new values of the fields to be updated.

4. TaskMaster2103 updates the specified fields of the person.

   Use case ends.

##### Extensions

- 2a. The list is empty.

  Use case ends.

- 3a. The given index is invalid.

  - 3a1. TaskMaster2103 shows an error message.

    Use case resumes at step 2.

- 3b1. A field is specified wrongly.

  - 3a1. TaskMaster2103 shows an error message.

    Use case resumes at step 2.

#### Use case: UCP03 - Delete a person

##### MSS

1.  User requests to list persons.
2.  TaskMaster2103 shows a list of persons.
3.  User requests to delete a specific person in the list.
4.  TaskMaster2103 deletes the person.

    Use case ends.

##### Extensions

- 2a. The list is empty.

  Use case ends.

- 3a. The given index is invalid.

  - 3a1. TaskMaster2103 shows an error message.

    Use case resumes at step 2.

#### Use case: UCP04 - Search for a person

##### MSS

1. User requests to search for a list of keywords.

2. TaskMaster2103 shows a list of people whose names match any of the keywords.

   Use case ends.

#### Use case: UCT01 - Add a task

##### MSS

1.  User requests to add a task.
2.  TaskMaster2103 shows an updated list of tasks.

    Use case ends.

##### Extensions

- 1a. The task does not contain a title.

  - 1a1. TaskMaster2103 shows an error message.

    Use case resumes at step 1.

#### Use case: UCT02 - Edit a task

##### MSS

1.  User requests to list tasks.
2.  TaskMaster2103 shows a list of tasks.
3.  User requests to edit a specific task in the list.
4.  TaskMaster2103 edits the task.

    Use case ends.

##### Extensions

- 2a. The list is empty.

  Use case ends.

- 3a. The given index is invalid.

  - 3a1. TaskMaster2103 shows an error message.

    Use case resumes at step 2.

#### Use case: UCT03 - Delete a task

##### MSS

1.  User requests to list tasks.
2.  TaskMaster2103 shows a list of tasks.
3.  User requests to delete a specific tasks in the list.
4.  TaskMaster2103 deletes the person.

    Use case ends.

##### Extensions

- 2a. The list is empty.

  Use case ends.

- 3a. The given index is invalid.

  - 3a1. TaskMaster2103 shows an error message.

    Use case resumes at step 2.

_{More to be added}_

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to work on both 32-bit and 64-bit environments.
3.  A user with above average typing speed of 60 words per minute for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4.  The system should respond within 1 second to any (valid or invalid) command given via typing or GUI.
5.  The system will store the data in a JSON file locally, and will not have a server component.
6.  Older versions of locally-stored data should be compatible with newer versions of the app.
7.  The system should load the locally-stored data within 1 second when the app starts up.
8.  The app should save any changes to the data immediately when the command is processed, to avoid possible data loss.
9.  The app should only read the JSON file for the locally-stored list, and nothing else.
10. The locally-stored data should not exceed 5GB in size, for tasks, tags and templates.

### Glossary

- **Mainstream OS**: Windows, Linux, Unix, OS-X
- **Locally-stored data**: Data stored under ./data/addressbook.json within the same folder\
- **Given index (of a task)**: The location of the task in the tasklist. First task is **1**, and increases in ascending order.
- **Templates**: User-defined preset format for a task
- **Tags**: User-defined label attached a task to provide identification and/or information

---

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
      Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
