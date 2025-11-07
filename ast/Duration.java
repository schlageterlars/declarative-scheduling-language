package ast;

public class Duration {
    private final String text;

    public Duration(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}