package SDM.Exception;

public class NegativeAmountOfItemInException extends Exception{
    private String currentAmount;
    private String amountTriedToAdd;

    public String getCurrentAmount() {
        return currentAmount;
    }

    public String getAmountTriedToAdd() {
        return amountTriedToAdd;
    }

    public NegativeAmountOfItemInException(String currentAmount, String amountTriedToAdd) {
        this.currentAmount = currentAmount;
        this.amountTriedToAdd = amountTriedToAdd;
    }
}
