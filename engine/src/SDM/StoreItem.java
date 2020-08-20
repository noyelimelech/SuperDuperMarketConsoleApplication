package SDM;

import SDM.Item;
import SDM.Store;
import com.sun.xml.internal.bind.v2.TODO;

public class StoreItem
{
    private Item item;
    private int price;
    private Store store;

//constracture that call item constracture;?


    public Item getItem()
    {
        return item;
    }

    public void setItem(Item item)
    {
        this.item = item;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public Store getStore()
    {
        return store;
    }

    public void setStore(Store store)
    {
        this.store = store;
    }
}
