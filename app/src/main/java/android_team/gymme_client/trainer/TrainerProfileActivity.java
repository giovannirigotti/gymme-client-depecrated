    package android_team.gymme_client.trainer;

    import android.app.Activity;
    import android.app.Dialog;
    import android.content.Intent;
    import android.graphics.Color;
    import android.graphics.drawable.ColorDrawable;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.view.Window;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;

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
    import android_team.gymme_client.local_database.local_dbmanager.DBManagerUser;
    import android_team.gymme_client.login.LoginActivity;
    import android_team.gymme_client.support.Utili;
    import butterknife.BindView;
    import butterknife.ButterKnife;

    public class TrainerProfileActivity extends AppCompatActivity {

        private int user_id;
        private DBManagerUser dbManagerUser = null;

        private String nome;
        private String cognome;
        private String email;
        private String nascita;

        private String qualifica;
        private String codice_fiscale;

        @BindView(R.id.tv_trainer_profile_nome)
        TextView _tv_trainer_profile_nome;
        @BindView(R.id.tv_trainer_profile_cognome)
        TextView _tv_trainer_profile_cognome;
        @BindView(R.id.tv_trainer_profile_nascita)
        TextView _tv_trainer_profile_nascita;
        @BindView(R.id.tv_trainer_profile_email)
        TextView _tv_trainer_profile_email;

        @BindView(R.id.tv_trainer_profile_qualifica)
        TextView _tv_trainer_profile_qualifica;
        @BindView(R.id.tv_trainer_profile_codice_fiscale)
        TextView _tv_trainer_profile_codice_fiscale;

        @BindView(R.id.btn_trainer_profile_password)
        Button _btn_trainer_profile_password;
        @BindView(R.id.btn_trainer_profile_mail)
        Button _btn_trainer_profile_mail;
        @BindView(R.id.btn_trainer_profile_qualifica)
        Button _btn_trainer_profile_qualifica;
        @BindView(R.id.btn_trainer_profile_logout)
        Button _btn_trainer_profile_logout;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_trainer_profile);
            ButterKnife.bind(this);
            dbManagerUser = new DBManagerUser(this);
            dbManagerUser.open();

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


            //Get User Data & Customer Data from DB
            //GetDataSetView(user_id);

            //Update password menegment
            _btn_trainer_profile_password.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   //ChangePassword(TrainerProfileActivity.this, user_id);
                }
            });

            //Update qualification menegment
            _btn_trainer_profile_qualifica.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ChangeDiseases(TrainerProfileActivity.this, user_id);
                }
            });

            //Update email menegment
            _btn_trainer_profile_mail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ChangeAllergies(TrainerProfileActivity.this, user_id);
                }
            });

            //Update email menegment
            _btn_trainer_profile_logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DoLogout();
                }
            });


        }



        //LOGOUT
        private void DoLogout() {
            Utili.logout(TrainerProfileActivity.this);
            Intent intent = new Intent(TrainerProfileActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }