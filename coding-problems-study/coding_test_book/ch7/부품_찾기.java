package coding_test_book.ch7;

import java.util.Arrays;

public class 부품_찾기 {

    public static void main(String[] args) {
        String[] results = solution(new int[]{8, 3, 7, 9, 2}, new int[]{5, 7, 9});
        for (String result : results) {
            System.out.println(result);
        }
    }

    /*
     * N개의 부품이 있다. 각 부품은 정수 형태의 고유한 번호가 있다.
     * M개 종류의 부품을 구매하고자 할 때, 부품 M개의 종류를 모두 확인해서 견적서를 작성해야 한다.
     * 이때 가게 안에 부품이 모두 있는지 확인하는 프로그램을 작성하라.
     *
     * 예)
     * 부품 배열 [8, 3, 7, 9, 2]가 있고, 손님이 요청한 부품 배열이 [5, 7, 9]일 때 각 부품이 존재하면 YES, 존재하지 않으면 NO를 출력
     * */
    public static String[] solution(int[] stock, int[] find) {
        Arrays.sort(stock);
        Arrays.sort(find);

        String[] result = new String[find.length];
        int start = 0, end = stock.length - 1;
        for (int i = 0; i < result.length; i++) {
            result[i] = hasStock(start, end, stock, find[i]) ? "YES" : "NO";
        }
        return result;
    }

    public static boolean hasStock(int start, int end, int[] stock, int target) {
        if (start > end) {
            return false;
        }
        int mid = (start + end) / 2;
        if (stock[mid] == target) {
            return true;
        }

        if (stock[mid] > target) {
            return hasStock(start, mid - 1, stock, target);
        } else {
            return hasStock(mid + 1, end, stock, target);
        }
    }
}
