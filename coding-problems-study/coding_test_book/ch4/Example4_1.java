package coding_test_book.ch4;

import java.util.Arrays;

public class Example4_1 {

    public static void main(String[] args) {
        int[] solution = solution(5, new String[]{"R", "R", "R", "U", "D", "D"});
        System.out.println("solution = " + Arrays.toString(solution));
    }

    /*
     * 상하좌우
     *
     * N * N 크기의 정사각형 공간이 있다. 이 공간은 1 * 1 크기의 정사각형으로 이루어져 있다.
     * 가장 왼쪽 위 좌표는 (1, 1)이고, 가장 오른쪽 아래 좌표는 (N, N)에 해당한다.
     * 상, 하, 좌, 우 방향으로 이동할 수 있으며 시작 좌표는 항상 (1, 1)이다.
     *
     * 움직일 수 있는 계획서(R, L, U, D로 이루어진 문자열 배열)가 주어질 때 최종 도착 좌표를 반환하라.
     * 단, 정사각형 공간을 벗어나는 움직임은 무시된다.
     */
    public static int[] solution(int n, String[] plans) {
        int[] answer = {1, 1};  // 시작 좌표로 초기화

        // R - y 좌표 1 증가
        // L - y 좌표 1 감소
        // U - x 좌표 1 감소
        // D - x 좌표 1 증가
        for (String plan : plans) {
            if ("R".equals(plan) && answer[1] < n) {
                answer[1] += 1;
            }
            if ("L".equals(plan) && answer[1] > 1) {
                answer[1] -= 1;
            }
            if ("U".equals(plan) && answer[0] > 1) {
                answer[0] -= 1;
            }
            if ("D".equals(plan) && answer[0] < n) {
                answer[0] += 1;
            }
        }

        return answer;
    }

}
