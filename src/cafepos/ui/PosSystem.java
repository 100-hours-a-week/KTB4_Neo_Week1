package cafepos.ui;

import cafepos.domain.menu.*;
import cafepos.domain.option.IceAmount;
import cafepos.domain.option.IceOrHot;
import cafepos.domain.order.OrderItem;
import cafepos.domain.shoppingcart.ShoppingCart;
import cafepos.service.PaymentService;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class PosSystem {

    /*
     * menuList : 판매되는 메뉴들에 대한 정보를 저장하는 리스트
     * curShoppingCart : 현재 시점의 장바구니 상태를 저장
     *
     */
    private final List<MenuItem> menuList = new ArrayList<>();
    private final ShoppingCart curShoppingCart = new ShoppingCart();

    // 콘솔 입력을 같은 Scanner로 처리
    private final Scanner sc = new Scanner(System.in);
    private final PaymentService paymentService = new PaymentService(sc);


    public static void main(String[] args) {

        PosSystem pos = new PosSystem();
        pos.initMenu();
        pos.start();

    }


    private void initMenu() {
        menuList.add(new Coffee(1, "아메리카노", 4000, true));
        menuList.add(new Coffee(2, "에스프레소", 3000, false));
        menuList.add(new Coffee(3, "바닐라라떼", 4500, true));

        menuList.add(new Tea(4, "얼그레이", 3000, true));
        menuList.add(new Tea(5, "자몽허니블랙티", 4000, true));
        menuList.add(new Tea(6, "캐모마일", 3000, true));

        menuList.add(new Cake(7, "초코케이크", 6500));
        menuList.add(new Cake(8, "치즈케이크", 6000));

        menuList.add(new Cookie(9, "초코칩쿠키", 2500));
        menuList.add(new Cookie(10, "버터쿠키", 2000));
    }

    private void start() {
        boolean running = true;

        while(running) {
            printScreen();

            System.out.println("무엇을 도와드릴까요 ?");
            System.out.println("1) 메뉴 주문    2) 장바구니 결제    3) 종료하기\n");

            int input = readInt();

            switch(input) {
                case 1 -> {
                    System.out.println("메뉴를 보고 메뉴 번호를 선택해주세요.");
                    int menuId = readMenuId();

                    MenuItem selectedMenu = getMenuById(menuId);
                    if(selectedMenu == null) {
                        System.out.println("선택하신 메뉴는 없는 메뉴입니다.");
                        continue;
                    }

                    System.out.println("해당 상품 구매하실 수량을 입력하세요.");
                    int qty = readQty();

                    if (selectedMenu instanceof Drink drink) {
                        // Drink 메뉴의 경우 원하는 수량을 담을 때,
                        // 해당 수량만큼 반복문을 돌려 옵션도 여러 번 선택할 수 있도록 해야 함.
                        for (int i = 1; i <= qty; i++) {
                            OrderItem orderItem = new OrderItem(selectedMenu, 1);

                            if (drink.isAvailableIce()) {
                                IceOrHot iceOrHot = readIceOrHot(i);
                                orderItem.setIceOrHot(iceOrHot);

                                if (iceOrHot == IceOrHot.ICE) {
                                    IceAmount ice = readIceAmount(i);
                                    orderItem.setIceAmount(ice);
                                }
                            }

                            curShoppingCart.addItem(orderItem);
                        }
                    } else {
                        OrderItem orderItem = new OrderItem(selectedMenu, qty);

                        if(selectedMenu instanceof Cake) {
                        int fork = readForkCount();
                        orderItem.setForkCount(fork);
                    }

                        curShoppingCart.addItem(orderItem);
                    }

                    System.out.println("선택하신 상품이 장바구니에 추가되었습니다.");
                }
                case 2 -> {
                    boolean paid = paymentService.processPayment(curShoppingCart);
                    if (paid) {
                        curShoppingCart.clear();
                    }
                }
                case 3 -> {
                    System.out.println("Pos 프로그램을 종료합니다.");
                    running = false;
                }
                default -> {
                    System.out.println("올바르지 않은 선택입니다.");
                }
            }
        }

    }

    private MenuItem getMenuById(int id) {
        for(MenuItem item : menuList) {
            if(item.getId() == id)
                return item;
        }

        return null;
    }

    private int readForkCount() {
        while (true) {
            System.out.println("필요한 포크 갯수를 입력하세요.");
            try {
                int n = Integer.parseInt(sc.nextLine().trim());

                if(n < 1) {
                    throw new IllegalArgumentException();
                }

                return n;
            }
            catch (NumberFormatException e) {
                System.out.println("숫자로 입력하세요.");
            }
            catch (IllegalArgumentException e) {
                System.out.println("올바른 갯수를 입력하세요.");
            }
        }
    }

    private IceOrHot readIceOrHot(int cupNo) {
        System.out.printf("%d번째 음료 온도를 선택하세요.%n", cupNo);
        System.out.println("1) 핫    2) 아이스");
        while(true) {
            int input = readInt();

            switch(input) {
                case 1 -> { return IceOrHot.HOT; }
                case 2 -> { return IceOrHot.ICE; }
                default -> { System.out.println("올바른 옵션을 고르세요.");}
            }
        }
    }

    private IceAmount readIceAmount(int cupNo) {
        System.out.printf("%d번째 음료의 얼음 양을 선택하세요.%n", cupNo);
        System.out.println("1) 적게    2) 많이    3) 없이");
        while(true) {
            int input = readInt();

            switch(input) {
                case 1 -> { return IceAmount.LESS; }
                case 2 -> { return IceAmount.MORE; }
                case 3 -> { return IceAmount.NONE; }
                default -> { System.out.println("올바른 얼음 양을 고르세요.");}
            }
        }
    }

    private int readMenuId() {
        while (true) {
            try {
                int menuId = Integer.parseInt(sc.nextLine().trim());

                if (menuId < 1) {
                    throw new IllegalArgumentException();
                }

                return menuId;
            }
            catch (NumberFormatException e) {
                System.out.println("숫자로 입력하세요.");
            }
            catch (IllegalArgumentException e) {
                System.out.println("올바른 메뉴 번호를 입력하세요.");
            }
        }
    }

    private int readQty() {
        while(true) {
            try{
                int qty = Integer.parseInt(sc.nextLine().trim());

                if(qty < 1) {
                    throw new IllegalArgumentException();
                }

                return qty;
            }
            catch (NumberFormatException e) {
                System.out.println("숫자로 입력하세요.");
            }
            catch (IllegalArgumentException e) {
                System.out.println("올바른 수량을 입력하세요.");
            }
        }
    }

    private int readInt() {
        while(true) {
            try {
                int num = Integer.parseInt(sc.nextLine().trim());

                if(num < 1 || num > 3) {
                    throw new IllegalArgumentException();
                }

                return num;
            }
            catch (NumberFormatException e) {
                System.out.println("숫자로 입력하세요.");
            }
            catch (IllegalArgumentException e) {
                 System.out.println("올바른 옵션을 선택하세요.");
            }
        }
    }

    private void printScreen() {

        System.out.println("\n\n=================================================");
        System.out.println("                    Cafe Pos System");
        System.out.println("=================================================\n\n");

        System.out.println("[Menu]");
        for(MenuItem menuItem : menuList) {
            menuItem.printInfo();
        }

        System.out.println("\n\n-------------------------------------------------");
        System.out.println("[Shopping Cart]");

        curShoppingCart.printCart();

        System.out.println("-------------------------------------------------\n");
    }
}
