package com.alirezazoghi.retrofit.app;

import android.util.Log;
import android.widget.Toast;

public class app {
    public static class main {
        public static final String TAG = "RETROFITSample";
        public static final String URL = "http://192.168.43.8/retrofit/";
    }

    public static void l(String massage) {
        Log.e(main.TAG, massage);
    }

    public static void t(String massage) {
        //Toast.makeText(Application.getContext(), massage, Toast.LENGTH_SHORT).show();
    }
}
