package programmers;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class 내적 {

    /*
    * 길이가 같은 두 1차원 정수 배열 a, b가 매개변수로 주어집니다.
    * a와 b의 내적을 return 하도록 solution 함수를 완성해주세요.
    * 이때, a와 b의 내적은 a[0]*b[0] + a[1]*b[1] + ... + a[n-1]*b[n-1] 입니다. (n은 a, b의 길이)
    */

    public static void main(String[] args) {
        int[] a = {1,2,3,4};
        int[] b= {-3, -1, 0, 2};
        int answer = solution(a, b);
        System.out.println("answer = " + answer);
    }

    public static int solution(int[] a, int[] b) {
       return IntStream.range(0, a.length)
                .reduce(0, (sum, i) -> sum += a[i] * b[i]);
    }
}
