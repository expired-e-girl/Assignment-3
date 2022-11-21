import java.util.Random;
import java.util.Scanner;

//Marilyn Ruptash
//ID: 261126626

//Assignment 3:
//Bulls and Cows is an old code-breaking mind game. This Java program
//implements a game of Bulls and Cows that randomly generates a secret 4-
//digits number. When the player takes a guess, the program reveals the number of digits that match with
//the secret number. If the matching digits are in the correct positions, they are called “bulls”, if they
//are in different positions, they are called “cows”. After each guess, the program reveals how many bulls
//and cows the player’s guess contains.

public class BullsAndCows {
    //Method to check if an integer is contained in an array.
    public static boolean contains(int x, int[] arrayToCheck) {
        for (int i = 0; i < arrayToCheck.length; i++) {
            if (x == arrayToCheck[i]) {
                return true;
            }
        }
        return false;
    }

    //Method to generate a random array of 4 digits
    public static int[] generateSecretDigits(int seed) {
        int[] secretNumber = new int[] {-1,-1,-1,-1}; //Initializing array with -1 so that can be used in secret number array
        int index = 0;                                //index counter
        boolean isSuccess = false;              //Check if the array is filled successfully
        Random randomNumber = new Random(seed); //Initializing Random object
        while (!isSuccess) {
            int digit = randomNumber.nextInt(10);  //Randomizing digits between 0 and 9 included
            boolean isDuplicate = false;                   //Check if a duplicate is present
            for (int i = 0; i < secretNumber.length; i++) {
                if (secretNumber[i] == digit) {            //Checking if both numbers are the same if so is a duplicate
                    isDuplicate = true;
                    break;
                }
            }
            if (!isDuplicate) {                 //If not a duplicate fill out indexes of secret digits array
                secretNumber[index] = digit;
                index++;
                if (index == 4) {           //Once it is up to 4 indexes, the array is a success.
                    isSuccess = true;
                }
            }

        }
        return secretNumber;  //Returns array of randomly generated 4 digit secret array
    }

    //Method that compares user array to secret array to determine amount of bulls
    public static int getNumOfBulls(int[] secretArray, int[] userInput) {
        int bulls = 0;              //bull counter
        for (int i = 0; i < userInput.length; i++) {
            for (int j = 0; j < secretArray.length; j++) {
                if (secretArray[j] == userInput[i] && i == j) { //iterate through both arrays to find matching positions
                    bulls++;                                    //add to counter if match
                }
            }
        }
        System.out.println("Number of bulls: " + bulls);        //prints and returns bull counter's result
        return bulls;
    }

    //Method that compares user array to secret array to determine amount of cows
    public static int getNumOfCows(int[] secretArray, int[] userInput) {
        int cows = 0;                       //cow counter
        for (int i = 0; i < userInput.length; i++) {        //iterate through both arrays to find matching digits
            for (int j = 0; j < secretArray.length; j++) {  //but different positions
                if (secretArray[j] == userInput[i] && j != i) {
                    cows++;                                 //add to counter if match digits but not same position
                }
            }
        }
        System.out.println("Number of cows: " + cows);      //prints and returns bull counter's result
        return cows;
    }

    //Method that checks for duplicates in the user input
    public static int checkDuplicates(String userInput){
        char [] list = userInput.toCharArray();     //declare array of characters
        int count = 0;                              //duplicate counter
            for (int i = 0; i < userInput.length(); i++) {      //iterating through our input character array to check for duplicates
                for (int j = i + 1 ; j < userInput.length(); j++) {
                    if (list[j] == list[i]) {
                        count++;                        //every duplicate increments counter
                    }
                }
            }
            return count;
    }

    //Method to initialize our user input array
    public static int [] initializeGuessArray(String userInput){
        char [] list = userInput.toCharArray();
        int[] digits = new int[userInput.length()];     //User input from string to character array to integer array

        for (int k = 0; userInput.length() > k; k++) {      //initializing digit array from index 0 to 3
            digits[k] = Character.digit(list[k], 10);   //adding each digit at corresponding index
        }
        return digits;                  //returns array of user input's digits
    }

    //Method to validate if the user input is the right format
    public static boolean validFormat(String userInput){
        int x = Integer.parseInt(String.valueOf(userInput));    //Checks if integer not alphabetical, if not integer is caught by try catch in playBullsAndCows method
        char [] list = userInput.toCharArray();                 //If integers, switched to character array
        for(int i = 0; i < list.length; i++)            //Iterating through char array
            if (list.length < 4 || x < 0){              //Checking if array is 4 digits and that there are no negative integers
                return false;
        }
        return true;
    }


    //Method to launch a game of Bulls and Cows using a seed
    public static void playBullsAndCows(int seed) {
        Scanner input = new Scanner(System.in);   //Declare scanner object

        int[] secretArray = generateSecretDigits(seed); //Generate secret digits array
//        System.out.println("Secret array is:");
//        for (int i = 0; i < secretArray.length; i++) {    //Uncomment these 4 lines to print the secret array for testing purposes
//            System.out.print(secretArray[i]);
//        }

        //Welcome message and game explanation
        System.out.println(" ");
        System.out.println("Welcome Player! This is a game of Bulls and Cows! Try to guess my secret number." +
                "\nBulls are digits in the right position, cows are digits that are present but not in the right position.");

        boolean notGuessed = true;     //guess boolean
        int guess = 1;              //guess counter
        while (notGuessed) {          //loop that repeats until guessed


            if (guess > 5) {    //after 5 guesses, ask user if they want to quit
                System.out.println("You're not really good at this...Would you like to quit? y/n");
                String quit = input.nextLine();
                if (quit.equals("y")) {                //if they want to quit, mention how many guesses they made and exit program
                    System.out.println("You quit after " + guess + " attempts. Too bad! Bye!");
                    break;
                } else {
                    notGuessed = true;
                }
            }

            System.out.println("\nGuess #" + guess + ". Please enter a four-digit number, each digit should be unique : ");
            String userInput = input.nextLine(); //Initialize scanner object

            try {
                if (checkDuplicates(userInput) >= 1) throw new IllegalArgumentException();  //calling checkDuplicates method in a try catch
            } catch (IllegalArgumentException e) {      //Checks for duplicates in user input with checkDuplicates method
                guess++;                                // if duplicates present, add to the guess counter and print guess loss message/error
                System.out.println("Pay attention! Invalid format. You wasted a guess. You must enter a four-digit number and each digit should be unique.");
                continue;
            }

            try {
                if (!validFormat(userInput)) throw new IllegalArgumentException();  //calling validFormat method in a try catch
            } catch (
                    IllegalArgumentException e) {      //checks input for letters, negative digits or any other character entered and checks length
                guess++;                                //if any are present, guess lost message/error
                System.out.println("Pay attention! Invalid format. You wasted a guess. You must enter a four-digit number and each digit should be unique.");
                continue;
            }

            int[] digits = initializeGuessArray(userInput);     //call initializeGuessArray method to fill userInput array with the input

            try {                                                //Checking length of both arrays in a try catch, so they match
                if (secretArray.length != digits.length) throw new IllegalArgumentException(); //Exception if array sizes do not match
            } catch (IllegalArgumentException e) {
                guess++;                                        //If they don't match print guess loss message and error
                System.out.println("Arrays do not match -- the guess must be 4 digits! You wasted a guess. You must enter a four-digit number " +
                        "\nand each digit should be unique.");
                continue;
            }

            int x = getNumOfBulls(secretArray, digits);         //call method to get number of bulls, store return in variable
            getNumOfCows(secretArray, digits);                  //call method to get number of cows


            if (x == 4) {                                   //checking if user guess matches secret digits by validation amount of bulls discovered
                System.out.println("Congrats! You guessed right after " + guess + " guesses!");
                notGuessed = false;
                break;                                      //announces how many guesses it took and exit program
            } else {
                guess++;                              //if not complete match, add to guess counter and continue
            }
        }
    }


    //Main method where a seed is randomly generated and play method is called
    public static void main(String[] args) {
        //Generates a random seed simply uncomment these two lines out and a as the argument in playBullsAndCows
//        int a = (int) (Math.random() * 100);
//        System.out.println("seed is: " + a);

        playBullsAndCows(123);
    }
}

//TESTING METHODS
//
//        int[] secretArray = generateSecretDigits(a);
//        System.out.println("Secret array is:");
//        for (int i = 0; i < secretArray.length; i++) {
//            System.out.print(secretArray[i]);
//        }
//        System.out.println(" ");
//        int[] userInput = new int[4];
//        System.out.println("User array is:");
//        for (int i = 0; i < userInput.length; i++) {
//            userInput[i] = (int) (Math.random() * 9);
//            System.out.print(userInput[i]);
//        }
//        System.out.println(" ");
//        getNumOfCows(secretArray, userInput);
//        getNumOfBulls(secretArray, userInput);