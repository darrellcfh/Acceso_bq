package com.example.prueba_bq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.dropbox.sync.android.DbxAccountManager;

public class MainActivity extends Activity {

	private final String DEBUG = "DEBUG";
	
	// Claves obtenidas al registrar la app en Dropbox
	private final String APP_KEY = "k1xxmwyygrn5yqx";
	private final String APP_SECRET = "s96ot1y1ibe8rgv";
	private final int REQUEST_LINK_TO_DBX = 0; 
	
	private DbxAccountManager mDbxAcctMgr;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(), APP_KEY, APP_SECRET);
        setContentView(R.layout.activity_main);
    }
    
    // Metodo invocado al pulsar el boton de login
    public void loginDropbox(View view){
    	mDbxAcctMgr.startLink((Activity)this, REQUEST_LINK_TO_DBX);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LINK_TO_DBX) {
            if (resultCode == Activity.RESULT_OK) {
                Log.d(DEBUG, "Todo OK");
            } else {
                // Login fallido o cancelado
            	Log.d(DEBUG, "Login abortado/incorrecto");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
