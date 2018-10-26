package cloud.wayne.com.cloudiaryexample;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyHolder extends RecyclerView.ViewHolder {
    public ImageView mImageView;
    public TextView mTextView;

    public MyHolder(View itemView) {
        super(itemView);
        mImageView = itemView.findViewById(R.id.image);
        mTextView = itemView.findViewById(R.id.text);
    }
}
