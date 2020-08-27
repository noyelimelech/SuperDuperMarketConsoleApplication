package SDM.Exception;

public class TryingToGiveDiffrentPricesForSameStoreItemException extends Exception
{

    private int storeId;

    public TryingToGiveDiffrentPricesForSameStoreItemException(int itemId)
    {
        this.storeId =itemId;
    }

    public int getStoreId()
    {
        return (storeId);
    }
}
