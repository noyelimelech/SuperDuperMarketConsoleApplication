package SDM;

import java.util.*;

public class Store implements Locatable
{

    private int id;
    private String name;
    private int deliveryPPK;
    private Location location;
    private Map<Integer, StoreItem> itemsThatSellInThisStore=new HashMap<>();
    private List<Order> orders= new LinkedList<>();

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getDeliveryPPK()
    {
        return deliveryPPK;
    }

    public void setDeliveryPPK(int deliveryPPK)
    {
        this.deliveryPPK = deliveryPPK;
    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public Map<Integer, StoreItem> getItemsThatSellInThisStore()
    {
        return itemsThatSellInThisStore;
    }

    public void setItemsThatSellInThisStore(Map<Integer, StoreItem> itemsThatSellInThisStore)
    {
        this.itemsThatSellInThisStore = itemsThatSellInThisStore;
    }

    public List<Order> getOrders()
    {
        return orders;
    }

    public void setOrders(List<Order> orders)
    {
        this.orders = orders;
    }

    public double getTotalAmountForDeliveries()
    {
        double retAmountOfDeliveries=0;
        for (Order order:this.getOrders())
        {
            retAmountOfDeliveries+=order.getDeliveryPrice();
        }
        return (retAmountOfDeliveries);
    }
}
