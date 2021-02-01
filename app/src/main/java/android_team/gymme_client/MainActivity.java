package android_team.gymme_client;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import android_team.gymme_client.customer.CustomerHomeActivity;
import android_team.gymme_client.local_database.local_dbmanager.DBManagerStatus;
import android_team.gymme_client.local_database.local_dbmanager.DBManagerUser;
import android_team.gymme_client.login.LoginActivity;
import android_team.gymme_client.support.NoNetworkActivity;


public class MainActivity extends AppCompatActivity {

    private DBManagerStatus dbManagerStatus = null;
    private DBManagerUser dbManagerUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbManagerStatus = new DBManagerStatus(this);
        dbManagerStatus.open();
        dbManagerUser = new DBManagerUser(this);
        dbManagerUser.open();
        Cursor statusCursor = dbManagerStatus.fetch();
        int status = statusCursor.getInt(statusCursor.getColumnIndex("status"));
        // LOGGATO = 1;
        // NON-LOGGATO = 0;
        Log.e("Status", Integer.toString(status));

        if (status == dbManagerStatus.STATUS_LOGGED) {
            Cursor userCursor = dbManagerUser.fetch();
            int type = userCursor.getInt(userCursor.getColumnIndex("type"));
            Log.e("Type", Integer.toString(type));
            dbManagerUser.close();
            dbManagerStatus.close();
            if (type == 0) { //Se è un utente "normale".
                Intent i = new Intent(getApplicationContext(), CustomerHomeActivity.class);
                startActivity(i);
                finish();
            } else if (type == 1) {
                //Se è un trainer.
            } else if (type == 2) {
                //Se è un nutrizionista.
            } else if (type == 3) {
                //Se è un proprietario.
            }

        } else {

            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            if (activeNetworkInfo == null) {
                Intent i = new Intent(getApplicationContext(), NoNetworkActivity.class);
                startActivity(i);
                finish();
            } else {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        }
    }


}

