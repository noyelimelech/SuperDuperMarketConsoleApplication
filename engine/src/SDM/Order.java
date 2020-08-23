package SDM;

import SDM.Exception.NegativeAmountOfItemInOrderException;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.*;
import java.util.stream.DoubleStream;

public class Order
{
    private static int idCounter = 1;
    private int id;
    private Map<Integer, OrderItem> orderList;
    private Store storeOrderMadeFrom;
    private double deliveryPrice;
    private Costumer costumer;
    private Date date;
    private double priceOfAllItems;
    private double totalPrice;

    private Order(Costumer costumer, Date date, Store storeOrderMadeFrom) {
        this.costumer = costumer;
        this.date = date;
        this.id = idCounter;
        this.storeOrderMadeFrom = storeOrderMadeFrom;
        orderList = new HashMap<Integer, OrderItem>();
    }

    public static Order makeNewOrder(Costumer costumer, Date date, Store storeOrderMadeFrom) {
        return new Order(costumer, date, storeOrderMadeFrom);
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

    public void completeOrder() {
        idCounter++;
        deliveryPrice = storeOrderMadeFrom.getDeliveryPPK() * distanceBetweenCostumerAndStore();
        priceOfAllItems = calculatePriceOfOrderItems();
        totalPrice = priceOfAllItems + deliveryPrice;
    }

    private double calculatePriceOfOrderItems() {
        return orderList.values().stream().mapToDouble((orderItem) -> orderItem.getTotalPrice()).sum();
    }

    public double distanceBetweenCostumerAndStore() {
        return Location.distanceBetweenLocations(costumer.getLocation(), storeOrderMadeFrom.getLocation());
    }

}
