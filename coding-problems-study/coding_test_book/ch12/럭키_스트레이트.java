package coding_test_book.ch12;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class 럭키_스트레이트 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        long s = Long.parseLong(reader.readLine());
        writer.write(solution(s));
        writer.flush();
        writer.close();
    }

    /*
    * 점수 N을 자릿수를 기준으로 반으로 나누어 왼쪽 부분의 각 자릿수의 합과 오른쪽 부분의 각 자릿수의 합을
    * 더한 값이 동일한 상황에 럭키 스트레이트를 사용할 수 있다.
    *
    * 현재 점수 N이 주어졌을 때, 럭키 스트레이트를 사용할 수 있는 상태인지 아닌지를 알려주는 프로그램을 작성하시오.
    * 럭키 스트레이트를 사용할 수 있다면 "LUCKY"를, 사용할 수 없다면 "READY"라는 단어를 출력한다.
     * */
    public static String solution(long N) {
        String s = String.valueOf(N);
        return sum(s.substring(0, s.length() / 2)) == sum(s.substring(s.length() / 2)) ? "LUCKY" : "READY";
    }

    private static long sum(String s) {
        String[] split = s.split("");
        return Arrays.stream(split)
                .map(Integer::parseInt)
                .reduce(Integer::sum)
                .get();
    }
}
