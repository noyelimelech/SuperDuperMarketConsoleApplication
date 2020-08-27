package SDM.Exception;

public class NegativeAmountOfItemInException extends Exception{

    private String currentAmount;
    private String amountTriedToAdd;

    public NegativeAmountOfItemInException(String currentAmount, String amountTriedToAdd) {
        this.currentAmount = currentAmount;
        this.amountTriedToAdd = amountTriedToAdd;
    }

    public String getCurrentAmount() {
        return currentAmount;
    }

    public String getAmountTriedToAdd() {
        return amountTriedToAdd;
    }
}
