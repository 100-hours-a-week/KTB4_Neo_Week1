package cafepos.ui;

import cafepos.domain.menu.MenuItem;
import cafepos.domain.order.OrderItem;
import cafepos.domain.shoppingcart.ShoppingCart;

import java.util.List;

public class OutputView {
    public void printMainScreen(List<MenuItem> menuList, ShoppingCart shoppingCart) {
        System.out.println("\n\n=================================================");
        System.out.println("                    Cafe Pos System");
        System.out.println("=================================================\n\n");

        System.out.println("[Menu]");
        for (MenuItem menuItem : menuList) {
            menuItem.printInfo();
        }

        System.out.println("\n\n-------------------------------------------------");
        System.out.println("[장바구니]");
        printCart(shoppingCart);
        System.out.println("-------------------------------------------------\n");
    }

    public void printMainActions() {
        System.out.println("무엇을 도와드릴까요 ?");
        System.out.println("1) 메뉴 주문    2) 장바구니 결제    3) 종료하기\n");
    }

    public void printInvalidMenuSelect() {
        System.out.println("선택하신 메뉴는 없는 메뉴입니다.");
    }

    public void printAddCart() {
        System.out.println("선택하신 상품이 장바구니에 추가되었습니다.");
    }

    public void printExit() {
        System.out.println("Pos 프로그램을 종료합니다.");
    }

    public void printInvalidChoice() {
        System.out.println("올바르지 않은 선택입니다.");
    }

    public void printPaymentFailed() {
        System.out.println("장바구니가 비어 있어서 결제를 진행할 수 없습니다.");
    }

    public void printPaymentPrice(int totalPrice) {
        System.out.printf("결제 금액은 %d원 입니다.\n", totalPrice);
    }

    public void printPaymentCompleted() {
        System.out.println("결제가 완료되었습니다.");
    }

    public void printPaymentCanceled() {
        System.out.println("결제가 취소되었습니다.");
    }

    public void printCart(ShoppingCart shoppingCart) {
        List<OrderItem> items = shoppingCart.getItems();

        for(int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            String option = item.getOption();

            System.out.printf("%3d)  %-10s  %-3d  %-6d원\n",
                    i + 1,
                    item.getMenuItem().getName(),
                    item.getQuantity(),
                    item.getItemPrice()
            );

            if(!option.isBlank()) {
                System.out.println("    (" + option + "    )");
            }
        }

        System.out.println();
        System.out.printf("총 금액 : %10d원\n", shoppingCart.getTotalPrice());
    }
}
