/**
 * Para la obtención de los epub se ha empleado la biblioteca de Paul Siegmann que se distribuye bajo licencia GNU Lesser General Public.
 * Para más información visitar su espacio web en http://www.siegmann.nl/epublib/android
 */

package com.example.prueba_bq;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;
import android.app.Dialog;
import android.app.ListActivity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxException.Unauthorized;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileInfo;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;

public class MostrarBiblioteca extends ListActivity implements OnItemClickListener{
	
	private List<DbxFileInfo> listaEpubsInfo;
	private List<Epub> listaEpubs;
	private DbxAccountManager mDbxAcctMgr;
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView lv = getListView();
        lv.setOnItemClickListener(this);
        mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(), MainActivity.APP_KEY, MainActivity.APP_SECRET);
        // Obtenemos el filesystem de la cuenta Dropbox. 
        DbxFileSystem dbxFs = null;
		try {
			dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
		} catch (Unauthorized e) {
			Util.mostrarAlerta(this, "Error de conexión", "Acceso no autorizado");
			e.printStackTrace();
		}
        listaEpubsInfo = new ArrayList<DbxFileInfo>();
		try {
			obtenerEpubs(DbxPath.ROOT, dbxFs);
			// Obtenemos los datos relevantes de los epubs para ser mostrados en la lista
			listaEpubs = new ArrayList<Epub>();
			for(DbxFileInfo epubInfo : listaEpubsInfo){
				// Abrimos el epub para obtener sus datos
				DbxFile epubFile = dbxFs.open(epubInfo.path);
				FileInputStream stream = epubFile.getReadStream();
				Book book = (new EpubReader()).readEpub(stream);
				
				listaEpubs.add(new Epub(
						book.getTitle(), 
						book.getMetadata().getAuthors().toString(), 
						epubInfo.modifiedTime, 
						book.getCoverImage() != null ? BitmapFactory.decodeStream(book.getCoverImage().getInputStream()) : null));
				epubFile.close();
			}
			setListAdapter(new AdaptorEpub(this, listaEpubs));
		} catch (Exception e) {
			Util.mostrarAlerta(this, "Error de conexión", "Error al conectar con Dropbox");
			e.printStackTrace();
		}	
	}
	
	// Recorre todos los directorios de la cuenta Dropbox y obtiene los ficheros *.epub
	public void obtenerEpubs(DbxPath path, DbxFileSystem dbxFs) throws DbxException{
    	List<DbxFileInfo> archivos = dbxFs.listFolder(path);
    	for(DbxFileInfo archivo : archivos) {
			if(archivo.path.getName().endsWith(".epub"))
				listaEpubsInfo.add(archivo);
			else if(archivo.isFolder)
				obtenerEpubs(archivo.path, dbxFs);
		}
    }
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Dialog imageDialog = new Dialog(this);
		imageDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		imageDialog.setContentView(getLayoutInflater().inflate(R.layout.portada_layout, null));
		ImageView portada = (ImageView) imageDialog.findViewById(R.id.portadaGrande);
		if(listaEpubs.get(position).getPortada() != null)
			portada.setImageBitmap(listaEpubs.get(position).getPortada());
		imageDialog.show();
	}
}
