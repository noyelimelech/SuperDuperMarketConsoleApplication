package SDM.Exception;

public class DuplicateStoreItemException extends Exception
{
    int id;

    public DuplicateStoreItemException(int itemId)
    {
        this.id=itemId;
    }

    public int getId() {
        return id;
    }
}
