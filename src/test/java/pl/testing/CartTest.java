package pl.testing;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.catalog.Catalog;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test cases for cart")
class CartTest {



    @Test
    @DisplayName("Cart is able to process 1000 orders in 100ms")
    void simulateLargeOrder() {
        //given
        Cart cart = new Cart();
        //when
        //then
        assertTimeout(Duration.ofMillis(100), cart::simulateLargeOrder);
    }
}