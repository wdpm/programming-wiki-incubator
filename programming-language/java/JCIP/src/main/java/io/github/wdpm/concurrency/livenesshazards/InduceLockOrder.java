package io.github.wdpm.concurrency.livenesshazards;

/**
 * InduceLockOrder
 * <p>
 * Inducing a lock order to avoid deadlock
 *
 * @author Brian Goetz and Tim Peierls
 */
public class InduceLockOrder {
    private static final Object tieLock = new Object();

    public void transferMoney(final Account fromAcct, final Account toAcct,
                              final DollarAmount amount) throws InsufficientFundsException {
        class Helper {
            public void transfer() throws InsufficientFundsException {
                if (fromAcct.getBalance().compareTo(amount) < 0) throw new InsufficientFundsException();
                else {
                    fromAcct.debit(amount);
                    toAcct.credit(amount);
                }
            }
        }
        // 使用 System.identityHashCode 指定获取锁的顺序
        int fromHash = System.identityHashCode(fromAcct);
        int toHash   = System.identityHashCode(toAcct);

        // if 和else-if 意义在于，先获取小hash的锁，再获取大hash的锁
        // else 为加时比赛策略，先获取加时赛的锁，然后获取小hash的锁，再获取大hash的锁
        // 如果Account具有唯一的key，例如ID，那么根据ID来指定加锁顺序，就能加时赛的加锁。因为此时ID不会相等

        // 假设 A hash < B hash
        //A: transferMoney(myAccount, yourAccount, 10)
        //B: transferMoney(yourAccount, myAccount, 20)

        // 过程：
        // A: get A lock -> get B lock -> do transfer
        // B: get A lock -> get B lock -> do transfer
        if (fromHash < toHash) {
            synchronized (fromAcct) {
                synchronized (toAcct) {
                    new Helper().transfer();
                }
            }
        } else if (fromHash > toHash) {
            synchronized (toAcct) {
                synchronized (fromAcct) {
                    new Helper().transfer();
                }
            }
        } else {
            // 小概率，hash两者相等。采用加时比赛策略。全局唯一锁。谁获得，谁就能进行transfer
            // hash一般不相等，这个一般不会是瓶颈
            synchronized (tieLock) {
                synchronized (fromAcct) {
                    synchronized (toAcct) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }

    interface DollarAmount extends Comparable<DollarAmount> {
    }

    interface Account {
        void debit(DollarAmount d);

        void credit(DollarAmount d);

        DollarAmount getBalance();

        int getAcctNo();
    }

    class InsufficientFundsException extends Exception {
    }
}
