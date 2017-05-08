package hk.com.sunnyng.androidokhttpdemo;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "OkHttp";
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView);
    }

    public void loadContent(View v) {
        MyAsyncTask task = new MyAsyncTask();
        task.execute("https://www.als.ogcio.gov.hk/lookup?q=fortress%20tower");
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String dataString = "";
            try {
                URL url = new URL(params[0]);
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();
                dataString = response.body().string();

            }
            catch (Exception e) {
                Log.e(TAG, "loadContent: " + e.getMessage());
                e.printStackTrace();
            }
            return dataString;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tv.append("Start OkHttp loading page ... \n");
            tv.append("============================= \n");

        }

        @Override
        protected void onPostExecute(String dataString) {
            super.onPostExecute(dataString);
            tv.append(dataString);
            tv.append("\n=============================");
            tv.append("\nFinished OkHttp loading page!\n\n");
        }

    }

}
