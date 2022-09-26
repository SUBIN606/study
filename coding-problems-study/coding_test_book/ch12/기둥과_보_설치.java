package coding_test_book.ch12;


import java.util.ArrayList;
import java.util.Arrays;

/** @see <a href="https://school.programmers.co.kr/learn/courses/30/lessons/60061">문제 링크</a>*/
public class 기둥과_보_설치 {

    public static void main(String[] args) {
        int n = 5;
        int[][] build_frame = {{0, 0, 0, 1}, {2, 0, 0, 1}, {4, 0, 0, 1}, {0, 1, 1, 1},
                {1, 1, 1, 1}, {2, 1, 1, 1}, {3, 1, 1, 1}, {2, 0, 0, 0}, {1, 1, 1, 0}, {2, 2, 0, 1}};
//        int[][] build_frame = {{1, 0, 0, 1}, {1, 1, 1, 1}, {2, 1, 0, 1}, {2, 2, 1, 1}, {5, 0, 0, 1}, {5, 1, 0, 1}, {4, 2, 1, 1}, {3, 2, 1, 1}};
        int[][] solution = solution(n, build_frame);
        print(solution);
    }

    /*
    * 죠르디는 기둥과 보를 이용하여 벽면 구조물을 자동으로 세우는 로봇을 개발할 계획이다.
    * 로봇의 동작을 시뮬레이션 할 수 있는 프로그램을 만들고 있다.
    *
    * 프로그램은 2차원 가상 벽면에 기둥과 보를 이용한 구조물을 설치할 수 있는데, 기둥과 보는 길이가 1인 선분으로 표현되며 다음과 같은 규칙을 갖는다.
    * - 기둥은 바닥 위에 있거나, 보의 한쪽 끝부분 위에 있거나, 또는 다른 기둥 위에 있어야 한다.
    * - 보는 한쪽 끝부분이 기둥 위에 있거나, 또는 양쪽 끝부분이 다른 보와 동시에 연결되어 있어야 한다.
    * 단, 바닥은 벽면의 맨 아래 지면을 말한다.
    *
    * 2차원 벽은 n x n 크기의 정사각 격자 형태이며, 각 격자는 1 x 1 크기이다.
    * 맨 처음 벽면은 비어있다.
    * 기둥과 보는 격자 선의 교차점에 걸치지 않고, 격자 칸의 각 변에 정확히 일치하도록 설치할 수 있다.
    *
    * 벽면의 크기 n, 기둥과 보를 설치하거나 삭제하는 작업이 순서대로 담긴 2차원 배열 build_frame이 매개변수로 주어질 때,
    * 모든 명령어를 수행한 후 구조물의 상태를 return 하도록 solution 함수를 완성하라.
    *
    * n은 5이상 100이하의 자연수다.
    * build_frame의 세로(행)의 길이는 1 이상 1000 이하이며, 가로(열)의 길이는 4이다.
    * build_frame의 원소는 [x, y, a, b]형태다.
    * x는 가로 좌표, y는 세로 좌표를 나타내며, a가 0이면 기둥을, 1이면 보를 나타낸다. b가 0이면 구조물 삭제, 1이면 구조물 설치를 나타낸다.
    * 구조물은 교차점 좌표를 기준으로 보는 오른쪽, 기둥은 위쪽 방향으로 설치 또는 삭제한다.
    *
    * 최종 구조물의 상태는 가로의 길이가 3인 2차원 배열로, 각 구조물의 좌표를 담고 있어야 한다.
    * return 하는 배열은 x 좌표 기준으로 오름차순 정렬하며, x 좌표가 같을 경우 y 좌교 피군으로 오름차순 정렬하라.
    * x, y 좌표가 모두 같을 경우 기둥이 보보다 앞에오면 된다.
    * */
    public static int[][] solution(int n, int[][] build_frame) {
        int[][] poll = new int[n + 1][n + 1];
        int[][] beam = new int[n + 1][n + 1];
        ArrayList<int[]> result = new ArrayList<>();

        // 기둥은 바닥 위에 있거나, 보의 한쪽 끝부분 위에 있거나, 또는 다른 기둥 위에 있어야 한다.
        // 보는 한쪽 끝부분이 기둥 위에 있거나, 또는 양쪽 끝부분이 다른 보와 동시에 연결되어 있어야 한다.
        for (int[] build : build_frame) {
            int x = build[0], y = Math.abs(build[1] - n), a = build[2], b = build[3];
            // 구조물 삭제
            if (b == 0) {
                if (a == 0) {   // 기둥 삭제
                    poll[y][x] = 0;
                }
                if (a == 1) {   // 보 삭제
                    beam[y][x] = 0;
                }

                boolean flag = true;
                for (int[] arr : result) {
                    int sx = arr[0], sy = Math.abs(arr[1] - n), stuff = arr[2];
                    if (stuff == 0 && !installPoll(poll, beam, sx, sy)) {
                        flag = false;
                        break;
                    }
                    if (stuff == 1 && !installBeam(poll, beam, sx, sy)) {
                        flag = false;
                        break;
                    }
                }

                if (flag) {
                    result.removeIf(arr -> Arrays.equals(arr, new int[]{x, build[1], a}));
                } else {
                    if(a == 0) {
                        poll[y][x] = 1;
                    }
                    if (a == 1) {
                        beam[y][x] = 1;
                    }
                }
            }
            // 구조물 설치
            if (b == 1) {
                if (a == 0 && installPoll(poll, beam, x, y)) {
                    poll[y][x] = 1;
                    result.add(new int[]{x, build[1], a});
                }
                if (a == 1 && installBeam(poll, beam, x, y)) {
                    beam[y][x] = 1;
                    result.add(new int[]{x, build[1], a});
                }
            }
        }

        result.sort((o1, o2) -> {
            if (o1[0] == o2[0] && o1[1] == o2[1]) {
                return Integer.compare(o1[2], o2[2]);
            }
            if (o1[0] == o2[0]) {
                return Integer.compare(o1[1], o2[1]);
            }
            return Integer.compare(o1[0], o2[0]);
        });

        return result.toArray(new int[0][]);
    }

    // 기둥은 바닥 위에 있거나, 보의 한쪽 끝부분 위에 있거나, 또는 다른 기둥 위에 있어야 한다.
    private static boolean installPoll(int[][] poll, int[][] beam, int x, int y) {
        int floor = poll.length - 1;

        // 기둥은 바닥에 설치할 수 있다.
        if (y == floor) {
            return true;
        }

        // 다른 기둥 위에 설치할 수 있다.
        if (y + 1 <= floor && poll[y + 1][x] == 1) {
            return true;
        }

        // 보의 한쪽 끝 부분에 설치할 수 있다.
        if (x - 1 >= 0 && beam[y][x - 1] == 1 || beam[y][x] == 1) {
           return true;
        }

        return false;
    }

    // 보는 한쪽 끝부분이 기둥 위에 있거나, 또는 양쪽 끝부분이 다른 보와 동시에 연결되어 있어야 한다.
    private static boolean installBeam(int[][] poll, int[][] beam, int x, int y) {
        boolean flag = false;
        int floor = beam.length - 1;
        // 한쪽 끝 부분이 기둥 위에 있으면 설치할 수 있다.
        if (y + 1 <= floor) {
            if (poll[y + 1][x] == 1) {
                System.out.println();
                flag = true;
            }
            if (x + 1 <= floor && poll[y + 1][x + 1] == 1) {
                flag = true;
            }
        }
        // 양쪽 끝부분이 다른 보와 동시에 연결되면 설치할 수 있다.
        boolean left = false, right = false;
        if (x - 1 >= 0 && beam[y][x - 1] == 1) {
            left = true;
        }
        if (x + 1 <= floor && beam[y][x + 1] == 1) {
            right = true;
        }
        if(left && right) {
            flag = true;
        }
        return flag;
    }

    private static void print(int[][] arr) {
        for (int[] s : arr) {
            System.out.println(Arrays.toString(s));
        }
        System.out.println();
    }
}
