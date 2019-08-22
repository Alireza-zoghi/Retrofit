package com.alirezazoghi.retrofit.App;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.alirezazoghi.retrofit.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActorActivity extends AppCompatActivity {
    private EditText fname, lname, age, description;
    private ImageView avatar;
    private Bitmap bitmap;
    private static final int IMG_REQUEST = 666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_actor);
        fname = findViewById(R.id.tv_name);
        lname = findViewById(R.id.tv_last_name);
        age = findViewById(R.id.tv_age);
        description = findViewById(R.id.tv_description);
        Button submit = findViewById(R.id.bt_submit);
        avatar = findViewById(R.id.iv_actor);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fname.getText() != null && lname.getText() != null && age.getText() != null && description.getText() != null &&
                        avatar != null) {
                    uploadData();
                }
            }
        });

    }

    private void uploadData() {

        Application.getAPI().upload(fname.getText().toString().trim(), lname.getText().toString().trim(), age.getText().toString().trim(), imageToString(), description.getText().toString().trim()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                app.l(response.toString());
                finish();
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
                avatar.setImageBitmap(bitmap);
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
