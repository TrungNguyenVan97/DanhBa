package com.example.danhbadienthoai;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    public static final int SDT_TYPE = 1;
    public static final int HEADER_TYPE = 2;

    private List<DataSDT> mDataSet = new ArrayList();
    private List<DataSDT> listOrigin = new ArrayList();

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
            ((HeaderHolder) holder).onBind((Header) mDataSet.get(position).getData());
        }
    }

    @Override
    public int getItemCount() {
        Log.d("aaa", "getItemCount");
        return mDataSet.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<DataSDT> list = new ArrayList();
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    list.addAll(listOrigin);
                } else {
                    for (int i = 0; i < listOrigin.size(); i++) {
                        if (listOrigin.get(i).getData() instanceof SoDienThoai) {
                            SoDienThoai contact = (SoDienThoai) listOrigin.get(i).getData();
                            if (contact.getTen().toLowerCase().contains(strSearch.toLowerCase())) {
                                list.add(listOrigin.get(i));
                            }
                        }
                    }
                }
                Log.d("bbb", "" + " " + strSearch + " " + list.toString());
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                reset((List<DataSDT>) results.values);
            }
        };
    }

    public void setData(List<Object> listDB) {
        listOrigin.clear();
        for (int i = 0; i < listDB.size(); i++) {
            listOrigin.add(new DataSDT(listDB.get(i)));
        }
        reset(listOrigin);
    }

    public void reset(List<DataSDT> listDB) {
        Log.d("aaa", "reset");
        this.mDataSet.clear();
        if (listDB != null) {
            mDataSet.addAll(listDB);
        }
        notifyDataSetChanged();
    }

    public void addItem(Object data) {
        if (data instanceof SoDienThoai) {
            String header = String.valueOf(((SoDienThoai) data).getTen().charAt(0)).toUpperCase();
            int positionHeader = -1;
            for (int i = 0; i < mDataSet.size(); i++) {
                if (mDataSet.get(i).getData() instanceof Header) {
                    Header chuCai = (Header) mDataSet.get(i).getData();
                    if (chuCai.getChuCai().equals(header)) {
                        positionHeader = i;
                        break;
                    }
                }
            }

            if (positionHeader < 0) {
                // ch??a t???n t???i header
                // them header
                int positionStart = mDataSet.size();
                mDataSet.add(new DataSDT(new Header(header)));
                mDataSet.add(new DataSDT(data));
                notifyItemRangeInserted(positionStart, 2);

            } else { // ???? t???n t???i
                mDataSet.add(positionHeader + 1, new DataSDT(data));
                notifyItemInserted(positionHeader + 1);
            }
        }
    }

    public void removeItemByID(String id) {
        for (int i = 0; i < mDataSet.size(); i++) {
            Object contact = mDataSet.get(i).getData();
            if (contact instanceof SoDienThoai) {
                if (id.equals(((SoDienThoai) contact).getId().trim())) {
                    if (i == mDataSet.size() - 1) {
                        if (mDataSet.get(i - 1).getData() instanceof Header) {
                            mDataSet.remove(i);
                            mDataSet.remove(i - 1);
                            notifyItemRangeRemoved(i - 1, 2);
                            break;
                        } else {
                            mDataSet.remove(i);
                            notifyItemRemoved(i);
                            break;
                        }
                    } else {
                        if (mDataSet.get(i - 1).getData() instanceof Header && mDataSet.get(i + 1).getData() instanceof Header) {
                            mDataSet.remove(i);
                            mDataSet.remove(i - 1);
                            notifyItemRangeRemoved(i - 1, 2);
                            break;
                        } else {
                            mDataSet.remove(i);
                            notifyItemRemoved(i);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public SoDienThoai getItemAt(int position) {
        return (SoDienThoai) mDataSet.get(position).getData();
    }

    public void removeItem() {
        int n = mDataSet.size();
        for (int i = 0; i < n; i++) {
            if (mDataSet.get(i).isSelected) {
                if (i == mDataSet.size() - 1) {
                    if (mDataSet.get(i - 1).getData() instanceof Header) {
                        mDataSet.remove(i);
                        mDataSet.remove(i - 1);
                        notifyItemRangeRemoved(i - 1, 2);
                        n -= 2;
                        i = 0;
                    } else {
                        mDataSet.remove(i);
                        notifyItemRemoved(i);
                        n--;
                        i = 0;
                    }
                } else {
                    if (mDataSet.get(i - 1).getData() instanceof Header && mDataSet.get(i + 1).getData() instanceof Header) {
                        mDataSet.remove(i);
                        mDataSet.remove(i - 1);
                        notifyItemRangeRemoved(i - 1, 2);
                        n -= 2;
                        i = 0;
                    } else {
                        mDataSet.remove(i);
                        notifyItemRemoved(i);
                        n--;
                        i = 0;
                    }
                }
            }
        }
    }

    public class SoDienThoaiHolder extends RecyclerView.ViewHolder {

        private TextView tvTen;
        private TextView tvSDT;
        private TextView tvID;
        private LinearLayout layoutItemSDT;
        private CheckBox cbSDT;

        public SoDienThoaiHolder(@NonNull View itemView) {
            super(itemView);
            Log.d("aaa", "Constructor SDTHolder");
            tvTen = itemView.findViewById(R.id.tvTen);
            tvSDT = itemView.findViewById(R.id.tvSDT);
            layoutItemSDT = itemView.findViewById(R.id.itemSDT);
            cbSDT = itemView.findViewById(R.id.cbSDT);
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
                    for (int i = 0; i < mDataSet.size(); i++) {
                        if (mDataSet.get(i).isSelected) {
                            isSelectMode = true;
                            break;
                        } else {
                            isSelectMode = false;
                        }
                    }
                    try {
                        if (isSelectMode) {
                            DataSDT dataSDT = mDataSet.get(getAdapterPosition());
                            dataSDT.setSelected(!dataSDT.isSelected());
                            notifyItemChanged(getAdapterPosition());
                        } else {
                            if (callBack != null) {
                                callBack.onCLickDetails(getAdapterPosition());
                            }
                        }
                    } catch (
                            Exception e) {
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

        private void onBind(Header chuCai) {
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

    // t???o interface ????? x??? c??c s??? ki???n onclick ho???c 1 s??? s??? ki???n kh??c n???u c???n thi???t
    interface CallBack {

        void onCLickDetails(int position);

    }
}
