package vip.wulang.spring;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * @author CoolerWu on 2019/3/9.
 * @version 1.0
 */
public class Test {
    static class Answer {
        int i;
        int j;
        int l;

        Answer(int i, int j, int l) {
            this.i = i;
            this.j = j;
            this.l = l;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int k = in.nextInt();
        int[] nums = new int[n];
        List<Answer> list = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            nums[i] = in.nextInt();
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int l = j + 1; l < n; l++) {
                    if (nums[i] + nums[j] + nums[l] == k) {
                        list.add(new Answer(i, j, l));
                    }
                }
            }
        }

        if (list.size() == 0) {
            System.out.println("-1 -1 -1");
            return;
        }

        Answer min = list.get(0);

        for (int i = 1; i < list.size(); i++) {
            Answer answer = list.get(i);
            if (answer.i + answer.j + answer.l < min.i + min.j + min.l) {
                min = answer;
            }
        }

        int a, b, c;
        a = Math.min(min.i, Math.min(min.j, min.l));
        c = Math.max(min.i, Math.max(min.j, min.l));

        if (min.i != a && min.i != c) {
            b = min.i;
        } else if (min.j != a && min.j != c) {
            b = min.j;
        } else {
            b = min.l;
        }

        System.out.println(a + " " + b + " " + c);
    }
}
