package programmers;

import java.util.Arrays;

/**
 * @see <a href="https://school.programmers.co.kr/learn/courses/30/lessons/92335">문제 링크</a>
 * */
public class K진수에서_소수_개수_구하기 {
    /*
     * 양의 정수 N이 주어진다. 이 숫자를 K진수로 바꿨을 때, 변환된 수 안에 아래 조건에 맞는 소수(Prime number)가 몇 개인지 알아보려 한다.
     * 1. 0P0처럼 소수 양쪽에 0이 있는 경우
     * 2. P0처럼 소수 오른쪽에만 0이 있고 왼쪽에는 아무것도 없는 경우
     * 3. 0P처럼 소수 왼쪽에만 0이 있고 오른쪽에는 아무것도 없는 경우
     * 4. P처럼 소수 양쪽에 아무것도 없는 경우
     * 단, P는 각 자릿수에 0을 포함하지 않는 소수다.
     *
     * 정수 n과 k가 매개변수로 주어집니다.
     * n을 k진수로 바꿨을 때, 변환된 수 안에서 찾을 수 있는 위 조건에 맞는 소수의 개수를 return 하도록 solution 함수를 완성해 주세요.
     * */
    public int solution(int n, int k) {
        // k진수로 바꾼다.
        String s = Long.toString(n, k);

        // 조건에 맞으면서 소수인 개수를 센다.
        return (int) Arrays.stream(s.split("0+"))
                .filter(str -> isPrime(Long.parseLong(str)))
                .count();
    }

    // 소수인지 확인한다.
    private boolean isPrime(long num) {
        if (num < 2) {
            return false;
        }
        for (int i = 2; i <= Math.floor(Math.sqrt(num)); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
