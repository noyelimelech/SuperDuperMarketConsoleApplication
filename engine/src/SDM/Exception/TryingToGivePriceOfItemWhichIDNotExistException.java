package SDM.Exception;

public class TryingToGivePriceOfItemWhichIDNotExistException extends Exception
{
    private int id;

    public TryingToGivePriceOfItemWhichIDNotExistException(int itemId)
    {
        id=itemId;
    }

    public int getId()
    {
        return(this.id);
    }
}
