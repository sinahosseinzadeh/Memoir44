import java.util.ArrayList;

/**
 * @author Sina Hosseinzadeh
 * @version 1.0
 */
public class Team {
    public static Team axis;
    public static Team allied;
    private String name;
    private String userName;
    private int soldierPerGroup;
    private int cannonPerGroup;
    private int tankPerGroup;
    private int numberOfCards;
    private int score;
    ArrayList<Card> cards;

    /**
     * Constructor
     * @param name name of team (axis or allied or something else?)
     * @param userName name of player of this team (sina or someone else?)
     * @param soldierPerGroup number of soldiers per group for this team
     * @param cannonPerGroup number of cannons per group for this team
     * @param tankPerGroup number of tanks per group for this team
     * @param numberOfCards number of cards in deck
     */
    public Team(String name , String userName , int soldierPerGroup , int cannonPerGroup , int tankPerGroup , int numberOfCards) {
        this.name = name;
        this.userName = userName;
        this.soldierPerGroup = soldierPerGroup;
        this.cannonPerGroup = cannonPerGroup;
        this.tankPerGroup = tankPerGroup;
        this.numberOfCards = numberOfCards;
        cards = new ArrayList<>();
    }

    public String getPlayerName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public int getSoldierPerGroup() {
        return soldierPerGroup;
    }

    public int getCannonPerGroup() {
        return cannonPerGroup;
    }

    public int getTankPerGroup() {
        return tankPerGroup;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void addScore(){
        score++;
    }

    public int getScore(){
        return score;
    }
}
