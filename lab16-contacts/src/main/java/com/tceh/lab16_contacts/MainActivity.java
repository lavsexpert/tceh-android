package com.tceh.lab16_contacts;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    SimpleCursorAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ListView listView = getListView();
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        String[] fromColumns = new String[] {"display_name"};
        int[] toLayoutIDs = new int[] {R.id.text};
        listAdapter = new SimpleCursorAdapter(this, R.layout.list_item, cursor, fromColumns, toLayoutIDs);
        setListAdapter(listAdapter);

        // Подключаем менеджер асинхронного загрузчика
        getLoaderManager().initLoader(0, null, this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                CharSequence text = ((TextView) v).getText();
                int duration = Toast.LENGTH_LONG;
                Context context = getApplicationContext();
                Toast.makeText(context, text, duration).show();
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle arg){
        Log.w("MyLog", "onCreateLoader()");
        Toast.makeText(this, "nCreateLoader()", Toast.LENGTH_LONG);

        return new CursorLoader(this, ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.w("MyLog", "onLoadFinished()");
        Toast.makeText(this, "onLoadFinished()", Toast.LENGTH_LONG);
        listAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.w("MyLog", "onLoaderReset()");
        Toast.makeText(this, "onLoaderReset()", Toast.LENGTH_LONG);
        listAdapter.swapCursor(null);
    }
}

