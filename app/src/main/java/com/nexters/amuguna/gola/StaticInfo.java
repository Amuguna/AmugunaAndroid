package com.nexters.amuguna.gola;

import com.bumptech.glide.DrawableRequestBuilder;
import java.util.ArrayList;

/**
 * Created by Daesub Kim on 2016-07-21.
 */
public class StaticInfo {

    private StaticInfo(){}
    /* 16강 기본 세팅 */
    public static int ROUND = 16;
    public static int CNT = 0;

    /* 랜덤 수를 담은 리스트 */
    public static final ArrayList<Integer> RAN = new ArrayList<Integer>();
    public static final ArrayList<DrawableRequestBuilder<Integer>> imageList = new ArrayList<DrawableRequestBuilder<Integer>>();
    public static DrawableRequestBuilder cardX;

    //public static final ArrayList<String> foodName = new ArrayList<String>();
    public static final String[] foodName = {"덮밥","갈비","감자탕","삼겹살","삼계탕","순두부찌개","쌀국수","쌈밥"
            ,"스테이크","초밥","스시롤","탕수육","우동","양고기","짬뽕","짜장면","찜닭"};

    public static int[] ROUND_16 = new int[16];
    public static int[] ROUND_8 = new int[8];
    public static int[] ROUND_4 = new int[4];
    public static int[] ROUND_2 = new int[2];

}