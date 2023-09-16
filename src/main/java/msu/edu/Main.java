package msu.edu;

public class Main {
    public static void main(String[] args) {

        switch (args.length) {
            case (0):
                System.out.println("No File Name Entered. Exiting. ");
            case (1):
                System.out.println("The first argument is:" + args[0]);
            default:
                System.out.println("Too many arguments entered. Using the first argument: " + args[0]);
        }
    }
}