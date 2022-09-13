package coding_test_book.ch10;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.IntStream;

public class 크루스칼_알고리즘 {

    public static void main(String[] args) {
        int v, e;
        Scanner sc = new Scanner(System.in);
        v = sc.nextInt();
        e = sc.nextInt();

        // 부모를 자기 자신으로 초기화
        int[] parent = IntStream.range(0, v + 1).toArray();
        int[][] edges = new int[e][3];
        for (int i = 0; i < e; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int cost = sc.nextInt();
            edges[i][0] = a;
            edges[i][1] = b;
            edges[i][2] = cost;
        }

        int total = kruskal(parent, edges);
        System.out.println("total = " + total);
    }

    /*
     * 신장 트리란 하나의 그래프가 있을 때 모든 노드를 포함하면서 사이클이 존재하지 않는 부분 그래프를 의미한다.
     *
     * 크루스칼 알고리즘은 가능한 한 최소한의 비용으로 신장 트리를 찾을 때 사용한다.
     * 크루스칼 알고리즘을 이용하면 가장 적은 비용으로 모든 노드를 연결할 수 있으며, 그리디 알고리즘으로 분류된다.
     *
     * 1. 간선 데이터를 비용에 따라 오름차순으로 정렬한다.
     * 2. 간선을 하나씩 확인하며 현재의 간선이 사이클을 발생시키는지 확인한다.
     *   2-1. 사이클이 발생하지 않으면 최소 신장 트리에 포함시킨다.
     *   2-2. 사이클이 발생하면 최소 신장 트리에 포함시키지 않는다.
     * 3. 모든 간선에 대하여 2번 과정을 반복한다.
     * */
    public static int kruskal(int[] parent, int[][] edges) {
        ArrayList<Edge> edgeList = new ArrayList<>();
        for (int[] edge : edges) {
            Edge e = new Edge(edge[2], edge[0], edge[1]);
            edgeList.add(e);
        }
        // 비용을 기준으로 오름차순 정렬
        Collections.sort(edgeList);

        int total = 0;
        for (Edge edge : edgeList) {
            // 사이클이 발생하지 않으면
            if (findParent(parent, edge.nodeA) != findParent(parent, edge.nodeB)) {
                unionParent(parent, edge.nodeA, edge.nodeB);
                total += edge.cost;
            }
        }
        return total;
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

        private int cost;
        private int nodeA;
        private int nodeB;

        public Edge(int cost, int nodeA, int nodeB) {
            this.cost = cost;
            this.nodeA = nodeA;
            this.nodeB = nodeB;
        }

        public int getCost() {
            return cost;
        }

        public int getNodeA() {
            return nodeA;
        }

        public int getNodeB() {
            return nodeB;
        }

        @Override
        public int compareTo(Edge other) {
            return this.cost < other.cost ? -1 : 1;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "cost=" + cost +
                    ", nodeA=" + nodeA +
                    ", nodeB=" + nodeB +
                    '}';
        }
    }
}
