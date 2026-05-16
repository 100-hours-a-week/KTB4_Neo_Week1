package cafepos.domain.menu;

public class Drink extends MenuItem {

    private final boolean isAvailableIce;

    public Drink(int id, String name, int price, boolean isAvailableIce) {
        super(id, name, price);
        this.isAvailableIce = isAvailableIce;
    }

    public boolean isAvailableIce() {
        return isAvailableIce;
    }

    @Override
    public String getCategory() { return "DRINK"; }
}
