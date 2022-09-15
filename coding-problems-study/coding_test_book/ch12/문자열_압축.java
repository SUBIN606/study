package coding_test_book.ch12;

public class 문자열_압축 {

    public static void main(String[] args) {
        int result = solution("acdhdh");
        System.out.println(result);
    }

    /*
    * 문자열에서 같은 값이 연속해서 나타나는 것을 그 문자의 개수와 반복되는 값으로 표현하여 더 짧은 문자열로 줄여서 표현
    * */
    public static int solution(String s) {
        int length = s.length();
        if (length <= 1) {
            return length;
        }

        for (int i = 1; i < s.length(); i++) {
            String[] split = s.split("(?<=\\G.{" + i + "})");

            String before = split[0];
            StringBuilder stringBuilder = new StringBuilder();
            int count = 1;
            for (int j = 1; j < split.length; j++) {
                if (before.equals(split[j])) {
                    count++;
                } else {
                    if (count > 1) {
                        stringBuilder.append(count);
                    }
                    stringBuilder.append(before);
                    before = split[j];
                    count = 1;
                }
            }
            if (count > 1) {
                stringBuilder.append(count);
            }
            stringBuilder.append(before);
            length = Math.min(length, stringBuilder.length());
        }
        return length;
    }
}
