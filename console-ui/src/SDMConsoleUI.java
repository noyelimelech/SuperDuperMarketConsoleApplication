import SDM.SDMEngine;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SDMConsoleUI
{
    private SDMEngine engine;
    private final String[] mainMenu = {"Load new XML file.","Show all stores information","Show all items information.","Make new order.","Show all orders.","Exit."};

    public void start() {
        boolean isFinished;
        int optionChoose;

        do {
            showMainMenu();
            optionChoose = getValidOption(mainMenu.length);
            isFinished = executeOptionChoose(optionChoose, mainMenu.length);
        }while(!isFinished);
    }

    private boolean executeOptionChoose(int choice, int exitOption) {
        if(choice == exitOption){
            return true;
        }

        switch (choice){
            case 1:
                loadNewXML();
                break;
            case 2:
                showAllStores();
                break;
            case 3:
                showAllItems();
                break;
            case 4:
                //TODO call method on engine does a new order
                break;
            case 5:
                //TODO show all orders
                break;
        }

        return false;
    }

    private void showAllItems() {
    }

    private void showAllStores() {
        //TODO
    }

    private void loadNewXML() {
        Scanner xmlFilePathScanner = new Scanner(System.in);
        String filePath = xmlFilePathScanner.nextLine();

        try {
            //TODO add calling method of engine
            //TODO exception handling and printing messages
        }
        catch(Exception ex) {}

    }

    private void showMainMenu() {
        for (int i = 0; i < mainMenu.length; i++) {
            System.out.println(i+1 + "." + mainMenu[i]);
        }
    }

    private int getValidOption(int optionMaxBound) {
        Scanner scannerFromConsole = new Scanner(System.in);
        String userInput;
        boolean isValidOption = false;
        int optionChoose = 1;

        do {
            try{
                optionChoose =  Integer.parseInt(scannerFromConsole.nextLine());
                if(optionChoose >= 1 && optionChoose <= optionMaxBound) {
                    isValidOption = true;
                }
                else {
                    System.out.println("Please give a number in the range of 1 to " + optionMaxBound + "\n");
                }
            } catch(Exception ex) {
                System.out.println("Please give an integer in the range of 1 to " + optionMaxBound + "\n");
            }
        }while(!isValidOption);

        return optionChoose;
    }



}
