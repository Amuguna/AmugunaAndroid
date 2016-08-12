package com.nexters.amuguna.gola;

import android.media.Image;

import com.bumptech.glide.DrawableRequestBuilder;

import java.util.ArrayList;

/**
 * Created by Daesub Kim on 2016-07-21.
 */
public class StaticInfo {

    private StaticInfo(){}
    /* 16강 기본 세팅 */
    //public static final int DEFAULT_ROUND = 16;
    public static final int IMAGE_COUNT = 16;
    public static final int DEFAULT_ROUND = 8;


    /* 해당 토너먼트 ROUND의 총 경기 회수 */
    public static int GAME_TOT = 8;
    /* 해당 토너먼트 ROUND에서 현재 진행중인 게임 COUNT */
    public static int GAME_CNT = 1;

    public static int[] CUR_NODE;
    public static int[] NEXT_NODE;

    /* 랜덤 수를 담은 리스트 */
    public static final ArrayList<Integer> RAN = new ArrayList<Integer>();
    public static ArrayList<Integer> resourceList = new ArrayList<Integer>();
    public static final ArrayList<DrawableRequestBuilder<Integer>> imageList = new ArrayList<DrawableRequestBuilder<Integer>>();


    public static final int TREE_SIZE=32;

    /* 현재 몇강 토너먼트인지 */
    public static int CUR_ROUND = 16;
    /*--*/
    public static int ROUND = 16;
    public static DrawableRequestBuilder<Integer>[] tournamentTree = new DrawableRequestBuilder[TREE_SIZE];
    public static DrawableRequestBuilder<Integer>[] initTree = new DrawableRequestBuilder[ROUND];
    public static int TREE_MAX_LEVEL = 5;
    public static int TREE_CURRENT = 0;
    public static int TREE_CURRENT_LEVEL = 0;


}