package SDM;

import SDM.Exception.NegativeAmountOfItemInException;

public class OrderQuantityItem extends OrderItem {

    private int quantity = 0;

    public OrderQuantityItem(StoreItem itemInOrder) {
        super(itemInOrder);
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public double getTotalPrice() {
        return itemInOrder.getPrice() * quantity;
    }

    @Override
    public void clearAmount() {
        quantity = 0;
    }

    @Override
    public void addAmount(String quantityToAdd) throws NegativeAmountOfItemInException {
        int addedQuantity = Integer.parseInt(quantityToAdd);
        if(quantity + addedQuantity < 0) {
            throw new NegativeAmountOfItemInException(String.valueOf(quantity), quantityToAdd);
        }
        else {
            quantity += addedQuantity;
        }
    }

    @Override
    public void updateItemAmountSold() throws NegativeAmountOfItemInException {
        itemInOrder.getItem().addAmountThatSold(quantity);
    }

    @Override
    public void updateStoreItemAmountSold() throws NegativeAmountOfItemInException {
        itemInOrder.addAmountThatSold(quantity);
    }

    @Override
    public String getAmount() {
        return String.valueOf(quantity);
    }


}

