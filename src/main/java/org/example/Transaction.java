package org.example;

import java.time.LocalDateTime;

public class Transaction {

    private String type;  // Deposit, Withdrawal, Transfer
    private double amount;
    private LocalDateTime date;
    private String targetAccount;  // For transfers

    public Transaction(String type, double amount, LocalDateTime date, String targetAccount) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.targetAccount = targetAccount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(String targetAccount) {
        this.targetAccount = targetAccount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type='" + type + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                (targetAccount != null ? ", targetAccount=" + targetAccount : "") +
                '}';
    }
}

