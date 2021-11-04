package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyTaskList;
import seedu.address.model.person.Person;
import seedu.address.model.task.Task;
import seedu.address.model.task.filters.TaskFilter;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        Command command = addressBookParser.parse(commandText);
        // If successfully parsed, add to history
        model.addCommandToHistory(commandText);
        return executeCommand(command);
    }

    @Override
    public CommandResult executeCommand(Command command) throws CommandException {
        CommandResult commandResult = command.execute(model);

        if (command instanceof UndoableCommand) {
            model.getCommandHistory().pushCommand((UndoableCommand) command);
        }

        try {
            storage.saveAddressBook(model.getAddressBook());
            storage.saveTaskList(model.getTaskList());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public void addTaskFilter(TaskFilter taskFilter) {
        model.addTaskFilter(taskFilter);
    }

    @Override
    public void removeTaskFilter(TaskFilter taskFilter) {
        model.removeTaskFilter(taskFilter);
    }

    @Override
    public void setTaskFilters(List<TaskFilter> taskFilters) {
        model.setTaskFilters(taskFilters);
    }

    @Override
    public ObservableList<TaskFilter> getSelectableTaskFilters() {
        return model.getSelectableTaskFilters();
    }

    @Override
    public ObservableList<TaskFilter> getSelectedTaskFilters() {
        return model.getSelectedTaskFilters();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ReadOnlyTaskList getTaskList() {
        return model.getTaskList();
    }

    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return model.getFilteredTaskList();
    }

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public CommandResult undoCommand() {
        try {
            return executeCommand(new UndoCommand());
        } catch (CommandException e) {
            return new CommandResult(e.getMessage());
        }
    }

    @Override
    public CommandResult redoCommand() {
        try {
            return executeCommand(new RedoCommand());
        } catch (CommandException e) {
            return new CommandResult(e.getMessage());
        }
    }

    @Override
    public String getHistoryCommand(boolean isNext, String currentString) {
        return model.getHistoryCommand(isNext, currentString);
    }

    @Override
    public void resetHistoryPosition() {
        model.resetHistoryPosition();
    }
}
