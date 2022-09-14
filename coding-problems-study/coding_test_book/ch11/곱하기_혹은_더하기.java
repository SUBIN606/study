package coding_test_book.ch11;

import java.util.Scanner;

public class 곱하기_혹은_더하기 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int answer = solution(s);
        System.out.println("answer = " + answer);
    }

    /*
     * 각 자리가 숫자로만 이루어진 문자열 S가 주어졌을 때, 왼쪽부터 오른쪽으로 하나씩 모든 숫자를 확인하며
     * 숫자 사이에 'x' 혹은 '+' 연산자를 넣어 결과적으로 만들어질 수 있는 가장 큰 수를 구하는 프로그램을 작성하라.
     *
     * 모든 연산은 왼쪽에서부터 순서대로 이루어진다.
     * */
    public static int solution(String s) {
        int answer = Integer.parseInt(s.charAt(0) + "");
        for (int i = 1; i < s.length(); i++) {
            int now = Integer.parseInt(s.charAt(i) + "");
            // 둘 다 0 혹은 1이 아니면 무조건 곱하기 연산이 더 큰 수가 된다.
            if (answer > 1 && now > 1) {
                answer *= now;
            } else {    // 둘 중 하나라도 0 혹은 1이면 더하기 연산이 더 크다.
                answer += now;
            }
        }
        return answer;
    }
}
