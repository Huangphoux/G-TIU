package com.example.g_tiu.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.g_tiu.databinding.ItemTransactionsBinding;
import com.example.g_tiu.item.Transactions;

import java.util.List;

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
        holder.bind(transactions.get(position));
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

        public void bind(Transactions transactions) {
            binding.tvCategoryName.setText(transactions.getCategory().getName());
            // binding.tvTime.setText(transactions.getTime());
            binding.tvMoney.setText(String.valueOf(transactions.getAmount()));

            binding.getRoot().setOnClickListener(v -> {
                if (onClickTransaction != null) {
                    onClickTransaction.onClick(transactions);
                }
            });
        }
    }
}
