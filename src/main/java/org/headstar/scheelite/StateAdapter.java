package org.headstar.scheelite;

public abstract class StateAdapter<T, U> extends AbstractState<T, U> {

    @Override
    public void onEntry(T entity) {
        // do nothing
    }

    @Override
    public void onExit(T entity) {
        // do nothing
    }

    @Override
    public void onEvent(T entity, Object event) {
        // do nothing
    }
}
