package SDM;

import java.awt.*;
import java.util.Objects;

public class Location
{
    public static final int minBorder=1;
    public static int maxBorder=50;

    private final Point location;

    public Location(Point p)
    {
        this.location = p;
    }

    public Point getLocation() {
        return location;
    }

    public static boolean checkIfIsLegalLocation(int x, int y)
    {
        return((x>=Location.minBorder&&x<=Location.maxBorder) && (y>=Location.minBorder&&y<=Location.maxBorder));
    }

    public static double distanceBetweenLocations(Location location1, Location location2) {
        return Math.sqrt(Math.pow(location1.getLocation().getX() - location2.getLocation().getX(),2)
                + Math.pow(location1.getLocation().getY() - location2.getLocation().getY(),2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location1 = (Location) o;
        return location.equals(location1.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }
}

