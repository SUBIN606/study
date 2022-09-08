package coding_test_book.ch8;

import java.util.Arrays;

public class 효율적인_화폐_구성 {

    public static void main(String[] args) {
        int answer = solution(new int[]{2, 3}, 15);
        System.out.println("answer = " + answer);
    }

    /*
     * N가지 종류의 화폐가 있다.
     * 이 화폐들의 개수를 최소한으로 이용해서 그 가치의 합이 M원이 되도록 하려고 한다.
     * 이때 각 화폐는 몇 개라도 사용할 수 있으며, 사용한 화폐의 구성은 같지만 순서만 다른 것은 같은 경우로 구분한다.
     *
     * M원을 만들기 위한 최소한의 화폐 개수를 출력하는 프로그램을 작성하라.
     * 불가능한 경우에는 -1을 출력한다.
     * */
    public static int solution(int[] units, int target) {
        int[] memo = new int[target + 1];
        Arrays.fill(memo, 10001);   // M원은 10000 이하로 주어지기 때문에 초기값을 10001로 지정한다. (나올 수 없는 수)
        memo[0] = 0;    // 0원은 항상 만들 수 없기 때문에 0은 0으로 초기화한다.

        // 작은 화폐 단위부터 계산하기 위해 정렬한다.
        Arrays.sort(units);

        for (int unit : units) {
            for (int j = unit; j <= target; j++) {
                memo[j] = Math.min(memo[j], memo[j - unit] + 1);
            }
        }

        return memo[target] == 10001 ? -1 : memo[target];
    }
}
