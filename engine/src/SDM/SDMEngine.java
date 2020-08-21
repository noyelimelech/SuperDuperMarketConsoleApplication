package SDM;

import SDM.Costumer;
import SDM.Item;
import SDM.jaxb.schema.XMLHandlerBaseOnSchema;

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

   public void updateAllStoresAndAllItems()
   {
       XMLHandlerBaseOnSchema xmlHandler=new XMLHandlerBaseOnSchema();

       this.allItems=xmlHandler.getItems();

       //convert List of store to Map of<int id,Store)
       for(Store st: xmlHandler.getStores() )
       {
           this.allStores.put(st.getId(),st);
       }
       //List<Item> list;
       //Map<Key,Item> map = new HashMap<Key,Item>();
       //for (Item i : list) map.put(i.getKey(),i);
   }
}
