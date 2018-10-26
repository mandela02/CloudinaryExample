package cloud.wayne.com.cloudiaryexample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyHolder> {
    Context mContext;
    ArrayList<Contents> mContentsArrayList;

    public MyAdapter(Context context, ArrayList<Contents> contents) {
        this.mContext = context;
        this.mContentsArrayList = contents;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        MyHolder holder = new MyHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.mTextView.setText(mContentsArrayList.get(position).getName());
        PicassoClient.downloadImage(mContext, CloudinaryClient.getRoundCornerImage
            (mContentsArrayList.get(position).getUrl()),holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mContentsArrayList.size();
    }
}
