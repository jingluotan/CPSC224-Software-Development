/**
 *  Zag Farkle - Gonzaga Farkle game
 *  CPSC 224 Class Project
 *  Author: Keyu Chen
 *  Copyright year: 2024
 *  Summary: this is the Farkle game with Java version without loop.
 *
 */

// Java packaging - specifies directory and Java project namespace
package edu.gonzaga.Farkle;

import java.util.Random;
import java.util.Scanner;

/*
 *  This is the main class for the Farkle project.
 *  It really should just instantiate another class and run
 *   a method of that other class.
 */

/** Main program class for launching Farkle program. */

public class Farkle {
    private int totalScore;
    private int[] dice;
    private int[] meld;
    private Random random;
    private Scanner scanner;
    private int[] diceNumberCount;
    private int meldScore;
    private int meldDiceCount;


    public Farkle() {
        this.totalScore = 0;
        this.dice = new int[6];
        this.meld = new int[6];
        this.random = new Random();
        this.scanner = new Scanner(System.in);
        this.diceNumberCount = new int[7];
        this.meldScore = 0;
        this.meldDiceCount = 0;
    }

    public static void main(String[] args) {
//        System.out.println("Hello Farkle");
//        Die die1 = new Die(6);
//        System.out.println(die1);
//        System.out.println("Now rolling our die!");
//        die1.roll();
//        System.out.println(die1);
//        System.out.println("Cool, huh?");

        // project starts with here
        Farkle game = new Farkle();
        game.startGame();
    }

    // Start game here
    public void startGame() {
        System.out.println("Hello Farkle");

        // Roll the dice
        System.out.println("Your dice:");
        rollDice();
        System.out.println();

        // Count and display dice numbers
        System.out.println("Quantity of each die value:");
        diceNumber();
        System.out.println();

        // Check for Farkle
        if (isFarkle()) {
            System.out.println("Farkle! Points: 0");
        } else {
            System.out.println("This is not Farkle!");
            totalScore += playRound();
        }
        System.out.println("Round over. Total score is now: " + totalScore);
    }

    // Roll and sort the dice
    public void rollDice() {
        for (int i = 0; i < 6; i++) {
            dice[i] = random.nextInt(6) + 1;
        }
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5 - x; y++) {
                if (dice[y] > dice[y + 1]) {
                    int temp = dice[y];
                    dice[y] = dice[y + 1];
                    dice[y + 1] = temp;
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            System.out.print(dice[i] + " ");
        }
        System.out.println();
    }

    // Count the occurrences of each dice value
    public void diceNumber() {
        for (int i = 1; i < 7; i++) {
            diceNumberCount[i] = 0;
        }
        for (int i = 0; i < dice.length; i++) {
            diceNumberCount[dice[i]]++;
        }
        for (int i = 1; i < diceNumberCount.length; i++) {
            System.out.println(i + ": " + diceNumberCount[i]);
        }
    }

    // Check if the roll results in a Farkle
    public boolean isFarkle() {
        if (diceNumberCount[1] != 0 || diceNumberCount[5] != 0) {
            return false;
        }
        for (int i = 2; i < 7; i++) {
            if (diceNumberCount[i] >= 3) {
                return false;
            }
        }
        int pairCount = 0;
        for (int i = 1; i < 7; i++) {
            if (diceNumberCount[i] == 2) {
                pairCount++;
            }
        }
        if (pairCount == 3) {
            return false;
        }
        return true;
    }

    // Play a single round
    public int playRound() {
        int meldScore;
        boolean done = false;

        while (!done) {
            System.out.println();
            System.out.println("************* Current hand and meld *************");
            System.out.println(" Die   Hand |   Meld");
            System.out.println("------------+---------------");

            for (int i = 0; i < 6; i++) {
                char option = (char) ('A' + i);
                System.out.print(" (" + option + ")    ");
                if (dice[i] != 0) {
                    System.out.print(dice[i] + "");
                } else {
                    System.out.print(" ");
                }
                System.out.print("   |     ");
                if (meld[i] != 0) {
                    System.out.println(meld[i]);
                } else {
                    System.out.println(" ");
                }
            }
            System.out.println("------------+---------------");

            meldScore = ScoreGet(meld);
            System.out.println("Meld Score: " + meldScore);
            System.out.println("(K) BanK Meld & End Round");
            System.out.println("(Q) Quit game");
            System.out.println();
            System.out.println("Enter letters for your choice(s): ");

            String userInput = scanner.nextLine();

            for (int i = 0; i < userInput.length(); i++) {
                char letter = Character.toUpperCase(userInput.charAt(i));
                if (letter >= 'A' && letter <= 'F') {
                    int index = letter - 'A';
                    if (dice[index] != 0) {
                        meld[index] = dice[index];
                        dice[index] = 0;
                    } else {
                        dice[index] = meld[index];
                        meld[index] = 0;
                    }
                } else if (letter == 'Q') {
                    done = true;
                } else if (letter == 'K') {
                    done = true;
                    totalScore += meldScore;
                }
            }
        }
        System.out.println();
        System.out.print("Round over. Total score is now: ");
        return totalScore;
    }

    // Calculate the score based on the meld
    public int ScoreGet(int[] meld) {
        int[] meldDice = new int[]{0, 0, 0, 0, 0, 0};
        for (int i = 0; i < 6; i++) {
            if (meld[i] != 0) {
                meldDice[meldDiceCount] = meld[i];
                meldDiceCount++;
            }
        }

        int[] meldDiceSizesCount = new int[]{0, 0, 0, 0, 0, 0, 0};
        for (int i = 0; i < 6; i++) {
            meldDiceSizesCount[meldDice[i]]++;
        }
        // Check straight
        boolean isStraight = true;
        for (int i = 1; i < 7; i++) {
            if (meldDiceSizesCount[i] != 1) {
                isStraight = false;
            }
        }
        if (isStraight) {
            meldScore += 1000;
        } else {
            // Check 3 pairs
            int pairCount = 0;
            for (int i = 1; i < 7; i++) {
                if (meldDiceSizesCount[i] == 2) {
                    pairCount++;
                }
            }
            if (pairCount == 3) {
                meldScore += 750;
            } else {
                // Check triples +
                for (int i = 1; i < 7; i++) {
                    if (meldDiceSizesCount[i] >= 3) {
                        int tripleSetPoints;
                        if (i == 1) {
                            tripleSetPoints = 1000;
                        } else {
                            tripleSetPoints = i * 100;
                        }
                        if (meldDiceSizesCount[i] > 3) { // 4 + in set
                            tripleSetPoints += (meldDiceSizesCount[i] - 3) * 100 * i;
                        }
                        meldScore += tripleSetPoints;
                    }
                }

                // Add 1's & 5's if unused
                if (meldDiceSizesCount[1] < 3) {
                    meldScore += meldDiceSizesCount[1] * 100;
                }

                if (meldDiceSizesCount[5] < 3) {
                    meldScore += meldDiceSizesCount[5] * 50;
                }
            }
        }
        return meldScore;
    }
}