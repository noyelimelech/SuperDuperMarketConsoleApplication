package SDM.Exception;

public class LocationIsOutOfBorderException extends Exception
{
    private final int minBorder;
    private final int maxBorder;
    private final String locatableType;
    private final int id;

    public LocationIsOutOfBorderException(int minBorder, int maxBorder, String locatableType, int id)
    {
        this.minBorder=minBorder;
        this.maxBorder=maxBorder;
        this.locatableType = locatableType;
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

    public String getLocatableType() {
        return locatableType;
    }

    public int getId() {
        return id;
    }
}
