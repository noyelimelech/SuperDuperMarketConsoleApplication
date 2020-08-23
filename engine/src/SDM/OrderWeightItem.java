package SDM;

import SDM.Exception.NegativeAmountOfItemInException;

public class OrderWeightItem extends OrderItem{

    private double weightOfItem = 0;

    public OrderWeightItem(StoreItem itemInOrder) {
        super(itemInOrder);
    }

    @Override
    public double getTotalPrice() {
        return itemInOrder.getPrice() * weightOfItem;
    }

    @Override
    public void clearAmount() {
        weightOfItem = 0;
    }

    public double getWeightOfItem() {
        return weightOfItem;
    }

    @Override
    public void addAmount(String weightToAdd) throws NegativeAmountOfItemInException {
        double addedWeight = Double.parseDouble(weightToAdd);

        if(weightOfItem + addedWeight < 0) {
            throw new NegativeAmountOfItemInException(String.valueOf(weightOfItem), weightToAdd);
        }
        else {
            weightOfItem += addedWeight;
        }
    }

    @Override
    public void updateItemAmountSold() throws NegativeAmountOfItemInException {
        itemInOrder.getItem().addAmountThatSold(weightOfItem);
    }
}
