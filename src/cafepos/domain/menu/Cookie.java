package cafepos.domain.menu;

public class Cookie extends Dessert {

    public Cookie(int id, String name, int price) {
        super(id, name, price);
    }

    @Override
    public String getCategory() { return "Cookie"; }
}
