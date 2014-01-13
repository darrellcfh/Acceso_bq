package com.example.prueba_bq;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Util {
	public static void mostrarAlerta(Context context, String titulo, String descripcion){
		new AlertDialog.Builder(context).setTitle(titulo)
    	.setMessage(descripcion)
    	.setPositiveButton("Volver", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
                return;
            }
         }).show();
	}
}
