package ca.cmpt213.asn4.bank;

/**
 * The {@code SavingsAccount} class represents a savings account, which is a specific type of
 * {@link BankAccount}. It extends the functionality of a generic bank account with additional
 * features related to activity status and associated rules for withdrawals.
 *
 * <p>A savings account is considered active as long as the balance is above a certain threshold
 * (e.g., $25). If the balance falls below this threshold, the account becomes inactive,
 * and additional restrictions are applied to withdrawals.
 *
 * <p>Withdrawals from an inactive account are not allowed, and a warning message is printed.
 * Additionally, service charges are applied if more than four withdrawals are made from an active account.
 *
 * @author Yurii Huba
 * @version 1.0
 */
public class SavingsAccount extends BankAccount {

    private boolean active;

    /**
     * Checks if the savings account is currently active.
     *
     * @return {@code true} if the account is active, {@code false} otherwise.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Constructs a new {@code SavingsAccount} with the specified initial balance
     * and annual interest rate. The account's activity status is updated based on the balance.
     *
     * @param balanceValue       The initial balance of the savings account.
     * @param interestRateValue  The annual interest rate for the savings account.
     */
    public SavingsAccount(double balanceValue, double interestRateValue) {
        super(balanceValue, interestRateValue);
        updateActive();
    }

    /**
     * Overrides the {@code withdraw} method to implement additional rules for withdrawals
     * based on the activity status of the savings account.
     *
     * @param amount The amount to be withdrawn.
     */
    @Override
    void withdraw(double amount) {
        if (active) {
            super.withdraw(amount);
            int withdrawalNumberValue = super.getWithdrawalsNumber();

            if (withdrawalNumberValue > 4) {
                super.updateServiceCharges();
            }
            this.updateActive();
        } else {
            System.err.println("Account inactive, withdrawals are not allowed!");
        }
    }

    /**
     * Overrides the {@code deposit} method to update the activity status after a deposit.
     *
     * @param amount The amount to be deposited.
     */
    @Override
    void deposit(double amount) {
        super.deposit(amount);
        updateActive();
    }

    /**
     * Overrides the {@code monthlyProcess} method to update the activity status
     * after performing monthly processing activities.
     */
    @Override
    void monthlyProcess() {
        super.monthlyProcess();
        updateActive();
    }

    /**
     * Updates the activity status of the savings account based on the current balance.
     * The account is considered active if the balance is equal to or above $25; otherwise, it is inactive.
     */
    private void updateActive() {
        double balanceValue = super.getBalance();
        if (balanceValue < 25) {
            active = false;
        } else {
            active = true;
        }
    }
}
