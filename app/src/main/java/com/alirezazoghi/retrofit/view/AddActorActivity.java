package com.alirezazoghi.retrofit.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alirezazoghi.retrofit.app.Application;
import com.alirezazoghi.retrofit.app.app;
import com.alirezazoghi.retrofit.R;
import com.alirezazoghi.retrofit.databinding.ActivityAddActorBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActorActivity extends AppCompatActivity {

    private Bitmap bitmap;
    private static final int IMG_REQUEST = 666;
    private ActivityAddActorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_actor);

        binding.ivActor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        binding.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.tvName.getText() != null && binding.tvLastName.getText() != null && binding.tvAge.getText() != null && binding.tvDescription.getText() != null &&
                        binding.ivActor.getResources() != null) {
                    uploadData();
                }
            }
        });

    }

    private void uploadData() {

        Application.getAPI().upload(binding.tvName.getText().toString().trim(), binding.tvLastName.getText().toString().trim(), binding.tvAge.getText().toString().trim(), imageToString(), binding.tvDescription.getText().toString().trim()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    app.l(response.toString());
                    finish();
                } else {
                    Toast.makeText(AddActorActivity.this, "error on save actor", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                app.l(call.toString());
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                binding.ivActor.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }
}
