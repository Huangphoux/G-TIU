package com.example.g_tiu.helper;

import com.example.g_tiu.item.Transactions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionSorter {
    public static void sortTransactions(List<Transactions> list) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        list.sort((t1, t2) -> {
            LocalDate d1 = LocalDate.parse(t1.getDate(), formatter);
            LocalDate d2 = LocalDate.parse(t2.getDate(), formatter);

            int dateCompare = d2.compareTo(d1);

            if (dateCompare != 0) {
                return dateCompare;
            }

            return Long.compare(t2.getCreateTime(), t1.getCreateTime());
        });
    }
}

