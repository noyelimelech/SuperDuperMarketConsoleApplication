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

    public Map<Integer,Store> getAllStoresMap()
    {
        return this.allStores;
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

 ////////////////////////////Noy's job
       for (Item item:this.allItems.values())
       {
           for (Store st:allStores.values())
           {
               if(st.getItemsThatSellInThisStore().containsKey(item.getId()))
               {
                   item.setStoresSellThisItem(st);
               }
           }
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


    public boolean checkIfThisLocationInUsedOfStore(Location costumerLocationToCheck)
    {
        boolean flagIsValidCostumerLocation=true;

        for (Store st:this.getAllStores())
        {
            if(st.getLocation()==costumerLocationToCheck)
                flagIsValidCostumerLocation=false;

        }
        return flagIsValidCostumerLocation;



    }
}
