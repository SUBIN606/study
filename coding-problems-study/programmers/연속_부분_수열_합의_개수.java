package programmers;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class 연속_부분_수열_합의_개수 {

    public static void main(String[] args) {
        int result = solution(new int[]{7, 9, 1, 1, 4});
        System.out.println("result = " + result);
    }

    public static int solution(int[] elements) {
        HashSet<Long> set = new HashSet<>();

        // 길이가 1인 연속 수열로부터의 합
        for (int element : elements) {
            set.add((long) element);
        }

        int len = 2;
        int maxLen = elements.length;
        // 길이가 2이상인 연속 수열로부터의 합
        while (len <= maxLen) {
            int sum = 0;
            for (int i = 0; i < maxLen; i++) {
                for (int j = i; j < i + len; j++) {
                    sum += elements[j % maxLen];
                }
                set.add((long) sum);
                sum = 0;
            }
            len++;
        }
        return set.size();
    }

}
