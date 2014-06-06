package districted.mynotes.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyNotePersistency extends SQLiteOpenHelper implements IPersistency {

	private static final String TAG	= "mynotes";
	
	private static final String DB_NAME = "mynotes";
	
	// Table MyNote
	private static final String TABLE_NAME 		= "mynote";
	private static final String ID 				= "id";
	private static final String TITLE 			= "title";
	private static final String BODY 			= "body";
	private static final String DATE_CREATED	= "date_created";

	// Data types
	private static final String INTEGER 	= "INTEGER";
	private static final String TEXT 		= "TEXT";
	private static final String VARCHAR_256	= "VARCHAR(256)";
	private static final String TIMESTAMP 	= "TIMESTAMP";

	// Commands
	private static final String PRIMARY_KEY 	= "PRIMARY KEY";
	private static final String AUTOINCREMENT 	= "AUTOINCREMENT";
	private static final String NOT_NULL	 	= "NOT NULL";
	
	// Queries
	private static final String CREATE_TABLE_MYNOTE = "CREATE TABLE " + TABLE_NAME + " (" +
			ID 				+ " " + INTEGER + " " + PRIMARY_KEY + " " + AUTOINCREMENT + " " + NOT_NULL + ", " +
    		TITLE			+ " " + VARCHAR_256 + " " + NOT_NULL + ", " +
    		BODY			+ " " + TEXT + " " + NOT_NULL + ", " +
    		DATE_CREATED	+ " " + TIMESTAMP + " DEFAULT CURRENT_TIMESTAMP " + NOT_NULL +
    ");";
	private static final String DROP_TABLE_MYNOTE = "DROP TABLE IF EXISTS " + TABLE_NAME;
	
	public MyNotePersistency(Context context) {
		super(context, DB_NAME, null, 1);
	}

	@Override
	public long insert(MyNote record) throws PersistencyException {
		SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert(TABLE_NAME, null, getContentValues(record));
        return id;
	}

	@Override
	public void update(MyNote record) throws PersistencyException {
		SQLiteDatabase db = this.getWritableDatabase();
	    
	    int rowsaffected = db.update(TABLE_NAME, getContentValues(record), ID + " = ?",
	            new String[] { String.valueOf(record.getID()) });
	    if (rowsaffected < 0) throw new PersistencyException("No rows where affected by this update.");
	}

	@Override
	public void delete(MyNote myNote) throws PersistencyException {
		SQLiteDatabase db = this.getWritableDatabase();
	    db.delete(TABLE_NAME, ID + " = ?", new String[] { String.valueOf(myNote.getID()) });
	}

	@Override
	public MyNote select(long id) throws PersistencyException {
		SQLiteDatabase db = this.getReadableDatabase();
	    Cursor cursor = db.query(
	    		TABLE_NAME, // table
	    		new String[] { ID }, // columns
	    		ID + "=?", // selection
	            new String[] { String.valueOf(id) }, // selectionArgs
	            null, // groupBy
	            null, // having
	            null, // orderBy
	            null); // limit
        cursor.moveToFirst();	    
	    MyNote data = setMyNote(cursor);
        cursor.close();
	    return data;
	}

	@Override
	public List<MyNote> selectAll() throws PersistencyException {
		List<MyNote> list = new ArrayList<MyNote>();
		
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(setMyNote(cursor));
            } while (cursor.moveToNext());
        }
    	cursor.close();
        return list;
	}
	
	@Override
	public int recordCount() {
	    SQLiteDatabase db = this.getReadableDatabase();
	    Log.i(TAG, "this.getReadableDatabase().getPath() = " + this.getReadableDatabase().getPath());
	    
	    Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
	    int count = cursor.getCount();
	    cursor.close();
	    return count;
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_MYNOTE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(DROP_TABLE_MYNOTE);
		db.execSQL(CREATE_TABLE_MYNOTE);
	}
	
	@Override
	public void closeTransaction() {
		this.close();
	}
	
	private ContentValues getContentValues(MyNote record) {
		ContentValues values = new ContentValues();
        values.put(TITLE, record.getTitle());
        values.put(BODY, record.getText());
        return values;
	}

	private MyNote setMyNote(Cursor cursor) {
		MyNote data = new MyNote();
        data.setID(cursor.getLong(0));
        data.setTitle(cursor.getString(1));
        data.setText(cursor.getString(2));
        data.setTimeCreated(cursor.getString(3));
        
        return data;
	}
	
	public void deleteDatabase(Context context) {
		context.deleteDatabase(DB_NAME);
	}
}
