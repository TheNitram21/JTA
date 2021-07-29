package de.arnomann.martin.jta.api;

public enum AdLength {
    SECONDS_30(30),
    SECONDS_60(60),
    SECONDS_90(90),
    SECONDS_120(120),
    SECONDS_150(150),
    SECONDS_180(180);

    private final int length;

    AdLength(int length) {
        this.length = length;
    }

    public int getLength() {
        return length;
    }
}
