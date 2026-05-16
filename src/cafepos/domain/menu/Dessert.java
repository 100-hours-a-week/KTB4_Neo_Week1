package cafepos.domain.menu;

public class Dessert extends MenuItem {

    public Dessert(int id, String name, int price) {
        super(id, name, price);
    }

    @Override
    public String getCategory() { return "Dessert"; }

}
