package programmers;

import java.util.Arrays;
import java.util.HashMap;

public class 숫자의_표현 {

//    public static void main(String[] args) {
//        int answer = solution(15);
//        System.out.println(answer);
//    }

    public static int solution(int n) {
        int answer = 1;
        for (int i = 1; i <= n / 2; i++) {
            int rest = n - i;
            if (Math.abs(rest - i) == 1) {
                answer++;
                continue;
            }

            int sum = 0;
            for (int j = i + 1; j < n; j++) {
                sum += j;
                if (sum == rest) {
                    answer++;
                    break;
                }
            }
        }

        return answer;
    }


    public static void drawLine(byte[] screen, int width, int x1, int x2, int y) {
        int startOffset = x1 % 8;
        int firstByte = startOffset == 0 ? x1/8 : x1/8 + 1;
        int endOffset = x2 % 8;
        int lastByte = endOffset == 7 ? x2/8 : x2/8 - 1;

        // draw line for full bytes
        for (int i = firstByte; i <= lastByte; ++i) {
            screen[i + width/8*y] = (byte) 0xFF;
        }

        // draw start and end of line
        byte startMask = (byte) (0xFF >> startOffset);
        byte endMask = (byte) ~(0xFF >> (endOffset + 1));
        if (x1/8 == x2/8) {
            byte mask = (byte) (startMask & endMask);
            screen[x1/8 + width/8*y] |= mask;
        }
        else {
            if (startOffset != 0) {
                screen[firstByte - 1 + width/8*y] |= startMask;
            }
            if (endOffset != 7) {
                screen[lastByte + 1 + width/8*y] |= endMask;
            }
        }
    }

    //TEST----------------------------------
    public static void main(String[] args) {
        byte[] screen = new byte[8];
        int width = 32;
        printScreen(screen, width);
        drawLine(screen, width, 0, 6, 0);
        printScreen(screen, width);
    }

    private static void printScreen(byte[] screen, int width) {
        int num = 1, widthNum = width/8;
        for (byte b : screen) {
            for (int i = 7; i >= 0; --i) {
                System.out.print((b >> i) & 1);
            }
            if (num % widthNum == 0) System.out.println();
            ++num;
        }
        System.out.println();
    }
}
