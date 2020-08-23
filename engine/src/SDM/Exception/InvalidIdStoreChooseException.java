package SDM.Exception;

public class InvalidIdStoreChooseException extends Exception
{
    int invalidId;


    public InvalidIdStoreChooseException(int storeId)
    {
        invalidId=storeId;
    }
}
