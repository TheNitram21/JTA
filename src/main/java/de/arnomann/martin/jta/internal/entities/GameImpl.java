package de.arnomann.martin.jta.internal.entities;

import de.arnomann.martin.jta.api.entities.Game;

public class GameImpl implements Game {

    private final String name;
    private final long id;

    public GameImpl(String name, long id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Long getId() {
        return id;
    }
}
