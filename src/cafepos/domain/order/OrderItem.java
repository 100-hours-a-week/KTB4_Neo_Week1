package cafepos.domain.order;

import cafepos.domain.menu.MenuCategory;
import cafepos.domain.menu.MenuData;
import cafepos.domain.option.IceAmount;
import cafepos.domain.option.IceOrHot;

public class OrderItem {

    private final MenuData menuData;
    private int quantity;

    private IceOrHot iceOrHot;
    private IceAmount iceAmount;

    private int forkCount;

    public OrderItem(MenuData menuData, int quantity) {
        this.menuData = menuData;
        this.quantity = quantity;
    }

    public MenuData getMenuData() { return menuData; }
    public int getQuantity() { return quantity; }
    public IceOrHot getIceOrHot() { return iceOrHot; }
    public IceAmount getIceAmount() { return iceAmount; }


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
        return menuData.getPrice() * quantity;
    }

    public String getOption() {
        StringBuilder sb = new StringBuilder();

        if (menuData.isAvailableIce()) {
                if(iceOrHot == IceOrHot.ICE) {
                    sb.append("아이스");
                    if (iceAmount != null) {
                        sb.append(" / 얼음 ").append(iceAmount.getLabel());
                    }
                }
                else {
                    sb.append("핫");
                }
        }

        if (menuData.getCategory() == MenuCategory.CAKE) {
            sb.append("포크 : ").append(forkCount).append("개");
        }

        return sb.toString();
    }

}
