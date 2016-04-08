package uk.co.ribot.androidboilerplate.util;

import android.content.Context;
import android.content.res.TypedArray;

import uk.co.ribot.androidboilerplate.R;

/**
 * Created by adhamenaya on 4/8/2016.
 */
public class CommonUtils {

    public static int getIconId(Context context,String name){
        return context.getResources().getIdentifier(name,"drawable",context.getPackageName());
    }

    public static String getBackgroundColor(Context context,int position){
        TypedArray colorCode = context.getResources().obtainTypedArray(R.array.colors);
        return String.valueOf(colorCode.getString(position));
    }
}
