package android_team.gymme_client.nutritionist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android_team.gymme_client.R;
import android_team.gymme_client.login.LoginActivity;

public class SignupNutritionist extends AppCompatActivity {

    EditText _qualificazione;
    EditText _codiceFiscale;
    Button _btnRegistrazione;

    Integer user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_trainer);

        _qualificazione = (EditText) findViewById(R.id.et_qualificazione);
        _codiceFiscale = (EditText) findViewById(R.id.ed_codice_fiscale);
        _btnRegistrazione = (Button) findViewById(R.id.btn_end_registrazione);

        // region CHECK INTENT
        Intent i = getIntent();
        if (!i.hasExtra("user_id")) {
            Toast.makeText(this, "User non creato", Toast.LENGTH_LONG).show();
            Intent new_i = new Intent(this, LoginActivity.class);
            startActivity(new_i);
        } else {
            user_id = i.getIntExtra("user_id", -1);
            Log.w("user_id ricevuto:", String.valueOf(user_id));
            if(user_id == -1){
                Toast.makeText(this, "User non creato.", Toast.LENGTH_LONG).show();
                Intent new_i = new Intent(this, LoginActivity.class);
                startActivity(new_i);
            }
        }
        // endregion //

        _btnRegistrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Controllo inserimento dati
                if(checkData()){ //DATI OK
                    String codice_fiscale = String.valueOf(_codiceFiscale.getText());
                    String qualificazione = String.valueOf(_qualificazione.getText());
                    add_trainer(user_id, qualificazione, codice_fiscale);
                }
            }
        });
    }

    private void add_trainer(Integer user_id, String qualificazione, String codice_fiscale) {

    }


    private boolean checkData(){
        if (_qualificazione.getText().length() == 0 || _codiceFiscale.getText().length() == 0){
            Toast.makeText(this, "Inserisci una qualificazione e il tuo codice fiscale", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }


}
