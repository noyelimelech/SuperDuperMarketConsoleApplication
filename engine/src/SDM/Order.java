package SDM;

import SDM.Exception.NegativeAmountOfItemInException;

import java.util.*;

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
        orderList = new HashMap<>();
    }

    public static Order makeNewOrder(Costumer costumer, Date date, Store storeOrderMadeFrom) {
        return new Order(costumer, date, storeOrderMadeFrom);
    }

    public double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void addItemToOrder(StoreItem itemToAdd, String amountToAdd) throws NegativeAmountOfItemInException {
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
        orderList.forEach((orderItemID, orderItem) -> {
            try {
                orderItem.updateItemAmountSold();
                orderItem.updateStoreItemAmountSold();
            } catch (NegativeAmountOfItemInException e) {
                orderItem.clearAmount();
            }
        });
    }

    private double calculatePriceOfOrderItems() {
        return orderList.values().stream().mapToDouble(OrderItem::getTotalPrice).sum();
    }

    public double distanceBetweenCostumerAndStore() {
        return Location.distanceBetweenLocations(costumer.getLocation(), storeOrderMadeFrom.getLocation());
    }


    public int getId() {
        return id;
    }

    public Map<Integer, OrderItem> getOrderList() {
        return orderList;
    }

    public Store getStoreOrderMadeFrom() {
        return storeOrderMadeFrom;
    }

    public Costumer getCostumer() {
        return costumer;
    }

    public double getPriceOfAllItems() {
        return priceOfAllItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getDate() {
        return date;
    }


    //Noy's job
    public int getTotalItemsInOrder()
    {
        int totalItems=0;

        /*
        for (OrderItem orderitem:orderList.values())
        {

         */

        for (Map.Entry<Integer, OrderItem> iterator : orderList.entrySet())
        {

            Integer key = iterator.getKey();
            OrderItem orderItem = iterator.getValue();
            Item.ItemType type=orderItem.getItemInOrder().getItem().getType();

            switch (type)
            {
                case QUANTITY:
                    totalItems+=((OrderQuantityItem)orderItem).getQuantity();
                    break;
                case WEIGHT:
                    totalItems+=1;
                    break;
            }
        }
        return (totalItems);
    }
}
