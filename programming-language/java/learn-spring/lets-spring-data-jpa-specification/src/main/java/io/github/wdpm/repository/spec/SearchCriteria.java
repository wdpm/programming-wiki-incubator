package io.github.wdpm.repository.spec;

public class SearchCriteria {
    /**
     * domain fields
     *
     * @see io.github.wdpm.domain.Movie
     */
    private String key;

    /**
     * actual value to compare
     */
    private Object value;

    /**
     * verb
     */
    private SearchOperation operation;

    public SearchCriteria() {
    }

    public SearchCriteria(String key, Object value, SearchOperation operation) {
        this.key = key;
        this.value = value;
        this.operation = operation;
    }

    // getters and setters, equals(), toString(), ... (omitted for brevity)

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public SearchOperation getOperation() {
        return operation;
    }

    public void setOperation(SearchOperation operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "SearchCriteria{" + "key='" + key + '\'' + ", value=" + value + ", operation=" + operation + '}';
    }
}
