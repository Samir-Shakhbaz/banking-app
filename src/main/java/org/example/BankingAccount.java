package org.example;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BankingAccount {

    private String name;
    private double balance;
    private int accountNumber;
    private List<Transaction> transactionHistory;
    private BankingAccount overdraftAccount;
    private List<String> notifications;
    private boolean locked;
    private double totalExpenses;
    private double budget;
    private double savingsBalance;
    private double savingsPercentage;
    private List<User> jointAccountHolders;

    BankingAccount(String name, double initialBalance, int accountNumber) {
        this.name = name;
        this.balance = initialBalance;
        this.accountNumber = accountNumber;
        this.transactionHistory = new ArrayList<>();
        this.notifications = new ArrayList<>();
        this.locked = false;
        this.totalExpenses = 0.0;
        this.budget = 0.0;
        this.savingsBalance = 0.0;
        this.savingsPercentage = 0.0;
        this.jointAccountHolders = new ArrayList<>();
    }

    public void setOverdraftAccount(BankingAccount overdraftAccount) {
        this.overdraftAccount = overdraftAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isLocked() {
        return locked;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double amount) {
        this.budget = amount;
        sendNotification("Budget set to: " + amount);
    }

    public double getSavingsPercentage() {
        return savingsPercentage;
    }

    public double deposit(double depositAmount) {
        if (depositAmount > 0) {
            this.balance += depositAmount;
            transactionHistory.add(new Transaction("Deposit", depositAmount, LocalDateTime.now(), null));
        }
        return this.balance;
    }

    public double withdraw(double withdrawAmount) {
        if (withdrawAmount > 0 && withdrawAmount <= balance) {
            this.balance -= withdrawAmount;
            transactionHistory.add(new Transaction("Withdrawal", withdrawAmount, LocalDateTime.now(), null));
        } else {
            System.out.println("Insufficient balance or invalid amount for withdrawal.");
        }
        return this.balance;
    }

    public String transfer(double amount, BankingAccount targetAccount) {
        if (amount <= 0) {
            return "Transfer amount must be greater than zero.";
        }

        if (this.balance < amount) {
            return "Insufficient balance for the transfer.";
        }

        this.balance -= amount;

        // Add the amount to the target account
        targetAccount.deposit(amount);

        transactionHistory.add(new Transaction("Transfer", amount, LocalDateTime.now(),
                "To Account: " + targetAccount.getAccountNumber()));

        return "Transfer of " + amount + " to account " + targetAccount.getAccountNumber() + " successful.";
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public List<User> getJointAccountHolders() {
        return jointAccountHolders;
    }

    public void applyInterest(double interestRate) {
        if (interestRate > 0) {
            double interest = balance * (interestRate / 100);
            balance += interest;

            transactionHistory.add(new Transaction("Interest", interest, LocalDateTime.now(), null));

            System.out.println("Interest applied at rate: " + interestRate + "%. New balance: " + balance);
        } else {
            System.out.println("Invalid interest rate.");
        }
    }

    public String overdraftProtection(double withdrawAmount) {
        if (withdrawAmount <= balance) {
            withdraw(withdrawAmount);
            return "Withdrawal of " + withdrawAmount + " successful.";
        } else if (overdraftAccount != null && overdraftAccount.getBalance() >= (withdrawAmount - balance)) {
            double deficit = withdrawAmount - balance;
            withdraw(balance);
            overdraftAccount.withdraw(deficit);
            return "Withdrawal of " + withdrawAmount + " successful using overdraft protection. " +
                    "Overdraft account used: " + overdraftAccount.getAccountNumber();
        } else {
            return "Insufficient funds and no overdraft protection available.";
        }
    }

    public String payBill(String biller, double amount) {
        if (amount <= 0) {
            return "Bill amount must be greater than zero.";
        }
        if (balance < amount) {
            return "Insufficient balance to pay the bill.";
        }

        this.balance -= amount;

        transactionHistory.add(new Transaction("Bill Payment", amount, LocalDateTime.now(), biller));

        return "Payment of " + amount + " to " + biller + " successful.";
    }

    public void sendNotification(String message) {
        notifications.add("Notification: " + message + " at " + LocalDateTime.now());
    }

    public void lockAccount() {
        this.locked = true;
        sendNotification("Your account has been locked.");
    }

    public void unlockAccount() {
        this.locked = false;
        sendNotification("Your account has been unlocked.");
    }

    private void trackExpense(double expense) {
        this.totalExpenses += expense;
        if (totalExpenses > budget) {
            sendNotification("Warning: You have exceeded your budget. Total expenses: " + totalExpenses);
        } else {
            sendNotification("Expense of " + expense + " added. Total expenses: " + totalExpenses);
        }
    }

    public void automateSavings(double percentage) {
        if (percentage < 0 || percentage > 100) {
            sendNotification("Invalid savings percentage. Please enter a value between 0 and 100.");
            return;
        }
        this.savingsPercentage = percentage;
        sendNotification("Automated savings set to " + percentage + "% of deposits.");
    }

    public String addJointAccountHolder(User user) {
        if (jointAccountHolders.contains(user)) {
            return "User " + user.getUsername() + " is already a joint account holder.";
        }
        jointAccountHolders.add(user);
        sendNotification(user.getUsername() + " added as a joint account holder.");
        return "User " + user.getUsername() + " has been successfully added as a joint account holder.";
    }

    public String generateStatement(String period) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDate;

        if (period.equalsIgnoreCase("monthly")) {
            startDate = now.minusMonths(1);
        } else if (period.equalsIgnoreCase("yearly")) {
            startDate = now.minusYears(1);
        } else {
            return "Invalid period. Please choose 'monthly' or 'yearly'.";
        }

        List<Transaction> filteredTransactions = transactionHistory.stream()
                .filter(t -> t.getDate().isAfter(startDate))  // Now comparing LocalDateTime instances
                .collect(Collectors.toList());

        StringBuilder statement = new StringBuilder();
        statement.append("Account Statement for ").append(period).append("\n");
        statement.append("Account Holder: ").append(name).append("\n");
        statement.append("Account Number: ").append(accountNumber).append("\n");
        statement.append("Statement Date: ").append(now.format(DateTimeFormatter.ISO_DATE)).append("\n\n");
        statement.append("Transactions:\n");

        for (Transaction t : filteredTransactions) {
            statement.append(t).append("\n");
        }

        statement.append("\nCurrent Balance: ").append(balance);
        return statement.toString();
    }


    @Override
    public String toString() {
        return "BankingAccount{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                ", accountNumber=" + accountNumber +
                '}';
    }
}
