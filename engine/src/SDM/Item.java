package SDM;

import java.util.HashMap;
import java.util.Map;

public class Item
{
    public enum ItemType
    {
        QUANTITY,WEIGHT
    }

    private int id;
    private String name;
    private ItemType type;
    private Map<Integer, Store> storesSellThisItem;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public Map<Integer, Store> getStoresSellThisItem() {
        return storesSellThisItem;
    }

    public void setStoresSellThisItem(Map<Integer, Store> storesSellThisItem) {
        this.storesSellThisItem = storesSellThisItem;
    }

    public Item(int id, String name)
    {
        this.id = id;
        this.name =name;
        //checkAndUpdateItemType(purchaseCategory);
        storesSellThisItem=null;
    }

    public void checkAndUpdateItemType(String purchaseCategory)
    {
        purchaseCategory=purchaseCategory.toUpperCase();
        this.type=ItemType.valueOf(purchaseCategory);
        ///לא ממש הבנתי מה קורה אם הסטרינג הוא לא אינם
    }

    public double getAveragePrice() {
        double avgPrice = 0;
        for (Store store : storesSellThisItem.values()) {
            avgPrice += store.getItemsThatSellInThisStore().get(this.id).getPrice();
        }
        avgPrice /= storesSellThisItem.size();

        return avgPrice;
    }

    public double getTotalSold() {
        return -1; //TODO
    }

}

