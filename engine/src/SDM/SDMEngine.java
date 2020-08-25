package SDM;

import SDM.Exception.*;
import SDM.jaxb.schema.XMLHandlerBaseOnSchema;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.*;

import static SDM.Item.ItemType.QUANTITY;

public class SDMEngine {
    private Map<Integer, Store> allStores = new HashMap<>();
    private Map<Integer, Item> allItems = new HashMap<>();
    private Map<Integer, Costumer> allCostumers = new HashMap<>();
    private List<Order> allOrders;
    private Order currentOrder;
    private Map<Integer, StoreItem> allStoreItemsWithPriceForSpecificStore = new HashMap<>(); //private Map for storeItems to show to UI

    public List<StoreItem> getAllStoreItemsWithPriceForSpecificStore() {
        return new ArrayList<>(allStoreItemsWithPriceForSpecificStore.values());
    }

    public List<Store> getAllStores() {
        return new ArrayList<>(allStores.values());
    }

    public Map<Integer, Store> getAllStoresMap() {
        return this.allStores;
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(allItems.values());
    }

    public void updateAllStoresAndAllItems(String stPath) throws DuplicateStoreIDException, DuplicateStoreItemException, LocationIsOutOfBorderException, JAXBException, FileNotFoundException, DuplicateItemException, FileNotEndWithXMLException {
        XMLHandlerBaseOnSchema xmlHandler = new XMLHandlerBaseOnSchema();
        xmlHandler.updateStoresAndItems(stPath);

        this.allItems = xmlHandler.getItems();

        //convert List of store to Map of<int id,Store)
        for (Store st : xmlHandler.getStores()) {
            this.allStores.put(st.getId(), st);
        }

        ////////////////////////////Noy's job
        for (Item item : this.allItems.values()) {
            for (Store st : allStores.values()) {
                if (st.getItemsThatSellInThisStore().containsKey(item.getId())) {
                    item.setStoresSellThisItem(st);
                }
            }
        }


    }

    public void CheckIfIsValidStoreId(int storeId) throws InvalidIdStoreChooseException {
        boolean flaIsValidIdStore = allStores.containsKey(storeId);
        if (!flaIsValidIdStore) {
            throw (new InvalidIdStoreChooseException(storeId));
        }
    }


    public boolean checkIfThisLocationInUsedOfStore(Location costumerLocationToCheck) {
        boolean flagIsValidCostumerLocation = true;

        for (Store st : this.getAllStores()) {
            if (st.getLocation() == costumerLocationToCheck)
                flagIsValidCostumerLocation = false;

        }
        return flagIsValidCostumerLocation;


    }

    //Noy's job
    public void createNewOrder(Costumer costumerEX1, Date dateOrder, Store store) {
        currentOrder.makeNewOrder(costumerEX1, dateOrder, this.allStores.get(store));
    }

    //noy job
    public void updateAllStoreItemsForSaleInCurrentStoreOrder() {
        for (Item item : allItems.values()) {
            StoreItem storeItem = new StoreItem();
            storeItem.setItem(item);
            storeItem.setStore(currentOrder.getStoreOrderMadeFrom());
            int priceOfItem = getPriceOfItemInThisStoreORZero(item.getId(), currentOrder.getStoreOrderMadeFrom());
            allStoreItemsWithPriceForSpecificStore.put(item.getId(), storeItem);
        }
    }

    //Noy's job
    private int getPriceOfItemInThisStoreORZero(int itemId, Store store) {
        int resPrice = 0;

        if (!(store.getItemsThatSellInThisStore().containsKey(itemId))) {
            resPrice = currentOrder.getStoreOrderMadeFrom().getItemsThatSellInThisStore().get(itemId).getPrice();
        }

        return resPrice;
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public boolean checkIfThisValidItemId(int choosedItemNumber) {
        if (allStoreItemsWithPriceForSpecificStore.containsKey(choosedItemNumber)) {
            return true;
        } else
            return false;
    }

    public boolean checkIfItemPriceIsNotZero(int choosedItemNumber) {
        if ((allStoreItemsWithPriceForSpecificStore.get(choosedItemNumber).getPrice()) != 0) {
            return true;
        } else
            return false;
    }
    
    public Item.ItemType getItemTypeByID(int itemID) {
        return allItems.get(itemID).getType();
    }

    public void addItemToCurrentOrder(int choosedItem, String choosedAmountOfItem) throws NegativeAmountOfItemInException
    {
        StoreItem storItemToAddToOrder = currentOrder.getStoreOrderMadeFrom().getItemsThatSellInThisStore().get(choosedItem);
        this.currentOrder.addItemToOrder(storItemToAddToOrder,choosedAmountOfItem);
    }

    public void completeCurrentOrder() {
        currentOrder.completeOrder();
        allOrders.add(currentOrder);
        currentOrder = null;
    }
}

