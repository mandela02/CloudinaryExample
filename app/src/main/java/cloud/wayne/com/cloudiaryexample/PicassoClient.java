package cloud.wayne.com.cloudiaryexample;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class PicassoClient {
    public static void downloadImage(Context context, String url, ImageView img)
    {
        if(url!=null && url.length() >0)
        {
            Picasso.with(context).load(url).placeholder(R.drawable.ic_launcher_background).into
                (img);
        }
        else {
            Toast.makeText(context, "dead", Toast.LENGTH_SHORT).show();
            Picasso.with(context).load(url).placeholder(R.drawable.ic_launcher_background);
        }
    }
}
