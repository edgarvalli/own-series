package Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import net.ddsn.ev_server.ownseries.R;
import net.ddsn.ev_server.ownseries.Season;
import net.ddsn.ev_server.ownseries.ShowVideo;

import org.json.JSONArray;
import org.json.JSONException;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {

    Context ctx;
    JSONArray chapters;

    public ChapterAdapter(Context ctx, JSONArray chapters){
        this.ctx = ctx;
        this.chapters = chapters;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        try {
            String title = Integer.toString(i + 1) + ". "+ chapters.getJSONObject(i).getString("name");
            String url = chapters.getJSONObject(i).getString("image");
            Picasso.get().load(url).into(viewHolder.imageView);
            viewHolder.textView.setText(title);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        Intent intent = new Intent(ctx.getApplicationContext(), ShowVideo.class);
                        intent.putExtra("url", chapters.getJSONObject(i).getString("url"));
                        ctx.startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return chapters.length();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.list_item_title);
            this.imageView = itemView.findViewById(R.id.list_item_image);
        }
    }
}