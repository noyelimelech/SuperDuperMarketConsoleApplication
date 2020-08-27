package SDM.Exception;

public class ItemNoOneSellException extends Exception {

    private final int Id;

    public ItemNoOneSellException(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }
}
