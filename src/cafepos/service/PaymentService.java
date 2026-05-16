package cafepos.service;

import cafepos.domain.shoppingcart.ShoppingCart;

import java.util.Scanner;

public class PaymentService {
    private final Scanner sc;

    public PaymentService(Scanner sc) {
        this.sc = sc;
    }

    public boolean processPayment(ShoppingCart shoppingCart) {
        if (shoppingCart.isEmpty()) {
            System.out.println("장바구니가 비어 있어서 결제를 진행할 수 없습니다.");
            return false;
        }

        int totalPrice = shoppingCart.getTotalPrice();
        System.out.printf("결제 금액은 %d원 입니다.%n", totalPrice);
        System.out.println("결제를 진행하시겠습니까?\n1) 예    2) 아니오)");

        while (true) {
            String input = sc.nextLine().trim();
            int n = Integer.parseInt(input);

            switch (n) {
                case 1:
                    System.out.println("결제가 완료되었습니다.");
                    return true;
                case 2:
                    System.out.println("결제가 취소되었습니다.");
                    return false;
                default:
                    System.out.println("올바른 옵션을 입력하세요.");
            }
        }
    }
}
