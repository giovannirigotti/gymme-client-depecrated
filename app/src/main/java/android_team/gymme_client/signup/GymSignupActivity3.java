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
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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


public class GymSignupActivity3 extends AppCompatActivity {

    @BindView(R.id.monday_opening_text_input)
    TextInputLayout monday_opening_text_input;
    @BindView(R.id.monday_opening_edit_text)
    EditText monday_opening_edit_text;
    @BindView(R.id.monday_closing_text_input)
    TextInputLayout monday_closing_text_input;
    @BindView(R.id.monday_closing_edit_text)
    EditText monday_closing_edit_text;
    @BindView(R.id.tuesday_opening_text_input)
    TextInputLayout tuesday_opening_text_input;
    @BindView(R.id.tuesday_opening_edit_text)
    EditText tuesday_opening_edit_text;
    @BindView(R.id.tuesday_closing_text_input)
    TextInputLayout tuesday_closing_text_input;
    @BindView(R.id.tuesday_closing_edit_text)
    EditText tuesday_closing_edit_text;
    @BindView(R.id.wednesday_opening_text_input)
    TextInputLayout wednesday_opening_text_input;
    @BindView(R.id.wednesday_opening_edit_text)
    EditText wednesday_opening_edit_text;
    @BindView(R.id.wednesday_closing_text_input)
    TextInputLayout wednesday_closing_text_input;
    @BindView(R.id.wednesday_closing_edit_text)
    EditText wednesday_closing_edit_text;
    @BindView(R.id.thursday_opening_text_input)
    TextInputLayout thursday_opening_text_input;
    @BindView(R.id.thursday_opening_edit_text)
    EditText thursday_opening_edit_text;
    @BindView(R.id.thursday_closing_text_input)
    TextInputLayout thursday_closing_text_input;
    @BindView(R.id.thursday_closing_edit_text)
    EditText thursday_closing_edit_text;
    @BindView(R.id.friday_opening_text_input)
    TextInputLayout friday_opening_text_input;
    @BindView(R.id.friday_opening_edit_text)
    EditText friday_opening_edit_text;
    @BindView(R.id.friday_closing_text_input)
    TextInputLayout friday_closing_text_input;
    @BindView(R.id.friday_closing_edit_text)
    EditText friday_closing_edit_text;
    @BindView(R.id.saturday_opening_text_input)
    TextInputLayout saturday_opening_text_input;
    @BindView(R.id.saturday_opening_edit_text)
    EditText saturday_opening_edit_text;
    @BindView(R.id.saturday_closing_text_input)
    TextInputLayout saturday_closing_text_input;
    @BindView(R.id.saturday_closing_edit_text)
    EditText saturday_closing_edit_text;
    @BindView(R.id.sunday_opening_text_input)
    TextInputLayout sunday_opening_text_input;
    @BindView(R.id.sunday_opening_edit_text)
    EditText sunday_opening_edit_text;
    @BindView(R.id.sunday_closing_text_input)
    TextInputLayout sunday_closing_text_input;
    @BindView(R.id.sunday_closing_edit_text)
    EditText sunday_closing_edit_text;

    @BindView(R.id.signup_button_final_gym)
    Button signup_button_final_gym;

    @BindView(R.id.progressBarSignupGym3)
    ProgressBar progressBarSignup3Gym;

    int user_id;
    String email;
    String vat_number;
    String gym_name;
    String gym_address;
    String zip_code;


    int opening_monday;
    int closing_monday;
    int opening_tuesday;
    int closing_tuesday;
    int opening_wednesday;
    int closing_wednesday;
    int opening_thursday;
    int closing_thursday;
    int opening_friday;
    int closing_friday;
    int opening_saturday;
    int closing_saturday;
    int opening_sunday;
    int closing_sunday;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_gym_3);

        ButterKnife.bind(this);
        user_id = getIntent().getIntExtra("user_id", -1);
        email = getIntent().getStringExtra("email");
        vat_number = getIntent().getStringExtra("vat_number");
        gym_name = getIntent().getStringExtra("gym_name");
        gym_address = getIntent().getStringExtra("gym_address");
        zip_code = getIntent().getStringExtra("zip_code");
        final String pool = Integer.toString(getIntent().getBooleanExtra("pool", false) ? 1 : 0);
        final String boxRing = Integer.toString(getIntent().getBooleanExtra("box_ring", false) ? 1 : 0);
        final String aerobics = Integer.toString(getIntent().getBooleanExtra("aerobics", false) ? 1 : 0);
        final String spa = Integer.toString(getIntent().getBooleanExtra("spa", false) ? 1 : 0);
        final String wifi = Integer.toString(getIntent().getBooleanExtra("wifi", false) ? 1 : 0);
        final String parkingArea = Integer.toString(getIntent().getBooleanExtra("parking_area", false) ? 1 : 0);
        final String personalTrainer = Integer.toString(getIntent().getBooleanExtra("personal_trainer", false) ? 1 : 0);
        final String nutritionist = Integer.toString(getIntent().getBooleanExtra("nutritionist", false) ? 1 : 0);
        final String impedanceBalance = Integer.toString(getIntent().getBooleanExtra("impedance_balance", false) ? 1 : 0);
        final String courses = Integer.toString(getIntent().getBooleanExtra("courses", false) ? 1 : 0);
        final String showers = Integer.toString(getIntent().getBooleanExtra("showers", false) ? 1 : 0);


        signup_button_final_gym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkFormat();
                if (checkFormat()) {
                    getHours();
                }

                new RegisterGymConnection().execute(Integer.toString(user_id), vat_number, gym_name, gym_address, zip_code,
                        pool, boxRing, aerobics, spa, wifi, parkingArea, personalTrainer, nutritionist, impedanceBalance, courses, showers);
            }
        });
    }

    private class RegisterGymConnection extends AsyncTask<String, String, Integer> {
        String toastMessage = null;

        @Override
        protected void onPreExecute() {
            signup_button_final_gym.setEnabled(false);
            signup_button_final_gym.setVisibility(View.GONE);
            progressBarSignup3Gym.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            URL url;
            HttpURLConnection urlConnection = null;
            JsonObject user = null;
            int responseCode = 500;
            try {
                url = new URL("http://10.0.2.2:4000/register/gym");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setConnectTimeout(5000);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                JsonObject paramsJson = new JsonObject();
                paramsJson.addProperty("user_id", params[0]);
                paramsJson.addProperty("vat_number", params[1]);
                paramsJson.addProperty("gym_name", params[2]);
                paramsJson.addProperty("gym_address", params[3]);
                paramsJson.addProperty("zip_code", params[4]);
                paramsJson.addProperty("pool", params[5]);
                paramsJson.addProperty("box_ring", params[6]);
                paramsJson.addProperty("aerobics", params[7]);
                paramsJson.addProperty("spa", params[8]);
                paramsJson.addProperty("wifi", params[9]);
                paramsJson.addProperty("parking_area", params[10]);
                paramsJson.addProperty("personal_trainer_service", params[11]);
                paramsJson.addProperty("nutritionist_service", params[12]);
                paramsJson.addProperty("impedance_balance", params[13]);
                paramsJson.addProperty("courses", params[14]);
                paramsJson.addProperty("showers", params[15]);


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

            signup_button_final_gym.setEnabled(true);
            signup_button_final_gym.setVisibility(View.VISIBLE);
            progressBarSignup3Gym.setVisibility(View.GONE);
        }
    }

    private void getHours() {
        if (!monday_opening_edit_text.getText().toString().isEmpty()) {
            opening_monday = toMins(monday_opening_edit_text.getText().toString());
        }
        if (!monday_closing_edit_text.getText().toString().isEmpty()) {
            closing_monday = toMins(monday_closing_edit_text.getText().toString());
        }
        if (!tuesday_opening_edit_text.getText().toString().isEmpty()) {
            opening_tuesday = toMins(tuesday_opening_edit_text.getText().toString());
        }
        if (!tuesday_closing_edit_text.getText().toString().isEmpty()) {
            closing_tuesday = toMins(tuesday_closing_edit_text.getText().toString());
        }
        if (!wednesday_opening_edit_text.getText().toString().isEmpty()) {
            opening_wednesday = toMins(wednesday_opening_edit_text.getText().toString());
        }
        if (!wednesday_closing_edit_text.getText().toString().isEmpty()) {
            closing_wednesday = toMins(wednesday_closing_edit_text.getText().toString());
        }
        if (!thursday_opening_edit_text.getText().toString().isEmpty()) {
            opening_thursday = toMins(thursday_opening_edit_text.getText().toString());
        }
        if (!thursday_closing_edit_text.getText().toString().isEmpty()) {
            closing_thursday = toMins(thursday_closing_edit_text.getText().toString());
        }
        if (!friday_opening_edit_text.getText().toString().isEmpty()) {
            opening_friday = toMins(friday_opening_edit_text.getText().toString());
        }
        if (!friday_closing_edit_text.getText().toString().isEmpty()) {
            closing_friday = toMins(friday_closing_edit_text.getText().toString());
        }
        if (!saturday_opening_edit_text.getText().toString().isEmpty()) {
            opening_saturday = toMins(saturday_opening_edit_text.getText().toString());
        }
        if (!saturday_closing_edit_text.getText().toString().isEmpty()) {
            closing_saturday = toMins(saturday_closing_edit_text.getText().toString());
        }
        if (!sunday_opening_edit_text.getText().toString().isEmpty()) {
            opening_sunday = toMins(sunday_opening_edit_text.getText().toString());
        }
        if (!sunday_closing_edit_text.getText().toString().isEmpty()) {
            closing_sunday = toMins(sunday_closing_edit_text.getText().toString());
        }
    }

    private int toMins(String s) {

        int minutes = Integer.parseInt(s.substring(0, 1)) % 60 + Integer.parseInt(s.substring(3, 4));
        return minutes;
    }


    private boolean checkFormat() {
        boolean result = false;

        if (validateHour(monday_opening_edit_text.getText().toString()) &&
                validateHour(monday_closing_edit_text.getText().toString()) &&
                validateHour(tuesday_opening_edit_text.getText().toString()) &&
                validateHour(tuesday_closing_edit_text.getText().toString()) &&
                validateHour(wednesday_opening_edit_text.getText().toString()) &&
                validateHour(wednesday_closing_edit_text.getText().toString()) &&
                validateHour(thursday_opening_edit_text.getText().toString()) &&
                validateHour(thursday_closing_edit_text.getText().toString()) &&
                validateHour(friday_opening_edit_text.getText().toString()) &&
                validateHour(friday_closing_edit_text.getText().toString()) &&
                validateHour(saturday_opening_edit_text.getText().toString()) &&
                validateHour(saturday_closing_edit_text.getText().toString()) &&
                validateHour(sunday_opening_edit_text.getText().toString()) &&
                validateHour(sunday_closing_edit_text.getText().toString())
        ) {
            result = true;
        } else {
            if (!validateHour(monday_opening_edit_text.getText().toString())) {
                monday_opening_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                monday_opening_text_input.setHint("Formato invalido!");
            }

            if (!validateHour(monday_closing_edit_text.getText().toString())) {
                monday_closing_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                monday_closing_text_input.setHint("Formato invalido!");
            }
            if (!validateHour(tuesday_opening_edit_text.getText().toString())) {
                tuesday_opening_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                tuesday_opening_text_input.setHint("Formato invalido!");
            }
            if (!validateHour(tuesday_closing_edit_text.getText().toString())) {
                tuesday_closing_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                tuesday_closing_text_input.setHint("Formato invalido!");
            }
            if (!validateHour(wednesday_opening_edit_text.getText().toString())) {
                wednesday_opening_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                wednesday_opening_text_input.setHint("Formato invalido!");
            }
            if (!validateHour(wednesday_closing_edit_text.getText().toString())) {
                wednesday_closing_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                wednesday_closing_text_input.setHint("Formato invalido!");
            }
            if (!validateHour(tuesday_opening_edit_text.getText().toString())) {
                tuesday_opening_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                tuesday_opening_text_input.setHint("Formato invalido!");
            }
            if (!validateHour(tuesday_closing_edit_text.getText().toString())) {
                tuesday_closing_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                tuesday_closing_text_input.setHint("Formato invalido!");
            }
            if (!validateHour(wednesday_opening_edit_text.getText().toString())) {
                wednesday_opening_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                wednesday_opening_text_input.setHint("Formato invalido!");
            }
            if (!validateHour(wednesday_closing_edit_text.getText().toString())) {
                wednesday_closing_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                wednesday_closing_text_input.setHint("Formato invalido!");
            }
            if (!validateHour(thursday_opening_edit_text.getText().toString())) {
                thursday_opening_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                thursday_opening_text_input.setHint("Formato invalido!");
            }
            if (!validateHour(thursday_closing_edit_text.getText().toString())) {
                thursday_closing_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                thursday_closing_text_input.setHint("Formato invalido!");
            }
            if (!validateHour(friday_opening_edit_text.getText().toString())) {
                friday_opening_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                friday_opening_text_input.setHint("Formato invalido!");
            }
            if (!validateHour(friday_closing_edit_text.getText().toString())) {
                friday_closing_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                friday_closing_text_input.setHint("Formato invalido!");
            }
            if (!validateHour(saturday_opening_edit_text.getText().toString())) {
                saturday_opening_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                saturday_opening_text_input.setHint("Formato invalido!");
            }
            if (!validateHour(saturday_closing_edit_text.getText().toString())) {
                saturday_closing_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                saturday_closing_text_input.setHint("Formato invalido!");
            }
            if (!validateHour(sunday_opening_edit_text.getText().toString())) {
                sunday_opening_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                sunday_opening_text_input.setHint("Formato invalido!");
            }
            if (!validateHour(sunday_closing_edit_text.getText().toString())) {
                sunday_closing_text_input.setHintTextColor(ColorStateList.valueOf(Color.parseColor("#fa8282")));
                sunday_closing_text_input.setHint("Formato invalido!");
            }

            result = false;
        }
        return result;
    }

    private boolean validateHour(String hour) {
        String regex = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$\\n";
        return hour.matches(regex) || hour.isEmpty();
    }

}
