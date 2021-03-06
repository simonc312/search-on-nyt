package com.simonc312.searchnyt.helpers;

import android.content.Context;
import android.widget.ImageView;

import com.simonc312.searchnyt.R;
import com.squareup.picasso.Picasso;

/**
 * Wrapper for Picasso or any third part image loading library
 * Created by Simon on 2/2/2016.
 */
public class ImageLoaderHelper {

    private ImageLoaderHelper(){
    }

    public static  void loadWithPlaceholder(Context context,
                                            String src,
                                            ImageView image,
                                            int placeHolderId,
                                            boolean doCenterCropAndFit){
        if(doCenterCropAndFit)
            centerCropAndFit(context,src,image,placeHolderId);
        else
            loadWithPlaceholder(context,src,image,placeHolderId);
    }

    public static void loadWithResizedPlaceHolder(Context context, String src, ImageView image, int placeHolderId, int width, int height){
        Picasso.with(context)
                .load(src)
                .resize(width,height)
                .placeholder(placeHolderId)
                .into(image);
    }

    public static  void loadWithPlaceholder(Context context, String src,ImageView image, int placeHolderId){
        Picasso.with(context)
                .load(src)
                .placeholder(placeHolderId)
                .into(image);
    }

    public static void centerCropAndFit(Context context, String src,ImageView image, int placeHolderId){
        Picasso.with(context)
                .load(src)
                .placeholder(placeHolderId)
                .fit()
                .centerCrop()
                .into(image);
    }
}
