package SDM;

import SDM.Costumer;
import SDM.Item;

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


}
