package pl.testing.account;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AccountServiceTest {

    @Test
    void getAllActiveAccounts() {
        //given
        List<Account>accounts = prepareAccountData();
        AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        when(accountRepository.getAllAccounts()).thenReturn(accounts);
        //when
        List<Account> accountList = accountService.getAllActiveAccounts();
        //then
        assertThat(accountList, hasSize(2));
    }

    @Test
    void getNoActiveAccounts() {
        //given

        AccountRepository accountRepository = Mockito.mock(AccountRepository.class);
        AccountService accountService = new AccountService(accountRepository);
        when(accountRepository.getAllAccounts()).thenReturn(Collections.emptyList());
        //when
        List<Account> accountList = accountService.getAllActiveAccounts();
        //then
        assertThat(accountList, hasSize(0));
    }

    private List<Account> prepareAccountData() {
        Address address1 = new Address("gazowa",21);
        Account account1 = new Account(address1);

        Account account2 = new Account();

        Address address2 = new Address("piekna", 26);
        Account account3 = new Account(address2);

        return Arrays.asList(account1,account2,account3);
    }

}