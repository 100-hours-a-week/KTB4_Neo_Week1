package cafepos.domain.menu;

public class MenuItem {
    private final int id;
    private final String name;
    private final int price;

    public MenuItem(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public int getPrice() { return price; }

    public String getCategory() { return "MENU"; }

    public void printInfo() {
        System.out.printf("%d. [%s] %s - %d원\n", id, getCategory(), name, price);
    }
}
