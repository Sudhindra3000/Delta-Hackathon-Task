package com.sudhindra.deltahackathontask.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sudhindra.deltahackathontask.databinding.HistoryItemBinding;
import com.sudhindra.deltahackathontask.models.RequestInfo;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private ArrayList<RequestInfo> infos;
    private HistoryListener listener;

    public HistoryAdapter(ArrayList<RequestInfo> infos) {
        this.infos = infos;
    }

    public void setListener(HistoryListener listener) {
        this.listener = listener;
    }

    public interface HistoryListener {
        void onItemClick(int pos);
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        private HistoryItemBinding binding;

        public HistoryViewHolder(@NonNull HistoryItemBinding historyItemBinding, HistoryListener listener) {
            super(historyItemBinding.getRoot());
            binding = historyItemBinding;

            binding.itemCard.setOnClickListener(v -> {
                if (!binding.itemCard.isChecked()) listener.onItemClick(getAdapterPosition());
                else binding.itemCard.setChecked(false);
            });
            binding.itemCard.setOnLongClickListener(v -> {
                binding.itemCard.setChecked(true);
                return true;
            });
        }

        public void setDetails(RequestInfo info) {
            binding.setInfo(info);
        }
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        HistoryItemBinding binding = HistoryItemBinding.inflate(inflater, parent, false);
        return new HistoryViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.setDetails(infos.get(position));
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }
}
