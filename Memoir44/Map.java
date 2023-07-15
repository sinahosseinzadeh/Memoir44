import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Map {
    private static String mapTemplate;
    private static Tile [][] tile;

    /**
     * this method reads map and map template from given file.
     * if file doesn't exists method will try to open default map
     * if default map doesn't exist either it will throw new exception
     * @throws FileNotFoundException if default.map doesn't exists
     */
    public static void create() throws FileNotFoundException {
        tile = new Tile[9][13];
        System.out.println("Enter map name (Enter for default): ");
        String mapName = Main.sc.nextLine().replace(".map","");
        Scanner mapReader;
        try {
            mapReader = new Scanner(new File("src\\" + mapName + ".map"));
        } catch (FileNotFoundException e) {
            if(!mapName.equals("")) System.out.println("Map not found. opening default map!");
            mapReader = new Scanner(new File("src\\default.map"));
        }

        for(int i = 0;i < 9;i++)
        {
            int j = 0;
            for(String s : mapReader.nextLine().split(""))
            {
                tile[i][j] = new Tile(Integer.valueOf(s));
                j++;
            }
        }
        tile[2][0].setOwner(Team.axis);
        tile[8][11].setOwner(Team.allied);
        try {
            mapReader = new Scanner(new File("src\\" + mapName + ".template"));
        } catch (FileNotFoundException e) {
            if(!mapName.equals("")) System.out.println("Template for this map not found. opening default template!");
            mapReader = new Scanner(new File("src\\default.template"));
        }

        mapTemplate = "";
        while(mapReader.hasNext())
            mapTemplate += mapReader.nextLine() + "\n";

    }


    /**
     * this method prints map with template and data
     */
    public static void print() {
        String mapString = mapTemplate;
        for(int i = 0;i < 9;i++)
        {
            for(int j = 0;j < 13;j++)
            {
                    if(tile[i][j].getTroop() == null)
                    {
                        if(j < 10)
                        {
                            mapString = mapString.replaceFirst(" " + i + "X" + j ,"    ");
                            mapString = mapString.replaceFirst(" " + i + "" + j ,"   ");
                        }
                        else
                        {
                            mapString = mapString.replaceFirst( " " + i + "X" + j,"     ");
                            mapString = mapString.replaceFirst(" " + i + "" + j,"    ");
                        }
                    }
                    else
                    {
                        if(j < 10)
                        {
                            mapString = mapString.replaceFirst(" " + i + "X" + j +  " "," " + (tile[i][j].getTroop().getOwner() == Team.axis? "AXIS" : "ALID"));
                            mapString = mapString.replaceFirst(" " + i + "" + j + " " , " " + tile[i][j].getTroop().getNumber() + tile[i][j].getTroop().getCharacter() + " ");
                        }
                        else
                        {
                            mapString = mapString.replaceFirst(" " + i + "X" + j," " + (tile[i][j].getTroop().getOwner() == Team.axis?"AXIS" : "ALID"));
                            mapString = mapString.replaceFirst(" " + i + "" + j," " + tile[i][j].getTroop().getNumber() + tile[i][j].getTroop().getCharacter() + " ");
                        }
                    }
//                System.out.println(mapString);Main.sc.nextLine();
            }
        }

        mapString = mapString.replaceAll("AXIS" , "\u001B[31mAXIS\u001B[0m");
        mapString = mapString.replaceAll("ALID" , "\u001B[32mALID\u001B[0m");
        mapString = mapString.replaceAll("RIVR" , "\u001B[34mRIVR\u001B[0m");

        if(tile[2][0].getOwner() != null)
            mapString = mapString.replaceAll("\\(0 \\)" , "\u001B[31m(0 )\u001B[0m");
        if(tile[8][11].getOwner() != null)
            mapString = mapString.replaceAll("\\(11\\)" , "\u001B[32m(11)\u001B[0m");

        System.out.println(mapString);
    }

    /**
     * adds a troop to map
     * @param troop adding troop
     */
    public static void putUnit(Troop troop) {
        tile[troop.getY()][troop.getX()].setTroop(troop);

    }

    /**
     * tells someone in x and y position can move to given direction?
     * @param x x position
     * @param y y position
     * @param dir direction
     * @return can move or not?
     */
    public static boolean canMove(int x,int y ,String dir)
    {
        int startX = x;
        int startY = y;
        try{
            Troop troop = tile[y][x].getTroop();
            if(troop == null) return false;
            switch (dir)
            {
                case "U":
                    y-=2;
                    break;
                case "UR":
                    y-=1;
                    if(startY %2 == 1)
                    x+=1;
                    break;
                case "UL":
                    y-=1;
                    if(startY %2 == 0)
                    x-=1;
                    break;
                case "D":
                    y+=2;
                    break;
                case "DR":
                    y+=1;
                    if(startY %2 == 1)
                    x+=1;
                    break;
                case "DL":
                    y+=1;
                    if(startY %2 == 0)
                    x-=1;
                    break;
            }
            if(tile[y][x].getType() == Tile.NULL || tile[y][x].getType() == Tile.RIVER) return false;
            if(troop.getCharacter() == 'T' && tile[y][x].getType() == Tile.SHELTER) return false;
            return tile[y][x].getTroop() == null;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * move troop on given position to given direction
     * @param x x position of troop
     * @param y y position of troop
     * @param dir direction of move
     */
    public static void move(int x,int y ,String dir)
    {
        int startY = y;
        int startX = x;
        tile[y][x].getTroop().move();
        switch (dir)
        {
            case "U":
                y-=2;
                break;
            case "UR":
                y-=1;
                if(startY %2 == 1)
                    x+=1;
                break;
            case "UL":
                y-=1;
                if(startY %2 == 0)
                    x-=1;
                break;
            case "D":
                y+=2;
                break;
            case "DR":
                y+=1;
                if(startY %2 == 1)
                    x+=1;
                break;
            case "DL":
                y+=1;
                if(startY %2 == 0)
                    x-=1;
                break;
        }
        tile[startY][startX].getTroop().setY(y);
        tile[startY][startX].getTroop().setX(x);
        tile[y][x].setTroop(tile[startY][startX].getTroop());
        tile[startY][startX].setTroop(null);
        if(tile[y][x].getOwner() != null && !tile[y][x].getOwner().getName().equals(tile[y][x].getTroop().getOwner().getName()))
        {
            tile[y][x].getTroop().getOwner().addScore();
            tile[y][x].setOwner(null);
        }
    }


    public static Tile[][] getTiles() {
        return tile;
    }
}
