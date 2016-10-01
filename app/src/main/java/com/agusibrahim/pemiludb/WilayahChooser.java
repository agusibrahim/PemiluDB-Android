package com.agusibrahim.pemiludb;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import com.agusibrahim.pemiludb.Model.*;
import android.app.AlertDialog;
import com.agusibrahim.pemiludb.Adapter.*;

public class WilayahChooser
{
	Context mContext;
	public WilayahChooser(final Context ctx){
		mContext=ctx;
		View v = LayoutInflater.from(ctx).inflate(R.layout.dialog_wilayah, null);
		Spinner provinsi_selector=(Spinner) v.findViewById(R.id.prov);
		Spinner kabupaten_selector=(Spinner) v.findViewById(R.id.kab);
		Spinner kecamatan_selector=(Spinner) v.findViewById(R.id.kec);
		final Spinner kelurahan_selector=(Spinner) v.findViewById(R.id.kel);
		ArrayList<Spinner> spinners = new ArrayList<Spinner>();
		spinners.add(provinsi_selector);
		spinners.add(kabupaten_selector);
		spinners.add(kecamatan_selector);
		spinners.add(kelurahan_selector);
		spinners.add(null);
		AlertDialog.Builder dlg=new AlertDialog.Builder(ctx);
		dlg.setTitle("Pilih Wilayah");
		dlg.setView(v);
		dlg.setPositiveButton("Pilih", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface p1, int p2) {
					Wilayah wil=(Wilayah) kelurahan_selector.getItemAtPosition(kelurahan_selector.getSelectedItemPosition());
					Toast.makeText(mContext, wil.getNama()+" - "+wil.getWilayah_id(), Toast.LENGTH_LONG).show();
					MainActivity.adapter.update(wil.getParent(), wil.getWilayah_id());
				}
			});
		dlg.setNegativeButton("Batal", null);
		AlertDialog dd=dlg.create();
		dd.show();
		provinsi_selector.setAdapter(new WilayahAdapter(0));
		//provinsi_selector.setSelection((1 + Double.valueOf(Math.random()*(34-1)).intValue()));
		onSelectedHadler(spinners, dd);
	}
	private void onSelectedHadler(final List<Spinner> spinners, final AlertDialog dialog){
		for (int i=0;i < spinners.size() - 1;i++) {
			Spinner spin=spinners.get(i);
			final Spinner target=spinners.get(i + 1);

			if (target != null) target.setVisibility(View.INVISIBLE);
			spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4) {
						Wilayah wilayah=(Wilayah) p1.getItemAtPosition(p3);
						if (spinners.get(0).getSelectedItemPosition() > 0 && spinners.get(1).getSelectedItemPosition() > 0 && spinners.get(2).getSelectedItemPosition() > 0 && spinners.get(3).getSelectedItemPosition() > 0) {
							dialog.getButton(AlertDialog.BUTTON1).setEnabled(true);
						} else {
							dialog.getButton(AlertDialog.BUTTON1).setEnabled(false);
						}
						if (wilayah.getWilayah_id() == -1) return;

						if (target != null) {
							WilayahAdapter adapter=new WilayahAdapter(wilayah.getWilayah_id());
							target.setVisibility(View.VISIBLE);
							target.setAdapter(adapter);
						}
					}
					@Override
					public void onNothingSelected(AdapterView<?> p1) {
						// TODO: Implement this method
					}
				});
		} // end for
	} // end funct
	
	
	public class WilayahAdapter extends ArrayAdapter<Wilayah>{
		public WilayahAdapter(int wilayah_id){
			super(mContext, 0, Wilayah.getData(mContext, wilayah_id));
		}
		public class Holder{
			TextView nama_wilayah;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final Wilayah wilayah=getItem(position);
			Holder holder;
			if(convertView==null){
				holder=new Holder();
				convertView=LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);
				holder.nama_wilayah=(TextView) convertView.findViewById(android.R.id.text1);
				convertView.setTag(holder);
			}else{
				holder=(WilayahAdapter.Holder) convertView.getTag();
			}
			holder.nama_wilayah.setText(wilayah.getNama());
			return convertView;
		}

		@Override
		public View getDropDownView(int position, View convertView, ViewGroup parent) {
			return getView(position, convertView, parent);
		}
	}
}
