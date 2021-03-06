package com.headstartech.scheelite.guards;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.headstartech.scheelite.Guard;

import java.util.List;

/**
 * Logical AND guard.
 */
public class AndGuard<T> implements Guard<T> {

    private static final Joiner COMMA_JOINER = Joiner.on(',');

    private final List<? extends Guard<? super T>> components;

    public AndGuard(List<? extends Guard<? super T>> components) {
        this.components = components;
    }

    @Override
    public boolean evaluate(T context, Optional<?> event) throws Exception {
        for (int i = 0; i < components.size(); i++) {
            if (!components.get(i).evaluate(context, event)) {
                return false;
            }
        }
        return true;
    }

    public List<? extends Guard<? super T>> getComponents() {
        return Lists.newArrayList(components);  // defensive copy
    }

    @Override
    public int hashCode() {
        // add a random number to avoid collisions with OrGuard
        return components.hashCode() + 0x12472c2c;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AndGuard) {
            AndGuard<?> that = (AndGuard<?>) obj;
            return components.equals(that.components);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Guards.and(" + COMMA_JOINER.join(components) + ")";
    }
}
