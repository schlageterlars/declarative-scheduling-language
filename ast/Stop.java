package ast;

public class Stop {
    private final Place place;
    private final Duration duration;

    public Stop(Place place, Duration duration) {
        this.place = place;
        this.duration = duration;
    }

    @Override
    public String toString() {
        return duration + "\t" + place.toString();
    }
}
