package coding_test_book.ch11;

import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;

public class 무지의_먹방_라이브 {

    public static void main(String[] args) {
        int answer = solution(new int[]{3, 1, 2}, 5);
        System.out.println("answer = " + answer);
    }

    /*
     * 회전판에 먹어야 할 N 개의 음식이 있다.
     * 각 음식에는 1부터 N 까지 번호가 붙어있으며, 각 음식을 섭취하는데 일정 시간이 소요된다.
     *
     * 무지는 1번 음식부터 먹기 시작하며, 회전판은 번호가 증가하는 순서대로 음식을 무지 앞으로 가져다 놓는다.
     * 마지막 번호의 음식을 섭취한 후에는 회전판에 의해 다시 1번 음식이 무지 앞으로 온다.
     * 무지는 음식 하나를 1초 동안 섭취한 후 남은 음식은 그대로 두고, 다음 음식을 섭취한다.
     *   다음 음식이란, 아직 남은 음식 중 다음으로 섭취해야 할 가장 가까운 번호의 음식을 말한다.
     * 회전판이 다음 음식을 무지 앞으로 가져오는데 걸리는 시간은 없다고 가정한다.
     *
     * 무지가 먹방을 시작한 지 K 초 후에 네트워크 장애로 인해 방송이 잠시 중단되었다.
     * 무지는 네트워크 정상화 후 다시 방송을 이어갈 때, 몇 번 음식부터 섭취해야 하는지를 알고자 한다.
     *
     * 만약 더 섭취해야 할 음식이 없다면 -1을 반환한다.
     * */
    public static int solution(int[] food_times, long k) {
        // 음식 먹는데 적게 걸리는 순서대로 정렬
        long totalTime = 0;
        PriorityQueue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>(Map.Entry.comparingByValue());
        for (int i = 0; i < food_times.length; i++) {
            // key: 음식 번호, value: 음식 먹는데 걸리는 시간
            queue.offer(Map.entry(i + 1, food_times[i]));
            totalTime += food_times[i];
        }

        if (totalTime <= k) {
            return -1;
        }
        // 초기화
        totalTime = 0;

        long restFoodCount = queue.size();  // 남은 음식 수
        long previousTime = 0;  // 이전에 걸린 시간
        while (totalTime + ((queue.peek().getValue() - previousTime) * restFoodCount) <= k) {
            int now =  queue.poll().getValue();
            totalTime += (now - previousTime) * restFoodCount;  // (해당 음식을 먹는데 걸리는 시간 - 이전에 걸린시간) * 남은 음식 수
            restFoodCount -= 1; // 먹은 음식을 뺀다
            previousTime = now;
        }

        ArrayList<Map.Entry<Integer, Integer>> list = new ArrayList<>(queue);
        list.sort(Map.Entry.comparingByKey());

        return list.get((int) ((k - totalTime) % restFoodCount)).getKey();
    }


    // 효율성 통과 못한 풀이
//    public static int solution(int[] food_times, long k) {
//        if (Arrays.stream(food_times).sum() <= k) {
//            return -1;
//        }
//        int time = 0;
//        int foodCount = food_times.length;
//
//        int idx = (time + foodCount) % foodCount;
//        while (time < k) {
//            if (food_times[idx] > 0) {
//                food_times[idx] -= 1;
//                time += 1;
//            }
//            idx = (idx + 1 + foodCount) % foodCount;
//        }
//
//        if (food_times[idx] > 0) {
//            return idx + 1;
//        } else {
//            int nextIdx = idx + 1;
//            for (int i = 0; i < foodCount; i++) {
//                int j = (nextIdx + i + foodCount) % foodCount;
//                if (food_times[j] > 0) {
//                    return j + 1;
//                }
//            }
//        }
//
//        return -1;
//    }
}
