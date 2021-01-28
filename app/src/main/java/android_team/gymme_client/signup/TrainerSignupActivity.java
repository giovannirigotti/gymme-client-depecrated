package android_team.gymme_client.signup;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android_team.gymme_client.R;
import android_team.gymme_client.login.LoginActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TrainerSignupActivity extends AppCompatActivity {


    @BindView(R.id.trainer_qualification_signup_text_input)
    TextInputLayout _trainer_qualification_signup_text_input;
    @BindView(R.id.trainer_qualification_signup_edit_text)
    TextInputEditText _trainer_qualification_signup_edit_text;
    @BindView(R.id.trainer_fiscal_code_signup_text_input)
    TextInputLayout _trainer_fiscal_code_signup_text_input;
    @BindView(R.id.trainer_fiscal_code_signup_edit_text)
    TextInputEditText _trainer_fiscal_code_signup_edit_text;
    @BindView(R.id.signup_button_final_trainer)
    Button _signup_button_final_trainer;

    int user_id;
    String email;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_trainer);
        ButterKnife.bind(this);

        Intent i = getIntent();
        if (!i.hasExtra("user_id")) {
            Toast.makeText(this, "Utente non creato", Toast.LENGTH_LONG).show();
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
        email = i.getStringExtra("email");
        _signup_button_final_trainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });


    }

    @Override
    public void onBackPressed() {
    }

    private void validateFields() {
        if (_trainer_qualification_signup_edit_text.getText().toString().isEmpty() || _trainer_fiscal_code_signup_edit_text.getText().toString().isEmpty()) {
            if (_trainer_qualification_signup_edit_text.getText().toString().isEmpty()) {
                _trainer_qualification_signup_text_input.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                _trainer_qualification_signup_text_input.setHint("Inserisci la tua qualifica!");
                _trainer_qualification_signup_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
            }
            if (_trainer_fiscal_code_signup_edit_text.getText().toString().isEmpty()) {
                _trainer_fiscal_code_signup_text_input.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                _trainer_fiscal_code_signup_text_input.setHint("Inserisci il tuo codice fiscale!");
                _trainer_fiscal_code_signup_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
            }
        } else {
            String qualification = _trainer_qualification_signup_edit_text.getText().toString();
            String fiscal_code = _trainer_fiscal_code_signup_edit_text.getText().toString();
            new TrainerSignupActivity.RegisterTrainerConnection().execute(Integer.toString(user_id), qualification, fiscal_code);
        }
    }

    private class RegisterTrainerConnection extends AsyncTask<String, String, Integer> {
        String toastMessage = null;

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Integer doInBackground(String... params) {
            URL url;
            HttpURLConnection urlConnection = null;
            JsonObject user = null;
            int responseCode = 500;
            try {
                url = new URL("http://10.0.2.2:4000/register/trainer");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setConnectTimeout(5000);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                JsonObject paramsJson = new JsonObject();
                paramsJson.addProperty("user_id", params[0]);
                paramsJson.addProperty("qualification", params[1]);
                paramsJson.addProperty("fiscal_code", params[2]);
                urlConnection.setDoOutput(true);
                OutputStream os = urlConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(paramsJson.toString());
                writer.flush();
                writer.close();
                os.close();
                urlConnection.connect();
                responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    i.putExtra("email", email);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    urlConnection.disconnect();
                    toastMessage = "Dati registrati correttamente!";
                    finish();
                } else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                    Log.e("Server response", "Error during signup!");
                    toastMessage = "Errore nella registrazione di un nuovo utente!";
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
                toastMessage = "Impossibile connettersi!";
                responseCode = 69;
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return responseCode;
        }

        @Override
        protected void onPostExecute(Integer responseCode) {
            Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG).show();

        }
    }

}
