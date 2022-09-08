package coding_test_book.ch9;

import java.util.Arrays;

public class 미래_도시 {

    public static void main(String[] args) {
        int[][] paths = {
                {1, 2},
                {1, 3},
                {1, 4},
                {2, 4},
                {3, 4},
                {3, 5},
                {4, 5},
        };
        int answer = solution(5, paths, 5, 4);
        System.out.println("answer = " + answer);
    }

    /*
     * 미래 도시에는 1번부터 N번 까지의 회사가 있는데, 특정 회사끼리는 서로 도로를 통해 연결되어 있다.
     * 방문 판매원 A는 현재 1번 회사에 위치해 있으며, X번 회사에 방문해 물건을 판매하고자 한다.
     * 그 전에 K번 회사에 방문해서 커피챗도 해야 한다.
     * (1 -> K -> X)
     *
     * input - 회사로 가는 경로 2차원 배열
     *         최종 목적지 X와 경유지 K
     * output - 최소 이동 시간 (만약 X번 회사에 도달할 수 없다면 -1을 반환)
     * */
    public static int solution(int N, int[][] paths, int K, int X) {
        int[][] graph = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (i == j) {
                    graph[i][j] = 0;    // 자기자신으로 가는 시간은 0이므로 0으로 초기화
                } else {
                    graph[i][j] = 10000;    // 다른 경로는 무한(최대값)으로 초기화
                }
            }
        }

        for (int[] path : paths) {
            int start = path[0], destination = path[1];
            // 도로는 양방향으로 이동할 수 있으므로 반대로도 1을 초기화해준다.
            graph[start][destination] = 1;
            graph[destination][start] = 1;
        }

        for (int k = 1; k <= N; k++) {
            // a -> b로 갈 때
            for (int a = 1; a <= N; a++) {
                for (int b = 1; b <= N; b++) {
                    if (a != b && b != k) {
                        // a -> b로 바로가는 것 보다 a -> k, k -> b의 경로(k를 거쳐서 가는)가 더 짧은지 확인한다.
                        graph[a][b] = Math.min(graph[a][b], graph[a][k] + graph[k][b]);
                    }
                }
            }
        }
        for (int[] ints : graph) {
            System.out.println(Arrays.toString(ints));
        }

        int answer = graph[1][K] + graph[K][X];
        System.out.println("1 -> K = " + graph[1][K]);
        System.out.println("K -> K = " + graph[K][X]);
        if (answer >= Integer.MAX_VALUE) {
            return -1;
        }
        return answer;
    }

}
