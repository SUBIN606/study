package coding_test_book.ch10;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class 커리큘럼 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        String[] info = new String[N];
        for (int i = 0; i < N; i++) {
            String s = "";
            while (true) {
                int n = sc.nextInt();
                if (n < 0) {
                    info[i] = s.trim();
                    break;
                }
                s += n + " ";
            }
        }

        Arrays.stream(solution(N, info))
                .forEach(System.out::println);
    }

    /*
     * 1번부터 N번 까지의 번호를 가진 강의가 있다.
     * 동시에 여러 강의를 들을 수 있다.
     *
     * 듣고자 하는 N개의 강의 정보가 주어졌을 때, N개의 강의에 대하여 수강하기까지 걸리는 최소 시간을 각각 출력하라.
     * 
     * input - [강의 시간, 그 강의를 듣기 위해 먼저 들어야 하는 강의들의 번호...]
     * output - 각 강의에 대해 수강하기까지 걸리는 최소 시간을 담은 배열
     * */
    public static int[] solution(int N, String[] info) {
        // 진입차수 담을 배열
        int[] indegrees = new int[N];
        // 강의 시간 저장 배열
        int[] times = new int[N];

        HashMap<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 0; i < N; i++) {
            graph.put(i, new HashSet<>());
        }
        for (int i = 0; i < info.length; i++) {
            String[] split = info[i].split(" ");
            times[i] = Integer.parseInt(split[0]);  // 강의 시간

            for (int j = 1; j < split.length; j++) {
                indegrees[i] += 1;    // 선수강의(진입차수)
                // 선수강의에 대해 간선 연결
                // 'b 강의를 들으려면 a 강의를 먼저 들어야 한다'의 그래프는 a -> b 이므로
                graph.get(Integer.parseInt(split[j]) - 1).add(i);
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < indegrees.length; i++) {
            if (indegrees[i] == 0) {
                queue.offer(i);
            }
        }

        int[] result = Arrays.copyOf(times, N);
        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            Set<Integer> advances = graph.get(poll);
            if (!advances.isEmpty()) {
                for (int adv : advances) {
                    // 다음 강의의 시간 계산
                    int advanceClassTime = result[poll];    // 선수 강의의 시간
                    result[adv] = Math.max(result[adv], advanceClassTime + times[adv]);

                    // 현재 노드와 연결된 간선을 제거
                    indegrees[adv] -= 1;
                    if (indegrees[adv] == 0) {
                        queue.offer(adv);
                    }
                }
            }
        }

        return result;
    }
}
