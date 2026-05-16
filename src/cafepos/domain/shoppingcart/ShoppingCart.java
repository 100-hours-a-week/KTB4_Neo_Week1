package cafepos.domain.shoppingcart;

import cafepos.domain.order.OrderItem;
import cafepos.domain.menu.Drink;
import cafepos.domain.option.IceOrHot;

import java.util.ArrayList;
import java.util.List;


/*
* 장바구니 콘솔 표기를 위한 클래스
* 같은 메뉴 + 같은 옵션이면 콘솔에 합쳐서 표기될 수 있게 하기.
* 같은 메뉴여도 옵션이 다르다면 나누어 장바구니에서 표기하기.
*/
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

    // 장바구니에서 같은 메뉴, 같은 옵션을 가진 라인을 하나로 합치기
    // 커피는 옵션이 다르다면 분리해서 표기할 수 있도록 해야하고,
    // 케이크의 포크 옵션 같은 경우는 인원 수에 맞게 고객이 선택한 포크를 중복해서
    // 제공할 필요가 없으므로, 굳이 분리해서 표기할 필요 없음.
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
