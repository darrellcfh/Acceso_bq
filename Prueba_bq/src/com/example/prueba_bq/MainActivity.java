package com.example.prueba_bq;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dropbox.sync.android.DbxAccountManager;

public class MainActivity extends Activity {
	
	// Claves obtenidas al registrar la app en Dropbox
	public static final String APP_KEY = "k1xxmwyygrn5yqx";
	public static final String APP_SECRET = "s96ot1y1ibe8rgv";
	private final int REQUEST_LINK_TO_DBX = 0; 
	
	private DbxAccountManager mDbxAcctMgr;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(), APP_KEY, APP_SECRET);
        // Comprobamos si ya se tienen los credenciales del usuario. Si no, es necesario realizar login
        if (!mDbxAcctMgr.hasLinkedAccount())
        	setContentView(R.layout.activity_main);
        else{
        	// Usuario ya logueado. Cargamos su biblioteca.
        	cargarBiblioteca();
        }
    }
    
    // Metodo invocado al pulsar el boton de login
    public void loginDropbox(View view){
    	mDbxAcctMgr.startLink((Activity)this, REQUEST_LINK_TO_DBX);
    }
    
    // Carga de la biblioteca
    public void cargarBiblioteca(){
    	Intent biblioteca = new Intent(this, MostrarBiblioteca.class);
		startActivity(biblioteca);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LINK_TO_DBX) {
            if (resultCode == Activity.RESULT_OK) {
            	//Login correcto. Cargamos biblioteca.
            	cargarBiblioteca();
            } else {
                // Login fallido o cancelado
            	Util.mostrarAlerta(this, "Error de conexión", "Login abortado o incorrecto");
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}