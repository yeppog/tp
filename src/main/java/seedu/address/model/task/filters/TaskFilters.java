package seedu.address.model.task.filters;

import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;

public class TaskFilters {
    public static final TaskFilter FILTER_DONE = new TaskFilter(Task::getIsDone,
        isInverted -> isInverted ? "Undone" : "Done");
    public static final Function<Tag, TaskFilter> FILTER_TAG = tag -> new TaskFilter(
        task -> task.getTags().contains(tag), isInverted -> (isInverted ? "Not tagged " : "Tagged ") + tag);

    public static class TaskFilter {
        private Predicate<Task> predicate;
        private boolean isInverted;
        private Function<Boolean, String> toString;

        private TaskFilter(Predicate<Task> predicate, Function<Boolean, String> toString) {
            this(predicate, toString, false);
        }

        private TaskFilter(Predicate<Task> predicate, Function<Boolean, String> toString, boolean isInverted) {
            this.predicate = predicate;
            this.toString = toString;
            this.isInverted = isInverted;
        }

        /**
         * Returns the predicate used to filter tasks.
         * @return The predicate used to filter tasks
         */
        public Predicate<Task> getPredicate() {
            return this.predicate;
        }

        /**
         * Returns a new task filter that accepts tasks not accepted by this task filter, and vice-versa.
         * @return A new task filter with an inverted predicate
         */
        public TaskFilter invert() {
            return new TaskFilter(predicate.negate(), this.toString, !isInverted);
        }

        @Override
        public String toString() {
            return toString.apply(this.isInverted);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }

            return (o instanceof TaskFilter)
                    && isInverted == ((TaskFilter) o).isInverted
                    && this.toString().equals(o.toString());
        }

        @Override
        public int hashCode() {
            return this.toString.hashCode();
        }
    }
}
