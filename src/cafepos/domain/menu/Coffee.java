package cafepos.domain.menu;

public class Coffee extends Drink {

    public Coffee(int id, String name, int price, boolean isAvailableIce) {
        super(id, name, price, isAvailableIce);
    }

    @Override
    public String getCategory() { return "Coffee"; }

}
