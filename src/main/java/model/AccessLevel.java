package model;

public enum AccessLevel {
    MORNING ("08:00", "12:00"),
    DAY ("12:00", "18:00"),
    EVENING ("18:00", "22:00"),
    ALL ("08:00", "22:00");

    private String start;
    private String end;

    AccessLevel(String start, String end) {
        this.start = start;
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }
}
