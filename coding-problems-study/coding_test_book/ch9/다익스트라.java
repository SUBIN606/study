package coding_test_book.ch9;

import java.util.Arrays;
import java.util.Map;
import java.util.PriorityQueue;

public class 다익스트라 {

    static final int INF = (int) 1e9;

    public static void main(String[] args) {
        dijkstra(6, new int[][] {
                {1,2,2},
                {1,3,5},
                {1,4,1},
                {2,3,3},
                {2,4,2},
                {3,2,3},
                {3,6,5},
                {4,3,3},
                {4,5,1},
                {5,3,1},
                {5,6,2}
        }, 1);
    }

    /*
     * 다익스트라 최단 경로 알고리즘은 그래프에서 여러 개의 노드가 있을 때,
     * 특정한 노드에서 출발하여 다른 노드로 가는 각각의 최단 경로를 구해주는 알고리즘이다.
     * (음의 간선이 없을 대 정상적으로 동작)
     *
     * 각 노드에 대한 현재까지의 최단거리 정보를 항상 1차원 리스트에 저장하며 리스트를 계속 갱신한다.
     * */
    public static void dijkstra(int N, int[][] path, int start) {
        // 최단거리 정보를 저장할 배열 선언
        int[] distances = new int[N + 1];
        // 모든 거리 최댓값으로 초기화
        Arrays.fill(distances, INF);
        // 시작 경로로 가는 비용은 0
        distances[start] = 0;

        // 최단거리가 가장 짧은 노드를 찾기 위해 우선순위 큐를 사용한다.
        PriorityQueue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>(Map.Entry.comparingByValue());
        // 출발 지점에 대한 거리는 0으로 초기화한다.
        queue.offer(Map.entry(start, 0));

        // 거리 정보 2차원 배열화
        int[][] graph = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N ; j++) {
                if (i == j) {
                    graph[i][j] = 0;
                } else {
                    graph[i][j] = INF;
                }
            }
        }
        for (int[] edge : path) {
            int s = edge[0], d = edge[1], cost = edge[2];   // 시작, 도착, 소요 시간
            graph[s][d] = cost;
        }

        while (!queue.isEmpty()) {
            Map.Entry<Integer, Integer> node = queue.poll();
            int now = node.getKey(), distance = node.getValue();

            if (distances[now] < distance) {
                continue;
            }

            for (int i = 1; i <= N; i++) {
                // 해당 노드를 거쳐 다른 노드로 가는 비용을 계산
                int cost = distances[now] + graph[now][i];
                // 더 작은 비용이 나오면(새로운 최단거리의 등장) 갱신한다.
                if (cost < distances[i]) {
                    distances[i] = cost;
                    queue.offer(Map.entry(i, cost));
                }
            }
        }
    }

}
