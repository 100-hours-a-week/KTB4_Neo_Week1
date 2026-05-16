package cafepos.domain.option;

public enum IceOrHot {
    HOT("핫"),
    ICE("아이스");

    private final String label;

    IceOrHot(String label) { this.label = label; }

    public String getLabel() { return label; }
}
