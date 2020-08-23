package SDM;

import SDM.Costumer;
import SDM.Exception.*;
import SDM.Item;
import SDM.jaxb.schema.XMLHandlerBaseOnSchema;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SDMEngine
{
    private Map<Integer, Store> allStores = new HashMap<>();
    private Map<Integer, Item> allItems = new HashMap<>();
    private Map<Integer, Costumer> allCostumers = new HashMap<>();

   public List<Store> getAllStores() {
       return new ArrayList<>(allStores.values());
   }

   public List<Item> getAllItems() {
       return new ArrayList<>(allItems.values());
   }

   public void updateAllStoresAndAllItems(String stPath) throws DuplicateStoreIDException, DuplicateStoreItemException, LocationIsOutOfBorderException, JAXBException, FileNotFoundException, DuplicateItemException, FileNotEndWithXMLException
   {
       XMLHandlerBaseOnSchema xmlHandler=new XMLHandlerBaseOnSchema();
       xmlHandler.updateStoresAndItems(stPath);

       this.allItems=xmlHandler.getItems();

       //convert List of store to Map of<int id,Store)
       for(Store st: xmlHandler.getStores() )
       {
           this.allStores.put(st.getId(),st);
       }
   }

   public void CheckIfIsValidStoreId(int storeId) throws InvalidIdStoreChooseException
   {
       boolean flaIsValidIdStore= allStores.containsKey(storeId);
       if (!flaIsValidIdStore)
       {
           throw(new InvalidIdStoreChooseException(storeId));
       }
   }



}
