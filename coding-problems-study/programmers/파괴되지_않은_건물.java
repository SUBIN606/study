package programmers;

import java.util.Arrays;

/**
* @see <a href="https://school.programmers.co.kr/learn/courses/30/lessons/92344">문제 링크</a>
* */
public class 파괴되지_않은_건물 {

    public static void main(String[] args) {
//        int[][] board = {
//                {5, 5, 5, 5, 5},
//                {5, 5, 5, 5, 5},
//                {5, 5, 5, 5, 5},
//                {5, 5, 5, 5, 5}
//        };
//        int[][] skill = {
//                {1, 0, 0, 3, 4, 4}, {1, 2, 0, 2, 3, 2}, {2, 1, 0, 3, 1, 2}, {1, 0, 1, 3, 3, 1}
//        };

//        int[][] board = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
//        int[][] skill = {{1, 1, 1, 2, 2, 4}, {1, 0, 0, 1, 1, 2}, {2, 2, 0, 2, 0, 100}};

        int[][] board = {{1, 2}, {3, 4}};
        int[][] skill = {{1, 0, 0, 0, 0, 2}, {1, 1, 1, 1, 1, 3}, {1, 1, 1, 1, 1, 2}};
        int solution = solution(board, skill);
        System.out.println(solution);
    }

    /*
    * N x M 크기의 행렬 모양의 게임 맵이 있습니다.
    * 이 맵에는 내구도를 가진 건물이 각 칸마다 하나씩 있습니다. 적은 이 건물들을 공격하여 파괴하려고 합니다.
    * 건물은 적의 공격을 받으면 내구도가 감소하고 내구도가 0이하가 되면 파괴됩니다.
    * 반대로, 아군은 회복 스킬을 사용하여 건물들의 내구도를 높이려고 합니다.
    *
    * 적의 공격과 아군의 회복 스킬은 항상 직사각형 모양입니다.
    * 건물은 파괴되었다가 복구될 수 있다.
    * 파괴된 건물도 공격을 받으면 내구도가 하락한다.
    *
    * skill의 각 행은 [type, r1, c1, r2, c2, degree]형태를 가지고 있습니다.
    * type이 1일 경우는 적의 공격을 의미합니다.
    * type이 2일 경우는 아군의 회복 스킬을 의미합니다.
    * (r1, c1)부터 (r2, c2)까지 직사각형 모양의 범위 안에 있는 건물의 내구도를 degree 만큼 낮추거나 높인다는 뜻입니다.
    *
    * 건물의 내구도를 나타내는 2차원 정수 배열 board와 적의 공격 혹은 아군의 회복 스킬을 나타내는 2차원 정수 배열 skill이 매개변수로 주어집니다.
    * 적의 공격 혹은 아군의 회복 스킬이 모두 끝난 뒤 파괴되지 않은 건물의 개수를 return하는 solution함수를 완성해 주세요.
    * */
    public static int solution(int[][] board, int[][] skill) {
        int[][] prefixSum = new int[board.length + 1][board[0].length + 1];

        for (int[] s : skill) {
            int type = s[0], r1 = s[1], c1 = s[2], r2 = s[3], c2 = s[4], degree = s[5];
            if (type == 1) {
                prefixSum[r1][c1] -= degree;
                prefixSum[r1][c2 + 1] += degree;
                prefixSum[r2 + 1][c2 + 1] -= degree;
                prefixSum[r2 + 1][c1] += degree;
            }
            if (type == 2) {
                prefixSum[r1][c1] += degree;
                prefixSum[r1][c2 + 1] -= degree;
                prefixSum[r2 + 1][c2 + 1] += degree;
                prefixSum[r2 + 1][c1] -= degree;
            }
            print(prefixSum);
        }

        // 위 -> 아래 누적합
        for (int i = 0; i < prefixSum[0].length; i++) {
            for (int j = 1; j < prefixSum.length; j++) {
                prefixSum[j][i] = prefixSum[j-1][i] + prefixSum[j][i];
            }
        }
        System.out.println("위 -> 아래 누적합");
        print(prefixSum);

        // 좌 -> 우 누적합
        for (int i = 0; i < prefixSum.length; i++) {
            for (int j = 1; j < prefixSum[i].length; j++) {
                prefixSum[i][j] = prefixSum[i][j-1] + prefixSum[i][j];
            }
        }
        System.out.println("좌 -> 우 누적합");
        print(prefixSum);

        // 원래 내구도 배열과 더하기
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] += prefixSum[i][j];
            }
        }

        return Arrays.stream(board)
                .map(sub -> Arrays.stream(sub).filter(it -> it > 0).count())
                .reduce(Long::sum)
                .get().intValue();
    }

    private static void print(int[][] arr) {
        for (int[] sub : arr) {
            System.out.println(Arrays.toString(sub));
        }
        System.out.println();
    }

}
