import java.util.Random;

/**
 * Roll , calculate and ... you know? dice things
 * @author Sina Hosseinzadeh
 * @version 1.0
 */
public class Dice {
    public static Random rand = new Random();

    public static int roll()
    {
        return rand.nextInt(6) + 1;
    }

    /**
     * calculate can a troop attack another unit with this dice number?
     * @param troop defender
     * @param diceNumber dice number
     * @return can attack or no?
     */
    public static boolean canAttack(Troop troop , int diceNumber)
    {
        if(diceNumber == 3 || diceNumber == 4)
        {
            return false;
        }
        else if(troop.getClass() == Soilder.class || diceNumber == 5)
        {
            return true;
        }
        else if(diceNumber == 2 && troop.getClass() == Tank.class)
        {
            return true;
        }
        return false;
    }


    /**
     * count how many dice will be roll and prints it
     * @param attacker attacker group
     * @param defender defender group
     * @return number of dices
     */
    public static int countDices(Troop attacker , Troop defender)
    {
        Tile attackerTile = Map.getTiles()[attacker.getY()][attacker.getX()];
        Tile defenderTile = Map.getTiles()[defender.getY()][defender.getX()];
        int dices = 0;
        int distance = (int) Math.round(Math.sqrt(Math.pow(attacker.getX()-defender.getX(),2) + Math.pow(attacker.getY()-defender.getY(),2)));
        System.out.println(attacker.getNumber() + "" + attacker.getCharacter() + " Attacking to " + defender.getNumber() + "" + defender.getCharacter() + " With distance: " + distance);
        if(attacker.getCharacter() == 'S')
        {
            dices = 4 - distance;
            if(dices <= 0) return 0;
        }
        else if(attacker.getCharacter() == 'T')
        {
            if(distance > 3) return 0;
            dices = 3;
        }
        else
        {
            if(distance == 1 || distance == 2)
                dices = 3;
            else if(distance == 3 || distance == 4)
                dices = 4;
            else if(distance == 6 || distance == 5)
                dices = 1;
            else
                return 0;
        }
        if(defenderTile.getType() == Tile.HILL) dices--;
        if((defenderTile.getType() == Tile.BUSH || defenderTile.getType() == Tile.TOWN) && attacker.getCharacter() == 'T') dices -=2;
        if((defenderTile.getType() == Tile.BUSH || defenderTile.getType() == Tile.TOWN) && attacker.getCharacter() == 'S') dices --;
        if(defenderTile.getType() == Tile.SHELTER && defender.getOwner() == Team.axis && attacker.getCharacter() == 'S') dices--;
        if(defenderTile.getType() == Tile.SHELTER && defender.getOwner() == Team.axis && attacker.getCharacter() == 'T') dices-=2;
        if(dices <= 0) return 0;
        System.out.println("Rolling " + dices + " dices");
        return dices;

    }
}
