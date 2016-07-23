package com.nexters.amuguna.gola.model;

/**
 * Created by Daesub Kim on 2016-07-21.
 */
public class GolaImage {

    private int num;
    private int imgId;

    public GolaImage(){}

    public GolaImage(int num, int imgId){
        this.num = num;
        this.imgId = imgId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
