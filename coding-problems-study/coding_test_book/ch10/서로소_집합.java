package coding_test_book.ch10;

import java.util.Scanner;
import java.util.stream.IntStream;

public class 서로소_집합 {

    public static void main(String[] args) {
        int v, e;
        Scanner sc = new Scanner(System.in);
        v = sc.nextInt();
        e = sc.nextInt();

        // 부모를 자기 자신으로 초기화
        int[] parent = IntStream.range(0, v + 1).toArray();
        int[][] unions = new int[e][2];

        for (int i = 0; i < e; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            unions[i][0] = a;
            unions[i][1] = b;
        }

        disjointSets(parent, unions);
    }

    public static void disjointSets(int[] parent, int[][] unions) {
        for (int[] union : unions) {
            unionParent(parent, union[0], union[1]);
        }
        System.out.print("각 원소가 속한 집합: ");
        for (int i = 1; i < parent.length; i++) {
            System.out.print(findParent(parent, i) + " ");
        }

        System.out.println("");
        System.out.print("부모 테이블: ");
        for (int i = 1; i < parent.length; i++) {
            System.out.print(parent[i] + " ");
        }
    }

    private static int findParent(int[] parent, int x) {
        if (x == parent[x]) {
            return x;
        }
        // 루트 노드를 찾을 때까지 재귀적으로 호출
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
