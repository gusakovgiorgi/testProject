package net.gusakov.testprojectexample.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.BaseColumns;

import net.gusakov.testprojectexample.R;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.ArrayList;

/**
 * Created by hasana on 10/10/2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns{

    SQLiteDatabase db;
    private final int[] mAlbumImage={R.drawable.tmp_album_1,R.drawable.tmp_album_2};
    private final int[] mPosterImage = {R.drawable.tmp_poster1, R.drawable.tmp_poster2};
    private static final String DATABASE_NAME = "picture_database.db";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_POSTERS_TABLE = "posters";
    private static final String PT_IMAGE_DATA = "pt_image_data";
    private static final String DATABASE_ALBUMS_TABLE = "albums";
    private static final String AL_IMAGE_DATA = "al_image_data";
    private Context context;

    private static final String DATABASE_CREATE_POSTERS_TABLE_SQL_QUERY="CREATE TABLE " + DATABASE_POSTERS_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
    PT_IMAGE_DATA + " BLOB);";
    private static final String DATABASE_CREATE_ALBUMS_TABLE_SQL_QUERY="CREATE TABLE "+DATABASE_ALBUMS_TABLE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            AL_IMAGE_DATA + " BLOB);";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
        db=this.getReadableDatabase();
    }

    @Override
    public synchronized void close() {
        super.close();
        db.close();
    }

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
        db = this.getReadableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //create database
            db.execSQL(DATABASE_CREATE_POSTERS_TABLE_SQL_QUERY);
        db.execSQL(DATABASE_CREATE_ALBUMS_TABLE_SQL_QUERY);
        addAlbumImage(db);
        addPosterImage(db);
    }

    public static Bitmap convertByteArrayToBitmap(
            byte[] byteArrayToBeCOnvertedIntoBitMap)
    {
        Bitmap bitMapImage = BitmapFactory.decodeByteArray(
                byteArrayToBeCOnvertedIntoBitMap, 0,
                byteArrayToBeCOnvertedIntoBitMap.length);
//        BitmapDrawable bitmapDrawable=new BitmapDrawable(bitMapImage);
//        bitMapImage.recycle();

        return bitMapImage;
    }

    // Adding new image
    public void addAlbumImage(SQLiteDatabase db) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ContentValues cv = new ContentValues();
        for(int i=0;i<mAlbumImage.length;i++) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(mAlbumImage[i]);
            Bitmap bitmap = bitmapDrawable.getBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] imageInByte = stream.toByteArray();
            cv.put(AL_IMAGE_DATA, imageInByte);
            db.insert(DATABASE_ALBUMS_TABLE, null, cv);
            stream.reset();
        }
    }
    public void addPosterImage( SQLiteDatabase db) {
        ContentValues cv = new ContentValues();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        for(int i = 0; i< mPosterImage.length; i++) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) context.getResources().getDrawable(mPosterImage[i]);
            Bitmap bitmap = bitmapDrawable.getBitmap();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] imageInByte = stream.toByteArray();
            cv.put(PT_IMAGE_DATA, imageInByte);
            db.insert(DATABASE_POSTERS_TABLE, null, cv);
            stream.reset();
        }
    }

    public Bitmap getPostersImage(int id){
        String selection = _ID+"=?";
        String[] selectionArgs = new String[] { String.valueOf(id) };
        Cursor imagesCursor = db.query(DATABASE_POSTERS_TABLE, null,selection , selectionArgs, null, null, null);
        if(imagesCursor.moveToNext()){
            Bitmap bitmap= convertByteArrayToBitmap(imagesCursor.getBlob(imagesCursor.getColumnIndex(PT_IMAGE_DATA)));
            imagesCursor.close();
            imagesCursor=null;
            return bitmap;
        }
        return null;
    }

    public Bitmap getAlbumsImage(int id){
        String selection = _ID+"=?";
        String[] selectionArgs = new String[] { String.valueOf(id) };
        Cursor imagesCursor = db.query(DATABASE_ALBUMS_TABLE, null,selection , selectionArgs, null, null, null);
        if(imagesCursor.moveToNext()){
            Bitmap bitmap= convertByteArrayToBitmap(imagesCursor.getBlob(imagesCursor.getColumnIndex(AL_IMAGE_DATA)));
            imagesCursor.close();
            imagesCursor=null;
            return bitmap;
        }
        return null;
    }

    public ArrayList<Integer> getPosterImagesIds(){
        String [] columns = {_ID}; // name of the column
        Cursor imagesCursor = db.query(DATABASE_POSTERS_TABLE, columns, null, null, null, null, null);
        ArrayList<Integer> ids=new ArrayList<>();
        while(imagesCursor.moveToNext()){
            ids.add(imagesCursor.getInt(imagesCursor.getColumnIndex(_ID)));
        }
        imagesCursor.close();
        return ids;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_POSTERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_ALBUMS_TABLE);

        // create new table
        onCreate(db);
    }

    public ArrayList<Integer> getAlbumImagesIds() {
        String [] columns = {_ID}; // name of the column
        Cursor imagesCursor = db.query(DATABASE_ALBUMS_TABLE, columns, null, null, null, null, null);
        ArrayList<Integer> ids=new ArrayList<>();
        while(imagesCursor.moveToNext()){
            ids.add(imagesCursor.getInt(imagesCursor.getColumnIndex(_ID)));
        }
        imagesCursor.close();
        return ids;
    }
}
