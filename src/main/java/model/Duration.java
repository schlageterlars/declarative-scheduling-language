package model;

public class Duration {
    private final String text;

    public Duration(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

    /**
     * Converts the duration to minutes.
     * Supports formats such as:
     * "(20 min)", "(1 h)", "(1 h 20 min)".
     * The method handles optional parentheses around the text.
     */
    public int toMinutes() {
        int minutes = 0;

        String lower = text.toLowerCase().trim();

        if (lower.startsWith("(") && lower.endsWith(")")) {
            lower = lower.substring(1, lower.length() - 1).trim();
        }

        if (lower.contains("h")) {
            String[] parts = lower.split("h");
            String hourPart = parts[0].trim();
            try {
                minutes += Integer.parseInt(hourPart) * 60;
            } catch (NumberFormatException e) {
                throw new RuntimeException("Invalid hours in duration: " + text);
            }
            if (parts.length > 1) {
                lower = parts[1].trim();
            } else {
                lower = "";
            }
        }

        if (lower.contains("min")) {
            String minPart = lower.replace("min", "").trim();
            if (!minPart.isEmpty()) {
                try {
                    minutes += Integer.parseInt(minPart);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid minutes in duration: " + text);
                }
            }
        }
        return minutes;
    }
}