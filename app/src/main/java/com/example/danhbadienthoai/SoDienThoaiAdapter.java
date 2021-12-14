package com.example.danhbadienthoai;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import java.util.ArrayList;
import java.util.List;

public class SoDienThoaiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static int SODIENTHOAI_TYPE = 1;
    public static int CHUCAI_TYPE = 2;

    private List<Object> objectList;

    boolean isSelectMode = false;
    ArrayList<Object> selectItem = new ArrayList<>();

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
            // ngắt tướng tác checkbox
            ((SoDienThoaiHolder) holder).cbSDT.setEnabled(false);

            ((SoDienThoaiHolder) holder).onBind((SoDienThoai) objectList.get(position));

            ((SoDienThoaiHolder) holder).layoutItemSDT.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    isSelectMode = true;

                    if (selectItem.contains(objectList.get(holder.getAdapterPosition()))) {
                        ((SoDienThoaiHolder) holder).cbSDT.setChecked(false);
                        ((SoDienThoaiHolder) holder).layoutItemSDT.setBackgroundColor(Color.TRANSPARENT);
                        selectItem.remove(objectList.get(holder.getAdapterPosition()));
                    } else {
                        ((SoDienThoaiHolder) holder).cbSDT.setChecked(true);
                        ((SoDienThoaiHolder) holder).layoutItemSDT.setBackgroundColor(Color.argb(25, 54, 77, 100));
                        selectItem.add(objectList.get(holder.getAdapterPosition()));
                    }
                    if (selectItem.size() == 0) {
                        isSelectMode = false;
                    }
                    return true;
                }
            });

            ((SoDienThoaiHolder) holder).layoutItemSDT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isSelectMode) {
                        if (selectItem.contains(objectList.get(holder.getAdapterPosition()))) {
                            ((SoDienThoaiHolder) holder).cbSDT.setChecked(false);
                            ((SoDienThoaiHolder) holder).layoutItemSDT.setBackgroundColor(Color.TRANSPARENT);
                            selectItem.remove(objectList.get(holder.getAdapterPosition()));
                        } else {
                            ((SoDienThoaiHolder) holder).cbSDT.setChecked(true);
                            ((SoDienThoaiHolder) holder).layoutItemSDT.setBackgroundColor(Color.argb(25, 54, 77, 100));
                            selectItem.add(objectList.get(holder.getAdapterPosition()));
                        }
                        if (selectItem.size() == 0) {
                            isSelectMode = false;
                        }
                    }
                }
            });

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
        private LinearLayout layoutItemSDT;
        private CheckBox cbSDT;

        public SoDienThoaiHolder(@NonNull View itemView) {
            super(itemView);

            tvTen = (TextView) itemView.findViewById(R.id.tvTen);
            tvSDT = (TextView) itemView.findViewById(R.id.tvSDT);
            layoutItemSDT = (LinearLayout) itemView.findViewById(R.id.itemSDT);
            cbSDT = (CheckBox) itemView.findViewById(R.id.cbSDT);

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
