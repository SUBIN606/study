package coding_test_book.ch9;

public class 플로이드_워셜 {

    /*
    * 다익스트라 알고리즘은 한 지점에서 다른 특정 지점까지의 최단 경로를 구할 때,
    * 플로이드 워셜 알고리즘은 모든 지점에서 다른 모든 지점까지의 최단 경로를 모두 구해야 하는 경우에 사용한다.
    *
    * 플로이드 워셜 알고리즘은 단계마다 '거쳐 가는 노드'를 기준으로 알고리즘을 수행하며
    * 매번 방문하지 않은 노드 중에서 최단 거리를 갖는 노드를 찾을 필요가 없다.
    * */

    public static void floydWarshall(int N, int[][] paths) {
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
            int start = path[0], destination = path[1], cost = path[2];
            graph[start][destination] = cost;
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

    }
}
