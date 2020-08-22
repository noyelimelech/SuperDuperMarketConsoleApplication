package SDM;

import SDM.Exception.NegativeAmountOfItemInOrderException;

public class OrderWeightItem extends OrderItem{

    private double weightOfItem = 0;

    public OrderWeightItem(StoreItem itemInOrder) {
        super(itemInOrder);
    }

    public double getWeightOfItem() {
        return weightOfItem;
    }

    @Override
    public void addAmount(String weightToAdd) throws NegativeAmountOfItemInOrderException {
        double addedWeight = Double.parseDouble(weightToAdd);

        if(weightOfItem + addedWeight < 0) {
            throw new NegativeAmountOfItemInOrderException(String.valueOf(weightOfItem), weightToAdd);
        }
        else {
            weightOfItem += addedWeight;
        }
    }
}
