package com.senpure.reload.cases.enumeration;


import com.senpure.reload.cases.AbstractReloadTestSupport;

import java.util.Arrays;

/**
 * EnumOption
 *
 * @author senpure
 * @time 2020-10-20 11:06:58
 */
public class EnumOption extends AbstractReloadTestSupport {
    @Override
    public void execute() {

        System.out.println(Arrays.toString(EnumCase.values()));
    }

    enum EnumCase{
        A,
        B,
        C,
        D,E
    }

    public static void main(String[] args) {
        start();
    }
}
