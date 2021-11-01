package seedu.address.model.task.filters;

import java.util.Objects;

class DoneTaskFilter extends TaskFilter {
    DoneTaskFilter() {
        this(false);
    }

    private DoneTaskFilter(boolean isInverted) {
        super(task -> task.isDone() != isInverted, isInverted);
    }

    @Override
    public String toDisplayString() {
        return isInverted ? "Undone" : "Done";
    }

    @Override
    public boolean hasConflictWith(TaskFilter other) {
        return other instanceof DoneTaskFilter;
    }

    @Override
    public DoneTaskFilter invert() {
        return new DoneTaskFilter(!isInverted);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        return (o instanceof DoneTaskFilter)
                && isInverted == ((DoneTaskFilter) o).isInverted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isInverted);
    }
}
