package org.headstar.scheelite.test;

import com.google.common.base.Optional;
import org.headstar.scheelite.*;
import org.testng.annotations.BeforeMethod;

/**
 * Created by Per on 2014-01-16.
 */
public class TestBase {
    enum StateId {A, B, C, D, E}

    protected class TestEntity {

        private StateId state;

        TestEntity() {
            this(StateId.A);
        }

        TestEntity(StateId state) {
            this.state = state;
        }

        public StateId getStateId() {
            return state;
        }

        @Override
        public String toString() {
            return "TestEntity{" +
                    "state=" + state +
                    '}';
        }
    }

    protected StateMachineBuilder<TestEntity, StateId> builder;

    @BeforeMethod
    public void setup() {
        builder = StateMachineBuilder.<TestEntity, StateId>newBuilder();
    }

    protected class TestAction implements Action<TestEntity> {

        @Override
        public String getName() {
            return "testAction";
        }

        @Override
        public void execute(TestEntity entity, Optional<?> event) {

        }

    }

    protected class TestInitialAction implements InitialAction<TestEntity> {

        @Override
        public String getName() {
            return "TestInitialAction";
        }

        @Override
        public void execute(TestEntity entity) {

        }

    }

    protected class AlwaysAcceptTestGuard extends TestGuard {

        public AlwaysAcceptTestGuard() {
            super(true);
        }

    }

    protected class AlwaysDenyTestGuard extends TestGuard {

        public AlwaysDenyTestGuard() {
            super(false);
        }

    }

    protected class TestGuard implements Guard<TestEntity> {
        private final boolean accept;

        @Override
        public String toString() {
            return getClass().getSimpleName();
        }

        public TestGuard(boolean accept) {
            this.accept = accept;
        }

        public TestGuard() {
            this(true);
        }


        @Override
        public boolean apply(GuardArgs<TestEntity> input) {
            return input.getEvent().isPresent() && accept;
        }
    }

    enum HandleEvent { YES, NO };

    protected class TestState extends StateAdapter<TestEntity, StateId> {

        private final StateId id;
        private final HandleEvent handleEvent;

        TestState(StateId id) {
            this(id, HandleEvent.YES);
        }

        TestState(StateId id, HandleEvent handleEvent) {
            this.id = id;
            this.handleEvent = handleEvent;
        }

        @Override
        public boolean onEvent(TestEntity entity, Object event) {
            return handleEvent.equals(HandleEvent.YES);
        }

        @Override
        public StateId getId() {
            return id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestState testState = (TestState) o;

            if (id != testState.id) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return id != null ? id.hashCode() : 0;
        }

        @Override
        public String toString() {
            return "TestState{" +
                    "id=" + id +
                    "}";
        }


    }

    protected class TestEventX {

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("TestEventX");
            return sb.toString();
        }
    }
}