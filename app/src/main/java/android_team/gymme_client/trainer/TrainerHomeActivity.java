package android_team.gymme_client.trainer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import android_team.gymme_client.R;
import android_team.gymme_client.customer.CustomerProfileActivity;
import android_team.gymme_client.login.LoginActivity;

public class TrainerHomeActivity extends AppCompatActivity {

    Button btn_profile;
    private int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_home);

        Intent i = getIntent();
        if (!i.hasExtra("user_id")) {
            Toast.makeText(this, "User_id mancante", Toast.LENGTH_LONG).show();
            Intent new_i = new Intent(this, LoginActivity.class);
            startActivity(new_i);
        } else {
            user_id = i.getIntExtra("user_id", -1);
            Log.w("user_id ricevuto:", String.valueOf(user_id));
            if (user_id == -1) {
                Toast.makeText(this, "Utente non creato.", Toast.LENGTH_LONG).show();
                Intent new_i = new Intent(this, LoginActivity.class);
                startActivity(new_i);
            }
        }

        btn_profile = (Button) findViewById(R.id.btn_trainer_home_profile);

        btn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("REDIRECT", "Trainer Profile Activity");
                Intent i = new Intent(getApplicationContext(), TrainerProfileActivity.class);
                i.putExtra("user_id", user_id);
                startActivity(i);
            }
        });

    }

}
