package coding_test_book.ch11;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class 문자열_뒤집기 {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        String s = reader.readLine();
        int answer = solution(s);
        writer.write(answer + "");
        writer.flush();
        writer.close();
    }

    /*
     * 0과 1로만 이루어진 문자열 S가 있다. 이 문자열 S에 있는 모든 숫자를 전부 같게 만들려고 한다.
     * S에서 연속된 하나 이상의 숫자를 잡고 모두 뒤집는 행동만 할 수 있다. (1을 0으로 혹은 0을 1로 바꾸는 것)
     *
     * 문자열 S가 주어졌을 때 모든 문자열을 같게 만들기 위해 해야하는 최소 행동 횟수를 구하라.
     *
     * 예) 0001100 문자열이 주어졌을 때 0000000로 한 번에 만들 수 있다. (연속된 11을 한 번 뒤집음)
     * */
    public static int solution(String s) {
        if (s.length() == 1) {
            return 0;
        }
        if (s.length() == 2) {
            return s.charAt(0) == s.charAt(1) ? 0 : 1;
        }

        char before = s.charAt(0);
        int zero = before == '0' ? 1 : 0, one = before == '1' ? 1 : 0;
        for (int i = 1; i < s.length(); i++) {
            char now = s.charAt(i);
            if (before != now) {    // 연속되지 않으면
                before = now;       // 연속되던 문자를 변경하고
                if (now == '0') {   // 개수를 샌다.
                    zero++;
                }else {
                    one++;
                }
            }
        }
        return Math.min(zero, one);
    }

}
