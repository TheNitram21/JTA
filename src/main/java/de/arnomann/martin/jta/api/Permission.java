package de.arnomann.martin.jta.api;

import de.arnomann.martin.jta.api.exceptions.JTAException;

import java.util.EnumSet;

/**
 * Represents a permission.
 */
public enum Permission {
    // Analytics Permissions
    ANALYTICS_VIEW_EXTENSION("analytics:read:extensions"),
    ANALYTICS_VIEW_GAME("analytics:read:games"),

    // Bits Permissions
    BITS_VIEW("bits:read"),

    // Channel Permissions
    CHANNEL_MANAGE_ADS("channel:edit:commercial"),
    CHANNEL_MANAGE_STREAM("channel:manage:broadcast"),
    CHANNEL_MANAGE_EXTENSIONS("channel:manage:extensions"),
    CHANNEL_MANAGE_POLLS("channel:manage:polls"),
    CHANNEL_MANAGE_PREDICTIONS("channel:manage:predictions"),
    CHANNEL_MANAGE_REWARDS("channel:manage:redemptions"),
    CHANNEL_MANAGE_SCHEDULE("channel:manage:schedule"),
    CHANNEL_MANAGE_VIDEOS("channel:manage:videos"),
    CHANNEL_VIEW_EDITORS("channel:read:editors"),
    CHANNEL_VIEW_HYPE_TRAIN("channel:read:hype_train"),
    CHANNEL_VIEW_POLLS("channel:read:polls"),
    CHANNEL_VIEW_PREDICTIONS("channel:read:predictions"),
    CHANNEL_VIEW_REWARDS("channel:read:redemptions"),
    CHANNEL_VIEW_STREAM_KEY("channel:read:stream_key"),
    CHANNEL_VIEW_SUBSCRIPTIONS("channel:read:subscriptions"),

    // Clips Permissions
    CLIPS_MANAGE("clips:edit"),

    // Moderation Permissions
    MODERATION_VIEW("moderation:read"),
    MODERATION_MANAGE_AUTOMOD("moderator:manage:automod"),

    // User Permissions
    USER_MANAGE("user:edit"),
    USER_MANAGE_FOLLOWS("user:edit:follows"),
    USER_MANAGE_BLOCKED_USERS("user:manage:blocked_users"),
    USER_VIEW_BLOCKED_USERS("user:read:blocked_users"),
    USER_VIEW_STREAM("user:read:broadcast"),
    USER_VIEW_EMAIL("user:view:email"),
    USER_VIEW_FOLLOWS("user:view:follows"),
    USER_VIEW_SUBSCRIPTIONS("user:view:subscriptions");

    /** All analytics permissions. */
    public static final EnumSet<Permission> analyticsPermissions = EnumSet.of(ANALYTICS_VIEW_GAME, ANALYTICS_VIEW_EXTENSION);
    /** All bits permissions. */
    public static final EnumSet<Permission> bitsPermissions = EnumSet.of(BITS_VIEW);
    /** All channel permissions. */
    public static final EnumSet<Permission> channelPermissions = EnumSet.of(CHANNEL_MANAGE_ADS, CHANNEL_MANAGE_STREAM, CHANNEL_MANAGE_EXTENSIONS,
            CHANNEL_MANAGE_POLLS, CHANNEL_MANAGE_PREDICTIONS, CHANNEL_MANAGE_REWARDS, CHANNEL_MANAGE_SCHEDULE, CHANNEL_MANAGE_VIDEOS, CHANNEL_VIEW_EDITORS,
            CHANNEL_VIEW_HYPE_TRAIN, CHANNEL_VIEW_POLLS, CHANNEL_VIEW_PREDICTIONS, CHANNEL_VIEW_REWARDS, CHANNEL_VIEW_STREAM_KEY, CHANNEL_VIEW_SUBSCRIPTIONS);
    /** All clips permissions. */
    public static final EnumSet<Permission> clipsPermissions = EnumSet.of(CLIPS_MANAGE);
    /** All moderation permissions. */
    public static final EnumSet<Permission> moderationPermissions = EnumSet.of(MODERATION_VIEW, MODERATION_MANAGE_AUTOMOD);
    /** All user permissions. */
    public static final EnumSet<Permission> userPermissions = EnumSet.of(USER_MANAGE, USER_MANAGE_FOLLOWS, USER_MANAGE_BLOCKED_USERS, USER_VIEW_BLOCKED_USERS,
            USER_VIEW_STREAM, USER_VIEW_EMAIL, USER_VIEW_FOLLOWS, USER_VIEW_SUBSCRIPTIONS);

    private final String scope;

    Permission(String scope) {
        this.scope = scope;
    }

    /**
     * Converts the permission to a valid twitch scope.
     * @return the permission as a twitch scope.
     */
    public String toTwitchScope() {
        return scope;
    }

    /**
     * Searches for a permission by scope.
     * @param scope the scope to search for.
     * @return the found permission.
     * @throws JTAException if no permission was found.
     */
    public static Permission getByScope(String scope) {
        for(Permission permission : values()) {
            if(permission.toTwitchScope().equalsIgnoreCase(scope))
                return permission;
        }
        throw new JTAException("Couldn't find permission with scope \"" + scope + "\"!");
    }

}
