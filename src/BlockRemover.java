// 211321823 amit homri
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * removing block so we remove from game and stop listening
     * @param beingHit block being hit by ball
     * @param hitter ball that hits blocks
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        System.out.println("Block removed");
        remainingBlocks.decrease(1);
        beingHit.removeFromGame(game);
        beingHit.removeHitListener(this);

    }
}
