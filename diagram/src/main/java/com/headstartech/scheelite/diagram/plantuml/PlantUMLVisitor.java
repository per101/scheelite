package com.headstartech.scheelite.diagram.plantuml;

import com.google.common.base.Optional;
import com.headstartech.scheelite.*;
import com.headstartech.scheelite.diagram.DiagramLabelProducer;
import com.headstartech.scheelite.diagram.StateTreeVisitor;

import java.io.PrintWriter;

/**
 * Created by per on 8/12/15.
 */
class PlantUMLVisitor<T, U> implements StateTreeVisitor<T, U> {

    private final PrintWriter printWriter;
    private final DiagramLabelProducer diagramLabelProducer;
    int depth = 0;

    public PlantUMLVisitor(PrintWriter printWriter, DiagramLabelProducer diagramLabelProducer) {
        this.printWriter = printWriter;
        this.diagramLabelProducer = diagramLabelProducer;
    }

    @Override
    public void visitStateStart(State<T, U> state) {
        if (depth > 0) {
            printWriter.println();
            printIndentation();
            printWriter.println(buildStateStart(state));
        } else {
            printWriter.println("@startuml");
        }
        depth++;
    }

    @Override
    public void visitTransition(Transition<T, U> t) {
        printIndentation();
        if (t.getTransitionType().equals(TransitionType.INITIAL)) {
            printWriter.println(buildInitialTransition(t.getMainTargetState()));
        } else {
            printWriter.println(buildTransition(t));
        }
    }

    @Override
    public void visitStateEnd(State<T, U> state) {
        depth--;
        printIndentation();
        if (depth > 0) {
            printWriter.println(buildStateEnd());
            if(state instanceof FinalState) {
                printWriter.println(buildFinalTransition(state));
            }
        } else {
            printWriter.println("@enduml");
        }
    }

    private void printIndentation() {
        for(int i=0; i<depth-1; ++i) {
            printWriter.print("    ");
        }
    }

    private <T, U> String buildStateStart(State<T, U> state) {
        return String.format("state %s {", getStateLabel(state));
    }

    private <T, U> String buildStateEnd() {
        return String.format("}");
    }

    private <T, U> String buildTransition(Transition<T, U> transition) {
        return buildTransition(getStateLabel(transition.getMainSourceState()), getStateLabel(transition.getMainTargetState()), transition.getTriggerEventClass(),
                transition.getGuard());
    }

    private <T, U> String buildInitialTransition(State<T, U> target) {
        return buildTransition("[*]", getStateLabel(target));
    }

    private <T, U> String buildFinalTransition(State<T, U> source) {
        return buildTransition(getStateLabel(source), "[*]");
    }

    private String buildTransition(String source, String target) {
        return buildTransition(source, target, Optional.<Class<?>>absent());
    }

    private String buildTransition(String source, String target, Optional<Class<?>> triggerEventClass) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s --> %s", source, target));
        if(triggerEventClass.isPresent()) {
            String eventLabel = getEventLabel(triggerEventClass.get());
            if(eventLabel != null) {
                sb.append(String.format(" : %s", eventLabel));
            }
        }
        return sb.toString();
    }

    private String buildTransition(String source, String target, Optional<Class<?>> triggerEventClass, Optional<? extends Guard<?>> guard) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s --> %s", source, target));
        if(triggerEventClass.isPresent()) {
            String eventLabel = getEventLabel(triggerEventClass.get());
            if(eventLabel != null) {
                sb.append(String.format(" : %s", eventLabel));
            }
        }
        if(guard.isPresent()) {
            String guardLabel = getGuardLabel(guard.get());
            if(guardLabel != null) {
                sb.append(String.format(" [%s]", guardLabel));
            }
        }
        return sb.toString();
    }

    private <T, U> String getStateLabel(State<T, U> state) {
        return diagramLabelProducer.getLabelForState(state);
    }

    private <T, U> String getEventLabel(Class<?> triggerEventClass) {
        return diagramLabelProducer.getLabelForTriggerEvent(triggerEventClass);
    }

    private String getGuardLabel(Guard<?> guard) {
        return diagramLabelProducer.getLabelForGuard(guard);
    }

}
