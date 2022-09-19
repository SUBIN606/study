import java.util.Arrays;
import java.util.Map;
import java.util.PriorityQueue;

public class Delivery {

    static final int INF = (int) 1e9;

    // 다익스트라 알고리즘 풀이
    public int solution_dijkstra(int N, int[][] road, int K) {
        PriorityQueue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>(Map.Entry.comparingByValue());
        int[] distances = new int[N + 1];
        Arrays.fill(distances, INF);

        // 시작 지점 설정
        queue.offer(Map.entry(1, 0));
        distances[1] = 0;

        // 연결된 도로
        int[][] graph = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (i == j) {
                    graph[i][j] = 0;
                } else {
                    graph[i][j] = INF;
                }
            }
        }

        for (int[] ints : road) {
            int start = ints[0], destination = ints[1], time = ints[2];
            graph[start][destination] = Math.min(graph[start][destination], time);
            graph[destination][start] = Math.min(graph[destination][start], time);
        }

        while (!queue.isEmpty()) {
            Map.Entry<Integer, Integer> node = queue.poll();
            int now = node.getKey(), dist = node.getValue();

            if (distances[now] < dist) {
                continue;
            }

            for (int i = 1; i <= N; i++) {
                // 거쳐가는 비용 계산
                int cost = distances[now] + graph[now][i];
                if (cost < distances[i]) {
                    distances[i] = cost;
                    queue.offer(Map.entry(i, cost));    // 갱신
                }
            }
        }

        return Arrays.stream(distances)
                .skip(1)
                .filter(time -> time <= K)
                .toArray()
                .length;
    }

    // 플로이드 워셜 알고리즘 풀이
    public int solution_floydWarshall(int N, int[][] road, int K) {
        int[][] graph = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (i == j) {
                    graph[i][j] = 0;
                } else {
                    graph[i][j] = INF;
                }
            }
        }

        for (int[] ints : road) {
            int start = ints[0], destination = ints[1], time = ints[2];
            graph[start][destination] = Math.min(graph[start][destination], time);
            graph[destination][start] = Math.min(graph[destination][start], time);
        }

        for (int k = 1; k <= N; k++) {
            for (int a = 1; a <= N; a++) {
                for (int b = 1; b <= N; b++) {
                    if (a != b && b != k) {
                        graph[a][b] = Math.min(graph[a][b], graph[a][k] + graph[k][b]);
                    }
                }
            }
        }

        return Arrays.stream(graph[1])
                .skip(1)
                .filter(time -> time <= K)
                .toArray()
                .length;
    }

}


