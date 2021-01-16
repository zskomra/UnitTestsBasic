package pl.testing.cart;

public interface CartHandler {

    boolean canHandleCart(Cart cart);
    void sendToPrepare(Cart cart);
}
