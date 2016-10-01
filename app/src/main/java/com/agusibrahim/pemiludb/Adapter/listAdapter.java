package com.agusibrahim.pemiludb.Adapter;
import de.codecrafters.tableview.*;
import android.view.*;
import android.content.*;
import java.util.*;
import com.agusibrahim.pemiludb.Model.*;
import android.widget.TextView;
import com.loopj.android.http.*;
import org.apache.http.*;
import android.widget.*;

public class listAdapter extends TableDataAdapter
{
	Context mContext;
	private Filter filter;
	public listAdapter(Context ctx){
		super(ctx, new ArrayList<Pemilih>());
		mContext=ctx;
	}
	@Override
	public View getCellView(int row, int column, ViewGroup p3) {
		Pemilih pemilih=(Pemilih) getRowData(row);
		if(column==0){
			return renderString(pemilih.getNama());
		}else{
			return renderString(pemilih.getTempatlahir());
		}
	}
	public void update(int parent, int wil_id){
		AsyncHttpClient client = new AsyncHttpClient();
		client.get(mContext, "https://data.kpu.go.id/ss8.php?cmd=select&grandparent="+parent+"&parent="+wil_id, new TextHttpResponseHandler(){
				@Override
				public void onFailure(int p1, Header[] p2, String err, Throwable p4) {
					Toast.makeText(mContext, "Connection failed, "+err, Toast.LENGTH_LONG).show();
				}

				@Override
				public void onSuccess(int p1, Header[] p2, String data) {
					ArrayList<Pemilih> bundleData=Pemilih.getData(data);
					Toast.makeText(mContext, ""+bundleData.get(2).getNama(), Toast.LENGTH_LONG).show();
					//getData().set(0,bundleData);
					getData().clear();
					getData().addAll(bundleData);
					notifyDataSetChanged();
				}
			});
	}
	@Override
	public Filter getFilter() {
		if (filter == null)
			filter = new FilterNama(getData());
		return filter;
	}

	public void filter(String kw){
		getFilter().filter(kw);
	}
	private View renderString(final String value) {
        final TextView textView = new TextView(getContext());
        textView.setText(value);
        textView.setPadding(20, 30, 20, 30);
        textView.setTextSize(14);
        return textView;
    }
	
	// Custom filter taken from https://gist.github.com/tobiasschuerg/3554252
	private class FilterNama extends Filter {

		private ArrayList<Pemilih> sourceObjects;

		public FilterNama(List<Pemilih> objects) {
			sourceObjects = new ArrayList<Pemilih>();
			synchronized (this) {
				sourceObjects.addAll(objects);
			}
		}

		@Override
		protected FilterResults performFiltering(CharSequence chars) {
			String filterSeq = chars.toString().toLowerCase();
			FilterResults result = new FilterResults();
			if (filterSeq != null && filterSeq.length() > 0) {
				ArrayList<Pemilih> filter = new ArrayList<Pemilih>();

				for (Pemilih pemilih : sourceObjects) {
					// the filtering itself:
					if (pemilih.getNama().toLowerCase().contains(filterSeq))
						filter.add(pemilih);
				}
				result.count = filter.size();
				result.values = filter;
			} else {
				// add all objects
				synchronized (this) {
					result.values = sourceObjects;
					result.count = sourceObjects.size();
				}
			}
			return result;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
									  FilterResults results) {
			// NOTE: this function is *always* called from the UI thread.
			ArrayList<Pemilih> filtered = (ArrayList<Pemilih>) results.values;
			notifyDataSetChanged();
			clear();
			for (int i = 0, l = filtered.size(); i < l; i++)
				add((Pemilih) filtered.get(i));
			notifyDataSetInvalidated();
		}
	}
}
