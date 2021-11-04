package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.model.task.filters.TaskFilter;
import seedu.address.storage.CommandHistory;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<? super Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<? super Person> predicate);

    /**
     * Gets the current predicate of the filtered person list.
     * @return The predicate of the filtered person list.
     */
    Predicate<? super Person> getFilteredPersonPredicate();


    /**
     * Deletes the given task. The task must exist in the task list.
     * @param deletedTask The task to delete.
     */
    void deleteTask(Task deletedTask);

    /**
     * Deletes the task at index. Bypass due to object inequality.
     */
    void deleteTaskAtLastIndex();

    /**
     * Deletes the list of Filtered Tasks and their filters.
     */
    void deleteAllInFilteredTaskList();

    /**
     * Adds the given task to the task list.
     * @param task The task to be added.
     */
    void addTask(Task task);

    /**
     * Adds the given to the task list at a given index
     * @param task The task to be added.
     * @param index The index to insert the task at.
     */
    void insertTask(Task task, int index);

    /**
     * Checks the index of the given task in the task list.
     * @param task Task to check the index of.
     * @return The 0-based index of the task.
     */
    int indexOf(Task task);

    /** Returns an unmodifiable view of the task list */
    ObservableList<Task> getFilteredTaskList();


    /**
     * Returns a list of selectable task filters.
     * @return The list of selectable task filters.
     */
    ObservableList<TaskFilter> getSelectableTaskFilters();

    /**
     * Returns the list of selected filters to filter tasks by.
     * @return The list of selected filters to filter tasks by
     */
    ObservableList<TaskFilter> getSelectedTaskFilters();

    /**
     * Adds a task filter to the list of selected filters.
     * @param taskFilter The task filter to add
     */
    void addTaskFilter(TaskFilter taskFilter);

    /**
     * Removes a task filter from the list of selected filters.
     * @param taskFilter The task filter to remove
     */
    void removeTaskFilter(TaskFilter taskFilter);

    /**
     * Sets the list of selected task filters.
     * @param taskFilters The list of task filters to filter by
     */
    void setTaskFilters(List<TaskFilter> taskFilters);

    /**
     * Gets the list of previously selected task filters.
     * @return The list of old task filters.
     */
    List<TaskFilter> getOldTaskFilters();

    /**
     * Updates the filter of the filtered task list to filter by the given {@code predicate}.
     * @param predicate The new predicate to filter by
     */
    void updateFilteredTaskList(Predicate<Task> predicate);

    /**
     * Retrieves the current predicate from the filtered task list.
     * @return The predicate of the current filtered task list.
     */
    Predicate<? super Task> getFilteredTaskPredicate();

    /** Returns the TaskList */
    ReadOnlyTaskList getTaskList();

    /**
     * Replaces the given task {@code target} with {@code editedTask}.
     * {@code target} must exist in the task list.
     * setTask uses targetIndex rather than target Person; This is because tasks may not be unique, unlike persons
     *
     * @return The edited task with updated contact information
     */
    Task setTask(Task target, Task editedTask);

    /**
     * Returns the command history instance.
     *
     * @return Command history instance.
     */
    CommandHistory getCommandHistory();

    /**
     * Get the next or previous command in the command history.
     *
     * @param isNext produces the next command in the stack if true, else the previous
     * @param currentString the string to be cached
     * @return string representation of the executed command in order.
     */
    String getHistoryCommand(boolean isNext, String currentString);

    /**
     * Adds a command to the the history stack
     *
     * @param command String representation of the executed command
     */
    void addCommandToHistory(String command);

    /**
     * Resets the history position to the top of the stack
     */
    void resetHistoryPosition();
}
