package cafepos.ui;

import cafepos.domain.menu.MenuData;
import cafepos.domain.option.IceAmount;
import cafepos.domain.option.IceOrHot;

import java.util.Scanner;

public class InputView {
    private final Scanner sc;

    public InputView(Scanner sc) {
        this.sc = sc;
    }

    public int readMainMenu() {
        return readIntInRange(1, 3, "올바른 옵션을 선택하세요.");
    }

    public int readMenuId() {
        System.out.println("메뉴를 보고 메뉴 번호를 선택해주세요.");
        return readIntInRange(1, MenuData.getMaxMenuId(), "올바른 메뉴 번호를 입력하세요.");
    }

    public int readQuantity() {
        System.out.println("해당 상품 구매하실 수량을 입력하세요.");
        return readIntInRange(1, Integer.MAX_VALUE, "올바른 수량을 입력하세요.");
    }

    public int readForkCount() {
        System.out.println("필요한 포크 갯수를 입력하세요.");
        return readIntInRange(1, Integer.MAX_VALUE, "올바른 갯수를 입력하세요.");
    }

    public IceOrHot readIceOrHot(int cupNo) {
        System.out.printf("%d번째 음료 온도를 선택하세요.%n", cupNo);
        System.out.println("1) 핫    2) 아이스");
        while (true) {
            int input = readIntInRange(1, 2, "올바른 옵션을 고르세요.");
            switch (input) {
                case 1:
                    return IceOrHot.HOT;
                case 2:
                    return IceOrHot.ICE;
                default:
            }
        }
    }

    public IceAmount readIceAmount(int cupNo) {
        System.out.printf("%d번째 음료의 얼음 양을 선택하세요.%n", cupNo);
        System.out.println("1) 적게    2) 많이    3) 없이");
        while (true) {
            int input = readIntInRange(1, 3, "올바른 얼음 양을 고르세요.");
            switch (input) {
                case 1:
                    return IceAmount.LESS;
                case 2:
                    return IceAmount.MORE;
                case 3:
                    return IceAmount.NONE;
                default:
            }
        }
    }

    public boolean readPaymentConfirm() {
        System.out.println("결제를 진행하시겠습니까?\n1) 예    2) 아니오)");
        while (true) {
            int input = readIntInRange(1, 2, "1 또는 2를 입력하세요.");
            if (input == 1) return true;
            if (input == 2) return false;
        }
    }

    private int readIntInRange(int min, int max, String invalidMessage) {
        while (true) {
            try {
                int num = Integer.parseInt(sc.nextLine().trim());
                if (num < min || num > max) {
                    throw new IllegalArgumentException();
                }
                return num;
            } catch (NumberFormatException e) {
                System.out.println("숫자로 입력하세요.");
            } catch (IllegalArgumentException e) {
                System.out.println(invalidMessage);
            }
        }
    }
}
