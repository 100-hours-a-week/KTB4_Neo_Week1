package cafepos.domain.shoppingcart;

import cafepos.domain.order.OrderItem;
import cafepos.domain.menu.Drink;
import cafepos.domain.option.IceOrHot;

import java.util.ArrayList;
import java.util.Collections;
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

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

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

    public void clear() {
        items.clear();
    }
}
