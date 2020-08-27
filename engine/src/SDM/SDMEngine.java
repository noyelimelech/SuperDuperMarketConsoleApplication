package SDM;

import SDM.Exception.*;
import SDM.jaxb.schema.XMLHandlerBaseOnSchema;

import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.*;

public class SDMEngine {
    private Map<Integer, Store> allStores = new HashMap<>();
    private Map<Integer, Item> allItems = new HashMap<>();
    private Map<Integer, Costumer> allCostumers = new HashMap<>();
    private List<Order> allOrders;
    private Order currentOrder;
    private Map<Integer, StoreItem> allStoreItemsWithPriceForSpecificStore = new HashMap<>(); //private Map for storeItems to show to UI
    private boolean xmlFileLoaded = false;

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

    public void updateAllStoresAndAllItems(String stPath) throws DuplicateStoreIDException, DuplicateStoreItemException, LocationIsOutOfBorderException, JAXBException, FileNotFoundException, DuplicateItemException, FileNotEndWithXMLException, TryingToGivePriceOfItemWhichIDNotExistException, TryingToGiveDiffrentPricesForSameStoreItemException, ItemNoOneSellException, StoreWithNoItemException {
        Map<Integer, Item> tempAllItems = new HashMap<>();
        Map<Integer, Store> tempAllStores = new HashMap<>();

        XMLHandlerBaseOnSchema xmlHandler = new XMLHandlerBaseOnSchema();
        xmlHandler.updateStoresAndItems(stPath);

        tempAllItems = xmlHandler.getItems();

        //convert List of store to Map of<int id,Store)
        for (Store st : xmlHandler.getStores()) {
            tempAllStores.put(st.getId(), st);
        }

        ////////////////////////////Noy's job
        for (Item item : tempAllItems.values()) {
            for (Store st : tempAllStores.values()) {
                if (st.getItemsThatSellInThisStore().containsKey(item.getId())) {
                    item.setStoresSellThisItem(st);
                }
            }
        }

        verifyEveryItemSoldByAtLeastOneStore(tempAllItems);
        verifyEveryStoreSellAtLeastOneItem(tempAllStores);
        xmlFileLoaded = true;
        allStores = tempAllStores;
        allItems = tempAllItems;
        allOrders = new LinkedList<>();
    }

    private void verifyEveryStoreSellAtLeastOneItem(Map<Integer, Store> tempAllStores) throws StoreWithNoItemException {
        for(Store store : tempAllStores.values()) {
            if(store.getItemsThatSellInThisStore().size() == 0) {
                throw new StoreWithNoItemException(store.getId());
            }
        }
    }

    private void verifyEveryItemSoldByAtLeastOneStore(Map<Integer, Item> tempAllItems) throws ItemNoOneSellException {
        for(Item item : tempAllItems.values()) {
            if(item.getStoresSellThisItem().size() == 0) {
                throw new ItemNoOneSellException(item.getId());
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
            if (st.getLocation().equals(costumerLocationToCheck))
                flagIsValidCostumerLocation = false;
        }
        return flagIsValidCostumerLocation;


    }

    //Noy's job
    public void createNewOrder(Costumer costumerEX1, Date dateOrder, Store store) {
        currentOrder = Order.makeNewOrder(costumerEX1, dateOrder, store);
    }

    //noy job
    public void updateAllStoreItemsForSaleInCurrentStoreOrder(Store store) {
        for (Item item : allItems.values()) {
            StoreItem storeItem = new StoreItem();
            storeItem.setItem(item);
            //currentOrder.setStoreOrderMadeFrom(store);
            storeItem.setStore(store);
            int priceOfItem = getPriceOfItemInThisStoreORZero(item.getId(),store);
            storeItem.setPrice(priceOfItem);
            allStoreItemsWithPriceForSpecificStore.put(item.getId(), storeItem);
        }
    }

    //Noy's job
    private int getPriceOfItemInThisStoreORZero(int itemId, Store store) {
        int resPrice = 0;

        if (store.getItemsThatSellInThisStore().containsKey(itemId)) {
            resPrice = store.getItemsThatSellInThisStore().get(itemId).getPrice();
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

    public List<Order> getAllOrders() {
        return allOrders;
    }

    public boolean isXMLFileLoaded() {
        return xmlFileLoaded;
    }
}

