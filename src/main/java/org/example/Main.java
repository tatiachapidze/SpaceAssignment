package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        List<Double> transfers = Arrays.asList(6057.40, 8836.62, 9764.25, 7497.91, 4357.27, 720.01, 302.5, 3955.23);
        List<String> transferDates = Arrays.asList("01-Oct-23", "15-Oct-23", "15-Apr-22", "15-Jan-22", "13-Jul-22", "13-Jul-22", "15-Mar-24", "15-Feb-23");

        TransferAnalyzer analyzer = TransferAnalyzer.fromStringDates(transfers, transferDates);

        //The first problem can be interpreted in two different ways:
        Long sixMonthAvg = analyzer.calculateSixMonthsAvg();
        Long monthlyAvg = analyzer.calculateSixMonthsMonthlyAvg();
        Map.Entry<String, Double> maxTransferEntry = analyzer.calculateMaxtMoneyTransfer();

        System.out.println("Last 6-month monthly average transfer amount is: " + monthlyAvg);
        System.out.println("Last 6-month average transfer amount is: " + sixMonthAvg);
        System.out.printf("Maximum transfer amount was %.2f in %s", maxTransferEntry.getValue(), maxTransferEntry.getKey());
    }
}

