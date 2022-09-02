package coding_test_book.ch6;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class 성적이_낮은_순서로_학생_출력하기 {

    public static void main(String[] args) {
        String[] result = solution(new String[]{"홍길동", "이순신", "유관순"}, new int[]{95, 77, 99});
        System.out.println("Arrays.toString(result) = " + Arrays.toString(result));
    }

    /*
     * N명의 학생 정보가 있다. 학생 정보는 학생의 이름과 성적으로 구분된다.
     * 각 학생의 이름과 성적 정보가 주어졌을 때 성적이 낮은 순서대로 학생의 이름을 출력하는 프로그램을 작성하라.
     * */
    public static String[] solution(String[] names, int[] scores) {
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            map.put(names[i], scores[i]);
        }
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .toArray(String[]::new);
    }
}
