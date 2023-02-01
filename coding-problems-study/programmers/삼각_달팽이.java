package programmers;

import java.util.Arrays;

public class 삼각_달팽이 {

    int[][] move = {
            {1, 0},     // down
            {0, 1},     // right
            {-1, -1}    // left + up
    };

    public static void main(String[] args) {
        삼각_달팽이 삼각_달팽이 = new 삼각_달팽이();
        int[] result = 삼각_달팽이.solution(10);
        System.out.println("result = " + Arrays.toString(result));
    }

    public int[] solution(int n) {
        int count = capacity(n);
        int[] answer = new int[count];
        int[][] triangle = new int[n][n];
        triangle[0][0] = 1;

        int turn = 1;
        int direction = 0;
        int x = 0;
        int y = 0;
        for (int i = 1; i < count; i++) {
            int nY = y, nX = x;
            nY += move[direction][0];
            nX += move[direction][1];

            // 내려가던 중 더 내려갈 곳이 없음
            if (direction == 0 && nY > n - turn) {
                direction++;
            }

            // 오른쪽으로 가던 중 더 갈 곳이 없음
            else if (direction == 1 && (nX > n - turn || triangle[nY][nX] > 0)) {
                direction++;
            }

            // 왼쪽 위로 올라가던 중 이미 갔던 곳임
            else if(direction == 2 && triangle[nY][nX] > 0) {
                direction = 0;
                turn++;
            }
            y += move[direction][0];
            x += move[direction][1];
            triangle[y][x] = i + 1;
        }

        int lastIndex = 0;
        for (int[] ints : triangle) {
            for (int i : ints) {
                if(i > 0) {
                    answer[lastIndex] = i;
                    lastIndex++;
                } else {
                    break;
                }
            }
        }

        return answer;
    }

    private int capacity(int n) {
        if(n == 1) {
            return 1;
        }
        return n + capacity(n - 1);
    }


    private void print(int[][] array) {
        for (int[] ints : array) {
            System.out.println(Arrays.toString(ints));
        }
        System.out.println();
    }
}
