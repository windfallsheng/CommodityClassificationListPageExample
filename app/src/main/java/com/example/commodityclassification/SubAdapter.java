package com.example.commodityclassification;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.GridLayoutAnimationController;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @CreateDate: 2018/1/26
 * @Author: lzsheng
 * @Description: 适配器，根据不同的数据类型，展示不同的UI效果
 * @Version:
 */
public class SubAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<CommodityClassificationEntity> mClassificationList;
    private OnRecyclerViewItemClick mOnRecyclerViewItemClick;

    public SubAdapter(Context context) {
        this.mContext = context;
    }

    private final int ITEM_VIEW_TYPE_PRIMARY = 1;

    public void setClassificationList(List<CommodityClassificationEntity> classificationList) {
        mClassificationList = classificationList;
    }

    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick) {
        mOnRecyclerViewItemClick = onRecyclerViewItemClick;
    }

    @Override
    public int getItemViewType(int position) {
        if (mClassificationList != null && mClassificationList.size() > 0) {
            CommodityClassificationEntity classificationEntity = mClassificationList.get(position);
            int type = classificationEntity.getType();
            return type;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new UnknowViewHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_recycler_primary_classification, parent, false));
//            case 1:
//                return new PrimaryViewHolder(
//                        LayoutInflater.from(mContext).inflate(R.layout.item_recycler_primary_classification, parent, false));
            case 2:
                return new BannerListHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_sub_banner_list, parent, false));
            case 3:
                return new GridNormalHolder(
                        LayoutInflater.from(mContext).inflate(R.layout.item_sub_grid_normal_temp, parent, false));
            /*case 3:
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
//            case 1: {
//                PrimaryViewHolder commodityHolder = (PrimaryViewHolder) holder;
//                String name = classificationEntity.getName();
//                commodityHolder.tvName.setText(name);
//                if (mClickedPosition == position){
//                    commodityHolder.bgItem.setBackground(mContext.getResources().getDrawable(R.drawable.shape_checked));
//                }else {
//                    commodityHolder.bgItem.setBackground(mContext.getResources().getDrawable(R.drawable.shape_normal));
//                }
//            }
//            break;
            case 2: {
                BannerListHolder bannerListHolder = (BannerListHolder) holder;
                // 设置数据
//                bannerListHolder.banner.setPages(list, new MZHolderCreator<BannerViewHolder>() {
//                    @Override
//                    public BannerViewHolder createViewHolder() {
//                        return new BannerViewHolder();
//                    }
//                });
                String[] urls = mContext.getResources().getStringArray(R.array.url);
                List list = Arrays.asList(urls);
                List<String> images = new ArrayList(list);
                //设置banner样式
//                bannerListHolder.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
                //设置图片加载器
                bannerListHolder.banner.setImageLoader(new GlideImageLoader());
                //设置图片集合
                bannerListHolder.banner.setImages(images);
                //设置banner动画效果
                bannerListHolder.banner.setBannerAnimation(Transformer.DepthPage);
                //设置标题集合（当banner样式有显示title时）
//                bannerListHolder.banner.setBannerTitles(titles);
                //设置自动轮播，默认为true
                bannerListHolder.banner.isAutoPlay(true);
                //设置轮播时间
                bannerListHolder.banner.setDelayTime(1500);
                //设置指示器位置（当banner模式中有指示器时）
                bannerListHolder.banner.setIndicatorGravity(BannerConfig.CENTER);
                //banner设置方法全部调用完毕时最后调用
                bannerListHolder.banner.start();

            }
            break;
            case 3: {
                GridNormalHolder commodityHolder = (GridNormalHolder) holder;
                commodityHolder.tvTitle.setText(classificationEntity.getName());
                List<CommodityClassificationEntity> subclassificationList = classificationEntity.getSubclassificationList();
                for (int i = 0; i < subclassificationList.size(); i++) {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.item_grid, null);
                    CommodityClassificationEntity classification = subclassificationList.get(i);
                    ImageView ivIcon = view.findViewById(R.id.imageview_icon);
                    TextView tvName = view.findViewById(R.id.textview_name);
                    ivIcon.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_launcher));
                    tvName.setText(classification.getName());
                    // 添加item
                    commodityHolder.gridLayout.addView(view);
                }

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
        return 1;
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

    class GridNormalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        GridLayout gridLayout;
        TextView tvTitle;

        GridNormalHolder(View itemView) {
            super(itemView);
            itemView.setTag(false);
            gridLayout = (GridLayout) itemView.findViewById(R.id.grid_layout);
            tvTitle = (TextView) itemView.findViewById(R.id.textview_title);
//            bgItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnRecyclerViewItemClick != null) {
                mOnRecyclerViewItemClick.onItemClick(v, getAdapterPosition());
            }
        }
    }

    class BannerListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Banner banner;
//        MZBannerView banner;

        BannerListHolder(View itemView) {
            super(itemView);
            itemView.setTag(false);
//            banner = (MZBannerView) itemView.findViewById(R.id.banner);
            banner = (Banner) itemView.findViewById(R.id.banner);
        }

        @Override
        public void onClick(View v) {
            if (mOnRecyclerViewItemClick != null) {
                mOnRecyclerViewItemClick.onItemClick(v, getAdapterPosition());
            }
        }
    }

    /*public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }*/

}
