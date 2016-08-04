
package com.zzsun.mahjonghelper;

public class Calculator {
    private static Calculator sInstance;

    public static Calculator getInstance() {
        if (sInstance == null) {
            sInstance = new Calculator();
        }
        return sInstance;
    }

    public int calculate(boolean isHu, int tai, double li) {
        int result = isHu ? 120 : 0;
        int multiple = (int) (Math.pow(2, tai));
        if (tai != 0 || li != 0.0) {
            result = (int) ((li * 4 + (isHu ? 40 : 0)) * multiple);
        }
        return ((int) Math.ceil(result / 10.0)) * 10;

    }

    public int calculateManHu(int tai, double li) {
        if (tai < 3) {
            return -1;
        } else if (tai == 3) {
            int result = calculate(true, tai, li) >= 500 ? 0 : -1;
            return result;

        } else {
            return tai - 3;
        }
    }
}
