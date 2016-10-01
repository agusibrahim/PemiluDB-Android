package com.agusibrahim.pemiludb;
import android.content.*;
import de.codecrafters.tableview.*;
import android.util.*;
import com.agusibrahim.pemiludb.Model.*;
import de.codecrafters.tableview.toolkit.*;
import android.support.v4.content.*;
import java.util.*;

public class myTable extends SortableTableView<Pemilih>
{
	public myTable(Context ctx){
		super(ctx, null);
	}
	public myTable(final Context context, final AttributeSet attributes) {
        this(context, attributes, android.R.attr.listViewStyle);
    }

    public myTable(final Context context, final AttributeSet attributes, final int styleAttributes) {
        super(context, attributes, styleAttributes);
		setColumnCount(2);
		SimpleTableHeaderAdapter simpleTableHeaderAdapter = new SimpleTableHeaderAdapter(context, "Nama", "Tempat Lahir");
		simpleTableHeaderAdapter.setTextColor(ContextCompat.getColor(context, R.color.table_header_text));
		final int rowColorEven = ContextCompat.getColor(context, R.color.table_data_row_even);
        final int rowColorOdd = ContextCompat.getColor(context, R.color.table_data_row_odd);
        setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(rowColorEven, rowColorOdd));
		setHeaderAdapter(simpleTableHeaderAdapter);
		setColumnComparator(0, new NamaComparator());
		setColumnComparator(1, new TLComparator());
	}
	private static class NamaComparator implements Comparator<Pemilih> {
        @Override
        public int compare(final Pemilih prod1, final Pemilih prod2) {
            return prod1.getNama().compareTo(prod2.getNama());
        }
    }
	private static class TLComparator implements Comparator<Pemilih> {
        @Override
        public int compare(final Pemilih prod1, final Pemilih prod2) {
            return prod1.getTempatlahir().compareTo(prod2.getTempatlahir());
        }
    }
}
