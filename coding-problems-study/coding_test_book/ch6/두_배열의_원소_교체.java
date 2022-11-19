package coding_test_book.ch6;

import java.util.Arrays;
import java.util.Collections;


public class 두_배열의_원소_교체 {

    public static void main(String[] args) {
        int result = solution(3, new Integer[]{1, 2, 5, 4, 3}, new Integer[]{5, 5, 6, 6, 5});
        System.out.println("result = " + result);
    }

    /*
     * 두 개의 배열 A, B가 있다.
     * 두 배열은 N개의 원소로 구성되어 있으며, 배열의 원소는 모두 자연수이다.
     * 최대 K번의 바꿔치기 연산을 수행할 수 있다.
     * 바꿔치기 연산이란 배열 A에 있는 원소 하나와 배열 B에 있는 원소 하나를 골라서 두 원소를 서로 바꾸는 것을 말한다.
     *
     * 배열 A의 모든 원소의 합이 최대가 되도록 하는 것이 목표다.
     *
     * N, K 그리고 배열 A, B가 주어졌을 때, 최대 K번의 바꿔치기 연산을 수행하여 만들 수 있는 배열 A의 모든 원소의 합의 최댓값을 구하라.
     * */
    public static int solution(int k, Integer[] a, Integer[] b) {

        // a 배열은 오름차순 정렬
        Arrays.sort(a);
        // b 배열은 내림차순 정렬
        Arrays.sort(b, Collections.reverseOrder());

//        for (int i = 0; i < k; i++) {
//            //b 배열의 원소가 a배열의 원소보다 클 경우에만 바꿔치기 연산을 수행한다.
//            if (b[i] > a[i]) {
//                int temp = a[i];
//                a[i] = b[i];
//                b[i] = temp;
//            }
//            break;
//        }
        recursive(0, a, b);

        return Arrays.stream(a)
                .reduce(Integer::sum)
                .get();
    }

    public static void recursive(int i, Integer[] a, Integer[] b) {
        if (i >= a.length || b[i] <= a[i]) {
            return;
        }

        int temp = a[i];
        a[i] = b[i];
        b[i] = temp;

        recursive(i + 1, a, b);

    }

}
