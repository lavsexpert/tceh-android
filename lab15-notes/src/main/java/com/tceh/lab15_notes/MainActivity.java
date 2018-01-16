package com.tceh.lab15_notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    private static final int DELETE_ID = 1;
    ListView myListView;
    DB dbText;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbText = new DB(this);
        myListView = (ListView) findViewById(R.id.myListView);
        adapter = dbText.getAdapter();
        myListView.setAdapter(adapter);
        registerForContextMenu(myListView);
    }

    @Override
    protected void onDestroy(){
        dbText.close();
        super.onDestroy();
    }

    public void onButtonClick(View v){
        EditText edit_text = (EditText)findViewById(R.id.edit_text);
        String text = edit_text.getText().toString();
        dbText.addText(text);
        //adapter.notifyDataSetChanged();
        Toast.makeText(v.getContext(), "Запись добавлена", Toast.LENGTH_LONG);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, "Удалить запись");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == DELETE_ID) {
            dbText.deleteText(item.getItemId());
            Toast.makeText(this, "Запись удалена", Toast.LENGTH_LONG);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    public class DB{

        private static final String DATABASE_NAME = "Database.db";      // Название файла БД
        private static final String DATABASE_TABLE = "Texts";           // Название таблицы БД
        private static final int DATABASE_VERSION = 1;                  // Версия БД
        private static final String KEY_ID = "_id";                     // Поле индекса
        private static final String KEY_NAME = "text";                  // Поле названия
        public static final int NAME_COLUMN = 1;                        // Индекс поля названия

        // SQL-запрос для создания БД
        private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE
                + " (" + KEY_ID + " integer primary key autoincrement, " + KEY_NAME + " text not null);";

        private SQLiteDatabase db;          // Переменная для хранения объекта БД
        private Context context;            // Контекст приложения
        private DBHelper dbHelper;          // Экземпляр вспомогательного класса для открытия и обновления БД
        private Cursor cursor;              // Основной курсор БД

        // Конструктор БД
        public DB(Context _context) {
            context = _context;
            dbHelper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
            try {
                this.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            cursor = getCursor(null, KEY_ID + " desc");
        }

        // Открытие БД
        public DB open() throws SQLException {
            try {
                db = dbHelper.getWritableDatabase();
            } catch (SQLiteException e) {
                db = dbHelper.getReadableDatabase();
            }
            return this;
        }

        // Закрытие БД
        public void close() {
            db.close();
        }

        public Cursor getCursor(String where, String order){
            // Получить все поля из таблицы, без дубликатов
            return db.query(true, DATABASE_TABLE,
                    null, where, null, null, null, order, null);
        }

        public SimpleCursorAdapter getAdapter(){
            String[] fromColumns = new String[] {KEY_NAME};
            int[] toLayoutIDs = new int[] { R.id.textView};
            return new SimpleCursorAdapter(context, R.layout.item, cursor, fromColumns, toLayoutIDs);
        }

        public String readText(long rowID){
            return cursor.getString(NAME_COLUMN);
        }

        public long addText(String text){
            ContentValues newRow = new ContentValues();
            newRow.put(KEY_NAME, text);
            long rowID = db.insert(DATABASE_TABLE, null, newRow);
            return rowID;
        }

        public void editText(int rowID, String text){
            ContentValues updatedRows = new ContentValues();
            updatedRows.put(KEY_NAME, text);
            String where = KEY_ID + "=" + rowID;
            db.update(DATABASE_TABLE, updatedRows, where, null);
        }

        public void deleteText(long rowID){
            db.delete(DATABASE_TABLE, KEY_ID + "=" + rowID, null);
        }

        // Вспомогательный класс для открытия и обновления БД
        private class DBHelper extends SQLiteOpenHelper {

            public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
                super(context, name, factory, version);
            }

            // Вызывается при необходимости создания БД
            @Override
            public void onCreate(SQLiteDatabase _db) {
                _db.execSQL(DATABASE_CREATE);
            }

            // Вызывается для обновления БД, когда текущая версия БД в приложении новее, чем у БД на диске
            @Override
            public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
                // Выдача сообщения в журнал, полезно при отладке
                Log.w("TaskDBAdapter", "Upgrading from version "
                        + _oldVersion + " to " + _newVersion + ", which will destroy all old data");
                // Обновляем БД до новой версии.
                // В простейшем случае убиваем старую БД
                // и заново создаем новую.
                // В реальной жизни стоит подумать о пользователях
                // вашего приложения и их реакцию на потерю
                // старых данных.
                _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
                onCreate(_db);
            }
        }
    }
}
