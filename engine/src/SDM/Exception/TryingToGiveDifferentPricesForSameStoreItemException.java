package SDM.Exception;

public class TryingToGiveDifferentPricesForSameStoreItemException extends Exception
{

    private final int storeId;

    public TryingToGiveDifferentPricesForSameStoreItemException(int itemId)
    {
        this.storeId =itemId;
    }

    public int getStoreId()
    {
        return (storeId);
    }
}
