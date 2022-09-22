package programmers;

import java.util.Arrays;

public class 양궁대회 {

    static int max = 0;
    static int[] ryan = new int[11];

    public static void main(String[] args) {
        int[] answer = solution(5, new int[]{2, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0});
        System.out.println();
        System.out.println(Arrays.toString(answer));
    }

    /*
    * 라이언은 지난 양궁대회 우승자이고, 결승전 상대는 어피치다.
    * 결승전 규칙을 라이언에게 불리하게 정했다.
    *
    * 1. 어피치가 화살 n발을 다 쏜 후에 라이언이 화살 n발을 쏜다.
    * 2. 점수를 계산한다.
    *   2-1. 만약 k점을 어피치가 a발 맞혔고, 라이언이 b발을 맞혔을 경우 더 많은 화살을 k점에 맞힌 선수가 k점을 가져간다.
    *           단, a == b일 경우 어피치가 점수를 가져간다.
    * 3. 최종 점수가 더 높은 선수를 우승자로 결정한다. 단, 최종 점수가 같을 경우 어피치를 우승자로 결정한다.
    *
    * 라이언이 어피치를 가장 큰 점수차로 이기기 위해서 n발의 화살을 어떤 과녁 점수에 맞혀야 하는지 구하려고 한다.
    * 화살의 개수를 담은 자연수 n, 어피치가 맞힌 과녁 점수의 개수를 10점부터 0점까지 순서대로 담은 정수 배열 info가 주어진다.
    * 이때 라이언이 가장 큰 점수차이로 우승하기 위해 n발의 화살을 어떤 과녁 점수에 맞혀야 하는지를 10점부터 0점까지 순서대로 정수 배열에 담아 반환하라.
    * 라이언이 가장 큰 점수 차이로 우승할 수 있는 방법이 여러 가지 일 경우, 가장 낮은 점수를 더 많이 맞힌 경우를 return 해주세요.
    *
    * 만약, 라이언이 우승할 수 없는 경우(무조건 지거나 비기는)에는 [-1]을 반환하라.
    * */
    public static int[] solution(int n, int[] info) {
        dfs(0, info, new int[11], n);
        return max > 0 ? ryan : new int[]{-1};
    }

    // 어피치와 라이언 점수 계산 후 점수차 반환
    public static int compare(int[] a, int[] r) {
        int aScore = 0, rScore = 0;
        for (int i = 0; i < 11; i++) {
            if (a[i] == 0 && r[i] == 0) {
                 continue;
            }
            if (a[i] >= r[i]) {
                aScore += (10 - i);
            } else {
                rScore += (10 - i);
            }
        }
        return rScore - aScore;
    }

    public static void dfs(int idx, int[] info, int[] record, int arrow) {
        if (arrow == 0) {   // n개의 화살만큼 모두 쏜 경우
            int compare = compare(info, record);    // 점수차를 계산한다.

            // 이전에 기록한 점수차보다 이번 기록한 점수차가 더 크면
            if (compare > max) {
                // 최대 점수차를 갱신한다.
                max = compare;
                ryan = Arrays.copyOf(record, record.length);
            } else if (compare == max) { // 이전 점수차와 이번 점수차가 같으면
                // 두 기록 중 낮은 점수를 더 많이 맞춘 기록으로 갱신한다.
                for (int i = 10; i >= 0; i--) {
                    if (record[i] > ryan[i]) {
                        ryan = Arrays.copyOf(record, record.length);
                    } else if (ryan[i] > record[i]) {
                        return;
                    }
                }
            }
           return;
        }

        for (int i = idx; i < 11; i++) {
            int[] newRecord = Arrays.copyOf(record, record.length);
            if (i < 10 && arrow > info[i]) {    // 쏠 화살이 남았으면
                newRecord[i] += info[i] + 1;
                dfs(i, info, newRecord, arrow - (info[i] + 1));
            } else {
                newRecord[i] += arrow;
                dfs(i, info, newRecord, 0);
            }
        }
    }
}
