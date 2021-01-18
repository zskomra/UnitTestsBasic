package pl.testing.cart;


import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mockito;
import pl.testing.order.Order;
import pl.testing.order.OrderStatus;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Test
    void processCartShouldSendToPrepare() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = Mockito.mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        when(cartHandler.canHandleCart(cart)).thenReturn(true);

        //when
        Cart result = cartService.processCart(cart);

        //then
        verify(cartHandler).sendToPrepare(cart);
        then(cartHandler).should().sendToPrepare(cart);
        verify(cartHandler, times(1)).sendToPrepare(cart);
        verify(cartHandler, atLeastOnce()).sendToPrepare(cart);

        InOrder inOrder = inOrder(cartHandler);
        inOrder.verify(cartHandler).canHandleCart(cart);
        inOrder.verify(cartHandler).sendToPrepare(cart);

        assertThat(result.getOrders(),hasSize(1));
        assertThat(result.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));

    }

    @Test
    void processCartShouldNotSendToPrepare() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = Mockito.mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        when(cartHandler.canHandleCart(cart)).thenReturn(false);

        //when
        Cart result = cartService.processCart(cart);

        //then

        verify(cartHandler,never()).sendToPrepare(cart);
        then(cartHandler).should(never()).sendToPrepare(cart);

        assertThat(result.getOrders(),hasSize(1));
        assertThat(result.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));

    }

    @Test
    void processCartShouldSendToPrepareArgumentMatchers() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = Mockito.mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        when(cartHandler.canHandleCart(any(Cart.class))).thenReturn(false);

        //when
        Cart result = cartService.processCart(cart);

        //then

        verify(cartHandler,never()).sendToPrepare(any(Cart.class));
        then(cartHandler).should(never()).sendToPrepare(any(Cart.class));

        assertThat(result.getOrders(),hasSize(1));
        assertThat(result.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.REJECTED));

    }

    @Test
    void canHandlerCartShouldReturnMultipleValues() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);
        CartHandler cartHandler = Mockito.mock(CartHandler.class);

        //when
        when(cartHandler.canHandleCart(any(Cart.class))).thenReturn(true,false,false,true);

        //then
        assertThat(cartHandler.canHandleCart(cart),equalTo(true));
        assertThat(cartHandler.canHandleCart(cart),equalTo(false));
        assertThat(cartHandler.canHandleCart(cart),equalTo(false));
        assertThat(cartHandler.canHandleCart(cart),equalTo(true));

    }

    @Test
    void processCartShouldSendToPrepareWithLambdas() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = Mockito.mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        when(cartHandler.canHandleCart(argThat(c -> c.getOrders().size() > 0))).thenReturn(true);

        //when
        Cart result = cartService.processCart(cart);

        //then

        then(cartHandler).should().sendToPrepare(cart);
        assertThat(result.getOrders(),hasSize(1));
        assertThat(result.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));

    }

    @Test
    void canHandleCartShouldThrowException() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = Mockito.mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        when(cartHandler.canHandleCart(cart)).thenThrow(IllegalStateException.class);
        //when
        //then
        assertThrows(IllegalStateException.class, () -> cartService.processCart(cart));

    }
    @Test
    void processCartShouldSendToPrepareWithArgumenCaptor() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = Mockito.mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        ArgumentCaptor<Cart> argumentCaptor = ArgumentCaptor.forClass(Cart.class);

        when(cartHandler.canHandleCart(cart)).thenReturn(true);

        //when
        Cart result = cartService.processCart(cart);

        //then
        then(cartHandler).should().sendToPrepare(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue().getOrders().size(),equalTo(1));

        assertThat(result.getOrders(),hasSize(1));
        assertThat(result.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));

    }

    @Test
    void shouldDoNothingWhenProcessCart() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = Mockito.mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        when(cartHandler.canHandleCart(cart)).thenReturn(true);

        //when
        Cart result = cartService.processCart(cart);

        //then
        then(cartHandler).should().sendToPrepare(cart);
        assertThat(result.getOrders(),hasSize(1));
        assertThat(result.getOrders().get(0).getOrderStatus(), equalTo(OrderStatus.PREPARING));

    }

    @Test
    void shouldAnswerWhenProcessCart() {
        //given
        Order order = new Order();
        Cart cart = new Cart();
        cart.addOrderToCart(order);

        CartHandler cartHandler = Mockito.mock(CartHandler.class);
        CartService cartService = new CartService(cartHandler);

        doAnswer(invocationOnMock -> {
            Cart argumentCart = invocationOnMock.getArgument(0);
            argumentCart.clearCart();
            return true;
        }).when(cartHandler).canHandleCart(cart);

        when(cartHandler.canHandleCart(cart)).then(i-> {
            Cart argumentCart = i.getArgument(0);
            argumentCart.clearCart();
            return true;
        });

        //when
        Cart result = cartService.processCart(cart);

        //then
        then(cartHandler).should().sendToPrepare(cart);
        assertThat(result.getOrders().size(), equalTo(0));

    }
    
    }
}