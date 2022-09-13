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

    /*
    * 서로소 집합 자료구조란 서로소 부분 집합들로 나누어진 원소들의 데이터를 처리하기 위한 자료구조라 할 수 있다.
    * 서로소 집합 자료 구조는 union과 find 이 2개의 연산으로 조작할 수 있다.
    * */
    public static void disjointSets(int[] parent, int[][] unions) {
        for (int[] union : unions) {
            unionParent(parent, union[0], union[1]);
        }
        System.out.print("각 원소가 속한 집합: ");
        for (int i = 1; i < parent.length; i++) {
            System.out.print(findParent(parent, i) + " ");
        }

        System.out.print("\n부모 테이블: ");
        for (int i = 1; i < parent.length; i++) {
            System.out.print(parent[i] + " ");
        }
    }

    /*
    * find 연산은 특정한 원소가 속한 집합이 어떤 집합인지 알려주는 연산이다.
    * */
    private static int findParent(int[] parent, int x) {
        if (x != parent[x]) {
            // 루트 노드를 찾을 때까지 재귀적으로 호출
            parent[x] = findParent(parent, parent[x]);
        }
        return parent[x];
    }

    /*
    * union 연산은 2개의 원소가 포함된 집합을 하나의 집합으로 합치는 연산이다.
    * */
    private static void unionParent(int[] parent, int a, int b) {
        a = findParent(parent, a);
        b = findParent(parent, b);

        // 일반적으로 서로소 집합을 표현할 때는 번호가 큰 노드가 번호가 작은 노드를 간선으로 가리키도록 한다.
        // 즉 번호가 작은 노드가 부모가 되고, 번호가 큰 노드가 자식이 된다.
        if (a < b) {
            parent[b] = a;
        } else {
            parent[a] = b;
        }
    }
}
