/**
 * This class is information of one tile of a map
 * also you can see consts of tile types
 * @author Sina
 */
public class Tile {

    public static final int NULL = 0;     //       ناموجود
    public static final int NORMAL = 1;   //        معمولی
    public static final int HILL = 2;     //تپه
    public static final int BUSH = 3;     //          جنگل
    public static final int RIVER = 4;    //        رودخانه
    public static final int BRIDGE = 5;   //            پل
    public static final int TOWN = 6;     //   شهر و روستا
    public static final int SHELTER = 7;  //       پناهگاه
    private int type;
    private Troop troop;
    private Team owner;

    public Tile(int type) {
        this.type = type;
        owner = null;
        troop = null;
    }

    public Troop getTroop() {
        return troop;
    }

    public void setTroop(Troop troop) {
        this.troop = troop;
    }

    public int getType() {
        return type;
    }

    public Team getOwner() {
        return owner;
    }

    public void setOwner(Team owner) {
        this.owner = owner;
    }
}
