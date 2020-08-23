package SDM.Exception;

public class NegativeAmountOfItemInOrderException  extends Exception{
    private String currentAmount;
    private String amountTriedToAdd;

    public NegativeAmountOfItemInOrderException(String currentAmount, String amountTriedToAdd) {
        this.currentAmount = currentAmount;
        this.amountTriedToAdd = amountTriedToAdd;
    }
}
