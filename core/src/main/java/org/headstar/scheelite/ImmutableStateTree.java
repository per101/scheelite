package org.headstar.scheelite;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by per on 15/02/14.
 */
public class ImmutableStateTree<T, U> extends AbstractStateTree<T, U> {

    private final Map<State<T, U>, State<T, U>> map;

    public ImmutableStateTree(MutableStateTree<T, U> stateTree) {
        // TODO: use Collections immutable map
        this.map = Maps.newHashMap(stateTree.getMap());
    }

    @Override
    protected Map<State<T, U>, State<T, U>> getMap() {
        return map;
    }
}
