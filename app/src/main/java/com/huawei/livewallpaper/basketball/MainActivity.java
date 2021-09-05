package com.huawei.livewallpaper.basketball;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobike.library.MobikeView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MobikeView mobikeView1;
    private MobikeView mobikeView2;
    private SensorManager sensorManager;
    private Sensor defaultSensor;

    private int[] imgs = {
            R.drawable.ball,
            R.drawable.ball,
            R.drawable.ball,
            R.drawable.ball,
            R.drawable.ball,
            R.drawable.ball,
            R.drawable.ball,
            R.drawable.ball,
            R.drawable.ball,
            R.drawable.ball,
            R.drawable.ball,
            R.drawable.ball,
            R.drawable.ball,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mobikeView1 = (MobikeView) findViewById(R.id.mobike_view1);
        mobikeView2 = (MobikeView) findViewById(R.id.mobike_view2);

        // 隐藏状态栏全屏显示
        setTranslucentStatus(this);

        // 设置参数
        //
        // 密度
        // mobikeView.getmMobike().setDensity(0.8f);
        //
        // 摩擦力
        mobikeView1.getmMobike().setFriction(0.2f);
        mobikeView2.getmMobike().setFriction(0.3f);
        //
        // 恢复系数
        mobikeView1.getmMobike().setRestitution(0.6f);
        mobikeView2.getmMobike().setRestitution(0.5f);
        //
        // ratio
        mobikeView1.getmMobike().setRatio(90);
        mobikeView2.getmMobike().setRatio(100);

        initViews();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        defaultSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //获取当前设备支持的传感器列表
//        List<Sensor> allSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
//        StringBuilder sb = new StringBuilder();
//        sb.append("当前设备支持传感器数：" + allSensors.size() + "   分别是：\n");
//        for(Sensor s:allSensors){
//            switch (s.getType()){
//                case Sensor.TYPE_ACCELEROMETER:
//                    sb.append("加速度传感器(Accelerometer sensor)" + "\n");
//                    break;
//                case Sensor.TYPE_GYROSCOPE:
//                    sb.append("陀螺仪传感器(Gyroscope sensor)" + "\n");
//                    break;
//                case Sensor.TYPE_LIGHT:
//                    sb.append("光线传感器(Light sensor)" + "\n");
//                    break;
//                case Sensor.TYPE_MAGNETIC_FIELD:
//                    sb.append("磁场传感器(Magnetic field sensor)" + "\n");
//                    break;
//                case Sensor.TYPE_PRESSURE:
//                    sb.append("气压传感器(Pressure sensor)" + "\n");
//                    break;
//                case Sensor.TYPE_PROXIMITY:
//                    sb.append("距离传感器(Proximity sensor)" + "\n");
//                    break;
//                default:
//                    break;
//            }
//        }
//        String str = sb.toString();
//
//        TextView t2 = (TextView) findViewById(R.id.text_support);
//        t2.setText(str);

    }

    /**
     * 设置状态栏透明
     *
     * @param act
     */
    public void setTranslucentStatus(Activity act) {
        if (null == act) {
            return;
        }
        Window window = act.getWindow();
        if (null == window) {
            return;
        }
        // 5.0以上系统状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private void initViews() {
        FrameLayout.LayoutParams layoutParams1 = new FrameLayout.LayoutParams(180, 180);
        layoutParams1.gravity = Gravity.CENTER;
        for(int i = 0; i < imgs.length  ; i ++){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imgs[i]);
            imageView.setTag(R.id.mobike_view_circle_tag,true);
            mobikeView1.addView(imageView,layoutParams1);
        }
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(200, 200);
        layoutParams2.gravity = Gravity.CENTER;
        for(int i = 0; i < imgs.length  ; i ++){
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imgs[i]);
            imageView.setTag(R.id.mobike_view_circle_tag,true);
            mobikeView2.addView(imageView,layoutParams2);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mobikeView1.getmMobike().onStart();
        mobikeView2.getmMobike().onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mobikeView1.getmMobike().onStop();
        mobikeView2.getmMobike().onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(listerner, defaultSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listerner);
    }

    private SensorEventListener listerner = new SensorEventListener(){

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){

                // 获取加速传感器的值
                float x =  event.values[0];
                float y =   event.values[1] * 2.0f;

//                float z = event.values[2];

                // 防止手机水平向上时抖动
                if (y > 10) {
                    y = event.values[2];
                }

                // 配置View
                mobikeView1.getmMobike().onSensorChanged(-x,y);
                mobikeView2.getmMobike().onSensorChanged(-x,y);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
}
