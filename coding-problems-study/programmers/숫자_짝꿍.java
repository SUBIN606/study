package programmers;

public class 숫자_짝꿍 {

    public static void main(String[] args) {
        숫자_짝꿍 숫자_짝꿍 = new 숫자_짝꿍();
        String result = 숫자_짝꿍.solution("100", "2345");
        System.out.println("result = " + result);
    }

    public String solution(String X, String Y) {
        int[] countX = new int[10];
        int[] countY = new int[10];

        for (char c: X.toCharArray()) {
            countX[Character.getNumericValue(c)]++;
        }

        for (char c: Y.toCharArray()) {
            countY[Character.getNumericValue(c)]++;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 9; i > -1 ; i--) {
            if(countX[i] == 0 || countY[i] == 0) {
                continue;
            }
            stringBuilder.append(String.valueOf(i).repeat(Math.max(0, Math.min(countX[i], countY[i]))));
        }

        String s = stringBuilder.toString();
        if(s.isBlank()) {
            return "-1";
        }
        if(s.charAt(0) == '0') {
            return "0";
        }

        return s;
    }
}
