package com.example.danhbadienthoai;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceActivity;
import android.provider.CalendarContract;
import android.util.Log;
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

    public static int SDT_TYPE = 1;
    public static int HEADER_TYPE = 2;

    List<DataSDT> mDataSet = new ArrayList<>();
    boolean selectMode = false;

    public SoDienThoaiAdapter(List<Object> listDB) {
        this.mDataSet.clear();
        for (int i = 0; i < listDB.size() - 1; i++) {
            mDataSet.add(new DataSDT(listDB.get(i)));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataSet.get(position).getData() instanceof SoDienThoai) {
            return SDT_TYPE;
        } else {
            return HEADER_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == SDT_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sodienthoai, parent, false);
            return new SoDienThoaiHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chucai, parent, false);
            return new HeaderHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SoDienThoaiHolder) {
            ((SoDienThoaiHolder) holder).cbSDT.setEnabled(false);
            ((SoDienThoaiHolder) holder).onBind(mDataSet.get(position));
        } else if (holder instanceof HeaderHolder) {
            ((HeaderHolder) holder).onBind((ChuCai) mDataSet.get(position).getData());
        }

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public class SoDienThoaiHolder extends RecyclerView.ViewHolder {

        private TextView tvTen;
        private TextView tvSDT;
        private LinearLayout layoutItemSDT;
        private CheckBox cbSDT;

        public SoDienThoaiHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvTen);
            tvSDT = itemView.findViewById(R.id.tvSDT);
            layoutItemSDT = itemView.findViewById(R.id.itemSDT);
            cbSDT = itemView.findViewById(R.id.cbSDT);
            onClick();
        }

        private void onBind(DataSDT dataSDT) {
            SoDienThoai soDienThoai = (SoDienThoai) dataSDT.getData();
            tvTen.setText(soDienThoai.getTen());
            tvSDT.setText(soDienThoai.getSdt());

            if (dataSDT.isSelected) {
                cbSDT.setChecked(true);
                layoutItemSDT.setBackgroundColor(Color.argb(25, 54, 77, 100));
            } else {
                cbSDT.setChecked(false);
                layoutItemSDT.setBackgroundColor(Color.TRANSPARENT);
            }

        }

        private void onClick() {
            layoutItemSDT.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (selectMode) {
                        return false;
                    }
                    try {
                        selectMode = true;
                        DataSDT dataSDT = mDataSet.get(getAdapterPosition());
                        dataSDT.setSelected(true);
                        notifyItemChanged(getAdapterPosition());

                    } catch (Exception e) {

                    }

                    return true;
                }
            });
            layoutItemSDT.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (selectMode) {
                            DataSDT dataSDT = mDataSet.get(getAdapterPosition());
                            dataSDT.setSelected(!dataSDT.isSelected());
                            notifyItemChanged(getAdapterPosition());
                        }
                    } catch (Exception e) {

                    }
                }
            });
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        private TextView tvChuCai;

        public HeaderHolder(@NonNull View itemView) {
            super(itemView);
            tvChuCai = (TextView) itemView.findViewById(R.id.tvChuCai);
        }

        private void onBind(ChuCai chuCai) {
            tvChuCai.setText(chuCai.getChuCai());
        }
    }

    public static class DataSDT {
        private Object data;
        boolean isSelected;

        public DataSDT(Object data) {
            this.data = data;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
