package seedu.address.logic.guiactions;

import seedu.address.model.AddressBook;
import seedu.address.model.TaskList;

@FunctionalInterface
public interface GuiAction {
    void executeWith(AddressBook addressBook, TaskList taskList);
}
