package ca.cmpt213.asn4.bank;

import static org.junit.Assert.*;
import org.junit.Test;

public class SavingsAccountTest{

    SavingsAccount myAccount;

    @Test
    public void testConstructor(){
        double negative_value = -10.15;
        double positive_value = 11.75;


        try {
            myAccount = new SavingsAccount(negative_value, positive_value);
            assertTrue("Negative balance was used to construct Savings Account and no exception is thrown!", false);
        } catch (IllegalArgumentException e) {
        }
        System.out.println("Constructor Negative Balance Test passed!");


        try {
            myAccount = new SavingsAccount(positive_value, negative_value);
            assertTrue("Negative interest rate was used to construct Savings Account and no exception is thrown!", false);
        } catch (IllegalArgumentException e) {
        }
        System.out.println("Constructor Negative Interest Rate Test passed!");


        try {
            myAccount = new SavingsAccount(negative_value, negative_value);
            assertTrue("Negative interest rate and balance were used to construct Savings Account and no exception is thrown!", false);
        } catch (IllegalArgumentException e) {
        }
        System.out.println("Constructor Negative Interest Rate and Negative Balance Test passed!");


        try {
            myAccount = new SavingsAccount(positive_value, positive_value);
        } catch (IllegalArgumentException e){
            assertTrue("Positive interest rate and balance were used to construct Savings Account and exception is still thrown!", false);
        }
        System.out.println("Constructor Positive Interest Rate and Positive Balance Test passed!");


        boolean active = myAccount.isActive();
        assertEquals("Balance is below 25 and account is still active!", false, active);


        myAccount = new SavingsAccount(1000, 1.0);
        active = myAccount.isActive();
        assertEquals("Balance is above 25 and account is inactive!", true, active);
    }



    @Test
    public void testCalcInterest() {
        double init_balance = 1000;
        double interest_rate = 0.12;
        myAccount = new SavingsAccount(init_balance, interest_rate);
        myAccount.calcInterest();
        
        double correct_balance = 1010;
        double actual_balance = myAccount.getBalance();
        assertTrue("Monthly interest calculated wrong!", (actual_balance == correct_balance));
        System.out.println("Calculate Positive Interest Test passed!");


        double init_balance2 = 0;
        double interest_rate2 = 0.12;
        SavingsAccount myAccount2 = new SavingsAccount(init_balance2, interest_rate2);
        myAccount2.calcInterest();
        
        double correct_balance2 = 0;
        double actual_balance2 = myAccount2.getBalance();
        assertTrue("Monthly interest calculated wrong!", (actual_balance2 == correct_balance2));
        System.out.println("Calculate Zero Interest Test passed!");
    }



    @Test
    public void testDeposit() {
        double negative_value = -10.5;
        double positive_value = 1000.25;

        double init_balance = 1000.5;
        double init_rate = 12;
        myAccount = new SavingsAccount(init_balance, init_rate);
        int init_deposits_num = myAccount.getDepositsNumber();

        try{
            myAccount.deposit(negative_value);
            assertTrue("Negative amount was deposited!", false);
        }catch(IllegalArgumentException e){
        }
        System.out.println("Negative Amount Deposited Test passed!");


        try{
            myAccount.deposit(positive_value);
        }catch(IllegalArgumentException e){
            assertTrue("Positive amount was not deposited!", false);
        }
        System.out.println("Positive Amount Deposited Test passed!");


        double new_balance = init_balance + positive_value;
        double actual_balance = myAccount.getBalance();

        assertTrue("Wrong amount deposited!", (new_balance == actual_balance));
        System.out.println("Amount Deposited Test Passed!");
        
        assertEquals("Deposit wasn't recorded!", (init_deposits_num + 1), myAccount.getDepositsNumber());
        System.out.println("Recording Deposit Test passed!");

        myAccount = new SavingsAccount(10, 1.0);
        myAccount.deposit(1000);

        boolean active = myAccount.isActive();
        assertEquals("Account received a deposit above 25 dollars and is still inactive!", true, active);
        System.out.println("Change Active After Deposit Test passed!");
    }


    @Test
    public void testMonthlyProcess() {

        double init_balance = 1000;
        double init_rate = 1.2;
        SavingsAccount myAccount = new SavingsAccount(init_balance, init_rate);

        for (int i = 0; i < 3; i++){
            myAccount.deposit(10);
        }

        for (int i = 0; i < 3; i++){
            myAccount.withdraw(10);
        }

        myAccount.monthlyProcess();
        
        assertTrue("Service charges weren't set to 0!", (myAccount.getServiceCharges() == 0));
        System.out.println("Service charges were set to 0 after Monthly Process!");

        assertTrue("Deposits number wasn't set to 0!", (myAccount.getDepositsNumber() == 0));
        System.out.println("Deposits number was set to 0 after Monthly Process!");

        assertTrue("Withdrawals number wasn't set to 0!", (myAccount.getWithdrawalsNumber() == 0));
        System.out.println("Withdrawals number was set to 0 after Monthly Process!");

        double correct_balance = init_balance + (init_balance)*init_rate/12;

        assertTrue("Incorrect amount is left on the balance (3 withdrawals)!", (myAccount.getBalance() == correct_balance));
        System.out.println("3 Withdrawals Monthly Process Test passed!");


        myAccount = new SavingsAccount(init_balance, init_rate);

        for (int i = 0; i < 10; i++){
            myAccount.deposit(10);
        }

        for (int i = 0; i < 10; i++){
            myAccount.withdraw(10);
        }
        myAccount.monthlyProcess();

        correct_balance = init_balance - 6 + (init_balance - 6)*init_rate/12;

        assertTrue("Incorrect amount is left on the balance (4+ withdrawals)!", (myAccount.getBalance() == correct_balance));
        System.out.println("4+ Withdrawals Monthly Process Test passed!");

    }


    @Test
    public void testWithdraw() {
        double negative_value = -10.5;
        double positive_value = 1000.25;

        double init_balance = 1000.5;
        double init_rate = 12;
        myAccount = new SavingsAccount(init_balance, init_rate);
        int init_withdrawals_num = myAccount.getWithdrawalsNumber();

        try{
            myAccount.withdraw(negative_value);
            assertTrue("Negative amount was withdrawed!", false);
            
        }catch(IllegalArgumentException e){
        }
        System.out.println("Negative Amount Withdrawed Test passed!");


        try{
            myAccount.withdraw(positive_value);
        }catch(IllegalArgumentException e){
            assertTrue("Positive amount below balance was not withdrawed!", false);
        }
        System.out.println("Positive Amount Below Balance Withdrawed Test passed!");

        myAccount.deposit(500);
        try{
            myAccount.withdraw(positive_value);
            assertTrue("Positive amount above balance was withdrawed!", false);
        }catch(IllegalArgumentException e){  
        }
        System.out.println("Positive Amount Above Balance Withdrawed Test passed!");

        myAccount = new SavingsAccount(init_balance, init_rate);
        myAccount.withdraw(positive_value);
        double new_balance = init_balance - positive_value;
        double actual_balance = myAccount.getBalance();

        assertTrue("Wrong amount withdrawed!", (new_balance == actual_balance));
        System.out.println("Amount Withdrawed Test Passed!");
        
        assertEquals("Wihtdrawal wasn't recorded!", (init_withdrawals_num + 1), myAccount.getWithdrawalsNumber());
        System.out.println("Recording Withdrawal Test passed!");

        myAccount = new SavingsAccount(1000, 1.0);
        myAccount.withdraw(1000);
        boolean active = myAccount.isActive();
        assertEquals("Account withdrawed an amount which dropped balance below 25 dollars and is still active!", false, active);
        System.out.println("Change Active After Withdrawal Test passed!");
    }

}
