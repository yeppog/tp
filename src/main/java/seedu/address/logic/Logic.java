package seedu.address.logic;

import java.nio.file.Path;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.model.task.filters.TaskFilter;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Executes the command and returns the result.
     * @param command The command to execute.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     */
    CommandResult executeCommand(Command command) throws CommandException;

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
     * Returns a list of available task filters.
     * @return The list of available task filters
     */
    ObservableList<TaskFilter> getSelectableTaskFilters();

    /**
     * Returns the list of selected filters to filter tasks by.
     * @return The list of selected filters to filter tasks by
     */
    ObservableList<TaskFilter> getSelectedTaskFilters();

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns the task list.
     */
    ReadOnlyTaskList getTaskList();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of tasks */
    ObservableList<Task> getFilteredTaskList();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Executes an undo command.
     * @return The command result of the command being undone.
     */
    CommandResult undoCommand();

    /**
     * Executes a redo command.
     * @return The command result of the command being redone.
     */
    CommandResult redoCommand();

    /**
     * Get the next or previous command in the command history.
     *
     * @param isNext produces the next command in the stack if true, else the previous
     * @param currentString the string to be cached
     * @return string representation of the executed command in order.
     */
    String getHistoryCommand(boolean isNext, String currentString);

    /**
     * Resets the history position to the top of the stack
     */
    void resetHistoryPosition();

}
