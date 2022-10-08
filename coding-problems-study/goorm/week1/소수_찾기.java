package goorm.week1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class 소수_찾기 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n+1];
        StringTokenizer tokenizer = new StringTokenizer(br.readLine(), " ");
        for (int i = 1; i < n+1; i++) {
            arr[i] = Integer.parseInt(tokenizer.nextToken());
        }

        int answer = solution(arr);
        bw.write(String.valueOf(answer));
        bw.flush();
        bw.close();
    }

    private static int solution(int[] arr) {
        int sum = 0;
        for (int i = 1; i <arr.length; i++) {
            if (!isPrime(i)) {
                continue;
            }
            sum += arr[i];
        }
        return sum;
    }

    private static boolean isPrime(long num) {
        if (num < 2) {
            return false;
        }
        for (int i = 2; i <= Math.floor(Math.sqrt(num)); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
