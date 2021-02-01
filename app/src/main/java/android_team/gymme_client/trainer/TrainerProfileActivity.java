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
    import android_team.gymme_client.customer.CustomerProfileActivity;
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
            GetDataSetView(user_id);

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

        private void GetDataSetView(int user_id) {
            //User Data
            new TrainerProfileActivity.GetUserDataConnection().execute(String.valueOf(user_id));
        }

        private class GetUserDataConnection extends AsyncTask<String, String, JsonObject> {

            @Override
            protected JsonObject doInBackground(String... params) {
                URL url;
                HttpURLConnection urlConnection = null;
                JsonObject user = null;

                try {
                    url = new URL("http://10.0.2.2:4000/user/get_all_data/" + params[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.connect();
                    int responseCode = urlConnection.getResponseCode();
                    urlConnection.disconnect();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Log.e("Server response", "HTTP_OK");
                        String responseString = readStream(urlConnection.getInputStream());
                        //Log.e("Server user", responseString);
                        user = JsonParser.parseString(responseString).getAsJsonObject();
                        nome = user.get("name").getAsString();
                        cognome = user.get("lastname").getAsString();
                        email = user.get("email").getAsString();
                        nascita = user.get("birthdate").getAsString();
                        _tv_trainer_profile_nome.setText(nome);
                        _tv_trainer_profile_cognome.setText(cognome);
                        _tv_trainer_profile_email.setText(email);
                        _tv_trainer_profile_nascita.setText(nascita.split("T")[0]);

                    } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                        Log.e("Server response", "HTTP_NOT_FOUND");
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
                return user;
            }

            private String readStream(InputStream in) throws UnsupportedEncodingException {
                BufferedReader reader = null;
                StringBuffer response = new StringBuffer();
                try {
                    reader = new BufferedReader(new InputStreamReader(in));
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return response.toString();
            }
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