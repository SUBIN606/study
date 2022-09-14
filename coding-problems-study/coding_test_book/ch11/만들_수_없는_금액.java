package coding_test_book.ch11;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class 만들_수_없는_금액 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        int N = Integer.parseInt(reader.readLine());
        int[] arr = new int[N];
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine(), " ");
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(tokenizer.nextToken());
        }
        writer.write(solution(arr) + "");
        writer.flush();
        writer.close();
    }

    /*
     * N개의 동전을 가지고 있을 때, N개의 동전을 이용해 만들 수 없는 양의 정수 금액 중 최솟값을 구하라.
     *
     * input - N개의 동전 단위가 담긴 배열
     * output - 동전들로 만들 수 없는 양의 정수 금액 중 최솟값
     * */
    public static int solution(int[] arr) {
        Arrays.sort(arr);
        int target = 1;
        for (int i : arr) {
            if (target < i) {
                break;
            }
            target += i;
        }
        return target;
    }
}
