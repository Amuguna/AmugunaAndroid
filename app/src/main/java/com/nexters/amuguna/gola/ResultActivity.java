package com.nexters.amuguna.gola;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.nexters.amuguna.gola.model.GpsInfo;
import com.nexters.amuguna.gola.utils.CustomPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Daesub Kim on 2016-07-15.
 */
public class ResultActivity extends AppCompatActivity {

    private CustomPreference pref;
    private Context mContext;
    Intent intent;
    private String myLocation = "";

    @Bind(com.nexters.amuguna.gola.R.id.retry_top_linear)
    ViewGroup retryTopLinear;

    @Bind(com.nexters.amuguna.gola.R.id.go_home_top_linear)
    ViewGroup goHomeTopLinear;

    @Bind(com.nexters.amuguna.gola.R.id.result_img)
    ImageView resultImg;

    @Bind(R.id.result_top_text)
    TextView resultTopText;

    @Bind(R.id.result_text)
    TextView resultText;

    @Bind(com.nexters.amuguna.gola.R.id.result_again)
    ImageView againBottomImg;

    @Bind(com.nexters.amuguna.gola.R.id.result_cross)
    ImageView crossBottomImg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.nexters.amuguna.gola.R.layout.activity_result);
        ButterKnife.bind(this);

        pref = pref.getInstance(this.getApplicationContext());
        // GPS 세팅들어갔다 나왔을때 체크
        // GPS켜진거 체크
        //pref.put("GPS_Enable", false);
        pref.put("GPS_Setting_Visited", false);

        /* Hide ActionBar */
        getSupportActionBar().hide();
        mContext = getApplicationContext();

        /* 토너먼트에서 넘어왔는지 랜덤 선택에서 넘어왔는지 확인 */
        intent = getIntent();
        if(intent.getBooleanExtra("isTournament", true)){

            StaticInfo.imageList.get(intent.getIntExtra("result",0)).into(resultImg);
            resultTopText.setText("토너먼트 결과");
            resultText.setText(StaticInfo.foodName[intent.getIntExtra("result",0)]);

            crossBottomImg.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.result_random_btn));

            Log.e("isTournament ?", "TOURNAMENT !!!! ");
            Log.e("RESULT", ""+intent.getIntExtra("result",1));

        } else {
            selectRandom();
            crossBottomImg.setImageBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.result_tournament_btn));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        resultImg.setClickable(false);
        new Handler().postDelayed(new Runnable() {// 3 초 후에 실행
            @Override
            public void run() {
                // 실행할 동작 코딩
                resultImg.setClickable(true);
            }
        }, 1500);

    }

    @OnClick(R.id.go_home_top_linear)
    void goHomeBtnClick() {
        Intent intent = new Intent(ResultActivity.this,GolaMainActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick({com.nexters.amuguna.gola.R.id.result_again, com.nexters.amuguna.gola.R.id.retry_top_linear})
    void againBtnClick() {

        if(intent.getBooleanExtra("isTournament", true)){
            /* Move to TournamentActivity. */
            Intent intent = new Intent(ResultActivity.this,TournamentActivity.class);
            intent.putExtra("isTournament", true);
            intent.putExtra("isFirstRound", true);
            intent.putExtra("round", StaticInfo.ROUND);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            selectRandom();
        }

    }

    @OnClick(R.id.result_cross)
    void crossBtnClick() {

        if(intent.getBooleanExtra("isTournament", true)){
            selectRandom();
        } else {
            /* Move to TournamentActivity. */
            Intent intent = new Intent(ResultActivity.this,TournamentActivity.class);
            intent.putExtra("isTournament", true);
            intent.putExtra("isFirstRound", true);
            intent.putExtra("round", StaticInfo.ROUND);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void selectRandom() {
        int ran = (int) (Math.random()*StaticInfo.foodName.length);
        StaticInfo.imageList.get( ran ).into(resultImg);
        resultTopText.setText("랜덤 선택 결과");
        resultText.setText(StaticInfo.foodName[ran]);

        intent.putExtra("isTournament", false);
        Log.e("isTournament ?", "NO !!!! : "+ ran);
    }

    @OnClick(R.id.result_img)
    void resultImgClicked() {

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                //moveToNaverApp(null);
                generateGPSModule();

                //Toast.makeText(ResultActivity.this, "위치정보 사용승인", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                myLocation = "";
                moveToNaverApp();
                Toast.makeText(ResultActivity.this, "위치정보 사용거부" /*+ deniedPermissions.toString()*/, Toast.LENGTH_SHORT).show();
            }
        };

        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage(
                        "Gola에서 사용자의 위치정보를 활용하고자 합니다.\n\n" +
                        "허용 : 현재위치 기준 주변 음식점 검색\n" +
                        "거부 : 선택음식 이름으로만 검색"
                )
                .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                .check();

        /*
        if (pref.getValue("GPS_Setting_Visited", false)) {

        } else {

        }*/


        //moveToNaverApp(null);
        /*
        GpsInfo gpsInfo = new GpsInfo(this);
        if(gpsInfo.checkGPSONOFF()) {
            moveToNaverApp(gpsInfo);
        }*/
    }

    //private void moveToNaverApp(GpsInfo gpsInfo) {
    private void moveToNaverApp() {

        /* GPS 버튼 강제로 켜는 코드 넣어야함.
         * 없으면 항상 addr = null */
        //String addr = getAddr(gpsInfo);
        //String addr = "";

        String naverParam = myLocation + resultText.getText().toString();
        PackageManager pm = getApplicationContext().getPackageManager();
        /* 네이버앱 설치시 */
        try {
            String strAppPackage = "com.nhn.android.search";
            pm.getApplicationIcon(strAppPackage).getClass();

            Intent in = new Intent(Intent.ACTION_VIEW);
            in.addCategory(Intent.CATEGORY_DEFAULT);
            in.addCategory(Intent.CATEGORY_BROWSABLE);
            in.setData(Uri.parse("naversearchapp://keywordsearch?mode=result&query=" + naverParam + "&version=5"));
            startActivity(in);
        }
        /* 네이버앱 미 설치시 */
        catch (Exception e) {
            Intent in = new Intent(Intent.ACTION_VIEW);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            in.setData(Uri.parse("http://m.search.naver.com/search.naver?query=" + naverParam));
            startActivity(in);
        }
    }

    /**
     * GPS Module 실행
     */
    private void generateGPSModule() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        GpsInfo gpsInfo = new GpsInfo(mContext);
                        if(gpsInfo.checkGPSONOFF()) {
                            /* GPS가 정상적으로 켜져있음. Naver App으로 이동 */
                            //moveToNaverApp(gpsInfo);
                            myLocation = getAddr(gpsInfo);
                            Log.e("myLocation", ""+myLocation);
                            moveToNaverApp();
                        } else {
                            /* GPS가 꺼져있음 */
                            alertGPSoff();
                        }

                    }
                });
            }
        }).start();

    }

    private String getAddr(GpsInfo gpsInfo) {

        Log.e("lat/lng", gpsInfo.getLatitude()+"/"+gpsInfo.getLongitude());

        String addr = getAddressName(gpsInfo.getLatitude(), gpsInfo.getLongitude());
        gpsInfo.stopUsingGPS();
        return addr;
    }

    public String getAddressName(double lat, double lng){
        String address = null;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> list = null;
        try{

            list = geocoder.getFromLocation(lat, lng, 1);

        } catch(Exception e){
            e.printStackTrace();
        }

        if(list == null){
            Log.e("getAddress", "주소 데이터 얻기 실패");
            return null;
        }

        if(list.size() > 0){
            Address addr = list.get(0);
            address = addr.getLocality() + " "
                    + addr.getThoroughfare() + " ";
        }
        return address;
    }

    /**
     * GPS가 꺼져있을 경우 GPS세팅으로 보내는 alert
     */
    private void alertGPSoff() {

        if (pref.getValue("GPS_Setting_Visited", true)) {
            myLocation = "";
            moveToNaverApp();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                            builder.setMessage("위치정보 활용을 위해 GPS 기능을\n활성화 해주세요.")
                                    .setCancelable(false)
                                    .setNegativeButton("거부",
                                            new DialogInterface.OnClickListener(){
                                                public void onClick(DialogInterface dialog, int id) {
                                                    myLocation = "";
                                                    moveToNaverApp();
                                                    dialog.cancel();
                                                }
                                            })
                                    .setPositiveButton("GPS 설정",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick (DialogInterface dialog,int id){
                                                    //moveConfigGPS();
                                                    startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                                                }
                                            })
                                    ;
                            AlertDialog alert = builder.create();
                            alert.show();
                            pref.put("GPS_Setting_Visited", true);
                        }
                    });
                }
            }).start();
        }


    }

}
