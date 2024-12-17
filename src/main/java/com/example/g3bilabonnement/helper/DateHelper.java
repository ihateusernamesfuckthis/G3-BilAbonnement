package com.example.g3bilabonnement.helper;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class DateHelper {
    public static List<LocalDate[]> getMonthDateRanges(int previousMonths, int futureMonths) {
        List<LocalDate[]> ranges = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (int i = -previousMonths; i <= futureMonths; i++) {
            YearMonth yearMonth = YearMonth.from(today).plusMonths(i);
            LocalDate startDate = yearMonth.atDay(1);
            LocalDate endDate = yearMonth.atEndOfMonth();
            ranges.add(new LocalDate[]{startDate, endDate});
        }

        return ranges;
    }
}
