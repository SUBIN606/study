package programmers;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * @see <a href="https://school.programmers.co.kr/learn/courses/30/lessons/92341">문제 링크</a>
 * */
public class 주차_요금_계산 {

    public static void main(String[] args) {
        int[] fees = {180, 5000, 10, 600};
        String[] records = {"05:34 5961 IN", "06:00 0000 IN", "06:34 0000 OUT",
                "07:59 5961 OUT", "07:59 0148 IN", "18:59 0000 IN",
                "19:09 0148 OUT", "22:59 5961 IN", "23:00 5961 OUT"};

        solution(fees, records);
    }
    /*
    * 주차장의 요금표와 차량이 들어오고(입차) 나간(출차) 기록이 주어졌을 때, 차량별로 주차 요금을 계산하려고 합니다.
    *
    * 어떤 차량이 입차된 후에 출차된 내역이 없다면, 23:59에 출차된 것으로 간주합니다.
    * 누적 주차 시간이 기본 시간이하라면, 기본 요금을 청구합니다.
    * 누적 주차 시간이 기본 시간을 초과하면, 기본 요금에 더해서, 초과한 시간에 대해서 단위 시간 마다 단위 요금을 청구합니다.
    * 초과한 시간이 단위 시간으로 나누어 떨어지지 않으면, 올림합니다.
    *
    * fees는 기본 시간, 기본 요금, 단위 시간, 단위 요금의 순서
    * records의 각 원소는 "시각 차량번호 내역" 형식의 문자열
    *
    * 주차 요금을 나타내는 정수 배열 fees, 자동차의 입/출차 내역을 나타내는 문자열 배열 records가 매개변수로 주어집니다.
    * 차량 번호가 작은 자동차부터 청구할 주차 요금을 차례대로 정수 배열에 담아서 return 하도록 solution 함수를 완성해주세요.
    * */
    public static int[] solution(int[] fees, String[] records) {
        HashMap<String, String> in = new HashMap<>();
        TreeMap<String, Integer> result = new TreeMap<>();  // 트리맵은 key 기준 오름차순 정렬한다.
        for (String str : records) {
            String[] split = str.split(" ");
            String time = split[0], carNumber = split[1], flag =  split[2];
            if ("IN".equals(flag)) {
                in.put(carNumber, time);
            }
            if ("OUT".equals(flag)) {
                int minutes = calcMinuteTotal(in.get(carNumber), time);
                in.remove(carNumber);   // 계산한 입차기록 삭제
                result.put(carNumber, result.getOrDefault(carNumber, 0) + minutes); // 주차시간 누적
            }
        }

        // 입차 후 출차기록 없는 차량 계산
        in.forEach((key, value) ->
                result.put(key, result.getOrDefault(key, 0)
                        + calcMinuteTotal(in.get(key), "23:59")));

        // 요금 계산
        // 기본시간, 기본요금, 단위시간, 단위요금
        int defaultTime = fees[0], defaultFee = fees[1], timeUnit = fees[2], feeUnit = fees[3];
        result.forEach((key, value) -> {
            if (value <= defaultTime) { // 기본시간 이하로 주차하면 기본요금만 청구
                result.put(key, defaultFee);
            } else {    // 기본시간 초과하면 요금 계산
                result.put(key, (int) (defaultFee + (Math.ceil((float) (value - defaultTime) / timeUnit)) * feeUnit));
            }
        });

        return result.values()
                .stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    // 입차시간에서부터 출차시간까지 총 몇 분 있었는지 계산
    private static int calcMinuteTotal(String in, String out) {
        return convertHHMMToMinutes(out) - convertHHMMToMinutes(in);
    }

    private static int convertHHMMToMinutes(String time) {
        String[] split = time.split(":");
        return Integer.parseInt(split[0]) * 60 + Integer.parseInt(split[1]);
    }
}
