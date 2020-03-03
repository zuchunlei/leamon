package joda;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.time.temporal.ChronoUnit.WEEKS;

public class TimeExample {
    public static void main(String[] args) {
        // 2019-09-02 after 100 weeks is 2021-08-02
        System.out.println(LocalDate.of(2019, 9, 2).plus(100, WEEKS).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

    }
}
