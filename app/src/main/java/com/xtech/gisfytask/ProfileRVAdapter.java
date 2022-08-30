package com.xtech.gisfytask;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProfileRVAdapter extends RecyclerView.Adapter<ProfileRVAdapter.ViewHolder> {

    private ArrayList<Profile> profileList;
    private Context context;

    public ProfileRVAdapter(ArrayList<Profile> profileList, Context context) {
        this.profileList = profileList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProfileRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_view_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Profile profile = profileList.get(position);

        holder.numberView.setText(String.valueOf(position+1));
        holder.nameView.setText(profile.getName());
        holder.classView.setText(profile.getClassName());
        holder.imageView.setImageURI(Uri.parse(profile.getImgUri()));

        holder.cardView.setOnClickListener(view -> {
            Intent profileViewIntent = new Intent(context, ViewProfileActivity.class);
            profileViewIntent.putExtra("profile", profile);
            context.startActivity(profileViewIntent);
        });

    }

    @Override
    public int getItemCount() {
        return profileList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView numberView, nameView, classView;
        private ImageView imageView;
        private CardView cardView;

        public ViewHolder(View itemView){
            super(itemView);

            numberView = itemView.findViewById(R.id.numberView);
            nameView = itemView.findViewById(R.id.nameView);
            classView = itemView.findViewById(R.id.classView);
            imageView = itemView.findViewById(R.id.imgView);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
