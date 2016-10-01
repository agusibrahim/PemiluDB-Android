package com.agusibrahim.pemiludb.Model;
import java.util.*;
import org.jsoup.nodes.*;
import org.jsoup.*;

public class Pemilih
{
	protected String nama;
	protected String nik;
	protected String tempatlahir;

	public Pemilih(String nama, String nik, String tempatlahir) {
		this.nama = nama;
		this.nik = nik;
		this.tempatlahir = tempatlahir;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getNama() {
		return nama;
	}

	public void setNik(String nik) {
		this.nik = nik;
	}

	public String getNik() {
		return nik;
	}

	public void setTempatlahir(String tempatlahir) {
		this.tempatlahir = tempatlahir;
	}

	public String getTempatlahir() {
		return tempatlahir;
	}
	public static ArrayList<Pemilih> getData(String docdata){
		ArrayList<Pemilih> users=new ArrayList<Pemilih>();
		try{
			Document doc=Jsoup.parse(docdata, "UTF-8");
			for(Element ee:doc.select("table > tbody tr:gt(1)")){
				String nik=ee.select("td:eq(1)").text();
				String nama=ee.select("td:eq(2)").text();
				String tempatlahir=ee.select("td:eq(3)").text();
				Pemilih user=new Pemilih(nama, nik, tempatlahir);
				if(nama.length()>2) users.add(user);
			}

		}catch(Exception erro){

		}
		return users;
	}
}
