package io.github.wdpm.concurrency.shareobject;

/**
 * StuffIntoPublic
 * <p/>
 * Unsafe publication
 *
 * @author Brian Goetz and Tim Peierls
 */
public class StuffIntoPublic {
    // unsafe publication, may cause partially constructed object
    public Holder holder;

    public void initialize() {
        holder = new Holder(42);
    }
}
