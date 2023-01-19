package programmers;

import java.util.Arrays;
import java.util.PriorityQueue;

public class 명예의_전당 {

    public static void main(String[] args) {
        명예의_전당 명예의_전당 = new 명예의_전당();
        int[] result = 명예의_전당.solution(4, new int[]{0, 300, 40, 300, 20, 70, 150, 50, 500, 1000});
        System.out.println("result = " + Arrays.toString(result));
    }

    public int[] solution(int k, int[] score) {
        int[] answer = new int[score.length];
        PriorityQueue<Integer> treeSet = new PriorityQueue<>();
        // 1일차의 최하위 점수는 무조건 1일차 점수가 된다.
        answer[0] = score[0];
        treeSet.add(score[0]);

        for (int i = 1; i < score.length; i++) {
            treeSet.add(score[i]);
            if (i < k && answer[i-1] > score[i]) {
                answer[i] = score[i];   // 새로운 최하위 값으로 갱신
            }
            if (treeSet.size() > k) {
                treeSet.poll();
            }
            answer[i] = treeSet.peek();
        }

        return answer;
    }
}
