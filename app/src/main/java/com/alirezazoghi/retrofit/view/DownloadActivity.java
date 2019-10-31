package com.alirezazoghi.retrofit.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alirezazoghi.retrofit.app.Application;
import com.alirezazoghi.retrofit.R;
import com.alirezazoghi.retrofit.databinding.ActivityDownloadBinding;
import com.tingyik90.snackprogressbar.SnackProgressBar;
import com.tingyik90.snackprogressbar.SnackProgressBarManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.text.DecimalFormat;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DownloadActivity extends AppCompatActivity {

    private static final String TAG = "DownloadTasks";

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1111;

    private static final String REQUEST_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final String STATIC_URL = "http://speedtest.ftp.otenet.gr/files/test10Mb.db";

    private SnackProgressBarManager snackProgressBarManager;
    private SnackProgressBar snackProgressBar;

    private Call<ResponseBody> service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this,R.layout.activity_download);
    }

    private void downloadFile(String url) {
        String fileName =url.substring(url.lastIndexOf('/') + 1, url.length());

        final String finalFileName=fileName.substring(0,fileName.lastIndexOf("."));

        service=Application.getAPI().downloadFile(url);
        service.enqueue(new Callback<ResponseBody>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onResponse(Call<ResponseBody> call, @Nullable final Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    new AsyncTask<Void, Long, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            saveToDisk(response.body(), finalFileName);
                            return null;
                        }
                    }.execute();
                } else {
                    Toast.makeText(DownloadActivity.this, "connection error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(DownloadActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveToDisk(ResponseBody body, String fileName) {

        int checkVal = checkCallingOrSelfPermission(REQUEST_PERMISSION);

        if (checkVal == PackageManager.PERMISSION_GRANTED) {
            try {
                File destinationFile = Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS);
                if (!destinationFile.exists()) {
                    destinationFile.mkdir();
                }

                File outPutFile = new File(destinationFile, fileName + "." + body.contentType().subtype());
                if (!outPutFile.exists()) {
                    outPutFile.createNewFile();

                }

                try (InputStream is = body.byteStream(); OutputStream os = new FileOutputStream(outPutFile)) {

                    byte[] data = new byte[4096];
                    int count;
                    int progress = 0;
                    final int fileSize = (Math.round((body.contentLength() / (1024 * 1024)) * 10) / 10);

                    makeSnackBar();

                    while ((count = is.read(data)) != -1) {
                        os.write(data, 0, count);

                        progress += count;

                        final int finalProgress = progress;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                snackProgressBarManager.setProgress((Math.round(finalProgress / (1024 * 1024)) * 100 / fileSize));
                            }
                        });
                    }

                    os.flush();

                    Log.e(TAG, "File saved successfully!");
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "Failed to save the file!");
                    return;
                } finally {
                    snackProgressBarManager.dismiss();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Failed to save the file!");
                return;
            }
        } else {
            getPermission();
        }
    }

    private void makeSnackBar() {
        snackProgressBarManager = new SnackProgressBarManager(findViewById(R.id.main), DownloadActivity.this);
        snackProgressBarManager
                .setProgressBarColor(R.color.colorAccent)
                .setBackgroundColor(SnackProgressBarManager.BACKGROUND_COLOR_DEFAULT)
                .setTextSize(14f)
                .setMessageMaxLines(2);

        snackProgressBar = new SnackProgressBar(SnackProgressBar.TYPE_CIRCULAR, "Loading...")
                .setIsIndeterminate(false)
                .setProgressMax(100)
                .setShowProgressPercentage(true)
                .setAction("cancel", new SnackProgressBar.OnActionClickListener() {
                    @Override
                    public void onActionClick() {
                        if (service != null) {
                            service.cancel();
                            Toast.makeText(DownloadActivity.this, "download cancelled !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                snackProgressBarManager.show(snackProgressBar, SnackProgressBarManager.LENGTH_INDEFINITE);
            }
        });

    }

    public String getFileSize(long size) {
        if (size <= 0)
            return "0";

        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));

        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    private void getDownloadManager() {

        File file = new File(getExternalFilesDir(null), "Dummy");

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(STATIC_URL))
                .setTitle("Dummy File")// Title of the Download Notification
                .setDescription("Downloading")// Description of the Download Notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true);
    }

    private void getPermission() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (ContextCompat.checkSelfPermission(DownloadActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(DownloadActivity.this, "you have already granted this permission!", Toast.LENGTH_SHORT).show();
                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(DownloadActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        new AlertDialog.Builder(DownloadActivity.this)
                                .setTitle("Permission needed")
                                .setMessage("this permission is needed!")
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        ActivityCompat.requestPermissions(DownloadActivity.this,
                                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                                    }
                                })
                                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        dialog.dismiss();
                                    }
                                })
                                .create();
                    } else {
                        ActivityCompat.requestPermissions(DownloadActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_PERMISSIONS_REQUEST_READ_CONTACTS);
                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void onClick(View view){
        switch (view.getId()) {
            case R.id.btn_retrofit:
                downloadFile(STATIC_URL);
                break;
            case R.id.btn_dm:
                getDownloadManager();
                break;
        }
    }
}
