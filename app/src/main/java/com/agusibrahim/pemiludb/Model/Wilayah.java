package com.agusibrahim.pemiludb.Model;
import java.util.*;
import android.database.*;
import android.database.sqlite.*;
import android.content.*;
import com.agusibrahim.pemiludb.*;

public class Wilayah
{
	protected String nama;
	protected int parent;
	protected int wilayah_id;
	
	public Wilayah(String nama, int parent, int wilayah_id) {
		this.nama = nama;
		this.parent = parent;
		this.wilayah_id = wilayah_id;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getNama() {
		return nama;
	}

	public void setParent(int parent) {
		this.parent = parent;
	}

	public int getParent() {
		return parent;
	}

	public void setWilayah_id(int wilayah_id) {
		this.wilayah_id = wilayah_id;
	}

	public int getWilayah_id() {
		return wilayah_id;
	}
	public static ArrayList<Wilayah> getData(Context ctx, int wilayah_id){
		SQLiteDatabase db=new AssetDatabaseOpenHelper(ctx).openDatabase();
		String sql;
		if(wilayah_id==0){
			sql="SELECT * FROM data WHERE tingkat = 1";
		}else{
			sql="SELECT * FROM data WHERE parent="+wilayah_id;
		}
		ArrayList<Wilayah> wilayahArray=new ArrayList<Wilayah>();
		wilayahArray.add(new Wilayah("Pilih...", -1, -1));
		Cursor cursor=db.rawQuery(sql, null);
		while (cursor.moveToNext()) {
			Wilayah wilayah = new Wilayah(cursor.getString(cursor.getColumnIndex("nama")), cursor.getInt(cursor.getColumnIndex("parent")), cursor.getInt(cursor.getColumnIndex("wilayah_id")));    
			wilayahArray.add(wilayah);
		}
		return wilayahArray;
	}
}
