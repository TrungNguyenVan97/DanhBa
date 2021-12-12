package com.example.danhbadienthoai;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.List;

public class SoDienThoaiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static int SODIENTHOAI_TYPE;
    public static int CHUCAI_TYPE;


    private List<Object> objectList;

    public SoDienThoaiAdapter(List<Object> objectList) {
        this.objectList = objectList;
    }

    @Override
    public int getItemViewType(int position) {
        if (objectList.get(position) instanceof ChuCai) {
            return CHUCAI_TYPE;
        } else {
            return SODIENTHOAI_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == SODIENTHOAI_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sodienthoai, parent, false);
            return new SoDienThoaiHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chucai, parent, false);
            return new ChuCaiHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof SoDienThoaiHolder) {
            ((SoDienThoaiHolder) holder).onBind((SoDienThoai) objectList.get(position));

        } else if (holder instanceof ChuCaiHolder) {
            ((ChuCaiHolder) holder).onBind((ChuCai) objectList.get(position));
        }
    }


    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public static class SoDienThoaiHolder extends RecyclerView.ViewHolder {

        private TextView tvTen;
        private TextView tvSDT;

        public SoDienThoaiHolder(@NonNull View itemView) {
            super(itemView);

            tvTen = (TextView) itemView.findViewById(R.id.tvTen);
            tvSDT = (TextView) itemView.findViewById(R.id.tvSDT);

        }

        public void onBind(SoDienThoai soDienThoai) {
            tvTen.setText(soDienThoai.getTen());
            tvSDT.setText(soDienThoai.getSdt());
        }
    }

    public static class ChuCaiHolder extends RecyclerView.ViewHolder {

        private TextView tvChuCai;

        public ChuCaiHolder(@NonNull View itemView) {
            super(itemView);

            tvChuCai = (TextView) itemView.findViewById(R.id.tvChuCai);

        }

        public void onBind(ChuCai chuCai) {
            tvChuCai.setText(chuCai.getChuCai());
        }
    }

}
