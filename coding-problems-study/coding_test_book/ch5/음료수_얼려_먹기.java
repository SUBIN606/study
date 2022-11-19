package coding_test_book.ch5;

public class 음료수_얼려_먹기 {

    public static void main(String[] args) {
        int[][] tray = {
                {0, 0, 1, 1, 0},
                {0, 0, 0, 1, 1},
                {1, 1, 1, 1, 1},
                {0, 0, 0, 0, 0}
        };
        solution(tray);
    }

    /*
     * N * M 크기의 얼음틀이 있다.
     * 구멍이 뚫린 부분은 0, 칸막이가 존재하는 부분은 1로 표시된다.
     * 구멍끼리 상, 하, 좌, 우로 붙어이쓴 경우 서로 연결되어 있는 것으로 간주한다.
     *
     * 얼음 틀의 모양이 주어졌을 때 생성되는 총 아이스크림의 개수를 구하는 프로그램을 작성하시오.
     *
     * input - 얼음틀
     * output - 한 번에 만들 수 있는 아이스크림의 개수
     * */
    public static int solution(int[][] tray) {
        int answer = 0;

        int cols = tray[0].length, rows = tray.length;
        boolean[][] visited = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (dfs(tray, cols, rows, i, j, visited)) {
                    answer++;
                }
            }
        }

        return answer;
    }

    public static boolean dfs(int[][] tray, int cols, int rows, int x, int y, boolean[][] visited) {
        if (x <= -1 || x >= rows || y <= -1 || y >= cols) {
            return false;
        }
        if (!visited[x][y] && tray[x][y] == 0) {
            visited[x][y] = true;
            dfs(tray, cols, rows, x - 1, y, visited);
            dfs(tray, cols, rows, x, y - 1, visited);
            dfs(tray, cols, rows, x + 1, y, visited);
            dfs(tray, cols, rows, x, y + 1, visited);
            return true;
        }
        return false;
    }

}
