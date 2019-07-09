package vip.wulang.spring.algorithm;

/**
 * Find k.
 *
 * @author coolerwu
 * @version 1.0
 */
public class FindKAlgorithm {
    private FindKAlgorithm() {
    }

    public static int findKMaoPao(int[] arr, int k) {
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

    public static int findKNew(int[] arr, int k) {
        if (k > arr.length || k <= 0) {
            throw new NullPointerException();
        }

        int[] new_arr = new int[k];

        for (int value : arr) {
            putX(new_arr, value);
        }

        return new_arr[k - 1];
    }

    private static void putX(int[] arr, int x) {
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
