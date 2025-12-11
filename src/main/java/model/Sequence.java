package model;

import java.util.List;

public class Sequence implements Visitable {
    private final String id;
    private final TimeRange timeRange;
    private final List<Stop> stops;

    public Sequence(String id, TimeRange timeRange, List<Stop> stops) {
        this.id = id;
        this.timeRange = timeRange;
        this.stops = stops;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" ").append(timeRange).append(":\n");
        for (Stop seg : stops) {
            sb.append("  ").append(seg).append("\n");
        }
        return sb.toString();
    }

    public String getId() {
        return this.id;
    }

    public String getEscapedId(){
        return this.id.replaceAll("[<>]", "");
    } 

    public TimeRange getTimeRange() {
        return this.timeRange;
    }

    public List<Stop> getStops() {
        return this.stops;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitSequence(this);
    }

}
