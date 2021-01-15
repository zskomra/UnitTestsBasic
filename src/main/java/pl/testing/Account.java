package pl.testing;

public class Account {

    private boolean active;
    private Address defaultDeliveryAddress;

    public void setDefaultDeliveryAddress(Address defaultDeliveryAddress) {
        this.defaultDeliveryAddress = defaultDeliveryAddress;
    }

    public Address getDefaultDeliveryAddress() {
        return defaultDeliveryAddress;
    }

    public Account() {
        active = false;
    }

    public void activate(boolean active) {
        this.active = true;
    }

    public boolean isActive() {
        return this.active;
    }
}
