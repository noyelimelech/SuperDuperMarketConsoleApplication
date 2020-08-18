import java.util.Date;
import java.util.List;

public class Order
{
    private int id;
    private List<StoreItem> orderList;
    private int deliveryPrice;
    private Costumer costumer;
    private Date date;
    private int priceOfAllItems;
    private int totalPrice;
}
