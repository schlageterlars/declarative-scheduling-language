package model;

public interface Visitor {

    default void visitSchedule(Schedule schedule) {
        if (schedule.getSequences() != null) {
            for (Sequence sequence : schedule.getSequences()) {
                sequence.accept(this);
            }
        }
    }


    default void visitSequence(Sequence sequence) {
        // Besuche das TimeRange-Objekt
        if (sequence.getTimeRange() != null) {
            sequence.getTimeRange().accept(this);
        }
        // Besuche alle Stop-Objekte
        if (sequence.getStops() != null) {
            for (Stop stop : sequence.getStops()) {
                stop.accept(this);
            }
        }
    }

    default void visitTimeRange(TimeRange timeRange) {}

    default void visitStop(Stop stop) {}
}