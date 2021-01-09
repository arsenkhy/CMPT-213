package ca.cmpt213.asn4.bank;

/**
 * BankAccount abstract class models the information about a
 * bank account. Data includes balance, number of deposits
 * number of withdrawals, interest rate, monthly charge.
 * It supports depositing, withdrawal, calculating interest,
 * charging monthly use.
 */
public abstract class BankAccount {

    protected double balance;
    protected int depositsNum;
    protected int withdrawalsNum;
    protected double interestRate;
    protected int monthlyCharge;

    public BankAccount(double balance, double interestRate) {
        // Checking for the negative arguments
        if (balance < 0 || interestRate < 0) {
            throw new IllegalArgumentException("Arguments cannot be negative");
        } else {
            this.balance = balance;
            this.interestRate = interestRate;
        }
    }

    public void deposit(double amount) {
        // Checking for the negative arguments
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit must be positive");
        }
        this.balance += amount;
        depositsNum++;
    }

    public void withdraw(double amount) {
        // Checking for the negative arguments
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal must be positive");
        }
        this.balance -= amount;
        withdrawalsNum++;
    }

    public void calcInterest() {
        double monthInterestRate = interestRate / 12;
        double monthInterest = balance * monthInterestRate;
        this.balance += monthInterest;
    }

    public void monthlyProcess() {
        this.balance -= monthlyCharge;
        calcInterest();
        this.withdrawalsNum = 0;
        this.depositsNum = 0;
        this.monthlyCharge = 0;
    }
}
