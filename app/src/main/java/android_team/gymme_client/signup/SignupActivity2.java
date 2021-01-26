package android_team.gymme_client.signup;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android_team.gymme_client.R;
import android_team.gymme_client.support.Fx;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity2  extends AppCompatActivity {

    @BindView(R.id.back_to_signup1_button)
    ImageButton _back_to_signup1_button;

    @BindView(R.id.dropdown_button_gym_user)
    ImageButton _dropdown_button_gym_user;
    @BindView(R.id.signup_gym_user_info)
    LinearLayout _signup_gym_user_info;
    @BindView(R.id.signup_button_gym_user)
    Button _signup_button_gym_user;
    @BindView(R.id.progress_bar_gym_user)
    ProgressBar _progress_bar_gym_user;

    @BindView(R.id.dropdown_button_gym_trainer)
    ImageButton _dropdown_button_gym_trainer;
    @BindView(R.id.signup_gym_trainer_info)
    LinearLayout _signup_gym_trainer_info;
    @BindView(R.id.signup_button_gym_trainer)
    Button _signup_button_gym_trainer;
    @BindView(R.id.progress_bar_gym_trainer)
    ProgressBar _progress_bar_gym_trainer;

    @BindView(R.id.dropdown_button_gym_nutritionist)
    ImageButton _dropdown_button_gym_nutritionist;
    @BindView(R.id.signup_gym_nutritionist_info)
    LinearLayout _signup_gym_nutritionist_info;
    @BindView(R.id.signup_button_gym_nutritionist)
    Button _signup_button_gym_nutritionist;
    @BindView(R.id.progress_bar_gym_nutritionist)
    ProgressBar _progress_bar_gym_nutritionist;

    @BindView(R.id.dropdown_button_gym_owner)
    ImageButton _dropdown_button_gym_owner;
    @BindView(R.id.signup_gym_owner_info)
    LinearLayout _signup_gym_owner_info;
    @BindView(R.id.signup_button_gym_owner)
    Button _signup_button_gym_owner;
    @BindView(R.id.progress_bar_gym_owner)
    ProgressBar _progress_bar_gym_owner;

    private static int workload = 12;

    private String email;
    private String name;
    private String lastname;
    private String birthdate;
    private String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.signup_activity2);
        ButterKnife.bind(this);


        _back_to_signup1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        name = getIntent().getStringExtra("name");
        lastname = getIntent().getStringExtra("lastname");
        email = getIntent().getStringExtra("email");
        birthdate = getIntent().getStringExtra("birthdate");
        password = getIntent().getStringExtra("password");

        _signup_gym_user_info.setVisibility(View.GONE);
        _signup_gym_trainer_info.setVisibility(View.GONE);
        _signup_gym_nutritionist_info.setVisibility(View.GONE);
        _signup_gym_owner_info.setVisibility(View.GONE);


        _dropdown_button_gym_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdownGymUser();
            }
        });
        _dropdown_button_gym_trainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdownGymTrainer();
            }
        });
        _dropdown_button_gym_nutritionist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdownNutritionist();
            }
        });
        _dropdown_button_gym_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropdownGymOwner();
            }
        });

        /////////////Signup Listeners

        _signup_button_gym_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(name, lastname, email, birthdate, password, 0);
            }
        });
        _signup_button_gym_trainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(name, lastname, email, birthdate, password, 1);
            }
        });

        _signup_button_gym_nutritionist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(name, lastname, email, birthdate, password, 2);
            }
        });

        _signup_button_gym_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(name, lastname, email, birthdate, password, 3);
            }
        });
    }

    private void dropdownGymUser() {
        if (_signup_gym_user_info.isShown()) {
            Fx.slide_up(getApplicationContext(), _signup_gym_user_info);
            _signup_gym_user_info.setVisibility(View.GONE);
            _dropdown_button_gym_user.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
        } else {

            hideInfo(_signup_gym_trainer_info, _dropdown_button_gym_trainer);
            hideInfo(_signup_gym_nutritionist_info, _dropdown_button_gym_nutritionist);
            hideInfo(_signup_gym_owner_info, _dropdown_button_gym_owner);
            Fx.slide_down(getApplicationContext(), _signup_gym_user_info);
            _signup_gym_user_info.setVisibility(View.VISIBLE);
            _dropdown_button_gym_user.setImageResource(R.drawable.ic_arrow_drop_up_white_24dp);


        }
    }

    private void dropdownGymTrainer() {
        if (_signup_gym_trainer_info.isShown()) {
            Fx.slide_up(getApplicationContext(), _signup_gym_trainer_info);
            _signup_gym_trainer_info.setVisibility(View.GONE);
            _dropdown_button_gym_trainer.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
        } else {


            hideInfo(_signup_gym_user_info, _dropdown_button_gym_user);
            hideInfo(_signup_gym_nutritionist_info, _dropdown_button_gym_nutritionist);
            hideInfo(_signup_gym_owner_info, _dropdown_button_gym_owner);

            Fx.slide_down(getApplicationContext(), _signup_gym_trainer_info);
            _signup_gym_trainer_info.setVisibility(View.VISIBLE);
            _dropdown_button_gym_trainer.setImageResource(R.drawable.ic_arrow_drop_up_white_24dp);

        }
    }

    private void dropdownNutritionist() {
        if (_signup_gym_nutritionist_info.isShown()) {
            Fx.slide_up(getApplicationContext(), _signup_gym_nutritionist_info);
            _signup_gym_nutritionist_info.setVisibility(View.GONE);
            _dropdown_button_gym_nutritionist.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
        } else {


            hideInfo(_signup_gym_trainer_info, _dropdown_button_gym_trainer);
            hideInfo(_signup_gym_user_info, _dropdown_button_gym_user);
            hideInfo(_signup_gym_owner_info, _dropdown_button_gym_owner);

            Fx.slide_down(getApplicationContext(), _signup_gym_nutritionist_info);
            _signup_gym_nutritionist_info.setVisibility(View.VISIBLE);
            _dropdown_button_gym_nutritionist.setImageResource(R.drawable.ic_arrow_drop_up_white_24dp);

        }
    }

    private void dropdownGymOwner() {
        if (_signup_gym_owner_info.isShown()) {
            Fx.slide_up(getApplicationContext(), _signup_gym_owner_info);
            _signup_gym_owner_info.setVisibility(View.GONE);
            _dropdown_button_gym_owner.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
        } else {
            hideInfo(_signup_gym_trainer_info, _dropdown_button_gym_trainer);
            hideInfo(_signup_gym_nutritionist_info, _dropdown_button_gym_nutritionist);
            hideInfo(_signup_gym_user_info, _dropdown_button_gym_user);

            Fx.slide_down(getApplicationContext(), _signup_gym_owner_info);
            _signup_gym_owner_info.setVisibility(View.VISIBLE);
            _dropdown_button_gym_owner.setImageResource(R.drawable.ic_arrow_drop_up_white_24dp);
        }
    }

    private void hideInfo(LinearLayout layout, ImageButton button) {
        Fx.slide_up(getApplicationContext(), layout);
        layout.setVisibility(View.GONE);
        button.setImageResource(R.drawable.ic_arrow_drop_down_white_24dp);
    }

    private void disableButtons() {

        _signup_button_gym_user.setEnabled(false);
        _signup_button_gym_trainer.setEnabled(false);
        _signup_button_gym_nutritionist.setEnabled(false);
        _signup_button_gym_owner.setEnabled(false);

        _dropdown_button_gym_user.setEnabled(false);
        _dropdown_button_gym_trainer.setEnabled(false);
        _dropdown_button_gym_nutritionist.setEnabled(false);
        _dropdown_button_gym_owner.setEnabled(false);

    }

    private void startSpinner(int type) {

        if (type == 0) {

            _signup_button_gym_user.setVisibility(View.GONE);
            _progress_bar_gym_user.setVisibility(View.VISIBLE);

        } else if (type == 1) {

            _signup_button_gym_trainer.setVisibility(View.GONE);
            _progress_bar_gym_trainer.setVisibility(View.VISIBLE);

        } else if (type == 2) {

            _signup_button_gym_nutritionist.setVisibility(View.GONE);
            _progress_bar_gym_nutritionist.setVisibility(View.VISIBLE);

        } else if (type == 3) {

            _signup_button_gym_owner.setVisibility(View.GONE);
            _progress_bar_gym_owner.setVisibility(View.VISIBLE);

        }
    }

    private void stopSpinner(String toastMessage) {

        _signup_button_gym_user.setVisibility(View.VISIBLE);
        _progress_bar_gym_user.setVisibility(View.GONE);
        _signup_button_gym_user.setEnabled(true);

        _signup_button_gym_trainer.setVisibility(View.GONE);
        _progress_bar_gym_trainer.setVisibility(View.VISIBLE);
        _signup_button_gym_trainer.setEnabled(true);

        _signup_button_gym_nutritionist.setVisibility(View.GONE);
        _progress_bar_gym_nutritionist.setVisibility(View.VISIBLE);
        _signup_button_gym_nutritionist.setEnabled(true);


        _signup_button_gym_owner.setVisibility(View.GONE);
        _progress_bar_gym_owner.setVisibility(View.VISIBLE);
        _signup_button_gym_owner.setEnabled(true);

        Toast responseToast = Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_LONG);
        responseToast.show();


    }

    private void signupSuccess(){
        /*
        Intent intent = new Intent(getApplicationContext(), SignupSuccessfulActivity.class);
        startActivity(intent);
        finish();
        */
    }

    private void signUp(String name, String lastname, String email, String birthdate, String password, int type) {
        disableButtons();
        startSpinner(type);
        new Connection().execute(name, lastname, email, birthdate, password, Integer.toString(type));

    }


    private class Connection extends AsyncTask<String, String, Integer> {

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
                url = new URL("http://10.0.2.2:9869/GymMeMoreServer_war/user/signup?name=" + params[0] + "&lastName=" + params[1] + "&birthDate=" + params[3] + "&email=" + params[2] + "&password=" + params[4]+ "&type=" + params[5]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setConnectTimeout(5000);
                responseCode = urlConnection.getResponseCode();
                Log.e("response code", Integer.toString(responseCode));

                if (responseCode == HttpURLConnection.HTTP_OK) {
                } else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                    Log.e("Server response", "Error during signup!");
                    toastMessage = "Errore nella registrazione di un nuovo utente!";
                } else if (responseCode == HttpURLConnection.HTTP_CONFLICT) {
                    Log.e("Server response", "Error during signup!");
                    toastMessage = "Esiste già un account registrato con questa email!";
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
            if (responseCode == 200) {
                signupSuccess();
            } else if (responseCode == 409) {
                stopSpinner(toastMessage);

            } else if (responseCode == 500) {
                stopSpinner(toastMessage);

            } else if (responseCode == 69)
                stopSpinner(toastMessage);

        }

    }
}


