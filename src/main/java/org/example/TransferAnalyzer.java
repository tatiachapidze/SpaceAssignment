package org.example;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TransferAnalyzer {
    private List<Double> transfers;
    private List<LocalDate> transferDates;

    public TransferAnalyzer(List<Double> transfers, List<LocalDate> transferDates) {
        this.transfers = transfers;
        this.transferDates = transferDates;
    }

    public static TransferAnalyzer fromStringDates(List<Double> transfers, List<String> transferDates) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yy");
        List<LocalDate> localDates = transferDates.stream()
                .map(date -> LocalDate.parse(date, dtf))
                .collect(Collectors.toList());
        return new TransferAnalyzer(transfers, localDates);
    }

    //calculates transfer sum monthly for last 6 months
    private Map<YearMonth, Double> calculateLastSixMonthTransfers() {
        LocalDate currentDate = LocalDate.now();
        YearMonth monthFiveMonthsAgo = YearMonth.from(currentDate.minusMonths(5));
        Map<YearMonth, Double> monthlyTransfers = new HashMap<>();

        for (int i = 0; i < transferDates.size(); i++) {
            LocalDate transferDate = transferDates.get(i);
            YearMonth transferMonth = YearMonth.from(transferDate);
            double transferAmount = transfers.get(i);
            if (!transferMonth.isBefore(monthFiveMonthsAgo) && transferAmount >= 300) {
                monthlyTransfers.merge(transferMonth, transferAmount, Double::sum);
            }
        }

        return monthlyTransfers;
    }

    //count the average transaction amount through last 6 months
    public Long calculateSixMonthsAvg() {
        Map<YearMonth, Double> monthlyTransfers = calculateLastSixMonthTransfers();
        double sum = monthlyTransfers.values().stream().mapToDouble(Double::doubleValue).sum();
        long count = monthlyTransfers.size();
        double average = count > 0 ? sum / count : 0;
        return Math.round(average);
    }

    //count the average of total transactions per month through last 6 months
    public Long calculateSixMonthsMonthlyAvg() {
        Map<YearMonth, Double> monthlyTransfers = calculateLastSixMonthTransfers();
        double sum = monthlyTransfers.values().stream().mapToDouble(Double::doubleValue).sum();
        double average = sum / 6;
        return Math.round(average);
    }

    public Map.Entry<String, Double> calculateMaxtMoneyTransfer() {
        Map<YearMonth, Double> monthlyTransfers = new HashMap<>();

        for (int i = 0; i < transferDates.size(); i++) {
            LocalDate transferDate = transferDates.get(i);
            YearMonth transferMonth = YearMonth.from(transferDate);
            double transferAmount = transfers.get(i);
            if (transferAmount >= 300) {
                monthlyTransfers.merge(transferMonth, transferAmount, Double::sum);
            }
        }

        Map.Entry<YearMonth, Double> maxEntry = monthlyTransfers.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(new AbstractMap.SimpleEntry<>(null, 0.0));

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM-yyyy");
        String maxMonthString = maxEntry.getKey() != null ? maxEntry.getKey().format(dtf) : "N/A";

        return new AbstractMap.SimpleEntry<>(maxMonthString, maxEntry.getValue());
    }

}
