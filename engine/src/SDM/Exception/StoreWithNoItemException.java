package SDM.Exception;

public class StoreWithNoItemException extends Exception {
    int Id;

    public StoreWithNoItemException(int id) {
        Id = id;
    }

    public int getId() {
        return Id;
    }
}
