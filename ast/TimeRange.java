package ast;

public class TimeRange {
    private final String text;

    public TimeRange(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
