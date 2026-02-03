package dev.training;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Дан массив интов, нужно найди два числа в массиве, которые в сумме дают заданное число target.
 */

public class ArraySum {

    public static pair processArray(Integer[] array, Integer target) {
        final Map<Integer, List<Integer>> indexValueMap = new HashMap<>();

        for (int i = 0; i < array.length; i++) {
            List<Integer> set = indexValueMap.get(array[i]);
            if (set == null) set = new ArrayList<>();
            set.add(i);
            indexValueMap.put(array[i], set);
        }

        for (int i = 0; i < array.length; i++) {

            int current = array[i];
            int targetElementValue = target - current;

            List<Integer> targetGet = indexValueMap.get(targetElementValue);

            if (targetGet == null) continue;

            int firstCandidate = targetGet.get(0);
            if (firstCandidate != i) return new pair(i, firstCandidate);

            if (targetGet.size() == 1) continue;
            int secondCandidate = targetGet.get(1);
            return new pair(i, secondCandidate);

        }

        return new pair(-1, -1);

    }

    public static void main(String[] args) throws Exception {
        print(processArray(new Integer[]{1, 2, 3, 4, 5}, 5));
        print(processArray(new Integer[]{2, 2, 2, 2, 2}, 5));
        print(processArray(new Integer[]{2, 2, 2, 2, 2}, 4));
        print(processArray(new Integer[]{2}, 2));
        print(processArray(new Integer[]{}, 2));
        print(processArray(new Integer[]{0, 0, 0, 0, 0, 0, 0, 2, 2}, 4));
    }

    private static void print(Object pair) {
        System.out.println(pair);
    }

    record pair(int ind1, int ind2) {
    }
}



