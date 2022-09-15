package coding_test_book.ch11;

import java.util.Scanner;

public class 볼링공_고르기 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        int[] balls = new int[N];
        for (int i = 0; i < N; i++) {
            balls[i] = sc.nextInt();
        }

        int answer = solution(N, M, balls);
        System.out.println(answer);
    }

    /*
     * 두 사람이 볼링츨 치고 있다. 두 사람은 서로 무게가 다른 볼링공을 고르려 한다.
     * 볼링공은 총 N개가 있으며 각 볼링공마다 무게가 적혀 있고, 공의 번호는 1번부터 순서대로 부여된다.
     * 같은 무게의 공이 여러 개 있을 수 있지만 서로 다른 공으로 간주한다.
     *
     * 두 사람이 고를 수 있는 볼링공의 경우의 수를 구하라.
     * */
    public static int solution(int N, int M, int[] balls) {
        int[] weightCount = new int[M + 1];
        // 각 무게별 공의 개수 확인
        for (int ball : balls) {
            weightCount[ball] += 1;
        }

        int result = 0;
        for (int i = 1; i < weightCount.length; i++) {
            N -= weightCount[i];
            if (N <= 0) {
                break;
            }
            result += weightCount[i] * N;
        }

        return result;
    }
}
