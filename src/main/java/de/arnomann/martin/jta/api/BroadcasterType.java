package de.arnomann.martin.jta.api;

/**
 * Represents the type of a broadcaster.
 */
public enum BroadcasterType {
    NORMAL,
    AFFILIATE,
    PARTNER;

    /**
     * Returns a broadcaster type by String.
     * @param s the type to search for.
     * @return the found type, or {@link BroadcasterType#NORMAL}, if nothing was found.
     */
    public static BroadcasterType getByString(String s) {
        for(BroadcasterType type : BroadcasterType.values()) {
            if (type.toString().toLowerCase().equals(s))
                return type;
        }
        return BroadcasterType.NORMAL;
    }
}
