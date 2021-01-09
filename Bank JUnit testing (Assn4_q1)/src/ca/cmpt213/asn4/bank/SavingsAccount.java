package ca.cmpt213.asn4.bank;

/**
 * SavingsAccount class extends BankAccount and models
 * the information about a savings account. Data includes
 * account status. It supports setting and checking
 * account status
 */
public class SavingsAccount extends BankAccount{

    private boolean accountStatus;

    public SavingsAccount(double balance, double interestRate) {
        super(balance, interestRate);
        accountStatus = balance > 25;
    }

    public void withdraw(double amount) {
        if (!accountStatus) {
            System.out.println("Sorry, account is inactive");
        } else {
            super.withdraw(amount);
            accountStatus = balance > 25;
        }
    }

    public void deposit(double amount) {
        super.deposit(amount);
        accountStatus = balance > 25;
    }

    public void monthlyProcess() {
        if (withdrawalsNum > 4) {
            monthlyCharge = withdrawalsNum - 4;
        }
        super.monthlyProcess();
        accountStatus = balance > 25;
    }

    public boolean isAccountStatus() {
        return accountStatus;
    }
}
