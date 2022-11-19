package coding_test_book.ch5;

import java.util.LinkedList;
import java.util.Queue;

public class 미로_탈출 {

    // 상 - 우 - 하 - 좌
    static int[] dx = {-1, 0, 1, 0};
    static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) {
        int[][] map = {
                {1, 0, 1, 0, 1, 0},
                {1, 1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1}
        };
        int solution = solution(map);
        System.out.println("solution = " + solution);
    }

    /*
    * N * M 크기의 직사각형 형태의 미로가 있다.
    * 최초 시작점은 (1, 1)이고, 탈출구는 (N, M)이다.
    *
    * 괴물이 있는 부분은 0이고, 괴물이 없는 부분은 1로 표시한다.
    * 미로는 반드시 탈출할 수 있는 형태로 제시된다.
    * 탈출하기 위해 움직여야 하는 최소 칸의 개수를 구하라.(시작 칸과 마지막 칸을 모두 포함해 계산한다.)
    * */
    public static int solution(int[][] map) {
        return bfs(map);
    }

    public static boolean canMove(int[][] map, boolean[][] visited, int x, int y) {
        int allow = 0;
        for (int i = 0; i < dx.length; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (nx >= 0 && nx < map.length && ny >= 0 && ny < map[0].length) {
                if (!visited[nx][ny] && map[nx][ny] == 1) {
                    allow++;
                }
            }
        }
        return allow > 0;
    }

    public static int bfs(int[][] map) {
        int count = 1;
        int exitX = map.length - 1, exitY = map[0].length - 1;

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[map.length][map[0].length];
        queue.add(new int[] {0, 0});
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            int x = poll[0];
            int y = poll[1];

            if (map[x][y] == 1 && !visited[x][y]) {
                if (exitX == x && exitY == y) {
                    break;
                }
                if (!canMove(map, visited, x, y)) {
                    continue;
                } else {
                    count++;
                }
                visited[x][y] = true;
                for (int i = 0; i < dx.length; i++) {
                    int nx = x + dx[i];
                    int ny = y + dy[i];
                    if (nx >= 0 && nx < map.length && ny >= 0 && ny < map[0].length && map[nx][ny] == 1) {
                        queue.add(new int[]{nx, ny});
                    }
                }
            }
        }
        return count;
    }
}
