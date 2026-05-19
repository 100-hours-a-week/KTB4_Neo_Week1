package cafepos.ui;

import cafepos.domain.menu.MenuCategory;
import cafepos.domain.menu.MenuData;
import cafepos.domain.option.IceAmount;
import cafepos.domain.option.IceOrHot;
import cafepos.domain.order.OrderItem;
import cafepos.domain.shoppingcart.ShoppingCart;
import cafepos.service.PaymentService;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class PosSystem {
    private final List<MenuData> menuList = new ArrayList<>();
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
        menuList.addAll(List.of(MenuData.values()));
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

                    MenuData selectedMenu = MenuData.findById(menuId);
                    if(selectedMenu == null) {
                        outputView.printInvalidMenuSelect();
                        continue;
                    }

                    int qty = inputView.readQuantity();
                    boolean isDrink = selectedMenu.getCategory().isDrink();

                    if (isDrink) {
                        for (int i = 1; i <= qty; i++) {
                            OrderItem orderItem = new OrderItem(selectedMenu, 1);

                            if (selectedMenu.isAvailableIce()) {
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

                        if (selectedMenu.getCategory() == MenuCategory.CAKE) {
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

}
