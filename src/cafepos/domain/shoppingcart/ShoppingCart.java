package cafepos.domain.shoppingcart;

import cafepos.domain.order.OrderItem;
import cafepos.domain.menu.Drink;
import cafepos.domain.option.IceOrHot;

import java.util.ArrayList;
import java.util.List;


// 장바구니 라인 병합 규칙을 관리한다.
// 같은 메뉴라도 옵션이 다르면 별도 라인으로 유지한다.

public class ShoppingCart {
    private final List<OrderItem> items = new ArrayList<>();

    public void addItem(OrderItem newItem) {
        for(OrderItem item : items) {
            if(isSameLine(item, newItem)) {
                item.addQuantity(newItem.getQuantity());
                return;
            }
        }

        items.add(newItem);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getTotalPrice() {
        int total = 0;

        for(OrderItem item : items) {
            total += item.getItemPrice();
        }

        return total;
    }

    // 메뉴 ID가 같고, 음료는 온도/얼음 양 옵션까지 같을 때만 같은 라인으로 합친다.
    // 디저트는 옵션(예: 포크 수)과 무관하게 같은 메뉴면 같은 라인으로 본다.
    // 포크는 보통 고객 수 만큼 체크하기 때문이다.
    boolean isSameLine(OrderItem a, OrderItem b) {
        boolean sameMenu = a.getMenuItem().getId() == b.getMenuItem().getId();

        if(!sameMenu) return false;

        boolean aIsDrink = a.getMenuItem() instanceof Drink;
        boolean bIsDrink = b.getMenuItem() instanceof Drink;

        if(aIsDrink && bIsDrink) {
            if (a.getIceOrHot() != b.getIceOrHot()) return false;
            if (a.getIceOrHot() == IceOrHot.ICE) {
                return a.getIceAmount() == b.getIceAmount();
            }
            return true;
        }

        return true;
    }

    public void printCart() {
        if(items.isEmpty()) {
            System.out.println("장바구니가 비어 있습니다.");
            return;
        }

        for(int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            String option = item.getOption();

            System.out.printf("%3d)  %-10s  %-3d  %6d원\n",
                    i + 1,
                    item.getMenuItem().getName(),
                    item.getQuantity(),
                    item.getItemPrice());

            if(!option.isBlank()) {
                System.out.println("    (" + option + ")");
            }
        }

        System.out.println();
        System.out.printf("총 금액 : %10d\n", getTotalPrice());
    }

    public void clear() {
        items.clear();
    }
}
