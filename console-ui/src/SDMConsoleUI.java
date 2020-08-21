import SDM.*;

import java.nio.file.Files;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

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
        int i = 1;
        for(Item item : engine.getAllItems()) {
            System.out.print(i + ".");
            showItem(item);
        }
    }

    private void showItem(Item item) {
    }

    private void showAllStores() {
        int i = 1;
        for(Store store : engine.getAllStores()) {
            System.out.print(i + ".");
            showStore(store);
        }
    }

    private void showStore(Store store) {
        final AtomicInteger i = new AtomicInteger(1); //the only way to have an "i" in foreach loop inside of map is in this way, atomic integer
                                                                //and the getAndIncrement method...

        System.out.println("1.ID:" + store.getId());
        System.out.println("2.Name:" + store.getName());
        System.out.println("3.Items sold by this store:");
        store.getItemsThatSellInThisStore().forEach(((storeItemID ,storeItem)-> {
            System.out.print(i.getAndIncrement() + ".");
            showStoreItem(storeItem);
        }));

        System.out.println("4.Orders made by this store:");
        i.set(1);
        if(store.getOrders().isEmpty()) {
            System.out.println("This store hadn't made any orders yet.");
        }
        else {
            for (Order order : store.getOrders()) {
                 System.out.print(i.getAndIncrement() + ".");
                showOrderInShowStoreChoice(order);
            }
        }

        System.out.println("5.PPK: " + store.getDeliveryPPK());
        System.out.println("6.Amount of money got only for deliveries: "); //TODO total amount for delivery should be a method in store, needed to be added..
    }

    //TODO
    private void showOrderInShowStoreChoice(Order order) {
    }

    //TODO
    private void showStoreItem(StoreItem storeItem) {
        //*showItemBasicData(storeItem.getItem());*//
        System.out.println("d.price for : "); //TODO
        System.out.println("e.Total sold: "/*storeItem.totalSold()*/);
    }

    void showItemBasicData(Item itemToShow) {
        System.out.println("a.ID: "/*storeItem.getItem.getID()*/);
        System.out.println("b.Name: "/*storeItem.getItem.getName()*/);
        System.out.println("c.Purchase type: "/*storeItem.getItem.getItemType()*/);
        //TODO need to make this enum to print his name when calling to toString, read about it..
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
