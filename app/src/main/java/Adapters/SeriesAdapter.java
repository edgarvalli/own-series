package Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.ddsn.ev_server.ownseries.R;
import net.ddsn.ev_server.ownseries.Season;

import org.json.JSONArray;
import org.json.JSONException;

public class SeriesAdapter extends RecyclerView.Adapter<SeriesAdapter.ViewHolder> {

    Context ctx;
    JSONArray series;

    public SeriesAdapter(Context ctx, JSONArray series){
        this.ctx = ctx;
        this.series = series;
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
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        try {
            String title = series.getJSONObject(i).getString("serie_name");
            String url = series.getJSONObject(i).getString("image");

            Picasso.get().load(url).into(viewHolder.imageView);
            viewHolder.textView.setText(title);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        Intent intent = new Intent(ctx.getApplicationContext(), Season.class);
                        intent.putExtra("id", series.getJSONObject(i).getString("_id"));
                        intent.putExtra("image", series.getJSONObject(i).getString("image"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        return series.length();
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
