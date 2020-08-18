import java.util.Map;

public class Cart
{
    private enum CartType
    {
        STATIC_CART,DYNAMIC_CART
    }

    private Map<Integer, Item> shoppingList;
    private CartType typeOfCart;
}
