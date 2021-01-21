package android_team.gymme_client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android_team.gymme_client.server_sync.HttpGetRequest;

public class MainActivity extends AppCompatActivity {

    private TextView tvServerResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvServerResponse = findViewById(R.id.label_test);
        Button contactServerButton = findViewById(R.id.btn_test_node);

        contactServerButton.setOnClickListener(onButtonClickListener);
    }

    View.OnClickListener onButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            HttpGetRequest request = new HttpGetRequest();
            request.execute();
            tvServerResponse.setText(HttpGetRequest.http_response);
        }
    };
}
