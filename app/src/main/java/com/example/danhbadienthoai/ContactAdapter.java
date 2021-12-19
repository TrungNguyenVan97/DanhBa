package com.example.danhbadienthoai;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static int SDT_TYPE = 1;
    public static int HEADER_TYPE = 2;

    public static List<DataSDT> mDataSet = new ArrayList();
    boolean isSelectMode = false;

    private CallBack callBack = null;

    public ContactAdapter() {
        Log.d("aaa", "Constructor Adapter");
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("aaa", "getItemViewType");
        if (mDataSet.get(position).getData() instanceof SoDienThoai) {
            return SDT_TYPE;
        } else {
            return HEADER_TYPE;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("aaa", "onCreateViewHolder");
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
        Log.d("aaa", "onBindViewHolder");
        if (holder instanceof SoDienThoaiHolder) {
            ((SoDienThoaiHolder) holder).onBind(mDataSet.get(position));

        } else if (holder instanceof HeaderHolder) {
            ((HeaderHolder) holder).onBind((ChuCai) mDataSet.get(position).getData());
        }
    }

    @Override
    public int getItemCount() {
        Log.d("aaa", "getItemCount");
        return mDataSet.size();
    }

    public void reset(List<Object> listDB) {
        Log.d("aaa", "reset");
        this.mDataSet.clear();
        for (int i = 0; i < listDB.size(); i++) {
            mDataSet.add(new DataSDT(listDB.get(i)));
        }

        notifyDataSetChanged();
    }

    public void addItem(Object data) {
        if (data instanceof SoDienThoai) {
            String header = String.valueOf(((SoDienThoai) data).getTen().charAt(0));
            int positionHeader = -1;
            for (int i = 0; i < mDataSet.size(); i++) {
                if (mDataSet.get(i).getData() instanceof ChuCai) {
                    ChuCai chuCai = (ChuCai) mDataSet.get(i).getData();
                    if (chuCai.getChuCai().toUpperCase().equals(header)) {
                        positionHeader = i;
                        break;
                    }
                }
            }
            if (positionHeader < 0) { // chưa tồn tại header
                // them header
                int positionStart = mDataSet.size() - 1;
                mDataSet.add(new DataSDT(new ChuCai(header)));
                mDataSet.add(new DataSDT(data));
                notifyItemRangeInserted(positionStart, 2);

            } else { // đã tồn tại
                mDataSet.add(positionHeader + 1, new DataSDT(data));
                notifyItemInserted(positionHeader + 1);
            }
        }
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public Object getItemAt(int position) {
        return mDataSet.get(position);
    }

    public void removeItem(Object item) {
        int index = mDataSet.indexOf(item);
        if (index > -1) {
            mDataSet.remove(index);
            notifyItemRemoved(index);
        }
    }

    public class SoDienThoaiHolder extends RecyclerView.ViewHolder {

        private TextView tvTen;
        private TextView tvSDT;
        private LinearLayout layoutItemSDT;
        private CheckBox cbSDT;
        private ImageView btnDelete;

        public SoDienThoaiHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("aaa", "Constructor SDTHolder");
            tvTen = itemView.findViewById(R.id.tvTen);
            tvSDT = itemView.findViewById(R.id.tvSDT);
            layoutItemSDT = itemView.findViewById(R.id.itemSDT);
            cbSDT = itemView.findViewById(R.id.cbSDT);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            onClick();
        }

        private void onBind(DataSDT dataSDT) {
            Log.d("aaa", "onBind SDTHolder");
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
            Log.d("aaa", "onClick");
            layoutItemSDT.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("aaa", "LongClick");
                    if (isSelectMode) {
                        return false;
                    }
                    try {
                        isSelectMode = true;
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
                    Log.d("aaa", "Click");

                    try {
                        if (isSelectMode) {
                            DataSDT dataSDT = mDataSet.get(getAdapterPosition());
                            dataSDT.setSelected(!dataSDT.isSelected());
                            notifyItemChanged(getAdapterPosition());
                        } else {
                            if (callBack != null) {
                                Log.d("128476149174", "onCLick 1231243" + getAdapterPosition());
                                callBack.onCLick(getAdapterPosition());
                            }
                        }
                    } catch (Exception e) {

                    }
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callBack != null) {
                        Log.d("128476149174", "onDeleteItemCLick 1231243" + getAdapterPosition());
                        callBack.onDeleteItemCLick(mDataSet.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {

        private TextView tvChuCai;

        public HeaderHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("aaa", "Constructor HeaderHolder");
            tvChuCai = (TextView) itemView.findViewById(R.id.tvChuCai);
        }

        private void onBind(ChuCai chuCai) {
            Log.d("aaa", "onBind HeaderHolder");
            tvChuCai.setText(chuCai.getChuCai());
        }
    }

    public static class DataSDT {
        private Object data;
        boolean isSelected;

        public DataSDT(Object data) {
            Log.d("aaa", "Constructor DataSDT");
            this.data = data;
        }

        public Object getData() {
            Log.d("aaa", "getData");
            return data;
        }

        public void setData(Object data) {
            Log.d("aaa", "setData");
            this.data = data;
        }

        public boolean isSelected() {
            Log.d("aaa", "isSelected");
            return isSelected;
        }

        public void setSelected(boolean selected) {
            Log.d("aaa", "setSelected");
            isSelected = selected;
        }
    }

    // tạo interface để xử các sự kiện onclick hoặc 1 số sự kiện khác nếu cần thiết
    interface CallBack {
        void onCLick(int position);

        void onDeleteItemCLick(Object item);
    }
}
