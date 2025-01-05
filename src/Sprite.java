// amit homri 211321823

import biuoop.DrawSurface;

/**
 * An interface for all drawable objects (called sprites) in a game.
 */
public interface Sprite {
    /**
     * Draws the Sprite on the given drawing surface.
     *
     * @param d The drawing surface.
     */
    void drawOn(DrawSurface d);

    /**
     * Notifies the Sprite that time has passed.
     */
    void timePassed();

    /**
     * Adds the Sprite to the game.
     *
     * @param g The game  object representing the game.
     */
    void addToGame(Game g);
}
