package com.example.prueba_bq;

import java.util.Date;

import android.graphics.Bitmap;

public class Epub {
	
	private String titulo, autor;
	Date fecha;
	Bitmap portada;
	
	public Epub(String titulo, String autor, Date fecha, Bitmap portada) {
		super();
		this.titulo = titulo;
		this.autor = autor;
		this.fecha = fecha;
		this.portada = portada;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public String getAutor() {
		return autor;
	}
	public Date getFecha() {
		return fecha;
	}
	public Bitmap getPortada() {
		return portada;
	}	
}
