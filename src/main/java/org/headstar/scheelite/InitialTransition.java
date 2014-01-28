package org.headstar.scheelite;

import com.google.common.base.Optional;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Per on 2014-01-24.
 */
public class InitialTransition<T, U> {

    private final U fromState;
    private final U toState;
    private final Optional<? extends InitialAction<T>> action;
    private final String name;

    public InitialTransition(U fromState, U toState, Optional<? extends InitialAction<T>> action) {
        this.fromState = checkNotNull(fromState);
        this.toState = checkNotNull(toState);
        this.action = checkNotNull(action);
        this.name = createName();
    }
    public InitialTransition(U fromState, U toState) {
        this(fromState, toState, Optional.<InitialAction<T>>absent());
    }

    public String getName() {
        return name;
    }

    protected String createName() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s-TO-%s", fromState, toState));
        return sb.toString();
    }

    public U getToState() {
        return toState;
    }

    public U getFromState() {
        return fromState;
    }

    public Optional<? extends InitialAction<T>> getAction() {
        return action;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InitialTransition that = (InitialTransition) o;

        if (!action.equals(that.action)) return false;
        if (!fromState.equals(that.fromState)) return false;
        if (!toState.equals(that.toState)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fromState.hashCode();
        result = 31 * result + toState.hashCode();
        result = 31 * result + action.hashCode();
        return result;
    }
}