package de.arnomann.martin.jta.api;

public enum BroadcasterType {
    NORMAL,
    AFFILIATE,
    PARTNER;

    public static BroadcasterType getByString(String s) {
        for(BroadcasterType type : BroadcasterType.values()) {
            if (type.toString().toLowerCase().equals(s))
                return type;
        }
        return BroadcasterType.NORMAL;
    }
}
