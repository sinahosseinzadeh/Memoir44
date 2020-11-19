/**
 * Troop is a abstract object for all troops (cannons , soldiers and tanks)
 * for moving and attacking and flagging.
 * @author Sina Hosseinzadeh
 * @version 1.0
 */

public abstract class Troop {
    private int numberOfMoves;
    private int numberOfAttacks;
    private int number;
    private Team owner;
    private char character;
    private int x;
    private int y;

    /**
     *
     * @param numberOfMoves number of available moves.
     * @param numberOfAttacks number of available attacks
     * @param number number of troops in group
     * @param owner team owner of group (AIXS or ALLIED)
     * @param x x position in map
     * @param y y position in map
     * @param character printing character in map
     */
    public Troop(int numberOfMoves , int numberOfAttacks , int number , Team owner , int x , int y , char character) {
        this.numberOfMoves = numberOfMoves;
        this.numberOfAttacks = numberOfAttacks;
        this.number = number;
        this.owner = owner;
        this.x = x;
        this.y = y;
        this.character = character;
    }

    /**
     * @return number of available moves
     */
    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }

    public int getNumberOfAttacks() {
        return numberOfAttacks;
    }

    public void setNumberOfAttacks(int numberOfAttacks) {
        this.numberOfAttacks = numberOfAttacks;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Team getOwner() {
        return owner;
    }

    public void setOwner(Team owner) {
        this.owner = owner;
    }


    public char getCharacter() {
        return character;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void move(){
        numberOfMoves--;
        if(numberOfMoves < 0) numberOfMoves = 0;
    }


    public abstract boolean haveAttack();
    public abstract boolean haveMove();

    @Override
    public String toString() {
        return getNumber() + String.valueOf(getCharacter());
    }

    public Tile getTile() {
        return Map.getTiles()[y][x];
    }
}
