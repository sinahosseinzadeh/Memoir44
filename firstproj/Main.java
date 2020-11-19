import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Main class which has main method and program starts from here
 * main menu which contains start game and change settings and about us and exit is in here.
 * @author Sina Hosseinzadeh
 * @version 1.0
 */
public class Main {
    public static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while(true)
        {
            printMenu();
            switch (sc.nextLine())
            {
                case "1":
                    try {
                        Game.startGame();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    break;
                case "2":
                    changeSettings();
                    break;
                case "3":
                    aboutUs();
                    break;
                case"4":
                    return;
            }
        }
    }

    /**
     * print menu details
     */
    private static void printMenu() {
        System.out.println("Welcome to Memoir 24");
        System.out.println("1- Start game");
        System.out.println("2- Change Settings");
        System.out.println("3- About us");
        System.out.println("4- Exit");
    }

    /**
     * just prints my name
     */
    private static void aboutUs() {
        System.out.println("I'm Sina Hosseinzadeh");
    }

    /**
     * change number of medals for win and change percent of cards (0.5 = half , 2 = double)
     */
    private static void changeSettings() {
        System.out.println("How many medals to win? (Default = 6)");
        Settings.MEDALS_TO_WIN = Integer.parseInt(sc.nextLine());
        System.out.println("Enter percent of cards (Default 1)");
        Settings.CARDS = Float.parseFloat(sc.nextLine());
        if(Settings.CARDS < 0.5 ) Settings.CARDS = 0.5f;
    }
}