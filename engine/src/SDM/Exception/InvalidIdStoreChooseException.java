package SDM.Exception;

public class InvalidIdStoreChooseException extends Exception
{
    private final int invalidId;

    public InvalidIdStoreChooseException(int storeId)
    {
        invalidId=storeId;
    }

    public int getInvalidId() {
        return invalidId;
    }
}
