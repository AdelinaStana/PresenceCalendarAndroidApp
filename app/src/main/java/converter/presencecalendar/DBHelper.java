package converter.presencecalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "contactsManager";

    private static final String TABLE_CONTACTS = "contacts";

    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";
    private static final String[] COLUMNS = {KEY_NAME,KEY_SURNAME,KEY_DATE,KEY_TIME};

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_NAME + " TEXT, " + KEY_SURNAME + " TEXT, "
                + KEY_DATE + " TEXT, " + KEY_TIME + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }


    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_SURNAME, contact.getSurname());
        values.put(KEY_DATE,contact.getDate());
        values.put(KEY_TIME,contact.getTime());

        db.insert(TABLE_CONTACTS, null, values);
        db.close();
    }

    // Getting All Contacts
    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }


    public ArrayList<Contact> getAllContactsForDate(String date) {

        ArrayList<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
             String dt =  cursor.getString(2);
             if(dt.equals(date))
                {
                    Contact contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3));
                    contactList.add(contact);
                }

            } while (cursor.moveToNext());
        }

        return contactList;
    }


    public ArrayList<Contact> getAllContactsForName(String name) {

        ArrayList<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String name_surname =  cursor.getString(0)+" "+cursor.getString(1);
                name_surname = name_surname.toLowerCase();
                if(name_surname.equals(name))
                {
                    Contact contact = new Contact(cursor.getString(0), cursor.getString(1), cursor.getString(2),cursor.getString(3));
                    contactList.add(contact);
                }

            } while (cursor.moveToNext());
        }

        return contactList;
    }

    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_NAME + " =? AND "+ KEY_SURNAME + " =? AND "+ KEY_DATE + " =? AND "+ KEY_TIME + " =?",
                new String[] { contact.getName(),contact.getSurname(),contact.getDate(),contact.getTime()});
        db.close();
    }

    public void deleteAll()
     {     SQLiteDatabase db = this.getWritableDatabase();
           db.execSQL("delete from "+ TABLE_CONTACTS);
       }

}