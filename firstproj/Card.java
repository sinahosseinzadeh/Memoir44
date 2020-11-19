/**
 * @author Sina Hosseinzadeh
 */

public class Card {
    int num;
    boolean canDiffrent;

    public Card(int num , boolean canDifferent) {
        this.num = num;
        this.canDiffrent = canDifferent;
    }

    @Override
    public String toString() {
        return "Choose " + num + " Group" + (num ==1?"":"s") + " of units " + (canDiffrent?"":"from same types");
    }
}
