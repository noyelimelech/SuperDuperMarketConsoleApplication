package SDM;

import java.util.Map;

public class Costumer implements  Locatable
{
    private int id;
    private String name;
    private Map<Integer, Order> historyOrders;
    private Location location;

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    public Costumer(int id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.historyOrders = null;
        this.location = location;
    }
}
