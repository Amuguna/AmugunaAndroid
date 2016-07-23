package com.nexters.amuguna.gola;

import java.util.ArrayList;

/**
 * Created by Daesub Kim on 2016-07-21.
 */
public class StaticInfomation {

    /* 16강 기본 세팅 */
    public static final int DEFAULT_ROUND = 16;

    /* 현재 몇강 토너먼트인지 */
    public static int ROUND = 16;
    /* 해당 토너먼트 ROUND의 총 경기 회수 */
    public static int ROUND_TOT = 8;
    /* 해당 토너먼트 ROUND에서 현재 진행중인 게임 COUNT */
    public static int CNT = 1;

    public static int[] ARR_NODE;
    public static int[] NEXT_ARR_NODE;

    /* 랜덤 수를 담은 리스트 */
    public static final ArrayList<Integer> RAN = new ArrayList<Integer>();

}
