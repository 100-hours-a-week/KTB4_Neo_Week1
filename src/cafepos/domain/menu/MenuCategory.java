package cafepos.domain.menu;

public enum MenuCategory {
    COFFEE("Coffee"),
    TEA("Tea"),
    CAKE("Cake"),
    COOKIE("Cookie");

    private final String label;

    MenuCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public boolean isDrink() {
        return this == COFFEE || this == TEA;
    }
}
