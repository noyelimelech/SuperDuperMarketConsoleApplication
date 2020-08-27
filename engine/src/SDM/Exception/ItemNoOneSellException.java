package SDM.Exception;

public class ItemNoOneSellException extends Exception {
    private int Id;

    public int getId() {
        return Id;
    }

    public ItemNoOneSellException(int id) {
        Id = id;
    }
}
