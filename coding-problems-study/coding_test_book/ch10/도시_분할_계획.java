package coding_test_book.ch10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.IntStream;

public class 도시_분할_계획 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();   // 집의 개수
        int M = sc.nextInt();   // 길의 개수

        int[][] edges = new int[M][3];
        for (int i = 0; i < M; i++) {
            edges[i][0] = sc.nextInt();
            edges[i][1] = sc.nextInt();
            edges[i][2] = sc.nextInt();
        }

        int answer = solution(N, edges);
        System.out.println("answer = " + answer);
    }

    /*
     * 마을은 N개의 집과 그 집들을 연결하는 M개의 길로 이루어져 있다. 길은 어느 방향으로든지 다닐 수 있다. 그리고 길마다 유지비가 있다.
     *
     * 마을을 2개의 분리된 마을로 분할할 계획을 세우고 있다.
     * 마을을 분할할 때는 각 분리된 마을 안에 집들이 서로 연결되도록 분할해야 한다.
     * 마을에는 집이 하나 이상 있어야 하며, 마을 안에 있는 임의의 두 집 사이에 경로가 항상 존재해야 한다.
     *
     * 길의 유지비의 합을 최소로 하면서, 필요 없는 길들은 없애고자 한다.
     *
     * input - 집의 개수 N, 길의 정보가 담긴 2차원 배열 [A집, B집, 길의 유지비]
     * output - 길을 없애고 남은 유지비 합의 최솟값
     * */
    public static int solution(int N, int[][] edges) {
        ArrayList<Edge> list = new ArrayList<>();
        for (int[] edge : edges) {
            list.add(new Edge(edge[2], edge[0], edge[1]));
        }
        Collections.sort(list);

        int[] parent = IntStream.range(0, N + 1).toArray();
        int maxCost = 0;
        int total = 0;
        for (Edge edge : list) {
            if (findParent(parent, edge.nodeA) != findParent(parent, edge.nodeB)) {
                unionParent(parent, edge.nodeA, edge.nodeB);
                total += edge.cost;
                maxCost = edge.cost;
            }
        }

        System.out.println("parent = " + Arrays.toString(parent));
        // 신장트리에서 가장 비용이 큰 간선을 끊는다.
        return total - maxCost;
    }

    private static int findParent(int[] parent, int x) {
        if (parent[x] == x) {
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

    static class Edge implements Comparable<Edge> {

        int cost;
        int nodeA;
        int nodeB;

        public Edge(int cost, int nodeA, int nodeB) {
            this.cost = cost;
            this.nodeA = nodeA;
            this.nodeB = nodeB;
        }

        @Override
        public int compareTo(Edge o) {
            return this.cost < o.cost ? -1 : 1;
        }
    }


}
