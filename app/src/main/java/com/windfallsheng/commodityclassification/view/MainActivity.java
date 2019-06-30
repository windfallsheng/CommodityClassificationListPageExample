package com.windfallsheng.commodityclassification.view;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.windfallsheng.commodityclassification.R;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置窗体为没有标题的模式
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CommodityClassificationFragment fragment = CommodityClassificationFragment.newInstance("param1", "param2");
        fragmentTransaction.add(R.id.framelayout_fragment_container, fragment);
        fragmentTransaction.commit();
    }

}
