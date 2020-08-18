import java.util.*;

public class Store
{
    private int id;
    private String name;
    private int deliveryPPK;
    private Location location;
    private Map<Integer,StoreItem> itemsThatSellInThisStore;
    private List<Order> orders=new LinkedList<Order>();

}
