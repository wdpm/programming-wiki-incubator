package io.github.wdpm.compositekey.domain;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author evan
 * @date 2020/5/21
 */
public class AccountId implements Serializable {

    private String accountNumber;

    private String accountType;

    public AccountId() {
    }

    public AccountId(String accountNumber, String accountType) {
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountId accountId = (AccountId) o;

        if (!accountNumber.equals(accountId.accountNumber)) return false;
        return accountType.equals(accountId.accountType);
    }

    @Override
    public int hashCode() {
        int result = accountNumber.hashCode();
        result = 31 * result + accountType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AccountId.class.getSimpleName() + "[", "]")
                .add("accountNumber='" + accountNumber + "'")
                .add("accountType='" + accountType + "'")
                .toString();
    }
}
