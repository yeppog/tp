package seedu.address.model.task;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

public class TaskContainsKeywordsPredicate implements Predicate<Task> {
    private final List<String> keywords;

    public TaskContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Task task) {
        String taskDescription = task.getDescription() == null ? "" : task.getDescription();
        return keywords.stream()
                .anyMatch(keyword -> (StringUtil.containsWordIgnoreCase(task.getTitle(), keyword)
                || StringUtil.containsWordIgnoreCase(taskDescription, keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TaskContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((TaskContainsKeywordsPredicate) other).keywords)); // state check
    }
}
