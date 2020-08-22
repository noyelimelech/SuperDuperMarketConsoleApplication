package SDM;

import SDM.Exception.NegativeAmountOfItemInOrderException;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.*;

public class Order
{
    private static int idCounter = 1;
    private int id;
    private Map<Integer, OrderItem> orderList;
    private int deliveryPrice;
    private Costumer costumer;
    private Date date;
    private int priceOfAllItems;
    private int totalPrice;

    private Order(Costumer costumer, Date date) {
        this.costumer = costumer;
        this.date = date;
        this.id = idCounter;
        orderList = new HashMap<Integer, OrderItem>();
    }

    public static Order makeNewOrder(Costumer costumer, Date date)
    {
        return new Order(costumer, date);
    }

    public void addItemToOrder(StoreItem itemToAdd, String amountToAdd) throws NegativeAmountOfItemInOrderException {
        if(orderList.containsKey(itemToAdd.getItem().getId())) {
            orderList.get(itemToAdd.getItem().getId()).addAmount(amountToAdd);
        }
        else {
            OrderItem newItemInOrder = OrderItem.Factory.makeNewOrderItem(itemToAdd);
            newItemInOrder.addAmount(amountToAdd);
            orderList.put(itemToAdd.getItem().getId(), newItemInOrder);
        }
    }

}
