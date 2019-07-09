package vip.wulang.algorithm.test.book;

import org.junit.Test;

/**
 * @author coolerwu
 * @version 1.0
 */
public class One {
    @Test
    public void test01() {
        System.out.println(findKMaoPao(new int[]{1, 2, 3, 4, 5, 6, 7}, 1));
        System.out.println(findKNew(new int[]{1, 2, 3, 4, 5, 6, 7}, 3));
    }

    private int findKMaoPao(int[] arr, int k) {
        if (k > arr.length || k <= 0) {
            throw new NullPointerException();
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] < arr[j]) {
                    int swap = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swap;
                }
            }
        }

        return arr[k - 1];
    }

    private int findKNew(int[] arr, int k) {
        if (k > arr.length || k <= 0) {
            throw new NullPointerException();
        }

        int[] new_arr = new int[k];

        for (int i = 0; i < arr.length; i++) {
            putX(new_arr, arr[i]);
        }

        return new_arr[k - 1];
    }

    private void putX(int[] arr, int x) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < x) {
                int tmp = arr[i];
                arr[i] = x;

                for (int j = i + 1; j < arr.length; j++) {
                    int tmp_l = arr[j];
                    arr[j] = tmp;
                    tmp = tmp_l;
                }

                break;
            }
        }
    }
}
