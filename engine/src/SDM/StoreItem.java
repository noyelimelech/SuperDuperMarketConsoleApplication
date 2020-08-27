package SDM;

import SDM.Exception.NegativeAmountOfItemInException;

public class StoreItem
{
    private Item item;
    private int price;
    private Store store;
    private double totalAmountSoldInThisStore = 0;

    public double getTotalAmountSoldInThisStore() {
        return totalAmountSoldInThisStore;
    }

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

    public void addAmountThatSold(int amountToAdd) throws NegativeAmountOfItemInException {
        addAmountThatSold((double)amountToAdd);
    }

    public void addAmountThatSold(double amountToAdd) throws NegativeAmountOfItemInException {
        if(amountToAdd + totalAmountSoldInThisStore < 0) {
            throw new NegativeAmountOfItemInException(String.valueOf(totalAmountSoldInThisStore), String.valueOf(amountToAdd));
        }
        else {
            totalAmountSoldInThisStore += amountToAdd;
        }
    }
}
