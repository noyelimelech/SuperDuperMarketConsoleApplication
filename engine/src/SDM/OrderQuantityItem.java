package SDM;

import SDM.Exception.NegativeAmountOfItemInOrderException;

public class OrderQuantityItem extends OrderItem {

    private int quantity = 0;

    public OrderQuantityItem(StoreItem itemInOrder) {
        super(itemInOrder);
    }

    @Override
    public double getTotalPrice() {
        return itemInOrder.getPrice() * quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public void addAmount(String quantityToAdd) throws NegativeAmountOfItemInOrderException {
        int addedQuantity = Integer.parseInt(quantityToAdd);
        if(quantity + addedQuantity < 0) {
            throw new NegativeAmountOfItemInOrderException(String.valueOf(quantity), quantityToAdd);
        }
        else {
            quantity += addedQuantity;
        }
    }


}

