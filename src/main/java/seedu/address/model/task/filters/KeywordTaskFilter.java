package seedu.address.model.task.filters;

import java.util.Objects;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskContainsKeywordsPredicate;

public class KeywordTaskFilter extends TaskFilter {
    private static final int MAX_KEYWORDS_LENGTH = 35;
    private final String keywords;

    KeywordTaskFilter(TaskContainsKeywordsPredicate predicate) {
        this(predicate, predicate.getKeywords(), false);
    }

    private KeywordTaskFilter(
        Predicate<Task> predicate,
        String keywords,
        boolean isInverted
    ) {
        super(predicate, isInverted);
        this.keywords = keywords;
    }

    @Override
    public TaskFilter invert() {
        return new KeywordTaskFilter(predicate.negate(), this.keywords, !isInverted);
    }

    @Override
    public String toDisplayString() {
        return (this.isInverted ? "Without " : "Contains ")
                + StringUtil.limitString(keywords, "...", KeywordTaskFilter.MAX_KEYWORDS_LENGTH);
    }

    @Override
    public boolean hasConflictWith(TaskFilter other) {
        return other instanceof KeywordTaskFilter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeywordTaskFilter that = (KeywordTaskFilter) o;
        return keywords.equals(that.keywords) && isInverted == that.isInverted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(keywords, isInverted);
    }
}
