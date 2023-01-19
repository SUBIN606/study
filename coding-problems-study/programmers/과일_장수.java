package programmers;

import java.util.Arrays;

public class 과일_장수 {

    public static void main(String[] args) {
        과일_장수 과일_장수 = new 과일_장수();
        int result = 과일_장수.solution(7, 2, new int[]{7, 7, 5, 3, 3, 3, 1});
        System.out.println("result = " + result);
    }

    //사과는 상태에 따라 1점부터 k점까지의 점수로 분류하며, k점이 최상품의 사과이고 1점이 최하품의 사과입니다.
    //한 상자에 사과를 m개씩 담아 포장합니다.
    //상자에 담긴 사과 중 가장 낮은 점수가 p (1 ≤ p ≤ k)점인 경우, 사과 한 상자의 가격은 p * m 입니다.
    public int solution(int k, int m, int[] score) {
        int answer = 0;

        score = Arrays.stream(score).sorted().toArray();

        for (int i = score.length % m; i < score.length; i += m) {
            answer += score[i] * m;
        }

        return answer;
    }
}
