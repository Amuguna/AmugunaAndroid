package com.nexters.amuguna.gola.manager;

/**
 * Created by Daesub Kim on 2016-07-15.
 */
public class TournametManager {

    private static TournametManager tournametManager;
    private int depth;
    private int count;

    static {
        tournametManager = new TournametManager();
    }
    private TournametManager() {
        initTree(16);
    }

    public static synchronized TournametManager getInstance() {
        return tournametManager;
    }

    public int getDepth() {
        return this.depth;
    }
    public int getCount() {
        return this.count;
    }

    /**
     * Init Tournament Tree by Round.
     * @param round Tournament Round
     */
    public void initTree(int round) {

        switch(round) {
            case 32 :
                this.depth = 4;
                this.count = 16;
                break;
            case 16 :
                this.depth = 3;
                this.count = 8;
                break;
            case 8 :
                this.depth = 2;
                this.count = 4;
                break;
            case 4 :
                this.depth = 1;
                this.count = 2;
                break;
            case 2 :
                this.depth = 0;
                this.count = 1;
                break;
        }
    }

    /**
     *
     * @return -1 : 결승전 완료(토너먼트 종료) // 0 : 다음 레벨로 이동 ( eg. 32강 -> 16강 ) // 1 : 다음 게임으로 이동
     */
    public int nextRound() {
        count--;
        if(count == 0) {
            if(depth == 0) {
                return -1;
            }
            depth--;
            nextLevel();
            return 0;
        }
        return 1;
    }

    private void nextLevel() {
        switch (depth) {
            case 3 :
                count = 8;
                break;
            case 2 :
                count = 4;
                break;
            case 1 :
                count = 2;
                break;
            case 0 :
                count = 1;
                break;
        }
    }
}
