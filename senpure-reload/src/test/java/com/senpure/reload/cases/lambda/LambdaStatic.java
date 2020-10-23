package com.senpure.reload.cases.lambda;

import com.senpure.reload.cases.AbstractReloadTestSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LambdaStatic
 *
 * @author senpure
 * @time 2020-10-16 15:56:49
 */
public class LambdaStatic extends AbstractReloadTestSupport {
    @Override
    public void execute() {

        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            list.add(i);
            list.add(i);

        }
        Map<Integer, Integer> map = new HashMap<>();

        for (Integer integer : list) {
            map.merge(integer, 1, Add::sum);
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {

            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        start();
        //System.out.println(LambdaStatic.class.getName());
    }
}
