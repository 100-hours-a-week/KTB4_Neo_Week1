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
    private final List<MenuItem> menuList = new ArrayList<>();
    private final ShoppingCart curShoppingCart = new ShoppingCart();

    private final Scanner sc = new Scanner(System.in);

    private final InputView inputView = new InputView(sc);
    private final OutputView outputView = new OutputView();

    private final PaymentService paymentService = new PaymentService();


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
            outputView.printMainScreen(menuList, curShoppingCart);
            outputView.printMainActions();

            int input = inputView.readMainMenu();

            switch(input) {
                case 1 -> {
                    int menuId = inputView.readMenuId();

                    MenuItem selectedMenu = getMenuById(menuId);
                    if(selectedMenu == null) {
                        outputView.printInvalidMenuSelect();
                        continue;
                    }

                    int qty = inputView.readQuantity();

                    if (selectedMenu instanceof Drink drink) {
                        for (int i = 1; i <= qty; i++) {
                            OrderItem orderItem = new OrderItem(selectedMenu, 1);

                            if (drink.isAvailableIce()) {
                                IceOrHot iceOrHot = inputView.readIceOrHot(i);
                                orderItem.setIceOrHot(iceOrHot);

                                if (iceOrHot == IceOrHot.ICE) {
                                    IceAmount ice = inputView.readIceAmount(i);
                                    orderItem.setIceAmount(ice);
                                }
                            }

                            curShoppingCart.addItem(orderItem);
                        }
                    } else {
                        OrderItem orderItem = new OrderItem(selectedMenu, qty);

                        if(selectedMenu instanceof Cake) {
                            int fork = inputView.readForkCount();
                            orderItem.setForkCount(fork);
                        }

                        curShoppingCart.addItem(orderItem);
                    }

                    outputView.printAddCart();
                }
                case 2 -> {
                    if (!paymentService.canPay(curShoppingCart)) {
                        outputView.printPaymentFailed();
                        break;
                    }

                    int totalPrice = paymentService.calculatePaymentPrice(curShoppingCart);
                    outputView.printPaymentPrice(totalPrice);

                    boolean confirm = inputView.readPaymentConfirm();
                    boolean paid = paymentService.processPayment(confirm);

                    if (paid) {
                        outputView.printPaymentCompleted();
                        curShoppingCart.clear();
                    } else {
                        outputView.printPaymentCanceled();
                    }
                }
                case 3 -> {
                    outputView.printExit();
                    running = false;
                }
                default -> outputView.printInvalidChoice();
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
}
