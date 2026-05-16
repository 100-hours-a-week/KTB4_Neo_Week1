package cafepos.domain.option;

public enum IceAmount {
    LESS("적게"),
    MORE("많이"),
    NONE("없이");

    private final String label;

    IceAmount(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
