package cafepos.domain.menu;

public class Tea extends Drink {

    public Tea(int id, String name, int price, boolean isAvailableIce) {
        super(id, name, price, isAvailableIce);
    }

    @Override
    public String getCategory() { return "Tea"; }
}
