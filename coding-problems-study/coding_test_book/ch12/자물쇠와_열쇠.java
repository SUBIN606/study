package coding_test_book.ch12;

import java.util.Arrays;

public class 자물쇠와_열쇠 {

    public static void main(String[] args) {
        int[][] key = {{0, 0, 0}, {1, 0, 0}, {0, 1, 1}};
        int[][] lock = {{1, 1, 1}, {1, 1, 0}, {1, 0, 1}};
        boolean answer = solution(key, lock);
        System.out.println(answer);
    }

    /*
     * 자물쇠는 격자 한 칸의 크기가 1 * 1인 N * N 크기의 정사각 격자 형태이고, 특이한 모양의 열쇠는 M * M 크기인 정사각 격자 형태로 되어 있다.
     * 자물쇠에는 홈이 파여 있고 열쇠 또한 홈과 돌기 부분이 있다.
     * 열쇠는 회전과 이동이 가능하며 열쇠의 돌기 부분을 자물쇠의 홈 부분에 맞게 딱 채우면 자물쇠가 열리게 되는 구조다.
     *
     * 자물쇠 영역을 벗어난 부분에 있는 열쇠의 홈과 돌기는 자물쇠를 여는 데 영향을 주지 않지만,
     * 자물쇠 영역 내에서는 열쇠의 돌기 부분과 자물쇠의 홈 부분이 정확히 일치해야 하며 열쇠의 돌기와 자물쇠의 돌기가 만나서는 안된다.
     * 또한 자물쇠의 모든 홈을 채워 비어있는 곳이 없어야 자물쇠를 열 수 있다.
     *
     * 열쇠로 자물쇠를 열 수 있으면 true를, 열 수 없으면 false를 리턴하라.
     * */
    public static boolean solution(int[][] key, int[][] lock) {
        // 자물쇠 3배 크기의 배열을 만든다.
        int lockLength = lock.length;
        int[][] arr = new int[lockLength * 3][lockLength * 3];

        // 가운데에 자물쇠를 위치시킨다.
        for (int i = 0; i < lockLength; i++) {
            System.arraycopy(lock[i], 0, arr[i + lockLength], lockLength, lockLength);
        }

        int keyLength = key.length;
        int rotateCount = 0;
        while (rotateCount < 4) {
            key = rotateArray(key);
            rotateCount++;
            // 열쇠를 이동하면서 딱 맞는 경우가 있는지 확인한다.
            for (int i = 0; i < lockLength * 2; i++) {
                for (int j = 0; j < lockLength * 2; j++) {
                    // 자물쇠에 열쇠를 맞춰본다.
                    for (int k = 0; k < keyLength; k++) {
                        for (int l = 0; l < keyLength; l++) {
                            arr[i + k][j + l] += key[k][l];
                        }
                    }

                    // 열 수 있는지 확인
                    if (canOpen(arr, lockLength)) {
                        return true;
                    }

                    // 맞춰본 열쇠를 제거한다.
                    for (int k = 0; k < keyLength; k++) {
                        for (int l = 0; l < keyLength; l++) {
                            arr[i + k][j + l] -= key[k][l];
                        }
                    }
                }
            }
        }

        return false;
    }

    // 열 수 있는지 확인한다.
    private static boolean canOpen(int[][] arr, int originLength) {
        for (int i = originLength; i < originLength * 2; i++) {
            for (int j = originLength; j < originLength * 2; j++) {
                if (arr[i][j] != 1) {   // 홈과 돌기가 완벽히 일치해야만 한다. 돌기와 돌기끼리 만날 수 없다.
                    return false;
                }
            }
        }
        return true;
    }

    // 배열을 반시계 방향으로 회전시킴
    private static int[][] rotateArray(int[][] arr) {
        int col = arr[0].length, row = arr.length;
        int[][] newArr = new int[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = col - 1; j >= 0; j--) {
                newArr[i][j] = arr[Math.abs(j - (row - 1))][i];
            }
        }
        return newArr;
    }

    private static void print(int[][] arr) {
        for (int[] sub : arr) {
            System.out.println(Arrays.toString(sub));

        }
        System.out.println();
    }
}
