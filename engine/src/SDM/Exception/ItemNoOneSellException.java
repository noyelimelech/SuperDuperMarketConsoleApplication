package SDM.Exception;

public class ItemNoOneSellException extends Exception {

    private int Id;

    public ItemNoOneSellException(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }
}
