package com.example.commodityclassification;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * @CreateDate: 2018/1/26
 * @Author: lzsheng
 * @Description: 适配器，根据不同的数据类型，展示不同的UI效果
 * @Version:
 */
public class PrimaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<CommodityClassificationEntity> mClassificationList;
    private OnRecyclerViewItemClick mOnRecyclerViewItemClick;
    private int mClickedPosition;

    public PrimaryAdapter(Context context) {
        this.mContext = context;
    }

    private final int ITEM_VIEW_TYPE_PRIMARY = 1;

    public PrimaryAdapter(Context context, List<CommodityClassificationEntity> classificationList) {
        mContext = context;
        mClassificationList = classificationList;
    }

    public void setClassificationList(List<CommodityClassificationEntity> classificationList) {
        mClassificationList = classificationList;
    }

    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick) {
        mOnRecyclerViewItemClick = onRecyclerViewItemClick;
    }

    public void setClickedPosition(int clickedPosition) {
        mClickedPosition = clickedPosition;
    }

    @Override
    public int getItemViewType(int position) {
        if (mClassificationList != null && mClassificationList.size() > 0) {
            CommodityClassificationEntity classificationEntity = mClassificationList.get(position);
            if (classificationEntity.getcId() == 0) {
                return ITEM_VIEW_TYPE_PRIMARY;
            }
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new UnknowViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_recycler_primary_classification, parent, false));
            case 1:
                return new PrimaryViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_recycler_primary_classification, parent, false));
            /*case 2:
                return new PrimaryViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.rv_item_commodity_info, parent, false));
            case 3:
                return new CategoryNevViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.rv_item_commodity_category_navigation, parent, false));
            case 4:
                return new CategoryVpViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.rv_item_commodity_category_vp, parent, false));*/
            default:
                return new UnknowViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_recycler_primary_classification, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int itemViewType = getItemViewType(position);
        CommodityClassificationEntity classificationEntity = mClassificationList.get(position);
        switch (itemViewType) {
            case 0: {
                UnknowViewHolder unknowHolder = (UnknowViewHolder) holder;

            }
            break;
            case 1: {
                PrimaryViewHolder commodityHolder = (PrimaryViewHolder) holder;
                String name = classificationEntity.getName();
//                commodityHolder.tvName.setTextSize(17);
//                commodityHolder.tvName.setTextColor(Color.BLACK);
                commodityHolder.tvName.setText(name);
                if (mClickedPosition == position){
                    commodityHolder.bgItem.setBackground(mContext.getResources().getDrawable(R.drawable.shape_checked));
                }else {
                    commodityHolder.bgItem.setBackground(mContext.getResources().getDrawable(R.drawable.shape_normal));
                }
            }
            break;
            case 2: {
                PrimaryViewHolder commodityHolder = (PrimaryViewHolder) holder;

            }
            break;
            case 3: {
                CategoryNevViewHolder categoryNevHolder = (CategoryNevViewHolder) holder;
//                List<ColorItem> items = DemoData.loadDemoColorItems(mContext);
//                Collections.sort(items, new Comparator<ColorItem>() {
//                    @Override
//                    public int compare(ColorItem lhs, ColorItem rhs) {
//                        return lhs.name.compareTo(rhs.name);
//                    }
//                });
//
//                DemoColorPagerAdapter adapter = new DemoColorPagerAdapter();
//                adapter.addAll(items);
//                categoryNevHolder.vp.setAdapter(adapter);
//                categoryNevHolder.tabLayout.setUpWithViewPager(categoryNevHolder.vp);
            }
            break;
            default:
                UnknowViewHolder unknowHolder = (UnknowViewHolder) holder;
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mClassificationList != null && mClassificationList.size() > 0) {
            return mClassificationList.size();
        }
        return 0;
    }

    class UnknowViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        TextView tvDesc;

        UnknowViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(false);
//            ivIcon = (ImageView) itemView.findViewById(R.id.imageview_head_icon);
//            tvName = (TextView) itemView.findViewById(R.id.textview_chat_name);
//            tvDesc = (TextView) itemView.findViewById(R.id.textview_last_msg_content);
        }
    }

    class PrimaryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout bgItem;
        TextView tvName;

        PrimaryViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(false);
            bgItem = (RelativeLayout) itemView.findViewById(R.id.relativelayout_bg_item);
            tvName = (TextView) itemView.findViewById(R.id.textview_name);
            bgItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnRecyclerViewItemClick != null) {
                mOnRecyclerViewItemClick.onItemClick(v, getAdapterPosition());
            }
        }
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        TextView tvDesc;

        CategoryViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(false);
//            ivIcon = (ImageView) itemView.findViewById(R.id.imageview_head_icon);
//            tvName = (TextView) itemView.findViewById(R.id.textview_chat_name);
//            tvDesc = (TextView) itemView.findViewById(R.id.textview_last_msg_content);
        }
    }

    class CategoryNevViewHolder extends RecyclerView.ViewHolder {
        ViewPager vp;
        ImageView ivIcon;
        TextView tvName;
        TextView tvDesc;

        CategoryNevViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(true);
//            vp = (ViewPager) itemView.findViewById(R.id.view_pager);
//            tabLayout = (RecyclerTabLayout) itemView.findViewById(R.id.recycler_tab_layout);

//            ivIcon = (ImageView) itemView.findViewById(R.id.imageview_head_icon);
//            tvName = (TextView) itemView.findViewById(R.id.textview_chat_name);
//            tvDesc = (TextView) itemView.findViewById(R.id.textview_last_msg_content);
        }
    }

    class CategoryVpViewHolder extends RecyclerView.ViewHolder {
        ViewPager vp;
        TextView tvName;
        TextView tvDesc;

        CategoryVpViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(false);
//            ivIcon = (ImageView) itemView.findViewById(R.id.imageview_head_icon);
//            tvName = (TextView) itemView.findViewById(R.id.textview_chat_name);
//            tvDesc = (TextView) itemView.findViewById(R.id.textview_last_msg_content);
        }
    }

}
