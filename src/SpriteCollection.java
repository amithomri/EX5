// amit homri 211321823
import biuoop.DrawSurface;
import java.util.ArrayList;

/**
 * class of sprites collection.
 */
public class SpriteCollection {
    private final ArrayList<Sprite> sprites;

    /** sprites collection constructor.
     * @param sprites The sprites collection
     */
    public SpriteCollection(ArrayList<Sprite> sprites) {
        this.sprites = new ArrayList<>(sprites);
    }

    /**
     * adding sprite to sprties collection if not null.
     * @param s The sprite  to add.
     */
    public void addSprite(Sprite s) {
        // if null so empty sprite we don't add to collection
        if (s == null) {
            return;
        }
        sprites.add(s);
    }
    public void removeSprite(Sprite s) {
        if (s == null || !sprites.contains(s)) {
            return;
        }
        sprites.remove(s);
    }

    /**
     * Calls method timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        for (Sprite s :  new ArrayList<>(sprites)) {
            s.timePassed();
        }
    }




    /**
     * Calls method drawOn(d) on all sprites by foreach loop.
     * @param d The drawing surface to draw on.
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s :  new ArrayList<>(sprites)) {
            s.drawOn(d);
        }
    }
}
