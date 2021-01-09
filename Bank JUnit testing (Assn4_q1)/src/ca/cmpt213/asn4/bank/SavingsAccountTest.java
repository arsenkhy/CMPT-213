package ca.cmpt213.asn4.bank;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * SavingsAccountTest class models testing of savings account
 * function using JUnit 5 framework. It supports testing
 * deposit, withdraw, calculate Interest, and monthly process.
 */
class SavingsAccountTest {

    @Test
    @DisplayName("Withdraw test")
    void withdraw() {
        SavingsAccount account = new SavingsAccount(1000, 0.02);
        account.withdraw(700);
        assertEquals(300, account.balance);

        try {
            account.withdraw(-1);
        } catch (IllegalArgumentException e) {
            assertEquals(300, account.balance);
            assertEquals(1, account.withdrawalsNum);
        }

        account.withdraw(301);
        assertFalse(account.isAccountStatus());

        account.withdraw(1);
        assertFalse(account.isAccountStatus());
        assertEquals(2, account.withdrawalsNum);
    }

    @Test
    @DisplayName("Deposit testing")
    void deposit() {
        SavingsAccount account = new SavingsAccount(1000, 0.02);
        account.deposit(1000);
        assertEquals(2000, account.balance);

        try {
            account.deposit(-1);
        } catch (IllegalArgumentException e) {
            assertEquals(2000, account.balance);
            assertEquals(1, account.depositsNum);
            assertTrue(account.isAccountStatus());
        }

        account.withdraw(1980);
        account.deposit(5);
        assertFalse(account.isAccountStatus());

        account.deposit(1);
        assertEquals(3, account.depositsNum);
        assertEquals(26, account.balance);
        assertTrue(account.isAccountStatus());
    }

    @Test
    @DisplayName("Montly process test")
    void monthlyProcess() {
        SavingsAccount account = new SavingsAccount(1000, 0.02);
        account.withdraw(100);
        account.deposit(100);
        account.monthlyProcess();
        assertEquals(0, account.monthlyCharge);
        assertEquals(0, account.withdrawalsNum );
        assertEquals(0, account.depositsNum);

        // 5X
        account.withdraw(100);
        account.withdraw(100);
        account.withdraw(100);
        account.withdraw(100);
        account.withdraw(100);

        account.monthlyProcess();
        assertEquals(501.5, account.balance,0.01);
        assertEquals(0, account.monthlyCharge);
        assertEquals(0, account.withdrawalsNum );
        assertEquals(0, account.depositsNum);

        account = new SavingsAccount(0, 2);
        account.monthlyProcess();
        assertEquals(0, account.balance);
    }

    @Test
    @DisplayName("Calculate interest test")
    void calcInterest() {
        SavingsAccount account = new SavingsAccount(1000, 0.12);
        account.calcInterest();
        assertEquals(1010, account.balance);
        account = new SavingsAccount(24.9, 0.5);
        assertFalse(account.isAccountStatus());
        account.calcInterest();
        assertFalse(account.isAccountStatus());
    }
}