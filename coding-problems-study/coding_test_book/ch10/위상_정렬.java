package coding_test_book.ch10;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class 위상_정렬 {

    public static void main(String[] args) {
        int v, e;
        Scanner sc = new Scanner(System.in);
        v = sc.nextInt();
        e = sc.nextInt();

        int[][] edges = new int[e][2];
        for (int i = 0; i < e; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            edges[i][0] = a;
            edges[i][1] = b;
        }

        int[] result = topologySort(v, edges);
        System.out.println("result = " + Arrays.toString(result));
    }

    /*
    * 위상 정렬은 순서가 정해져 있는 일련의 작업을 차례대로 수행해야 할 때 사용할 수 있는 알고리즘이다.
    * 방향 그래프의 모든 노드를 '방향성에 거스르지 않도록 순서대로 나열하는 것'이다.
    *
    * 그래프상에서 선후관계가 있다면, 위상 정렬을 수행하여 모든 선후 관계를 지키는 전체 순서를 계산할 수 있다.
    *
    * 진입차수란 특정한 노드로 '들어오는' 간선의 개수를 의미한다.
    *
    * 1. 진입차수가 0인 노드를 큐에 넣는다.
    * 2. 큐가 빌 때까지 다음의 과정을 반복한다.
    *   2-1. 큐에서 원소를 꺼내 해당 노드에서 출발하는 간선을 그래프에서 제거한다.
    *   2-2. 새롭게 진입차수가 0이 된 노드를 큐에 넣는다.
    * */

    public static int[] topologySort(int N, int[][] edges) {
        // 순서를 담을 리스트
        ArrayList<Integer> result = new ArrayList<>();
        // 진입차수를 저장할 배열
        int[] indegree = new int[N + 1];

        HashMap<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 0; i <= N; i++) {
            graph.put(i, new HashSet<>());
        }

        for (int[] edge : edges) {
            int a = edge[0], b = edge[1];
            // a -> b 가능
            Set<Integer> destinations = graph.get(a);
            destinations.add(b);

            // 진입차수 1 증가
            indegree[b] += 1;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i < indegree.length; i++) {
            // 진입차수가 0인 노드를 큐에 삽입
            if (indegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            result.add(poll);

            Set<Integer> set = graph.get(poll);
            if (!set.isEmpty()) {
                for (int destination : set) {
                    indegree[destination] -= 1;

                    if (indegree[destination] == 0) {
                        queue.offer(destination);
                    }
                }
            }
        }

        return result
                .stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }
}
