package goorm.week1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class 최장_맨해튼_거리 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int[] arr = new int[4];
        StringTokenizer tokenizer = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < 4; i++) {
            arr[i] = Integer.parseInt(tokenizer.nextToken());
        }

        int answer = solution(arr);
        bw.write(String.valueOf(answer));
        bw.flush();
        bw.close();
    }

    private static int solution(int[] arr) {
        Arrays.sort(arr);
        return (arr[3] - arr[0]) + (arr[2] - arr[1]);
    }
}
