package model.exceptions;

/**
 * Throw if there are too many items in the list
 */

public class TooManyItemsException extends TodoListException {
    public TooManyItemsException(String msg) {
        super(msg);
    }

}
