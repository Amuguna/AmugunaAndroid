package com.nexters.amuguna.gola;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nexters.amuguna.gola.model.GpsInfo;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Daesub Kim on 2016-07-15.
 */
public class ResultActivity extends AppCompatActivity {

    Intent intent;

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

        /* Hide ActionBar */
        getSupportActionBar().hide();

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
        //moveToNaverApp();
        GpsInfo gpsInfo = new GpsInfo(this);
        if(gpsInfo.checkGPSONOFF()) {
            moveToNaverApp(gpsInfo);
        }
    }

    private void moveToNaverApp(GpsInfo gpsInfo) {

        /* GPS 버튼 강제로 켜는 코드 넣어야함.
         * 없으면 항상 addr = null */
        String addr = getAddr(gpsInfo);
        Log.e("Address", addr + "");

        String str = addr + resultText.getText().toString();
        PackageManager pm = getApplicationContext().getPackageManager();
        /* 네이버앱 설치시 */
        try {
            String strAppPackage = "com.nhn.android.search";
            pm.getApplicationIcon(strAppPackage).getClass();

            Intent in = new Intent(Intent.ACTION_VIEW);
            in.addCategory(Intent.CATEGORY_DEFAULT);
            in.addCategory(Intent.CATEGORY_BROWSABLE);
            in.setData(Uri.parse("naversearchapp://keywordsearch?mode=result&query=" + str + "&version=5"));
            startActivity(in);
        }
        /* 네이버앱 미 설치시 */
        catch (Exception e) {
            Intent in = new Intent(Intent.ACTION_VIEW);
            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            in.setData(Uri.parse("http://m.search.naver.com/search.naver?query=" + str));
            startActivity(in);
        }
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

}
