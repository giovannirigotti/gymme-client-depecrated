package android_team.gymme_client.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android_team.gymme_client.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
    }
}