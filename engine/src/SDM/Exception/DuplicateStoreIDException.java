package SDM.Exception;

public class DuplicateStoreIDException extends Exception
{
    int id;

    public DuplicateStoreIDException(int id)
    {
        this.id=id;
    }

    public int getId() {
        return id;
    }
}


