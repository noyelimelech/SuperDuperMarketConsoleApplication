package SDM;

import java.util.HashMap;
import java.util.Map;

public class Costumer implements  Locatable
{
    private int id;
    private String name;
    private Map<Integer, Order> historyOrders;
    private Location location;

    public Costumer(int id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.historyOrders = new HashMap<>();
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addNewOrder(Order newOrder) {
        historyOrders.put(newOrder.getId(), newOrder);
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }
}
