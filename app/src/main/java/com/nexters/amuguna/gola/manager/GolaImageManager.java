package com.nexters.amuguna.gola.manager;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.nexters.amuguna.gola.BuildConfig;
import com.nexters.amuguna.gola.model.GolaImage;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daesub Kim on 2016-07-21.
 */
public class GolaImageManager {

    private static GolaImageManager instatnce;
    public List<String> golas;
    public static String []food = "gola_img_dupbab,gola_img_galbi,gola_img_gamza,gola_img_sam,gola_img_samkye,gola_img_soondubu,gola_img_ssal_kooksu,gola_img_ssam,gola_img_staek,gola_img_sushi,gola_img_sushi_roll,gola_img_tang,gola_img_woodong,gola_img_yang,gola_img_zzambbong,gola_img_zzazang,gola_img_zzimdak".split(",");
    //public static String []food = "gola_img_dupbab.jpg,gola_img_galbi.jpg,gola_img_gamza.jpg,gola_img_sam.jpg,gola_img_samkye.jpg,gola_img_soondubu.jpg,gola_img_ssal_kooksu.jpg,gola_img_ssam.jpg,gola_img_staek.jpg,gola_img_sushi.jpg,gola_img_sushi_roll.jpg,gola_img_tang.jpg,gola_img_woodong.jpg,gola_img_yang.jpg,gola_img_zzambbong.jpg,gola_img_zzazang.jpg,gola_img_zzimdak.jpg".split(",");

    static {
        instatnce = new GolaImageManager();
    }
    private GolaImageManager(){golas = new ArrayList<String>();}

    public static synchronized GolaImageManager getInstatnce(){
        return instatnce;
    }

    public void initImages() {


        /*String imageFileName="";
        File drawable = new File(Uri.parse("android.resource://"+ BuildConfig.APPLICATION_ID+"/").toString());

        File [] fileList = drawable.listFiles();
        for(int i=0;i<fileList.length;i++){
            imageFileName = fileList[i].getName();
            Log.e("imageFileName",imageFileName);
            if(imageFileName.contains("gola_img_")) {
                golas.add(imageFileName);

            }
        }*/

    }
    /*golas = new ArrayList<GolaImage>();
        final Field[] fields = com.nexters.amuguna.gola.R.drawable.class.getFields();
        int i=1;
        for (Field f : fields) {
            if (f.getName().startsWith("gola_img_")) {
                try {
                    //golas.add(new GolaImage(i, f.getInt(f.getClass())));
                    golas.add(new GolaImage(i,i));
                    Log.e("IMAGE : ", f.getName());
                } catch (Exception e) {
                    continue;
                }
            }
        }*/

    public  List<String> getGolas() {
        return this.golas;
    }

    /*public static Map<String,Integer> loadLayouts(){
        final Class<?> clz = R.layout.class;
        Map<String,Integer> layouts = new HashMap<>();
        final Field[] fields = clz.getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            if (
                    !name.startsWith("abc_")
                            && !name.startsWith("design_")
                            && !name.startsWith("notification_")
                            && !name.startsWith("select_dialog_")
                            && !name.startsWith("support_")
                    ) {
                try {
                    layouts.put(field.getName(), field.getInt(clz));
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return layouts;
    }*/

}
