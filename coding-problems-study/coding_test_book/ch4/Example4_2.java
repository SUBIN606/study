package coding_test_book.ch4;

public class Example4_2 {

    public static void main(String[] args) {
        int solution = solution(5);
        System.out.println("solution = " + solution);
    }

    /*
    * 시각
    *
    * 정수 N이 입력되면 00시 00분 00초 부터 N시 59분 59초까지의 모든 시각 중에서
    * 3이 하나라도 포함되는 모든 경우의 수를 구하는 프로그램을 작성하라.
     */

    public static int solution(int n) {
        int hour = count3(n) * 3600;
        System.out.println("hour = " + hour);

        int min = count3(60) * (n + 1 - count3(n));
        System.out.println("min = " + min);

        int sec = count3(60) * 60 * (n + 1 - count3(n)) - min;
        System.out.println("sec = " + sec);

        return hour + min + sec;
    }

    private static int count3(int end) {
        int count = 0;
        for (int i = 0; i <= end; i++) {
            if (String.valueOf(i).contains("3")) {
                count++;
            }
        }
        return count;
    }

    private static int countHour(int end) {
        int count = 0;
        for (int i = 0; i <= end; i++) {
            if (String.valueOf(i).contains("3")) {
                count += 60 * 60;
            }
        }
        return count;
    }

    private static int countMinute(int end) {
        int count = 0;
        for (int i = 0; i < end; i++) {
            if (String.valueOf(i).contains("3")) {
                count += 60;
            }
        }
        return count;
    }

    private static int countSecond(int end) {
        int count = 0;
        for (int i = 0; i < end; i++) {
            if (String.valueOf(i).contains("3")) {
                count++;
            }
        }
        return count;
    }
}
