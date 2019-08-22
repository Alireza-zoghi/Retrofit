package com.alirezazoghi.retrofit.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.alirezazoghi.retrofit.R;
import com.alirezazoghi.retrofit.App.ActorActivity;
import com.alirezazoghi.retrofit.App.app;
import com.alirezazoghi.retrofit.Model.Actors;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActorsAdapter extends RecyclerView.Adapter<ActorsAdapter.MyViewHolder> {
    private AppCompatActivity activity;
    private List<Actors> list;

    public ActorsAdapter(AppCompatActivity activity, List<Actors> list) {
        this.activity = activity;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.acters_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Actors actors = list.get(position);
        holder.name.setText(actors.getFname() + " " + actors.getLname());
        holder.description.setText(actors.getStatus());
        holder.age.setText(actors.getAge()+"");
        Picasso.get().load(app.main.URL + actors.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView name, age, description;
        CircleImageView image;
        RelativeLayout parent;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            description = itemView.findViewById(R.id.description);
            image = itemView.findViewById(R.id.profileImage);
            parent = itemView.findViewById(R.id.parent);
            parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(activity, ActorActivity.class);
                    intent.putExtra("id",list.get(getAdapterPosition()).getId());
                    activity.startActivity(intent);
                }
            });
        }
    }
}
