package SDM.Exception;

import SDM.Locatable;

public class LocationIsOutOfBorderException extends Exception
{
    private int minBorder;
    private int maxBorder;
    private Locatable missLocatedObject;
    private int id;

    public LocationIsOutOfBorderException(int minBorder, int maxBorder, Locatable missLocatedObject, int id)
    {
        this.minBorder=minBorder;
        this.maxBorder=maxBorder;
        this.missLocatedObject = missLocatedObject;
        this.id = id;
    }

    public int getMinBorder()
    {
        return minBorder;
    }


    public int getMaxBorder()
    {
        return maxBorder;
    }

    public Locatable getMissLocatedObject() {
        return missLocatedObject;
    }

    public int getId() {
        return id;
    }
}
