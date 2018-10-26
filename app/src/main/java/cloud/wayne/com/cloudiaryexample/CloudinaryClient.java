package cloud.wayne.com.cloudiaryexample;

import android.os.AsyncTask;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.android.MediaManager;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;

public class CloudinaryClient {

    public static Cloudinary cloud;

    public static Cloudinary cloud()
    {
        cloud = new Cloudinary(MyConfiguration.getMyConfigs());
        return cloud;
    }
    public static String getRoundCornerImage(String ImageName)
    {
        Transformation t = new Transformation();
        t.radius(60);
        t.height(500);
        t.width(800);
        String url = cloud.url().transformation(t).generate(ImageName);
        return url;
    }

}
