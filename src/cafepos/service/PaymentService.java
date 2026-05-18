package cafepos.service;

import cafepos.domain.shoppingcart.ShoppingCart;

public class PaymentService {
    public boolean canPay(ShoppingCart shoppingCart) {
        return !shoppingCart.isEmpty();
    }

    public int calculatePaymentPrice(ShoppingCart shoppingCart) {
        return shoppingCart.getTotalPrice();
    }

    public boolean processPayment(boolean confirm) {
        return confirm;
    }
}
