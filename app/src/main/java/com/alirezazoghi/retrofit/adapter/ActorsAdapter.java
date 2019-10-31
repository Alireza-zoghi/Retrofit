package com.alirezazoghi.retrofit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alirezazoghi.retrofit.R;
import com.alirezazoghi.retrofit.app.app;
import com.alirezazoghi.retrofit.databinding.ActersListBinding;
import com.alirezazoghi.retrofit.model.Actors;
import com.alirezazoghi.retrofit.tools.onRecyclerViewItemClick;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ActorsAdapter extends RecyclerView.Adapter<ActorsAdapter.MyViewHolder> {
    private List<Actors> list=new ArrayList<>();
    private onRecyclerViewItemClick itemClickListener;
    private LayoutInflater layoutInflater;

    public ActorsAdapter(onRecyclerViewItemClick itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void Submit(List<Actors> actors) {
        list.addAll(actors);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());

        ActersListBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.acters_list, parent, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Actors actors = list.get(position);

        holder.binding.setActors(actors);
        Picasso.get().load(app.main.URL + actors.getImage()).into(holder.binding.profileImage);
        holder.binding.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClick(actors, holder.binding.profileImage);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ActersListBinding binding;

        MyViewHolder(ActersListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
