package cafepos.domain.menu;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum MenuData {
    AMERICANO(1, "아메리카노", 4000, MenuCategory.COFFEE, true),
    ESPRESSO(2, "에스프레소", 3000, MenuCategory.COFFEE, false),
    VANILLA_LATTE(3, "바닐라라떼", 4500, MenuCategory.COFFEE, true),
    EARL_GREY(4, "얼그레이", 3000, MenuCategory.TEA, true),
    GRAPEFRUIT_HONEY_BLACK_TEA(5, "자몽허니블랙티", 4000, MenuCategory.TEA, true),
    CHAMOMILE(6, "캐모마일", 3000, MenuCategory.TEA, true),
    CHOCOLATE_CAKE(7, "초코케이크", 6500, MenuCategory.CAKE, false),
    CHEESE_CAKE(8, "치즈케이크", 6000, MenuCategory.CAKE, false),
    CHOCO_CHIP_COOKIE(9, "초코칩쿠키", 2500, MenuCategory.COOKIE, false),
    BUTTER_COOKIE(10, "버터쿠키", 2000, MenuCategory.COOKIE, false);

    private final int id;
    private final String name;
    private final int price;
    private final MenuCategory category;
    private final boolean availableIce;

    private static final Map<Integer, MenuData> MENU_MAP;
    private static final int MAX_MENU_ID;

    static {
        Map<Integer, MenuData> map = new HashMap<>();
        int maxId = 0;
        for (MenuData menu : MenuData.values()) {
            map.put(menu.id, menu);
            if (menu.id > maxId) {
                maxId = menu.id;
            }
        }
        MENU_MAP = Collections.unmodifiableMap(map);
        MAX_MENU_ID = maxId;
    }

    MenuData(int id, String name, int price, MenuCategory category, boolean availableIce) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.availableIce = availableIce;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public MenuCategory getCategory() {
        return category;
    }

    public boolean isAvailableIce() {
        return availableIce;
    }

    public void printInfo() {
        System.out.printf("%d. [%s] %s - %d원\n", id, category.getLabel(), name, price);
    }

    public static MenuData findById(int id) {
        return MENU_MAP.get(id);
    }

    public static int getMaxMenuId() {
        return MAX_MENU_ID;
    }
}
