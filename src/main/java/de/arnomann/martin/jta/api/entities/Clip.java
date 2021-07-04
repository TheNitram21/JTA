package de.arnomann.martin.jta.api.entities;

public interface Clip {

    /**
     * Returns the slug of the clip.
     *
     * @return the slug.
     */
    String getSlug();

    /**
     * Returns the title of the clip.
     *
     * @return the title.
     */
    String getTitle();

    /**
     * Returns the creator of the clip.
     *
     * @return the creator.
     */
    User getCreator();

    /**
     * Returns the URL of the clip.
     *
     * @return the URL.
     */
    String getUrl();

}
