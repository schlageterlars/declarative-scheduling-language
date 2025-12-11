package model;

public class Stop implements Visitable {
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

    @Override
    public void accept(Visitor visitor) {
        visitor.visitStop(this);
    }

    public Place getPlace() {
        return this.place;
    }

    public Duration geDuration() {
        return this.duration;
    }
}
