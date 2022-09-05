package coding_test_book.ch7;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class 떡볶이_떡_만들기 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), " ");
        int n = Integer.parseInt(tokenizer.nextToken());
        int m = Integer.parseInt(tokenizer.nextToken());

        tokenizer = new StringTokenizer(reader.readLine(), " ");
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Long.parseLong(tokenizer.nextToken());
        }
        writer.write(String.valueOf(solution(arr, m)));
        writer.flush();
        writer.close();
    }

    /*
     * 떡볶이 떡의 길이는 일정하지 않다. 대신 한 봉지 안에 들어가는 떡의 총 길이는 절단기로 잘라서 맞춘다.
     * 절단기에 높이를 지정하면 줄지어진 떡을 한 번에 절단한다.
     *
     * 손님이 요청한 총 길이가 M일 때 적어도 M만큼의 떡을 얻기 위해 절단기에 설정할 수 있는 높이의 최댓값을 구하라.
     *
     * input - 떡의 개수 N, 요청한 떡의 길이 M, 떡의 개별높이 배열
     * output - 절단기에 설정할 수 있는 높이의 최댓값
     * */
    public static long solution(long[] arr, int request) {
        // 오름차순 정렬
        Arrays.sort(arr);

        // 칼날 높이의 최소값은 0, 최대값은 떡 길이의 최대로 설정한다.
        long start = 0, end = arr[arr.length - 1];

        // 반복문 버전
//        while (start <= end) {
//            long mid = (start + end) / 2;
//            long total = Arrays.stream(arr)
//                    .filter(el -> el > mid)
//                    .map(el -> el - mid)
//                    .reduce(Long::sum)
//                    .orElse(0);
//
//            if (total >= request) {
//                start = mid + 1;
//            } else {
//                end = mid - 1;
//            }
//        }

        return searchHeight(start, end, arr, request);
    }

    public static long searchHeight(long start, long end, long[] arr, long target) {
        if (start >= end) {
            return end;
        }
        long mid = (start + end) / 2;
        long total = Arrays.stream(arr)
                .filter(el -> el > mid)
                .map(el -> el - mid)
                .reduce(Long::sum)
                .orElse(0);

        if (total >= target) { // 높이를 올린다.
            return searchHeight(mid + 1, end, arr, target);
        } else {    // 높이를 내린다.
            return searchHeight(start, mid - 1, arr, target);
        }
    }
}
