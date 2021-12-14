package com.example.danhbadienthoai;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoDienThoaiAdapterV2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static int SODIENTHOAI_TYPE = 1;
    public static int CHUCAI_TYPE = 2;

    private List<DataVH> mDataSet = new ArrayList<>();

    boolean isSelectMode = false;

    public SoDienThoaiAdapterV2(List<Object> objectList) {
        this.mDataSet.clear();
        for (int i = 0; i < objectList.size() - 1; i++) {
            mDataSet.add(new DataVH(objectList.get(i)));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataSet.get(position).getData() instanceof ChuCai) {
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
            ((SoDienThoaiHolder) holder).onBind(mDataSet.get(position));
        } else if (holder instanceof ChuCaiHolder) {
            ((ChuCaiHolder) holder).onBind((ChuCai) mDataSet.get(position).getData());
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
            initAction();
        }

        public void onBind(DataVH dataVH) {
            tvTen.setText(((SoDienThoai) dataVH.getData()).getTen());
            tvSDT.setText(((SoDienThoai) dataVH.getData()).getSdt());
            if(isSelectMode){
                cbSDT.setVisibility(View.VISIBLE);
            } else {
                cbSDT.setVisibility(View.GONE);
            }
            if (dataVH.isSelected()) {
                cbSDT.setChecked(true);
                layoutItemSDT.setBackgroundColor(Color.argb(25, 54, 77, 100));;
            } else {
                cbSDT.setChecked(false);
                layoutItemSDT.setBackgroundColor(Color.TRANSPARENT);
            }
        }

        private void initAction() {
            layoutItemSDT.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (isSelectMode) {
                        return false;
                    }
                    try {
                        isSelectMode = true;
                        mDataSet.get(getAdapterPosition()).setSelected(true);
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
                        if (isSelectMode) {
                            DataVH dataVH = mDataSet.get(getAdapterPosition());
                            dataVH.setSelected(!dataVH.isSelected());
                            notifyItemChanged(getAdapterPosition());
                        }
                    } catch (Exception e) {

                    }
                }
            });
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

    public static class DataVH {
        private Object data;
        private boolean isSelected;

        public DataVH(Object data) {
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
