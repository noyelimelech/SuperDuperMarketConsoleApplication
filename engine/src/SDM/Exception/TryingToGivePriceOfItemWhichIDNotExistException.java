package SDM.Exception;

public class TryingToGivePriceOfItemWhichIDNotExistException extends Exception
{
    private final int id;

    public TryingToGivePriceOfItemWhichIDNotExistException(int itemId)
    {
        id=itemId;
    }

    public int getId()
    {
        return(this.id);
    }
}
