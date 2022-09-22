package coding_test_book.ch12;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class 뱀 {

    static int[][] dxy = {
            {0, 1},     // 우
            {1, 0},     // 하
            {0, -1},    // 좌
            {-1, 0},    // 상
    };

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(reader.readLine());    // 보드의 크기
        int K = Integer.parseInt(reader.readLine());

        // 사과의 위치
        int[][] apple = new int[K > 0 ? K : 1][2];
        for (int i = 0; i < K; i++) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), " ");
            for (int j = 0; j < 2; j++) {
                apple[i][j] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        int L = Integer.parseInt(reader.readLine());
        // 뱀의 방향 변환 정보 [X, C] X = 게임 시작 시간으로부터 X초가 흘렀다. C = 왼쪽 ('L') 혹은 오른쪽 ('D')으로 90도 방향을 회전시킨다는 뜻
        String[][] path = new String[L][2];
        for (int i = 0; i < L; i++) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), " ");
            for (int j = 0; j < 2; j++) {
                path[i][j] = tokenizer.nextToken();
            }
        }

        writer.write(solution(N, apple, path) + "");
        writer.flush();
        writer.close();
    }

    /*
     * 'Dummy'라는 도스 게임이 있다. 이 게임의 뱀은 사과를 먹으면 뱀 길이가 늘어난다.
     * 뱀이 이리저리 기어다니다가 벽 또는 자기 자신의 몸과 부딪히면 게임이 끝난다.
     *
     * 게임은 N x N 정사각 보드 위에서 진행되고, 몇몇 칸에는 사과가 놓여 있다. 보드의 상하좌우 끝에는 벽이 있다.
     * 게임을 시작할 때 뱀은 맨 위 맨 좌측에 위치하고, 뱀의 길이는 1이다.
     * 뱀은 처음에 오른쪽을 향한다.
     *
     * 뱀은 매 초마다 이동을 한다.
     * 1. 먼저 뱀은 몸 길이를 늘려 머리를 다음 칸에 위치시킨다.
     * 2. 만약 이동한 칸에 사과가 있다면, 그 칸에 있던 사과가 없어지고 꼬리는 움직이지 않는다.
     * 3. 만약 이동한 칸에 사과가 없다면, 몸 길이를 줄여서 꼬리가 위치한 칸을 지워준다. 즉, 몸길이는 변하지 않는다.
     *
     * 사과의 위치와 뱀의 이동경로가 주어질 때 이 게임이 몇 초에 끝나는지 계산하라.
     * */
    public static int solution(int N, int[][] apple, String[][] path) {

        int[][] board = new int[N + 1][N + 1];

        // 사과 위치 마킹
        for (int[] location : apple) {
            board[location[0]][location[1]] = 1;
        }

        HashMap<Integer, String> map = new HashMap<>();
        // 방향 기록
        for (String[] d : path) {
            int time = Integer.parseInt(d[0]);
            map.put(time, d[1]);
        }

        int direction = 0;  // 기본적으로 계속 오른쪽으로 이동하기 때문에 기본 방향은 0으로 지정한다.
        int x = 1, y = 1;   // 시작 지점
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y});

        int count = 0;
        while (true) {
            int nx = x + dxy[direction][0];
            int ny = y + dxy[direction][1];

            // 벽을 만났거나 몸통을 만나면 게임이 종료된다.
            if (isWall(N, nx, ny) || board[nx][ny] == 2) {
                count++;
                break;
            }

            // 사과를 먹는 경우
            if (board[nx][ny] == 1) {
                board[nx][ny] = 2;
                queue.offer(new int[]{nx, ny});
            }
            // 사과를 먹지 않는 경우
            if (board[nx][ny] == 0) {
                board[nx][ny] = 2;
                queue.offer(new int[]{nx, ny});
                int[] poll = queue.poll();
                board[poll[0]][poll[1]] = 0;    // 꼬리 이동시키기
            }

            // 이동
            x = nx;
            y = ny;
            count++;

            if (map.get(count) != null) {
                direction = turn(map.get(count), direction);
            }
        }
        return count;
    }

    private static boolean isWall(int N, int x, int y) {
        return x <= 0 || x > N || y <= 0 || y > N;
    }

    private static int turn(String str, int direction) {
        if ("D".equals(str)) {    // 오른쪽으로 90도 회전
            return (direction + 1 + 4) % 4;
        } else {    // 왼쪽으로 90도 회전
            return  (direction - 1 + 4) % 4;
        }
    }
}
