package SDM.Exception;

public class DuplicateItemException extends Exception
{
    int id;

    public DuplicateItemException(int id)
    {
        this.id=id;
    }

    public int getId()
    {
        return id;
    }
}
