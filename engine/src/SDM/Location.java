package SDM;

import java.awt.*;

public class Location
{
    public static final int minBorder=1;
    public static int maxBorder=50;

    private Point location;

    public Location(Point p)
    {
        this.location = p;
    }



    public static boolean checkIfIsLegalLocation(int x, int y)
    {
        return((x>=Location.minBorder&&x<=Location.maxBorder) && (y>=Location.minBorder&&y<=Location.maxBorder));
    }

}

