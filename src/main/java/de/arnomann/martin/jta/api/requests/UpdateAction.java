package de.arnomann.martin.jta.api.requests;

import de.arnomann.martin.jta.api.entities.Updatable;

public class UpdateAction<T> {

    private final Updatable u;
    private final Runnable<T> r;

    public UpdateAction(Updatable u, Runnable<T> r) {
        this.u = u;
        this.r = r;
    }

    public T queue() {
        u.update();
        return r.run();
    }

    public interface Runnable<T> {
        T run();
    }

}
