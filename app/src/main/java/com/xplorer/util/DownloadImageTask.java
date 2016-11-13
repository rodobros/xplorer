package com.xplorer.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Rodobros on 2016-11-10.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView_;

    public DownloadImageTask(ImageView imageView) {
        imageView_ = imageView;
    }

    protected Bitmap doInBackground(String... urls) /* Note: "String..." means that one or more string can be passed as param */ {
        String imageToDisplayUrl = urls[0];
        Bitmap imageBitmap = null;
        try {
            InputStream in = new java.net.URL(imageToDisplayUrl).openStream();
            imageBitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return imageBitmap;
    }

    protected void onPostExecute(Bitmap result) {
        imageView_.setImageBitmap(result);
    }
}
