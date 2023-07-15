import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * whole game logic is in this class
 * much of the method in this class are static
 * @author Sina Hosseinzadeh
 * @version 1.0
 */
public class Game {
    private static String player1Name; //AXIS
    private static String player2Name; //ALLIED
    private static Team turn;
    private static ArrayList<Card> deck;
    private static ArrayList<Card> usedCards = new ArrayList<>();
    public static void startGame() throws FileNotFoundException {
        getNames();
        createTeams();
        Map.create();
        createUnits();
        shuffleDeck();
        drawCards();
        while(true)
        {
            Map.print();
            ArrayList<Troop> choosedTroops = playCard();
            move(choosedTroops);
            attack(choosedTroops);
            if(checkWin()) return;
            changeTurn();
        }
    }

    private static boolean checkWin() throws FileNotFoundException {
        if(turn.getScore() >= Settings.MEDALS_TO_WIN)
        {
            System.out.println(turn.getUserName() + " Won!");

            return true;
        }
        return false;
    }

    private static void changeTurn() {
        if(turn == Team.axis) turn = Team.allied;
        else turn = Team.axis;
    }

    /**
     * try attack with choosed troops
     * @param choosedTroops choosed troops
     */
    private static void attack(ArrayList<Troop> choosedTroops) {
        System.out.println("Enter target like 2X0 , you can type skip");
        for (int i = 0; i < choosedTroops.size(); i++) {
            Troop troop = choosedTroops.get(i);
            if(!troop.haveAttack())continue;
            System.out.println(troop.getNumber() + "" + troop.getCharacter() + " on " + troop.getY() + "X" + troop.getX());
            String input = Main.sc.nextLine();
            if(input.equals("skip"))continue;
            if(!input.matches("([0-9])X(([0-9])|(1[0-3]))"))
            {
                System.out.println("Wrong format, try again.");
                i--;
                continue;
            }
            Troop defender = Map.getTiles()[Integer.parseInt(input.split("X")[0])][Integer.parseInt(input.split("X")[1])].getTroop();
            if(defender == null)
            {
                i--;
                System.out.println("Empty tile, try again!");
                continue;
            }
            else if(defender.getOwner() == turn)
            {
                i--;
                System.out.println("You can't attack yourself");
                continue;
            }
            boolean attacked = false;
            int dices = Dice.countDices(troop,defender);
            if(dices <=0)
            {
                i--;
                System.out.println("Cannot Attack to this target!");
                continue;
            }
            for(int j = 0;j < dices;j++)
            {
                int dice = Dice.roll();
                System.out.println("Rolled dice number " + (j+1) + ": " + dice);
                if(Dice.canAttack(defender,dice))
                {
                    if(!attacked)
                    {
                        defender.setNumber(defender.getNumber() - 1);
                        if(defender.getNumber() == 0)
                        {
                            System.out.println(troop.getNumber() + "" + troop.getCharacter() + " Killed " + defender.getNumber() + defender.getCharacter());
                            defender.getTile().setTroop(null);
                            turn.addScore();
                            defender.setX(-1);
                            defender.setY(-1);
                        }
                    }
                    attacked = true;
                }
            }
            System.out.println("Attack " + (attacked?"Successful!":"Failed!"));
        }
    }

    /**
     * moves choosed troops
     * @param choosedTroops choosed troops
     */
    private static void move(ArrayList<Troop> choosedTroops) {
        ///TODO FORCE MOVE
        System.out.println("Enter moves like [2U 1UR]");
        for(Troop troop : choosedTroops)
        {
            System.out.println(troop.getNumber() + "" + troop.getCharacter() + " on " + troop.getY() + "X" + troop.getX());
            String input = Main.sc.nextLine();
            if(input.equals("skip"))continue;
            while(!input.matches("(([1-3](([DU])|([DU][LR]))\\s)|([1-3](([DU])|([DU][LR]))))+"))
            {
                System.out.println("Wrong pattern");
                input = Main.sc.nextLine();
                if(input.equals("skip"))continue;
            }
            for(String arg : input.split("\\s+"))
            {
                int num = Integer.parseInt(arg.substring(0,1));
                String dir = arg.substring(1);
                for(int i = 0;i < num;i++)
                {
                    if(!dir.contains("U") && !dir.contains("D"))
                    {
                        System.out.println("Wrong direction for " + num + dir);
                        break;
                    }
                    else if(Map.canMove(troop.getX(),troop.getY(),dir) && troop.haveMove())
                    {
                        Map.move(troop.getX(),troop.getY(),dir);
                    }
                    else
                    {
                        System.out.println("Group can't move " + num + dir);
                        break;
                    }
                }
            }
            Map.print();
        }
    }

    /**
     * Let player choose card and choose troops he/she wants move and attack
     * @return list of troops which want to move
     */
    private static ArrayList<Troop> playCard() {
        Card choosedCard;
        ArrayList<Troop> troops = new ArrayList<>();
        try{
            System.out.println(turn.getPlayerName() + " Your turn. Score: " + turn.getScore());
            System.out.println(turn.getPlayerName() + " Play a card: ");
            for (int i = 0;i < turn.getNumberOfCards();i++)
            {
                System.out.println((i+1) + "- " + turn.cards.get(i));
            }
            int choose = Integer.parseInt(Main.sc.nextLine())-1;
            choosedCard = turn.cards.get(choose);
            usedCards.add(turn.cards.remove(choose));
            turn.cards.add(deck.remove(0));
            if(deck.isEmpty())
            {
                shuffleDeck();
            }
        }
        catch (Exception e)
        {
            System.out.println("Wrong input.");
            return playCard();
        }
        int num = choosedCard.num;
        boolean canDiffrent = choosedCard.canDiffrent;
        char type = ' ';

        System.out.println("Choose groups locations like 3X10 (Type Skip for skip =) )");
        int i = 1;
        while(i <= num)
        {
            System.out.println("Choose group number " + i + ":");
            String input = Main.sc.nextLine();
            if(input.toLowerCase().equals("skip")) break;
            else if(!input.matches("([0-9])X(([0-9])|(1[0-3]))"))
            {
                System.out.println("Wrong pattern.");
                continue;
            }
            else
            {
                int y = Integer.parseInt(input.split("X")[0]);
                int x = Integer.parseInt(input.split("X")[1]);
                if(Map.getTiles()[y][x].getTroop() == null)
                {
                    System.out.println("Not a group. try another tile.");
                    continue;
                }
                else if(troops.contains(Map.getTiles()[y][x].getTroop()))
                {
                    System.out.println("Duplicate. choose another group");
                    continue;
                }
                else if(Map.getTiles()[y][x].getTroop().getOwner() != turn)
                {
                    System.out.println("The owner of this group is not you.");
                    continue;
                }
                else if(!canDiffrent && type != ' ' && type != Map.getTiles()[y][x].getTroop().getCharacter())
                {
                    System.out.println("You can't choose this type of units with your card.");
                    continue;
                }
                else
                {
                    Troop troop = Map.getTiles()[y][x].getTroop();
                    troops.add(troop);
                    if(!canDiffrent)
                    {
                        type = troop.getCharacter();
                    }
                }
            }
            i++;
        }
        return troops;
    }

    private static void drawCards() {
        for (int i = 0; i < Team.axis.getNumberOfCards(); i++) {
            Team.axis.cards.add(deck.remove(0));
        }
        for (int i = 0; i < Team.allied.getNumberOfCards(); i++) {
            Team.allied.cards.add(deck.remove(0));
        }
    }

    /**
     * shuffle cards
     */
    private static void shuffleDeck() {
        if(usedCards.size() == 0)
        {
            deck = new ArrayList<>();
            for (int i = 0; i < 6 * Settings.CARDS; i++) {
                deck.add(new Card(1,true));
            }
            for (int i = 0; i < 13 * Settings.CARDS; i++) {
                deck.add(new Card(2,true));
            }
            for (int i = 0; i < 10 * Settings.CARDS; i++) {
                deck.add(new Card(3,true));
            }
            for (int i = 0; i < 6 * Settings.CARDS; i++) {
                deck.add(new Card(4,true));
            }
            for (int i = 0; i < 5 * Settings.CARDS; i++) {
                deck.add(new Card(3,false));
            }
            Collections.shuffle(deck);
        }
        else
        {
            deck.addAll(usedCards);
            while(!usedCards.isEmpty())
            {
                deck.add(usedCards.get(0));
                usedCards.remove(0);
            }
            Collections.shuffle(deck);
        }
    }

    /**
     * create init units
     */
    private static void createUnits() {
//        Map.putUnit(new Tank(Team.axis,10,7));


        Map.putUnit(new Tank(Team.axis,0,0));
        Map.putUnit(new Soilder(Team.axis,1,0));
        Map.putUnit(new Soilder(Team.axis,2,0));
        Map.putUnit(new Tank(Team.axis,5,0));
        Map.putUnit(new Soilder(Team.axis,7,0));
        Map.putUnit(new Tank(Team.axis,8,0));
        Map.putUnit(new Soilder(Team.axis,10,0));
        Map.putUnit(new Tank(Team.axis,11,0));
        Map.putUnit(new Soilder(Team.axis,12,0));

        Map.putUnit(new Tank(Team.axis,5,1));
        Map.putUnit(new Soilder(Team.axis,4,1));
        Map.putUnit(new Tank(Team.axis,10,1));
        Map.putUnit(new Soilder(Team.axis,8,1));

        Map.putUnit(new Tank(Team.allied,0,8));
        Map.putUnit(new Tank(Team.allied,1,8));
        Map.putUnit(new Soilder(Team.allied,8,8));
        Map.putUnit(new Tank(Team.allied,12,8));

        Map.putUnit(new Soilder(Team.allied,0,7));
        Map.putUnit(new Cannon(Team.allied,1,7));
        Map.putUnit(new Cannon(Team.allied,5,7));
        Map.putUnit(new Soilder(Team.allied,9,7));

        Map.putUnit(new Soilder(Team.allied,7,6));

        Map.putUnit(new Soilder(Team.allied,3,5));

        Map.putUnit(new Soilder(Team.allied,1,4));
        Map.putUnit(new Soilder(Team.allied,6,4));
        Map.putUnit(new Soilder(Team.allied,8,4));
        Map.putUnit(new Soilder(Team.allied,11,4));

    }


    /**
     * gets player names
     */
    private static void getNames() {
        System.out.print("Player 1 (AXIS)\nEnter your name: ");
        player1Name = Main.sc.nextLine();
        System.out.print("Player 2 (ALLIED)\nEnter your name: ");
        player2Name = Main.sc.nextLine();

    }

    /**
     * creates 2 allied and axis team for play
     */
    private static void createTeams() {
        Team.allied = new Team("Allied",player1Name,4,2,3,4);
        Team.axis = new Team("Axis",player2Name,4,2,4,2);
        turn = Team.axis;
    }
}
