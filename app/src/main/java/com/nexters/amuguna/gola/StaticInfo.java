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

    //public static final ArrayList<String> foodName = new ArrayList<String>();
    public static final String[] foodName = {"알밥", "백반", "닭갈비", "닭볶음탕", "떡볶이", "돈까스", "오징어덮밥", "갈비찜", "감자탕",
            "삼겹살", "삼계탕", "순두부찌개", "비빔밥", "쌀국수", "쌈밥", "스테이크", "초밥", "롤", "탕수육", "우동", "양꼬치", "짬뽕",
            "짜장", "볶음밥", "찜닭", "해물탕", "해물찜", "햄버거", "회덮밥", "장어", "제육볶음", "족발", "김밥", "김치찌개", "보쌈",
            "깐풍기", "곱창", "잔치국수", "국밥", "막국수", "냉면", "된장찌개", "파스타", "팟타이", "피자", "부대찌개", "라멘", "회",
            "샌드위치", "도시락", "쭈꾸미볶음", "고기국수", "비빔국수", "설렁탕", "샤브샤브", "돈부리덮밥", "불고기", "연탄불고기",
            "브리또", "모듬전", "뼈찜", "만두", "칼국수", "수제비", "치킨", "소고기", "카레"};

    public static int[] ROUND_16 = new int[16];
    public static int[] ROUND_8 = new int[8];
    public static int[] ROUND_4 = new int[4];
    public static int[] ROUND_2 = new int[2];

}

