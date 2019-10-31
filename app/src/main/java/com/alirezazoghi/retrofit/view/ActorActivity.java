package com.alirezazoghi.retrofit.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;

import com.alirezazoghi.retrofit.app.app;
import com.alirezazoghi.retrofit.R;
import com.alirezazoghi.retrofit.databinding.ActivityActorBinding;
import com.alirezazoghi.retrofit.model.Actors;
import com.squareup.picasso.Picasso;


import de.hdodenhof.circleimageview.CircleImageView;

public class ActorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityActorBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_actor);

        Bundle extras = getIntent().getExtras();

        assert extras != null;
        Actors actors = extras.getParcelable("actors");

        if (actors != null) {
            binding.setActor(actors);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String imageTransitionName = extras.getString("transaction");
                binding.profileImage.setTransitionName(imageTransitionName);
            }

            Picasso.get()
                    .load(app.main.URL + actors.getImage())
                    .noFade()
                    .into(binding.profileImage, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            supportStartPostponedEnterTransition();
                        }

                        @Override
                        public void onError(Exception e) {
                            supportStartPostponedEnterTransition();
                        }
                    });
        }
    }
}
