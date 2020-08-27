package SDM.Exception;

public class StoreWithNoItemException extends Exception {

    private final int Id;

    public StoreWithNoItemException(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }
}
