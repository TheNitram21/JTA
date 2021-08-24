package de.arnomann.martin.jta.internal;

import de.arnomann.martin.jta.internal.entities.UserImpl;

import java.util.ArrayList;
import java.util.List;

public class Cache {

    private final List<UserImpl> users;

    public Cache() {
        this.users = new ArrayList<>();
    }

    public Cache add(UserImpl user) {
        users.add(user);
        return this;
    }

    public List<UserImpl> getUsers() {
        return users;
    }

}
