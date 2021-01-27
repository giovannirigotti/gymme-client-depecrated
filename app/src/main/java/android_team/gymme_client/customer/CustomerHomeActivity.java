package android_team.gymme_client.customer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android_team.gymme_client.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerHomeActivity extends AppCompatActivity {


    @BindView(R.id.send_notification_button)
    Button _send_notification_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_customer_home);

        ButterKnife.bind(this);
        _send_notification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    send_notification();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void send_notification() throws IOException {
        String notification_type = "1";
        String text = "notifica";
        String user_id = "1";
        new SendNotification().execute(notification_type, text, user_id);
    }


    private class SendNotification extends AsyncTask<String, String, JsonObject> {

        String toastMessage = null;


        @Override
        protected JsonObject doInBackground(String... params) {

            Integer params0 = Integer.parseInt(params[0]);
            Integer params2 = Integer.parseInt(params[2]);


            URL url;
            HttpURLConnection urlConnection = null;
            JsonObject notification = null;

            try {
                url = new URL("http://10.0.2.2:4000/insert_notifications/");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setConnectTimeout(5000);
                urlConnection.setRequestProperty("Content-Type", "application/json");

                JsonObject paramsJson = new JsonObject();


                paramsJson.addProperty("notification_type", params0);
                paramsJson.addProperty("text", params[1]);
                paramsJson.addProperty("user_id", params2);

                Log.e("json", paramsJson.toString());

                urlConnection.setDoOutput(true);

                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(paramsJson.toString());
                writer.flush();
                writer.close();
                os.close();


                urlConnection.connect();
                Log.e("connection", "connected");

                int responseCode = urlConnection.getResponseCode();
                Log.e("response code", Integer.toString(responseCode));

                String responseMessage = urlConnection.getResponseMessage();

                urlConnection.disconnect();


            } catch (IOException e) {
                e.printStackTrace();
                toastMessage = "Impossibile inviare la notifica!";
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return notification;
        }

    }
}