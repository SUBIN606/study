package coding_test_book.ch10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

public class 팀_결성 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();

        int[][] commands = new int[M][3];
        for (int i = 0; i < M; i++) {
            commands[i][0] = sc.nextInt();
            commands[i][1] = sc.nextInt();
            commands[i][2] = sc.nextInt();
        }

        String[] result = solution(N, commands);
        for (String str : result) {
            System.out.println(str);
        }
    }

    /*
     * 0번부터 N번까지의 학생들이 있다.
     * 처음에는 모든 학생이 서로 다른 팀으로 구분되어 총 N + 1개의 팀이 존재한다.
     *
     * 팀 합치기와 같은 팀 여부 확인 연산을 할 수 있다. 연산에 대한 각 결과를 반환하라.
     *
     * 팀 합치기 연산은 0 a b 형태로 주어진다.
     * 같은 팀 여부 확인 연산은 1 a b 형태로 주어진다.
     * */
    public static String[] solution(int N, int[][] commands) {
        ArrayList<String> result = new ArrayList<>();
        int[] parent = IntStream.range(0, N + 1).toArray();

        for (int[] arr : commands) {
            int command = arr[0];
            int a = arr[1];
            int b = arr[2];
            if (0 == command) {  // 팀 합치기
                unionParent(parent, a, b);
            }
            if (1 == command) {  // 같은 팀 여부 확인
                result.add(findParent(parent, a) == findParent(parent, b) ? "YES" : "NO");
            }
        }

        return result.toArray(String[]::new);
    }

    private static int findParent(int[] parent, int x) {
        if (x == parent[x]) {
            return x;
        }
        return parent[x] = findParent(parent, parent[x]);
    }

    private static void unionParent(int[] parent, int a, int b) {
        a = findParent(parent, a);
        b = findParent(parent, b);

        if (a < b) {
            parent[b] = a;
        } else {
            parent[a] = b;
        }
    }
}
