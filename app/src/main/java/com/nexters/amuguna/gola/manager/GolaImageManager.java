package com.nexters.amuguna.gola.manager;

import android.util.Log;

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
    private List<String> golas;

    static {
        instatnce = new GolaImageManager();
    }
    private GolaImageManager(){golas = new ArrayList<String>();}

    public static synchronized GolaImageManager getInstatnce(){
        return instatnce;
    }

    public void initImages() {
        String imageFileName="";

        File drawable = new File("/drawable/");
        File [] fileList = drawable.listFiles();
        for(int i=0;i<fileList.length;i++){
            imageFileName = fileList[i].getName();
            if(imageFileName.startsWith("gola_img_")) {
                golas.add(imageFileName);
            }
        }

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
