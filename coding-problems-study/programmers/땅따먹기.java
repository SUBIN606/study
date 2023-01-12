package programmers;

import java.util.Arrays;

public class 땅따먹기 {

    public static void main(String[] args) {
        int result = solution(new int[][]{
                {1,2,3,5},
                {5,6,7,100},
                {4,3,2,1},
        });
        System.out.println("result = " + result);
    }

    static int solution(int[][] land) {
        for (int i = 1; i < land.length; i++) {
            // 연속으로 같은 번호가 아니면서 최댓값을 더한다.
            land[i][0] += Math.max(land[i - 1][1], Math.max(land[i - 1][2], land[i - 1][3]));
            land[i][1] += Math.max(land[i - 1][0], Math.max(land[i - 1][2], land[i - 1][3]));
            land[i][2] += Math.max(land[i - 1][0], Math.max(land[i - 1][1], land[i - 1][3]));
            land[i][3] += Math.max(land[i - 1][0], Math.max(land[i - 1][1], land[i - 1][2]));
        }
        return Arrays.stream(land[land.length - 1]).max().getAsInt();
    }

    private static int[] findMaxAndSecondMaxIndex(int[] row) {
        int maxValue = 0;
        int max = -1;
        int secondMax = -1;
        for (int i = 0; i < row.length; i++) {
            if(row[i] > maxValue) {
                maxValue = row[i];
                if(max != -1) {
                 secondMax = max;
                }
                max = i;
            }
        }
        return new int[] {max, secondMax};
    }

}
