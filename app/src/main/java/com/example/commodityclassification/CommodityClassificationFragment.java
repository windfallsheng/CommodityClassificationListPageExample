package com.example.commodityclassification;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CommodityClassificationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CommodityClassificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommodityClassificationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = CommodityClassificationFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View mViewContent;
    private RecyclerView mRecyclerViewPrimary, mRecyclerViewSecondary;

    public CommodityClassificationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommodityClassificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommodityClassificationFragment newInstance(String param1, String param2) {
        CommodityClassificationFragment fragment = new CommodityClassificationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mViewContent = inflater.inflate(R.layout.fragment_commodity_classification, container, false);
        return mViewContent;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerViewPrimary = mViewContent.findViewById(R.id.recyclerview_primary);
        mRecyclerViewSecondary = mViewContent.findViewById(R.id.recyclerview_secondary);
//        LinearLayoutManager linearLayoutManagerPrimary = new CenterLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        final LinearLayoutManager linearLayoutManagerPrimary = new LinearLayoutManager(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerViewPrimary.setLayoutManager(linearLayoutManagerPrimary);
        DividerItemDecoration decoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        // 设置样式
        decoration.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.shape_divider));
        mRecyclerViewPrimary.addItemDecoration(decoration);
        mRecyclerViewSecondary.setLayoutManager(linearLayoutManager);
        final List<CommodityClassificationEntity> primaryList = parsePrimaryJson();
        final PrimaryAdapter primaryAdapter = new PrimaryAdapter(getActivity(), primaryList);
        mRecyclerViewPrimary.setAdapter(primaryAdapter);
        final SubAdapter subAdapter = new SubAdapter(getActivity());
        mRecyclerViewSecondary.setAdapter(subAdapter);

        final List<CommodityClassificationEntity> subList = parseSubJson();

        primaryAdapter.setOnRecyclerViewItemClick(new OnRecyclerViewItemClick() {
            @Override
            public void onItemClick(View childView, int position) {
                Log.d(TAG, "method:onItemClick#clickedPosition=" + position);
                CommodityClassificationEntity classificationEntity = primaryList.get(position);
                Log.d(TAG, "method:onItemClick#点击的分类名称为：" + classificationEntity.getName());
                primaryAdapter.setClickedPosition(position);
                primaryAdapter.notifyDataSetChanged();

                List<CommodityClassificationEntity> subclassificationList = classificationEntity.getSubclassificationList();
                if (subclassificationList == null) {
                    List<CommodityClassificationEntity> subList = parseSubJson();
                    classificationEntity.setSubclassificationList(subList);
                }
                List<CommodityClassificationEntity> subList1 = classificationEntity.getSubclassificationList();
                subAdapter.setClassificationList(subList1);
//                subAdapter.notifyDataSetChanged();

                int height = mRecyclerViewPrimary.getHeight();
                Log.d(TAG, "method:onItemClick#RecyclerView_height=" + height);
                Log.d(TAG, "method:onItemClick#childView_height=" + childView.getHeight());
                int firstVisibleItemPosition = linearLayoutManagerPrimary.findFirstVisibleItemPosition();
                scrollToMiddleVertical(childView, position, firstVisibleItemPosition);
            }
        });

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void scrollToMiddleVertical(View childView, int clickedPosition, int firstVisibleItemPosition) {
        Log.d(TAG, "method:onItemClick#clickedPosition=" + clickedPosition + ", firstVisibleItemPosition=" + firstVisibleItemPosition);
        int vHeight = childView.getHeight();
        Rect rect = new Rect();
        mRecyclerViewPrimary.getGlobalVisibleRect(rect);
        int reHeight = rect.bottom - rect.top - vHeight;
        Log.d(TAG, "method:onItemClick#reHeight=" + (rect.bottom - rect.top));
        int top = mRecyclerViewPrimary.getChildAt(clickedPosition - firstVisibleItemPosition).getTop();
        Log.d(TAG, "method:onItemClick#top=" + top);
        int half = reHeight / 2;
        Log.d(TAG, "method:onItemClick#smoothScrollBy_dy=" + (top - half));
        mRecyclerViewPrimary.smoothScrollBy(0, top - half);
    }

    private void scrollToMiddleHorizontal(View view, int clickedPosition, int firstVisibleItemPosition) {
        int vWidth = view.getWidth();
        Rect rect = new Rect();
        mRecyclerViewPrimary.getGlobalVisibleRect(rect);
        //除掉点击View的宽度，剩下整个屏幕的宽度
        int reWidth = rect.right - rect.left - vWidth;
        //从左边到点击的Item的位置距离
        int left = mRecyclerViewPrimary.getChildAt(clickedPosition - firstVisibleItemPosition).getLeft();
        //半个屏幕的宽度
        int half = reWidth / 2;
        //向中间移动的距离
        int moveDis = left - half;
        mRecyclerViewPrimary.smoothScrollBy(moveDis, 0);
    }

    /**
     * 解析出json文件中的所有最新数据
     *
     * @return
     */
    public List<CommodityClassificationEntity> parseSubJson() {
        List<CommodityClassificationEntity> classificationList = null;
        Gson gson = new Gson();
        try {
            String jsonStr = ResUtils.getJsonStr(getActivity(), "classification_sub_01.json");
            classificationList = gson.fromJson(jsonStr, new TypeToken<List<CommodityClassificationEntity>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "method:parseSubJson#return value:classificationList=" + classificationList);
        return classificationList;
    }

    /**
     * 解析出json文件中的所有最新数据
     *
     * @return
     */
    public List<CommodityClassificationEntity> parsePrimaryJson() {
        List<CommodityClassificationEntity> classificationList = null;
        Gson gson = new Gson();
        try {
            String jsonStr = ResUtils.getJsonStr(getActivity(), "classification_primary.json");
            classificationList = gson.fromJson(jsonStr, new TypeToken<List<CommodityClassificationEntity>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "method:parsePrimaryJson#return value:classificationList=" + classificationList);
        return classificationList;
    }
}
