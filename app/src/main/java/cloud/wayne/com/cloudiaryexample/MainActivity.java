package cloud.wayne.com.cloudiaryexample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cloudinary.Cloudinary;
import com.cloudinary.android.MediaManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CAPTURE_IMAGE = 100;
    RecyclerView mRecyclerView;
    MyAdapter mAdapter;
    FloatingActionButton mButton;
    FloatingActionButton mButtonTake;
    File photoFile;
    Cloudinary cloud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cloud = CloudinaryClient.cloud();
        mRecyclerView = findViewById(R.id.recycler_image);
        mButton = findViewById(R.id.float_btn);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mAdapter = new MyAdapter(this, ContentsCollection.getContents());
        //new Upload().execute();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.setAdapter(mAdapter);
                Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePhotoIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePhotoIntent,
                        100);
                }
            }
        });
        mButtonTake = findViewById(R.id.float_btn_take);
        mButtonTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pictureIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE
                );
                if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(pictureIntent, REQUEST_CAPTURE_IMAGE);
                }
            }
        });
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media
            .insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            final Uri mImageUri = data.getData();
            final File finalFile = new File(getRealPathFromURI(mImageUri));
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    MediaManager.init(getApplicationContext(), MyConfiguration.getMyConfigs());
                    String result = MediaManager.get().upload(mImageUri)
                        .option("folder", "my_folder/my_sub_folder/")
                        .option("public_id", "my_dog")
                        .option("overwrite", true)
                        .dispatch();

                    return result;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                }
            }.execute();
        } else if (resultCode == RESULT_CANCELED) {
            //finish();
        }
    }
}
