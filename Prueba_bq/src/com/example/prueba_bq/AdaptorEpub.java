package com.example.prueba_bq;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdaptorEpub extends BaseAdapter{
	
	private LayoutInflater li;
	private List<Epub> epubs = new ArrayList<Epub>();
	
	public AdaptorEpub(Context context, List<Epub> listaEpubs) {
		li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (listaEpubs != null) {
			epubs = listaEpubs;
		}
	}
	
	@Override
	public View getView(int posicion, View view, ViewGroup parent) {
		// Se añade el libro actual al listado
		Epub epub = epubs.get(posicion);
		if (view == null) {
			view = li.inflate(com.example.prueba_bq.R.layout.item_biblioteca, parent, false);
		}
		
		TextView titulo = (TextView) view.findViewById(R.id.titulo);
		titulo.setText(epub.getTitulo()); 
		TextView autor = (TextView) view.findViewById(R.id.autor);
		autor.setText(epub.getAutor()); 
		TextView fecha = (TextView) view.findViewById(R.id.fecha);
		fecha.setText(epub.getFecha().toString());
		ImageView portada = (ImageView) view.findViewById(R.id.portada);
		if(epub.getPortada() != null)
			portada.setImageBitmap(epub.getPortada());
		else
			portada.setImageResource(R.drawable.icon);
		
		return view;
	}
	
	@Override
	public int getCount() {
		return epubs.size();
	}
	@Override
	public Object getItem(int arg0) {
		return epubs.get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		return arg0;
	}
}
