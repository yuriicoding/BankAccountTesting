package ca.cmpt213.asn4.bank;
/**
 * The {@code BankAccount} class represents a generic bank account with basic functionalities
 * such as deposits, withdrawals, interest calculation, and monthly processing.
 * This is an abstract class, and specific account types should extend this class.
 *
 * <p>The class provides information about the account balance, number of deposits,
 * number of withdrawals, annual interest rate, and service charges.
 * It also includes methods to deposit, withdraw, calculate interest, update service charges,
 * and perform monthly processing activities.
 *
 * <p>Instances of this class should be created by providing an initial balance and
 * an annual interest rate. The initial balance and interest rate must be non-negative;
 * otherwise, an {@code IllegalArgumentException} will be thrown.
 *
 * <p>The monthly process involves deducting service charges, calculating monthly interest,
 * and resetting the counters for deposits, withdrawals, and service charges.
 *
 * @author Yurii Huba
 * @version 1.0
 */
public abstract class BankAccount {
    private double balance;
    private int depositsNumber;
    private int withdrawalsNumber;
    private double interestRate;
    private double serviceCharges;

    /**
     * Gets the current balance of the bank account.
     *
     * @return The current balance of the bank account.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Gets the number of deposits made to the bank account.
     *
     * @return The number of deposits made.
     */
    public int getDepositsNumber() {
        return depositsNumber;
    }

    /**
     * Gets the number of withdrawals made from the bank account.
     *
     * @return The number of withdrawals made.
     */
    public int getWithdrawalsNumber() {
        return withdrawalsNumber;
    }

    /**
     * Gets the annual interest rate of the bank account.
     *
     * @return The annual interest rate.
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * Gets the total service charges applied to the bank account.
     *
     * @return The total service charges applied.
     */
    public double getServiceCharges() {
        return serviceCharges;
    }

    /**
     * Updates the service charges by incrementing it by 1.
     */
    public void updateServiceCharges() {
        serviceCharges = serviceCharges + 1;
    }

    /**
     * Constructs a new {@code BankAccount} with the specified initial balance
     * and annual interest rate.
     *
     * @param balanceValue       The initial balance of the bank account.
     * @param interestRateValue  The annual interest rate for the bank account.
     * @throws IllegalArgumentException If the initial balance or interest rate is less than 0.
     */
    public BankAccount(double balanceValue, double interestRateValue) {
        if (balanceValue < 0) {
            throw new IllegalArgumentException("Balance cannot be less than 0!");
        }
        if (interestRateValue < 0) {
            throw new IllegalArgumentException("Annual Interest Rate cannot be less than 0!");
        }
        this.balance = balanceValue;
        this.interestRate = interestRateValue;
        this.depositsNumber = 0;
        this.withdrawalsNumber = 0;
        this.serviceCharges = 0;
    }

    /**
     * Deposits a specified amount into the bank account, updating the balance
     * and incrementing the number of deposits.
     *
     * @param depositAmount The amount to be deposited.
     * @throws IllegalArgumentException If the deposit amount is less than 0.
     */
    void deposit(double depositAmount) {
        if (depositAmount < 0) {
            throw new IllegalArgumentException("Deposit Amount is less than 0!");
        } else {
            balance += depositAmount;
            depositsNumber++;
        }
    }

    /**
     * Withdraws a specified amount from the bank account, updating the balance,
     * incrementing the number of withdrawals.
     *
     * @param withdrawalAmount The amount to be withdrawn.
     * @throws IllegalArgumentException If the withdrawal amount is less than 0
     *                                  or greater than the current balance.
     */
    void withdraw(double withdrawalAmount) {
        if (withdrawalAmount < 0) {
            throw new IllegalArgumentException("Withdrawal Amount is less than 0!");
        } else if (withdrawalAmount > balance) {
            throw new IllegalArgumentException("Withdrawal Amount is greater than balance!");
        } else {
            balance -= withdrawalAmount;
            withdrawalsNumber++;
        }
    }

    /**
     * Calculates and adds monthly interest based on the annual interest rate.
     */
    void calcInterest() {
        double monthlyRate = interestRate / 12;
        double monthlyInterest = balance * monthlyRate;
        balance += monthlyInterest;
    }

    /**
     * Processes monthly activities, including deducting service charges,
     * calculating interest, resetting counters, and printing the remaining balance.
     */
    void monthlyProcess() {
        balance -= serviceCharges;
        this.calcInterest();
        depositsNumber = 0;
        withdrawalsNumber = 0;
        serviceCharges = 0;
    }
}
