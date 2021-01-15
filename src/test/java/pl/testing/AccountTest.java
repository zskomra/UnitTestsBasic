package pl.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumingThat;

class AccountTest {

    Account account;

    @BeforeEach
    void setUp(){
        account = new Account();
    }

    @Test
    @DisplayName("Check if new account is not active")
    void checkIfNewAccountIsNotActiveAfterCreation() {
        //given
        //when
        //then
        assertFalse(account.isActive());
        //lub
//        assertThat(account.isActive(), equalTo(false)); //-hamcrest
//        //lub
//        assertThat(account.isActive(), is(false)); //-hamcrest
        //lub

        assertThat(account.isActive()).isFalse();
    }
    @Test
    @DisplayName("Check if new account is active after activation")
    void checkIfNewAccountIsActiveAfterActivation(){
        //given
        //when
        account.activate();
        //then
        assertTrue(account.isActive());
    }

    @Test
    void newlyCreatedAccountShouldNotHaveDefaultDeliveryAddress() {
        //given
        //when
        Address address = account.getDefaultDeliveryAddress();
        //then
        assertNull(address);
        //lub
//        assertThat(address,nullValue()); //--hamcrest
        //lub
        assertThat(address).isNull();
    }

    @Test
    void newlyCreatedAccountShouldNotBeNullAfterBeingSet(){
        //given
        Address address = new Address("bydgoska",4);
        account.setDefaultDeliveryAddress(address);
        //when
        Address defaultAddress = account.getDefaultDeliveryAddress();
        //then
        assertNotNull(defaultAddress);
        //lub
//        assertThat(defaultAddress,notNullValue()); //-- Hamcrest
        //lub
        assertThat(defaultAddress).isNotNull();

    }

    @RepeatedTest(3)
    void newAccountWithNotNullAddressShouldBeActive(){
        //given
        Address address = new Address("bydgoska",2);
        //when
        Account account = new Account(address);
        //then
        assumingThat(address != null, () -> {
            assertTrue(account.isActive());
        });
    }

}