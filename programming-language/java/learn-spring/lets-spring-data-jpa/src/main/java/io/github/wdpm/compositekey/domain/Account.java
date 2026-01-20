package io.github.wdpm.compositekey.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.StringJoiner;

/**
 * Use @IdClass
 *
 * @author evan
 * @date 2020/5/21
 */
@Entity
@Table(name = "account")
@IdClass(AccountId.class)
public class Account implements Serializable {

    @Id
    private String accountNumber;
    @Id
    private String accountType;

    private double balance;

    public Account() {
    }

    public Account(String accountNumber, String accountType, double balance) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Account.class.getSimpleName() + "[", "]")
                .add("accountNumber='" + accountNumber + "'")
                .add("accountType='" + accountType + "'")
                .add("balance=" + balance)
                .toString();
    }
}

