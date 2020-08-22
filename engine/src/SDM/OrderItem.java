package SDM;

import SDM.Exception.NegativeAmountOfItemInOrderException;

public abstract class OrderItem {

    public static class Factory {
        public static OrderItem makeNewOrderItem(StoreItem storeItem ) {
            OrderItem newItemOrder;

            switch (storeItem.getItem().getType()) {
                case QUANTITY:
                    newItemOrder = new OrderQuantityItem(storeItem);
                    break;
                case WEIGHT:
                    newItemOrder = new OrderWeightItem(storeItem);
                    break;
                default:
                    newItemOrder = null;
            }

            return newItemOrder;
        }
    }


    protected StoreItem itemInOrder;

    public OrderItem(StoreItem itemInOrder) {
        this.itemInOrder = itemInOrder;
    }

    public abstract double getTotalPrice();

    public abstract void addAmount(String amountToAdd) throws NegativeAmountOfItemInOrderException;

    public StoreItem getItemInOrder() {
        return itemInOrder;
    }
}
