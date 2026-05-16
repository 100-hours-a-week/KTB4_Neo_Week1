package cafepos.domain.order;

import cafepos.domain.menu.Cake;
import cafepos.domain.menu.Drink;
import cafepos.domain.menu.MenuItem;
import cafepos.domain.option.IceAmount;
import cafepos.domain.option.IceOrHot;

public class OrderItem {

    private final MenuItem menuItem;
    private int quantity;

    private IceOrHot iceOrHot;
    private IceAmount iceAmount;

    private int forkCount;

    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public MenuItem getMenuItem() { return menuItem; }
    public int getQuantity() { return quantity; }
    public IceOrHot getIceOrHot() { return iceOrHot; }
    public IceAmount getIceAmount() { return iceAmount; }
    public int getForkCount() { return forkCount; }


    public void setIceOrHot(IceOrHot iceOrHot) {
        this.iceOrHot = iceOrHot;
    }

    public void setIceAmount(IceAmount iceAmount) {
        this.iceAmount = iceAmount;
    }

    public void setForkCount(int forkCount) {
        this.forkCount = forkCount;
    }

    public void addQuantity(int q) {
        this.quantity += q;
    }

    public int getItemPrice() {
        return menuItem.getPrice() * quantity;
    }

    public String getOption() {
        StringBuilder sb = new StringBuilder();

        if(menuItem instanceof Drink drink) {
            if(drink.isAvailableIce()) {
                if(iceOrHot == IceOrHot.ICE) {
                    sb.append("아이스");
                    if (iceAmount != null) {
                        sb.append(" / 얼음 ").append(iceAmount.getLabel()).append("");
                    }
                }
                else {
                    sb.append("핫");
                }
            }
        }

        if(menuItem instanceof Cake cake) {
            sb.append("포크 : ").append(forkCount).append("개");
        }

        return sb.toString();
    }

}
