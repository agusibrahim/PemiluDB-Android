package com.agusibrahim.pemiludb;

import android.app.*;
import android.os.*;
import java.io.*;
import android.database.sqlite.*;
import com.agusibrahim.pemiludb.Model.*;
import java.util.*;
import android.view.*;
import android.support.v7.app.*;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.support.design.widget.FloatingActionButton;
import com.agusibrahim.pemiludb.Adapter.*;
import android.support.v7.widget.*;

public class MainActivity extends AppCompatActivity 
{
	public static listAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
		((FloatingActionButton) findViewById(R.id.fab)).setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View p1) {
					new WilayahChooser(p1.getContext());
				}
			});
		myTable tabel=(myTable) findViewById(R.id.tabel);
		adapter=new listAdapter(this);
		tabel.setDataAdapter(adapter);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mainmenu, menu);
		MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView mSearchView = (SearchView) searchMenuItem.getActionView();
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
				@Override
				public boolean onQueryTextSubmit(String p1) {
					// TODO: Implement this method
					return false;
				}

				@Override
				public boolean onQueryTextChange(String p1) {
					adapter.filter(p1);
					return false;
				}
			});
        return true;
	}
}
