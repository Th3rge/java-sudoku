package br.com.dio.model;

public class Space {

    private Integer value1;

    private int expectedValue;

    private final boolean fixed;

    public Space(final int expectedValue, final boolean fixed) {
        this.expectedValue = expectedValue;
        this.fixed = fixed;

        if (fixed) {
            this.value1 = expectedValue;
        }
    }

    public Integer getValue() {
        return value1;
    }

    public void setValue(final Integer value) {
        if (!fixed) return;
        this.value1 = value;
    }

    public int getExpectedValue() {
        return expectedValue;
    }

    public void setExpectedValue(final int expectedValue) {
        if (!fixed) return;
        this.expectedValue = expectedValue;
    }

    public void clearValue() {
        setValue(null);
    }

    public boolean isFixed() {
        return fixed;
    }

}
