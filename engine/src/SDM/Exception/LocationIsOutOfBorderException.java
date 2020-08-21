package SDM.Exception;

public class LocationIsOutOfBorderException extends Exception
{
    private int minBorder;
    private int maxBorder;

    public LocationIsOutOfBorderException(int minBorder, int maxBorder)
    {
        this.minBorder=minBorder;
        this.maxBorder=maxBorder;
    }

    public int getMinBorder()
    {
        return minBorder;
    }


    public int getMaxBorder()
    {
        return maxBorder;
    }

}
