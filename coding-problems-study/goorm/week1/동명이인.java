package goorm.week1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class 동명이인 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(br.readLine(), " ");
        int n = Integer.parseInt(tokenizer.nextToken());
        String name = tokenizer.nextToken();
        String[] names = new String[n];
        for (int i = 0; i < n; i++) {
            names[i] = br.readLine();
        }

        int answer = solution(names, name);
        bw.write(String.valueOf(answer));
        bw.flush();
        bw.close();
    }

    public static int solution(String[] arr, String name) {
        int count = 0;
        for (String str : arr) {
            if (str.contains(name)) {
                count++;
            }
        }
        return count;
    }
}
