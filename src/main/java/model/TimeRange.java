package model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeRange implements Visitable {
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
    private final String text;

    public TimeRange(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    public LocalTime[] toLocalTimes() {
        String rangeText = text.trim();

        // Entferne eckige Klammern
        if (rangeText.startsWith("[") && rangeText.endsWith("]")) {
            rangeText = rangeText.substring(1, rangeText.length() - 1).trim();
        }

        // Teile Start- und Endzeit
        String[] parts = rangeText.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid TimeRange format: " + text);
        }

        LocalTime start = LocalTime.parse(parts[0].trim(), timeFormatter);
        LocalTime end = LocalTime.parse(parts[1].trim(), timeFormatter);

        return new LocalTime[]{start, end};
    }

    /**
     * Returns the total duration of the time range in minutes.
     * Supports formats like "[07:00 - 14:00]".
     */
    public int toMinutes() {
        String rangeText = text.trim();

        // Remove square brackets
        if (rangeText.startsWith("[") && rangeText.endsWith("]")) {
            rangeText = rangeText.substring(1, rangeText.length() - 1).trim();
        }

        // Split start and end times
        String[] parts = rangeText.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid TimeRange format: " + text);
        }

        LocalTime start = LocalTime.parse(parts[0].trim(), timeFormatter);
        LocalTime end = LocalTime.parse(parts[1].trim(), timeFormatter);

        return (int) java.time.Duration.between(start, end).toMinutes();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visitTimeRange(this);
    }

}
