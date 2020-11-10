package com.example.parkapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.constant.TimeConstants;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.parkapp.base.BaseActivity;
import com.example.parkapp.common.Constants;
import com.example.parkapp.http.LicensePlate;
import com.example.parkapp.http.PlateResult;
import com.example.parkapp.model.AppDataManager;
import com.example.parkapp.model.ParkCar;
import com.example.parkapp.model.ParkCarAuth;
import com.example.parkapp.util.AppPerferHelp;
import com.example.parkapp.util.DisplayUtil;
import com.example.parkapp.util.GlideEngine;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;

import java.util.List;

import androidx.lifecycle.Observer;

public class MainActivity extends BaseActivity {
    private Toolbar mToolbar;
    private TextView mTvParkSum;
    private Button mBtnCarIn;
    private Button mBtnCarOut;
    private Button quit_login;


    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//savadInstanceState是保存当前Activity中的信息
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);;//初始化标题栏 按钮等控件
        mTvParkSum = findViewById(R.id.tv_park_sum);
        mBtnCarIn = findViewById(R.id.btn_car_in);
        mBtnCarOut = findViewById(R.id.btn_car_out);
        quit_login = findViewById((R.id.quit_login));

        quit_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,logActivity.class);
                startActivity(intent);
            }
        });
        //设置Toolbar
       // setSupportActionBar(mToolbar);
       // mToolbar.setTitle("智能停车管理系统");//标题栏改名字
        PermissionUtils.permission(PermissionConstants.CAMERA, PermissionConstants.STORAGE).request();//允许调用照相机
        mBtnCarIn.setOnClickListener(new View.OnClickListener() {//按下车辆驶入
            @Override
            public void onClick(View v) {
//                processCarIn("京A9B7V1");
                PictureSelector.create(MainActivity.this)
                        .openCamera(PictureMimeType.ofImage())
                        .loadImageEngine(GlideEngine.createGlideEngine())//打开照相机采集图像
                        //对图像进行压缩处理
                        .compress(true)
                        .forResult(new OnResultCallbackListener<LocalMedia>() {//绑定回调监听
                            @Override
                            public void onResult(List<LocalMedia> result) {//对拍照的结果进行回调
                                // 结果回调
                                if (result.size() > 0) {//如果照片获取正常
                                    showLoading("解析车辆信息中", false);//弹窗提示
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {//解析到了
                                            String code = LicensePlate.licensePlate(result.get(0).getCompressPath());
                                            dismissPopup();//提示消失
                                            PlateResult plateResult = null;
                                            try {
                                                assert code != null;
                                                plateResult = GsonUtils.fromJson(code, PlateResult.class);//从Json相关对象到Java实体的方法
                                                String number = plateResult.getWords_result().getNumber();//words_result : {"color":"blue","number":"京A63083","probability":[0.901121
                                                //从识别到的words_result解析出车牌
                                                processCarIn(number);//调用processCarIn函数处理处理车辆进入
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                showError("车牌识别错误：" + e.getMessage());//否则报错误弹窗
                                            }
                                        }
                                    }).start();
                                    LogUtils.e("" + result.get(0).getCompressPath());
                                }
                            }

                            @Override
                            public void onCancel() {
                                // 取消
                            }
                        });

            }


        });

        mBtnCarOut.setOnClickListener(new View.OnClickListener() {//按下车辆驶出
            @Override
            public void onClick(View v) {

                PictureSelector.create(MainActivity.this)
                        .openCamera(PictureMimeType.ofImage())
                        .loadImageEngine(GlideEngine.createGlideEngine())//打开照相机采集图像
                        //对图像进行压缩处理
                        .compress(true)
                        .forResult(new OnResultCallbackListener<LocalMedia>() {//绑定回调监听
                            @Override
                            public void onResult(List<LocalMedia> result) {//对拍照的结果进行回调
                                // 结果回调
                                if (result.size() > 0) {//如果照片获取正常
                                    showLoading("解析车辆信息中", false);//弹窗提示
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            String code = LicensePlate.licensePlate(result.get(0).getCompressPath());
                                            dismissPopup();
                                            PlateResult plateResult = null;
                                            try {
                                                assert code != null;
                                                plateResult = GsonUtils.fromJson(code, PlateResult.class);//从Json相关对象到Java实体的方法
                                                String number = plateResult.getWords_result().getNumber();//words_result : {"color":"blue","number":"京A63083","probability":[0.901121
                                                //从识别到的words_result解析出车牌
                                                processCarOut(number);//调用processCarOut函数处理处理车辆进入
                                            } catch (Exception e) {
                                                showError("车牌识别错误：" + e.getMessage());//否则报错误弹窗
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();
                                    LogUtils.e("" + result.get(0).getCompressPath());
                                }
                            }

                            @Override
                            public void onCancel() {
                                // 取消
                            }
                        });

//                processCarOut("黑A9B7V1");
            }
        });

        //LiveData实时监听车位变化状态 自动更新页面
        initSumObser();
    }

    //LiveData实时监听车位变化状态 自动更新页面
    private void initSumObser() {//初始化停车场，读数据库里停车场的状态和容量
        AppDataManager.getInstance().getAppDatabase().parkCarDao().getAllCarInParkObrs().observe(this, new Observer<List<ParkCar>>() {
            @Override
            public void onChanged(List<ParkCar> parkCars) {
                updateParkStatus(parkCars.size());
            }
        });
    }

    private void updateParkStatus(int size) {//改停车场容量
        updateParkStatus(size, AppPerferHelp.getInstance().getSum());
    }

    private void updateParkStatus(int size, int sum) {//该停车场容量和现有数量
        mTvParkSum.setText(String.format("车位状态：%d/%d", size, sum));
    }

    /**
     * 处理车辆进入逻辑
     *
     * @param number 车牌号
     */
    private void processCarIn(String number) {
        //首先检测是否有授权
        if (AppDataManager.getInstance().getAppDatabase().parkCarAuthDao().isAuth(number)) {//首先检测是否有授权
            if (AppDataManager.getInstance().getAppDatabase().parkCarDao().isParkIn(number)) {//其次检测是否已进入车库
                new XPopup.Builder(MainActivity.this)//已经在车库，弹窗提示
                        .asConfirm(number, number + "该车辆信息已经在停车场登记驶入，请仔细核对车辆信息或联系车厂管理员", () -> {
                        }).show();
            } else {
                new XPopup.Builder(getContext())//不在车库
                        .asConfirm(number, "该车辆将进入停车场，点击确定允许进入， 并开始自动计费！", new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                //插入停车信息
                                ParkCar parkCar = new ParkCar();
                                parkCar.setNumber(number);//车牌号
                                parkCar.setStatus(Constants.CAR_IN);//状态改成已进入
                                parkCar.setEnterTime(TimeUtils.getNowMills());//进入时间用系统时间
                                AppDataManager.getInstance().getAppDatabase().parkCarDao().insertParkCar(parkCar);//将实例加入数据库
                                ToastUtils.showShort("车辆进入成功！开始计费");//提示进入成功
                            }
                        }).show();
            }

        } else {//没有获得授权的话
            //快捷询问是否添加到授权
            new XPopup.Builder(MainActivity.this)
                    .asConfirm("错误信息", number + "该车辆没有授权信息！是否添加到授权？", () -> addCarAuth(number)).show();//将该车牌号添加授权
        }
    }

    /**
     * 处理车辆驶出信息
     *
     * @param number 车牌号码
     */
    private void processCarOut(String number) {
        //首先检测是否已停在车库， 只有已经停进来的车辆才能正确处理
        if (AppDataManager.getInstance().getAppDatabase().parkCarDao().isParkIn(number))
            {//检查是否已经在车库里，在的话
                ParkCar parkCar = AppDataManager.getInstance().getAppDatabase().parkCarDao().find(number);//从数据库中获取车牌，进出时间
                long timeLong = TimeUtils.getTimeSpan(System.currentTimeMillis(), parkCar.getEnterTime(), TimeConstants.MIN);
                String time = TimeUtils.getFitTimeSpan(System.currentTimeMillis(), parkCar.getEnterTime(), 3);
                time = StringUtils.isEmpty(time) ? "0分钟" : time;
                LogUtils.e("long:" + timeLong + "   string:" + time);
                String finalTime = time;//总停时长
                new XPopup.Builder(MainActivity.this)//弹窗给出计费提示
                        .asConfirm(number, number + " 停车时长" + time + " \n点击确定开始结束计费", new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                new XPopup.Builder(getContext()).asConfirm("费用统计",
                                        "停车时长 " + finalTime + "  应收 " + (AppPerferHelp.getInstance().getPay() * timeLong) + " 元",
                                        "联系人工处理", "已收费完成", new OnConfirmListener() {
                                            @Override
                                            public void onConfirm() {//更改车辆实例信息
                                                parkCar.setStatus(Constants.CAR_OUT);//状态改成驶出
                                                parkCar.setParkTime(timeLong);//停车总时长
                                                parkCar.setOutTime(TimeUtils.getNowMills());//出去的时间用系统时间
                                                AppDataManager.getInstance().getAppDatabase().parkCarDao().updateParkCar(parkCar);
                                                ToastUtils.showShort("收费成功，车辆驶出！");
                                            }
                                        }, null, false).show();

                                LogUtils.e("--" + finalTime);
                            }
                        }).show();
            } else{//没有车辆的话
                new XPopup.Builder(getContext())
                        .asConfirm(number, "登记信息中并没有找到该车辆登记信息， 请联系车厂管理员人工确认！", new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                            }
                        }).show();
            }
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){//设置小菜单栏
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add_auth_car://管理授权车辆
//                new XPopup.Builder(this).asInputConfirm("添加授权车辆", "输入车牌号码将车辆授权允许进入停车场", new OnInputConfirmListener() {
//                    @Override
//                    public void onConfirm(String text) {
//                        if (RegexUtils.getMatches(Constants.CAR_REG, text).size() <= 0) {
//                            ToastUtils.showShort("请填写正确车牌号码");
//                            return;
//                        }
//                        addCarAuth(text);
//
//                    }
//                }).show();
                startActivity(new Intent(MainActivity.this,ParkCarAuthListActivity.class));//跳转到管理授权车辆界面
                break;
            case R.id.action_set_pay://添加收费标准
                new XPopup.Builder(getContext()).asInputConfirm("设置收费标准", "输入收费标准 元/分钟", "" + AppPerferHelp.getInstance().getPay(), "", new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        AppPerferHelp.getInstance().setPay(Float.parseFloat(text));
                        ToastUtils.showShort("设置成功！");
                    }
                }).show();
                break;
            case R.id.action_set_sum://设置停车场容量
                new XPopup.Builder(getContext()).asInputConfirm("设置车位数量", "输入车位总数", "" +
                        AppPerferHelp.getInstance().getSum(), "", new OnInputConfirmListener() {
                    @Override
                    public void onConfirm(String text) {
                        AppPerferHelp.getInstance().setSum(Integer.parseInt(text));
                        ToastUtils.showShort("设置成功！");
                        initSumObser();
                    }
                }).show();
                break;
            case R.id.action_car_history://查询停车记录
                List<ParkCar> parkCars = AppDataManager.getInstance().getAppDatabase().parkCarDao().getAllCarInPark();
                String[] arrays = new String[parkCars.size()];
                for (int i = 0; i < parkCars.size(); i++) {
                    ParkCar parkCar = parkCars.get(i);
                    String time = TimeUtils.getFitTimeSpan(System.currentTimeMillis(), parkCar.getEnterTime(), 3);
                    time = StringUtils.isEmpty(time) ? "0分钟" : time;
                    arrays[i] = (parkCar.getId() + 1) + ". " + parkCar.getNumber() + " (停车时长:" + time + ")";
                    Log.d("a=",time);
                }
                new XPopup.Builder(getContext()).maxWidth(DisplayUtil.getWidth(MainActivity.this) - 20).asCenterList("停车场详情", arrays, new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {

                    }
                }).show();
                break;
            default:
        }

        return super.onOptionsItemSelected(item);
    }

    private void addCarAuth(String text) {//添加新的授权车辆
        ParkCarAuth parkCarAuth = new ParkCarAuth();
        parkCarAuth.setNumber(text);
        parkCarAuth.setAuthTime(TimeUtils.getNowMills());
        AppDataManager.getInstance().getAppDatabase().parkCarAuthDao().insertParkCarAuth(parkCarAuth);
        ToastUtils.showShort("添加车辆" + text + "授权成功");
    }
}
