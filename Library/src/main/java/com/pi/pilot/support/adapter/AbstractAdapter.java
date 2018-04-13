package com.pi.pilot.support.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractAdapter<T,VM extends ItemViewModel> extends RecyclerView.Adapter<AbstractAdapter.ItemViewHolder> {
    private static final String TAG      = "AbstractAdapter";

    private List<T> mItems;
//    private int mLayoutId;
    private OnItemClickListener<T>     mOnItemClickListener;
    private OnItemLongClickListener<T> mOnItemLongClickListener;


    public AbstractAdapter(List<T> datas,
                           OnItemClickListener clickListener,
                           OnItemLongClickListener longClickListener) {
        if(null == datas){
            this.mItems = new ArrayList<>();
        }
        this.mItems     = datas;
        this.mOnItemClickListener     = clickListener;
        this.mOnItemLongClickListener = longClickListener;
    }

    /**
     * 获取绑定变量名
     * @return
     */
    public abstract int getVariableId();

    /**
     * 获取item layout布局
     * @return
     */
    public abstract int getItemLayoutId();
    /**
     * 获取绑定VM绑定id
     * @return
     */
    public abstract int getVariableModelId();

    public VM createItemViewModel(){
        return null;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemViewHolder.create(parent,this.getItemLayoutId());
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        final VM  model = createItemViewModel();
        final T   item  = this.mItems.get(position);
        model.setItem(item);
        holder.bind(getVariableId(),getVariableModelId(),item,model);
        registerOnItemClickListener(holder.binding.getRoot(),position,item);
    }

    @Override
    public int getItemCount() {
        if(null != mItems){
            return this.mItems.size();
        }
        return 0;
    }

    /**
     * 获取所有数据
     * @return
     */
    public final List<T> getItems(){
        return this.mItems;
    }

    /**
     * 带有增加Item
     * @param item
     * @param position
     */
    public final void add(T item, int position) {
        this.mItems.add(position, item);
        notifyItemInserted(position);
    }

    private final  void registerOnItemClickListener(final View rootView,final int position,final T item){
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mOnItemClickListener) mOnItemClickListener.onItemClick(v,position,item);
            }
        });
    }
    /**
     * 设置条目点击监听
     *
     * @param itemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener<T> itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

    /**
     * 设置条目长按监听
     *
     * @param itemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener<T> itemLongClickListener) {
        this.mOnItemLongClickListener = itemLongClickListener;
    }

    //---------------------------------------------------------------------------------------自定义ItemViewHolder-------------------------------------------------------------------------
    static class ItemViewHolder<T,VM> extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        /**
         * 创建holder
         * @param parent
         * @param viewType
         * @return
         */
        static final ItemViewHolder create(ViewGroup parent, int viewType) {
            ViewDataBinding binding =
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.getContext()),
                            viewType, parent, false);
            return new ItemViewHolder(binding);
        }

        ItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        /**
         * 绑定数据
         * @param variableId
         * @param item
         */
        final void bind(int variableId,int variableModelId,T item,VM model) {
            binding.setVariable(variableId,item);
            binding.setVariable(variableModelId,model);
            binding.executePendingBindings();
        }
    }



    /**
     * 条目点击事件监听
     *
     * @param <T>
     */
    public interface OnItemClickListener<T> {
        /**
         * 某个条目被点击时调用
         *
         * @param v         被点击的条目
         * @param position 被点击条目对应的数据
         */
        void onItemClick(View v, int position, T item);
    }

    /**
     * 条目长按事件监听
     *
     * @param <T>
     */
    public interface OnItemLongClickListener<T> {
        /**
         * 某个条目被长按时调用
         *
         * @param v         被点击的条目
         * @param position 被点击条目对应的数据
         */
        void onItemLongClick(View v, int position, T item);
    }

}