package coding_test_book.ch6;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class 위에서_아래로 {

    public static void main(String[] args) {
        int[] arr = solution2(new int[] {1, 2, 3, 4});
        System.out.println(Arrays.toString(arr));
    }

    /*
     * 내림차순으로 정렬하는 프로그램을 만들어라.
     * */
    public static int[] solution1(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] > arr[i]) {
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        return arr;
    }

    public static int[] solution2(int[] arr) {
        return Arrays.stream(arr)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList())
                .stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }
}
