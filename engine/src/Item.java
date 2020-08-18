import java.util.Map;

public class Item
{
    private enum ItemType
    {
        QUANTITY,WIGHT
    }

    private int id;
    private String name;
    private ItemType type;
    private Map<Integer,Store> storesSellThisItem;

}
