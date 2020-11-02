package com.senpure.reload.bytebuddy;

/**
 * Add
 *
 * @author senpure
 * @time 2020-10-22 17:35:43
 */
public class Add {
    public static int c = 6;
    public static int d = 7;
    private int b = 1;

    public int add(int x, int y) {

        if (x == 1) {
            x++;
        }

        return x + y ;
    }


}
