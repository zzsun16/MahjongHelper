package com.zzsun.mahjonghelper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import com.zzsun.mahjonghelper.PickerView.onSelectListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private final int MAX_TAI = 8;

    private final int MAX_LI = 30;

    private static final int CALCULATE_HU = 0;

    private static final int CALCULATE_NO_HU = 1;

    private PickerView mTaiPicker;

    private PickerView mLiPicker;

    private TextView mResult;

    private Button mBtnHu;

    private Button mBtnNoHu;

    private CheckBox mCbHalfLi;

    private String mStrTai = "00";

    private String mStrLi = "00";

    private Calculator mCalculator;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int tai = Integer.parseInt(mStrTai);
            int li = Integer.parseInt(mStrLi);
            double baseLi = li + (mCbHalfLi.isChecked() ? 0.5 : 0.0);
            switch (msg.what) {
                case CALCULATE_HU:
                    int di = mCalculator.calculateManHu(tai, baseLi);
                    if (di < 0) {
                        mResult.setText("" + mCalculator.calculate(true, tai, baseLi));
                    } else if (di == 0) {
                        mResult.setText("满胡");
                    } else {
                        mResult.setText("满胡，身上钱" + di + "底");
                    }
                    mResult.setBackgroundColor(getResources().getColor(R.color.Pink));
                    break;
                case CALCULATE_NO_HU:
                    mResult.setText("" + mCalculator.calculate(false, tai, baseLi));
                    mResult.setBackgroundColor(getResources().getColor(R.color.Aquamarine));
                    break;

                default:
                    break;
            }

        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTaiPicker = (PickerView) findViewById(R.id.tai_pickerview);
        mLiPicker = (PickerView) findViewById(R.id.li_pickerview);
        List<String> data = new ArrayList<String>();
        List<String> seconds = new ArrayList<String>();
        for (int i = 0; i <= MAX_TAI; i++) {
            data.add("0" + i);
        }
        for (int i = 0; i <= MAX_LI; i++) {
            seconds.add(i < 10 ? "0" + i : "" + i);
        }
        mTaiPicker.setData(data);
        mTaiPicker.setSelected(0);
        mTaiPicker.setOnSelectListener(new onSelectListener() {

            @Override
            public void onSelect(String text) {
                mStrTai = text;
            }
        });
        mLiPicker.setData(seconds);
        mLiPicker.setSelected(0);
        mLiPicker.setOnSelectListener(new onSelectListener() {

            @Override
            public void onSelect(String text) {
                mStrLi = text;
            }
        });
        mResult = (TextView) findViewById(R.id.result_textview);
        mCbHalfLi = (CheckBox) findViewById(R.id.half_li_checkbox);
        mBtnHu = (Button) findViewById(R.id.hu_button);
        mBtnHu.setOnClickListener(this);
        mBtnNoHu = (Button) findViewById(R.id.no_hu_button);
        mBtnNoHu.setOnClickListener(this);
        mCalculator = Calculator.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                break;
            case R.id.action_more:
                startActivity(new Intent(getApplicationContext(), MoreActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnHu) {
            mHandler.sendEmptyMessage(CALCULATE_HU);
        } else if (v == mBtnNoHu) {
            mHandler.sendEmptyMessage(CALCULATE_NO_HU);
        }
    }


}
