package android_team.gymme_client.signup;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import android_team.gymme_client.R;
import android_team.gymme_client.login.LoginActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GymSignupActivity2 extends AppCompatActivity {



    int user_id;
    String email;
    String vat_number;
    String gym_name;
    String gym_address;
    String zip_code;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup_gym_2);


        user_id = getIntent().getIntExtra("user_id", -1);
        email = getIntent().getStringExtra("email");
        vat_number = getIntent().getStringExtra("vat_number");
        gym_name = getIntent().getStringExtra("gym_name");
        gym_address = getIntent().getStringExtra("gym_address");
        zip_code = getIntent().getStringExtra("zip_code");


    }
}
