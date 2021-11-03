package seedu.address.logic.commands.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_INTERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.DESC_REPORT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_INTERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CAREER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESTAMP_INTERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_INTERVIEW;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.task.EditTaskCommand.EditTaskDescriptor;
import seedu.address.testutil.EditTaskDescriptorBuilder;

public class EditTaskDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditTaskDescriptor descriptorWithSameValues = new EditTaskDescriptor(DESC_REPORT);
        assertTrue(DESC_REPORT.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_REPORT.equals(DESC_REPORT));

        // null -> returns false
        assertFalse(DESC_REPORT.equals(null));

        // different types -> returns false
        assertFalse(DESC_REPORT.equals(5));

        // different values -> returns false
        assertFalse(DESC_REPORT.equals(DESC_INTERVIEW));

        // different title -> returns false
        EditTaskDescriptor editedReport = new EditTaskDescriptorBuilder(DESC_REPORT)
                .withTitle(VALID_TITLE_INTERVIEW).build();
        assertFalse(DESC_REPORT.equals(editedReport));

        // different report -> returns false
        editedReport = new EditTaskDescriptorBuilder(DESC_REPORT)
                .withDescription(VALID_DESCRIPTION_INTERVIEW).build();
        assertFalse(DESC_REPORT.equals(editedReport));

        // different timestamp -> returns false
        editedReport = new EditTaskDescriptorBuilder(DESC_REPORT)
                .withTimestamp(VALID_TIMESTAMP_INTERVIEW).build();
        assertFalse(DESC_REPORT.equals(editedReport));

        // different tags -> returns false
        editedReport = new EditTaskDescriptorBuilder(DESC_REPORT)
                .withTags(VALID_TAG_CAREER).build();
        assertFalse(DESC_REPORT.equals(editedReport));

        // different contacts -> returns false
        editedReport = new EditTaskDescriptorBuilder(DESC_REPORT)
                .withContacts(VALID_NAME_AMY).build();
        assertFalse(DESC_REPORT.equals(editedReport));
    }
}
