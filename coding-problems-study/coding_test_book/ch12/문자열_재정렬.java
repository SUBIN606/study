package coding_test_book.ch12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class 문자열_재정렬 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String answer = solution(s);
        System.out.println(answer);
    }

    /*
     * 알파벳 대문자와 숫자로만 구성된 문자열이 주어진다.
     * 이때 모든 알파벳을 오름차순으로 정렬하여 이어서 출력한 뒤, 그 뒤에 모든 숫자를 더한 값을 이어서 출력하라.
     * */
    public static String solution(String s) {
        int sum = 0;
        ArrayList<Character> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isAlphabetic(c)) {
                list.add(c);
            } else {
                sum += Integer.parseInt(c + "");
            }
        }
        Collections.sort(list);
        StringBuilder stringBuilder = new StringBuilder();
        for (char c : list) {
            stringBuilder.append(c);
        }
        if (sum > 0) {
            stringBuilder.append(sum);
        }
        return stringBuilder.toString();
    }
}
