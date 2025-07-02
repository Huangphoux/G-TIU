package com.example.g_tiu.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_tiu.R;
import com.example.g_tiu.databinding.ItemTransactionsBinding;
import com.example.g_tiu.helper.AppConstants;
import com.example.g_tiu.item.Transactions;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder> {
    private final OnClickTransaction onClickTransaction;
    private List<Transactions> transactions;

    public TransactionsAdapter(List<Transactions> transactions, OnClickTransaction onClickTransaction) {
        this.transactions = transactions;
        this.onClickTransaction = onClickTransaction;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataSource(List<Transactions> transactions) {
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransactionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTransactionsBinding binding = ItemTransactionsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TransactionsViewHolder(binding, onClickTransaction);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsViewHolder holder, int position) {
        Transactions prevTransactions = null;
        if (position > 0) {
            prevTransactions = transactions.get(position - 1);
        }
        holder.bind(transactions.get(position), prevTransactions);
    }

    @Override
    public int getItemCount() {
        return transactions != null ? transactions.size() : 0;
    }

    public interface OnClickTransaction {
        void onClick(Transactions transactions);
    }

    public static class TransactionsViewHolder extends RecyclerView.ViewHolder {
        private final ItemTransactionsBinding binding;
        private final OnClickTransaction onClickTransaction;

        public TransactionsViewHolder(ItemTransactionsBinding binding, OnClickTransaction onClickTransaction) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickTransaction = onClickTransaction;
        }

        private String convertToVietnameseDate(String inputDate) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(inputDate, inputFormatter);

            DayOfWeek dayOfWeek = date.getDayOfWeek();
            String sttOfWeeks = getVietnameseDayOfWeek(dayOfWeek);

            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dateFormatted = date.format(outputFormatter);

            return sttOfWeeks + ", " + dateFormatted;
        }

        private static String getVietnameseDayOfWeek(DayOfWeek day) {
            switch (day) {
                case MONDAY:
                    return "Thứ 2";
                case TUESDAY:
                    return "Thứ 3";
                case WEDNESDAY:
                    return "Thứ 4";
                case THURSDAY:
                    return "Thứ 5";
                case FRIDAY:
                    return "Thứ 6";
                case SATURDAY:
                    return "Thứ 7";
                case SUNDAY:
                    return "Chủ nhật";
                default:
                    return "";
            }
        }

        public void bind(Transactions transactions, Transactions prevTransactions) {
            binding.tvCategoryName.setText(transactions.getCategory().getName());
            try {
                String formatted = NumberFormat.getInstance(Locale.US).format(transactions.getAmount());
                binding.tvMoney.setText(formatted);
            } catch (NumberFormatException e) {
                binding.tvMoney.setText("0");
            }

            binding.ivCategoryType.setImageResource(AppConstants.getIcons().get(transactions.getCategory().getIcon()).getResId());

            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            Date date = new Date(transactions.getCreateTime());
            binding.tvCreateTime.setText(sdf.format(date));

            binding.getRoot().setOnClickListener(v -> {
                if (onClickTransaction != null) {
                    onClickTransaction.onClick(transactions);
                }
            });
            if (prevTransactions == null) {
                binding.tvTime.setVisibility(View.VISIBLE);
                binding.tvTime.setText(convertToVietnameseDate(transactions.getDate()));
            } else {
                if (!prevTransactions.getDate().equals(transactions.getDate())) {
                    binding.tvTime.setVisibility(View.VISIBLE);
                    binding.tvTime.setText(convertToVietnameseDate(transactions.getDate()));
                } else {
                    binding.tvTime.setVisibility(View.GONE);
                }
            }
        }
    }
}

