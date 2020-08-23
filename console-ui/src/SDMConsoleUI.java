import SDM.*;
import SDM.Exception.FileNotEndWithXMLException;
import SDM.Exception.InvalidIdStoreChooseException;
import SDM.Exception.LocationIsOutOfBorderException;

import java.awt.*;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class SDMConsoleUI
{
    private SDMEngine engine;
    private final String[] mainMenu = {"Load new XML file.","Show all stores information","Show all items information.","Make new order.","Show all orders.","Exit."};

    public SDMConsoleUI() {
        this.engine = new SDMEngine();
    }

    public void start() {
        boolean isFinished;
        int optionChoose;

        do {
            showMainMenu();
            optionChoose = getValidOption(mainMenu.length);
            isFinished = executeOptionChoose(optionChoose, mainMenu.length);
            System.out.println("");
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
                MakeNewOrder();
                break;
            case 5:
                //TODO show all orders
                break;
        }

        return false;
    }

    private void MakeNewOrder()
    {
        int chooseStore= getFromUserChooseStore();
        Date dateOrder=getValidDateFromCostumer();
        Location costumerLocation=getValidCostumerLocation();
        Costumer costumerEX1=new Costumer(1234,"user",costumerLocation);

        //new Location(new Point(getValidOption(Location.maxBorder),getValidOption(Location.maxBorder)));

        Order.makeNewOrder(costumerEX1,dateOrder,engine.getAllStoresMap().get(chooseStore));
    }

    private Location getValidCostumerLocation()
    {

        boolean flagIsValidCostumerLocation = false;
        Location costumerLocation;
        do {
            System.out.println("Please insert your X location ");
            int x = getValidOption(Location.maxBorder);
            System.out.println("Please insert your y location ");
            int y = getValidOption(Location.maxBorder);
            costumerLocation =new Location(new Point(x,y));

            flagIsValidCostumerLocation=engine.checkIfThisLocationInUsedOfStore(costumerLocation);
        }
        while(!flagIsValidCostumerLocation);

        return (costumerLocation);
    }

    private int getFromUserChooseStore()
    {
        boolean flagIsValidChoose;
        int idStoreChoose;

        do{
            System.out.println("Please select an id store from the stores below: ");

            for(Store st: engine.getAllStores())
            {
                System.out.println("\n");
                showStoreBasicDetails(st);
            }

            idStoreChoose=getChooseFromUser();

            try
            {
                engine.CheckIfIsValidStoreId(idStoreChoose);
                flagIsValidChoose=true;

            }
            catch (InvalidIdStoreChooseException e)
            {
                flagIsValidChoose=false;
                System.out.println("the id store is not correct, please try again ");
            }
        }
        while (!flagIsValidChoose);

        return(idStoreChoose);

        /*
        while (!flagIsValidChoose)
        {
            idStoreChoose=getChooseFromUser();
            flagIsValidChoose=engine.CheckIfIsValidStoreId(idStoreChoose);
            if (!flagIsValidChoose)
            {
                System.out.println("the id store is not correct, please try again ");
            }
        }
        return(idStoreChoose);

         */

    }

    private int getChooseFromUser()
    {
        Scanner scan = new Scanner(System.in);
        int choose = scan.nextInt();
        return (choose);
    }

    private Date getValidDateFromCostumer()
    {
        Scanner scanner = new Scanner(System.in);

        boolean flagIsValidDate = true;

        do {
            System.out.println("Enter the Date ,in format: d/M-hh:mm");
            String date = scanner.next();
            SimpleDateFormat dateFormat = new SimpleDateFormat("d/M-hh:mm");
            Date date2 = null;

            try
            {
                //Parsing the String
                date2 = dateFormat.parse(date);
            }
            catch (ParseException e)
            {
                System.out.println("the date is not in the flowing format:  dd/mm-hh:mm \n please try again");
                flagIsValidDate = false;
            }

            return(date2);
        }
        while (!flagIsValidDate);
    }

    private void showStoreBasicDetails(Store st)
    {
        System.out.println("    ID:" + st.getId());
        System.out.println("    Name:" + st.getName());
        System.out.println("    PPK: " + st.getDeliveryPPK());
    }







    private void showAllItems() {
        int i = 1;
        for(Item item : engine.getAllItems()) {
            System.out.println("####ITEM NUMBER " + i + "####");
            showItem(item);
            i++;
            System.out.println("");
        }
    }

    private void showItem(Item item) {
        showItemBasicData(item, "", '1');
        System.out.println("    4.Number of stores sell this item: " + item.getStoresSellThisItem().size());
        System.out.println("    5.Average price of this item: " + item.getAveragePrice());
        System.out.println("    6.Total amount that has been sold: " + item.getTotalAmountSoldOnAllStores() + (item.getType() == Item.ItemType.QUANTITY ? " pieces" : " KG"));

    }

    private void showAllStores() {
        int i = 1;
        System.out.println("");
        for(Store store : engine.getAllStores()) {
            System.out.println("####STORE NUMBER " + i + "####");
            showStore(store);
            i++;
            System.out.println("");
        }
    }

    private void showStore(Store store) {
        final AtomicInteger i = new AtomicInteger(1); //the only way to have an "i" in foreach loop inside of map is in this way, atomic integer
                                                                //and the getAndIncrement method...

        System.out.println("1.ID:" + store.getId());
        System.out.println("2.Name:" + store.getName());
        System.out.println("3.Items sold by this store:");
        store.getItemsThatSellInThisStore().forEach(((storeItemID ,storeItem)-> {
            //System.out.print(i.getAndIncrement() + ".");
            System.out.println("    ##Store-Item number " + i.getAndIncrement()+ "##");

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
        System.out.println("6.Amount of money got only for deliveries: "+ store.getTotalAmountForDeliveries()); //total amount for delivery should be a method in store, needed to be added..
    }

    //Noy's job
    private void showOrderInShowStoreChoice(Order order)
    {

        System.out.println("a.Date of order: " + order.getDate());
        System.out.println("b.Total items: " + order.getTotalItemsInOrder());//Noy's job---> method in Order "getTotalItemsInOrder".
        System.out.println("c.Total price of all items: " + order.getPriceOfAllItems());
        System.out.println("d.Delivery price: " + order.getDeliveryPrice());
        System.out.println("e.Total order price: " + order.getDeliveryPrice()+order.getPriceOfAllItems());
    }

    private void showStoreItem(StoreItem storeItem) {
        showItemBasicData(storeItem.getItem(),"\t", 'a');
        if(storeItem.getItem().getType() == Item.ItemType.QUANTITY) {
            System.out.println('\t'+ "    d.price for 1 item is: " + storeItem.getPrice());
        }
        else {
            System.out.println('\t'+ "    d.price for 1kg is: " + storeItem.getPrice());
        }

        System.out.println('\t'+ "    e.Total sold: " + storeItem.getTotalAmountSoldInThisStore());     //TODO Daniel---> StoreItem + OrderItem+ Item--> need method in storeItem that says how many of this item has been sold
    }

    void showItemBasicData(Item itemToShow, String linePrefix, char countingPrefix) {
        System.out.println("    "+linePrefix + countingPrefix + ".ID: " + itemToShow.getId());
        System.out.println("    "+linePrefix + (++countingPrefix) + ".Name: " +  itemToShow.getName());
        System.out.println("    "+linePrefix + (++countingPrefix) + ".Purchase type: " + itemToShow.getType());

    }

    private void loadNewXML() {
        Scanner xmlFilePathScanner = new Scanner(System.in);
        System.out.println("Please enter your XML file path: ");
        String filePath = xmlFilePathScanner.nextLine();

        try {
            engine.updateAllStoresAndAllItems(filePath);
            System.out.println("XML file loaded successfully!");
        }
        catch (FileNotFoundException ex) {
            System.out.println("ERROR: The file does not exist in the path given, please try again.");
        }
        catch(FileNotEndWithXMLException ex) {
            System.out.println("ERROR: The file you given is not an XML file, please make sure it ends with .xml and try again");
        }
        catch(LocationIsOutOfBorderException ex) {
            System.out.println("ERROR: The object of type " +  ex.getLocatableType()+
                    "with id of: " + ex.getId() + " is located out of allowed borders which are between "
                    + ex.getMinBorder() + "to " + ex.getMaxBorder() + ".Please fix this ");
        }
        catch(Exception ex) {
            System.out.println("ERROR: Unknown error has happen, the error message is: " + ex.getMessage());
        }

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
                System.out.print("Please enter your choice: ");
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
