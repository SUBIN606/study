package coding_test_book.ch9;

import java.util.Arrays;
import java.util.Map;
import java.util.PriorityQueue;

public class 전보 {

    public static void main(String[] args) {
        int[] solution = solution(3, new int[][]{
                {1, 2, 4},
                {1, 3, 2}
        }, 1);
        System.out.println("Arrays.toString(solution) = " + Arrays.toString(solution));
    }

    public static final int INF = (int) 1e9;
    /*
     * N개의 도시가 있다.
     * 각 도시는 보내고자 하는 메시지가 있는 경우, 다른 도시로 전보를 보내서 다른 도시로 해당 메시지를 전송할 수 있다.
     * 메시지를 보내려면 도시 간에 통로가 있어야 한다. 통로를 거쳐 메시지를 보낼 때는 일정 시간이 소요된다.
     *
     * input - 도시의 개수 N, 통로의 개수 M, 메시지를 보내고자 하는 도시 C,
     *         통로에 대한 정보 X, Y, Z (X에서 Y로 이어지는 통로이며 이 시간은 Z만큼 걸린다)
     * output - 도시 C에서 보낸 메시지를 받는 도시의 총 개수,
     *          총 걸리는 시간
     * */
    public static int[] solution(int N, int[][] paths, int C) {
        // 우선순위 큐
        PriorityQueue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>(Map.Entry.comparingByValue());
        // 최단거리 배열
        int[] distances = new int[N + 1];
        Arrays.fill(distances, INF);
        // 출발지에서 걸리는 시간은 0으로 초기화
        queue.offer(Map.entry(C, 0));
        distances[C] = 0;

        int[][] graph = new int[N+1][N+1];
        for (int i = 1; i <= N; i++) {
            for (int j = 1; j <= N; j++) {
                if (i == j) {
                    graph[i][j] = 0;
                } else {
                    graph[i][j] = INF;  // 통로가 없는 도시는 최대값으로 초기화
                }
            }
        }

        for (int[] path : paths) {
            int x = path[0], y = path[1], z = path[2];
            graph[x][y] = z;
        }

        while (!queue.isEmpty()) {
            Map.Entry<Integer, Integer> node = queue.poll();
            int now = node.getKey(), dist = node.getValue();

            // 이미 알아본 경로인지 확인
            if (distances[now] < dist) {
                continue;
            }

            for (int i = 1; i <= N; i++) {
                // 거쳐서 가는 경로 계산
                int cost = distances[now] + graph[now][i];

                // 거쳐서 가는 경로가 이미 계산된 경로보다 작다면 갱신한다.
                if (cost < distances[i]) {
                    distances[i] = cost;
                    queue.offer(Map.entry(i, cost));
                }
            }
        }

        int[] ints = Arrays.stream(distances)
                .filter(time -> time < INF && time != 0)
                .toArray();

        return new int[]{ints.length, Arrays.stream(ints).max().getAsInt()};
    }
}
