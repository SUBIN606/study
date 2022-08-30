package coding_test_book.ch4;

public class GameDev {

    public static void main(String[] args) {
        int[][] map = {
                {1, 1, 1, 1},
                {1, 0, 0, 1},
                {1, 1, 0, 1},
                {1, 1, 1, 1}
        };
        int solution = solution(1, 1, 0, map);
        System.out.println("solution = " + solution);
    }

    /*
    * 게임 개발
    *
    * 1 * 1 크기의 정사각형으로 이뤄진 N * M 크기의 직사각형 맵이 있다. 각각의 칸은 육지 또는 바다.
    * 맵의 각 칸은 (A, B)로 나타낼 수 있고, A는 북쪽으로부터 떨어진 칸의 개수, B는 서쪽으로부터 떨어진 칸의 개수이다.
    * 캐릭터는 상하좌우로 움직일 수 있고, 바다로 되어있는 공간은 갈 수 없다.
    *
    * 1. 현재 위치에서 현재 방향을 기본으로 왼쪽 방향부터 차례대로 갈 곳을 정한다.
    * 2. 캐릭터의 바로 왼쪽 방향에 아직 가보지 않은 칸이 존재한다면, 왼쪽 방향으로 회전한 다음 왼쪽으로 한 칸 전진한다.
    *   왼쪽 방향에 가보지 않은 칸이 없다면, 왼쪽 방향으로 회전만 수행하고 1단계로 돌아간다.
    * 3. 만약 네 방향 모두 이미 가본 칸이거나 바다로 되어 있는 칸인 경우에는, 바라보는 방향을 유지한 채로 한 칸 뒤로 가고 1단계로 돌아간다.
    *   이때 뒤쪽 방향이 바다인 칸이라 뒤로 갈 수 없는 경우에는 움직임을 멈춘다.
    *
    * 메뉴얼에 따라 캐릭터를 이동시킨 뒤, 캐릭터가 방문한 칸의 수를 출력하는 프로그램을 만드시오.
    *
    * input: 맵의 세로크기 n, 가로 크기 m
    *       게임 캐릭터의 현재 위치 (a, b), 바라보는 방향 d
    *       맵이 육지인지 바다인지에 대한 정보
    *
    * 방향은 0 - 북쪽, 1 - 동쪽, 2 - 남쪽, 3 - 서쪽
    * 맵은 0 - 육지, 1 - 바다
    * */
    public static int solution(int x, int y, int d, int[][] map) {
        //북동남서로 이동할 때 좌표를 변경하는 값을 정의한다.
        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};

        int n = map.length;     // row
        int m = map[0].length;  // col

        // 방문했음을 기록하는 2차원 배열
        boolean[][] visited = new boolean[n][m];
        // 최초 위치에 방문등록
        visited[x][y] = true;

        int nx = 0, ny = 0; // 다음 좌표를 저장할 변수
        int count = 1, turn_limit = 0;

        while (true) {
            if (turn_limit == 4) {  // 네 방향을 모두 돌았음.. 갈 데가 없다 -> 후진
                nx = x - dx[d];
                ny = y - dy[d];

                if (isInside(n, m, nx, ny) && isLand(map, nx, ny)) {
                    x = nx; y = ny;
                    turn_limit = 0; // 초기화
                    continue;
                } else {
                    break;
                }
            }

            // 현재 방향을 기준으로 반시계 방향으로 90도 회전한 방향
            if(d == 0) {
                d = 3;
            } else {
                d = d - 1;
            }
            // 바뀐 방향을 통해 다음 좌표를 구한다.
            nx = x + dx[d];
            ny = y + dy[d];

            // 다음 좌표에 갈 수 있는지 확인한다.
            //  가본 적이 없어야 하고, 육지여야 하고, map 이내인지 확인
            if (isInside(n, m, nx, ny) && isLand(map, nx, ny) && !isVisited(visited, nx, ny)) {
                x = nx; y = ny;
                visited[x][y] = true;   // 방문했음을 등록한다.
                count++;        // 방문이 가능하므로 방문 횟수를 증가시킨다.
                turn_limit = 0;
            } else {
                turn_limit++;   // 방문할 수 없으므로 방향을 전환한다.
            }
        }

        return count;
    }

    // 방문했던 좌표라면 true를, 방문하지 않았다면 false를 반환한다.
    private static boolean isVisited(boolean[][] visited, int x, int  y) {
        return visited[x][y];
    }

    // 좌표에 해당하는 곳이 육지라면 true를, 바다라면 false를 반환한다.
    private static boolean isLand(int[][] map, int x, int  y) {
        return map[x][y] == 0;
    }

    // 좌표가 map 구역에 해당한다면 true를, 아니라면 false를 반환한다.
    private static boolean isInside(int n, int m, int x, int y) {
        return x >= 0 && x < n && y >= 0 && y < m;
    }

}
