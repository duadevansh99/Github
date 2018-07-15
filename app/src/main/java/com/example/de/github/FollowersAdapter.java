package com.example.de.github;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FollowersAdapter extends ArrayAdapter {

    ArrayList<Follower> objects=new ArrayList<>();
    LayoutInflater inflater;

    public FollowersAdapter(@NonNull Context context, ArrayList<Follower> objects ) {
        super(context,0, objects);
        this.objects=objects;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View output=convertView;
        if(output==null){
            output=inflater.inflate(R.layout.followers_row_layout,parent,false);
            TextView name=output.findViewById(R.id.textViewFollowers);
            ImageView dp=output.findViewById(R.id.dpFollowers);
            FollowersViewHolder viewHolder=new FollowersViewHolder();
            viewHolder.name=name;
            viewHolder.dp=dp;
            output.setTag(output);
        }
        FollowersViewHolder viewHolder=(FollowersViewHolder) output.getTag();
        Follower follower=objects.get(position);
        viewHolder.name.setText(follower.name);
        Picasso.get().load(follower.dp).into(viewHolder.dp);
        return  output;
    }
}
