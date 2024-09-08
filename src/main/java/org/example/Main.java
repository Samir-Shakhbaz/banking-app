package org.example;

public class Main {
    public static void main(String[] args) {
        // TESTS
        // Create banking accounts Test
        BankingAccount account1 = new BankingAccount("John Donne", 1000.0, 123456);
        BankingAccount account2 = new BankingAccount("Jane Donne", 2000.0, 789101);

        // Deposit Test
        System.out.println("=== Deposit Test ===");
        account1.deposit(500.0);
        System.out.println("Account 1 Balance after deposit: " + account1.getBalance());

        // Withdraw Test
        System.out.println("=== Withdraw Test ===");
        account1.withdraw(200.0);
        System.out.println("Account 1 Balance after withdrawal: " + account1.getBalance());

        // Transfer Test
        System.out.println("=== Transfer Test ===");
        String transferResult = account1.transfer(300.0, account2);
        System.out.println(transferResult);
        System.out.println("Account 1 Balance after transfer: " + account1.getBalance());
        System.out.println("Account 2 Balance after transfer: " + account2.getBalance());

        // Overdraft Protection Test
        System.out.println("=== Overdraft Protection Test ===");
        account1.setOverdraftAccount(account2);  // setting overdraft protection for account2
        String overdraftResult = account1.overdraftProtection(1500.0);  // Withdraw more than balance
        System.out.println(overdraftResult);
        System.out.println("Account 1 Balance after overdraft protection: " + account1.getBalance());
        System.out.println("Account 2 Balance after overdraft protection: " + account2.getBalance());

        // Budget and Expense Tracking Test
        System.out.println("=== Budget and Expense Tracking Test ===");
        account1.setBudget(1000.0);
        account1.withdraw(200.0);
        account1.withdraw(900.0);
        System.out.println("Account 1 Total Expenses: " + account1.getBalance());

        // Interest Calculation Test
        System.out.println("=== Interest Calculation Test ===");
        account2.applyInterest(5.0);
        System.out.println("Account 2 Balance after interest: " + account2.getBalance());

        // Bill Payment Test
        System.out.println("=== Bill Payment Test ===");
        String billPaymentResult = account1.payBill("Utility", 300.0);
        System.out.println(billPaymentResult);
        System.out.println("Account 1 Balance after bill payment: " + account1.getBalance());

        // Generate Monthly Statement Test
        System.out.println("=== Monthly Statement Test ===");
        String monthlyStatement = account1.generateStatement("monthly");
        System.out.println(monthlyStatement);

        // Joint Account Holder Test
        System.out.println("=== Joint Account Holder Test ===");
        User jointUser = new User("Jack Donne", "password123", "owner");
        String jointAccountResult = account1.addJointAccountHolder(jointUser);
        System.out.println(jointAccountResult);
    }
}
