package cafepos.domain.menu;

public class Cake extends Dessert {

    public Cake(int id, String name, int price) {
        super(id, name, price);
    }

    @Override
    public String getCategory() { return "Cake"; }
}
