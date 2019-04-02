package hr.unidu.kz.sqlkorisnici;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class KorisniciHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "korisnik.db";
        private static final int DATABASE_VERSION = 2;
        public KorisniciHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_TABLE_KORISNIK = "create table korisnik ("
                    + "_id" + " integer primary key autoincrement, "
                    + "korisnik" + " text not null, "
                    + "ime" + " text not null)";
            db.execSQL(CREATE_TABLE_KORISNIK);
            String CREATE_UNIQUE_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS korisnik_ui ON korisnik(korisnik)";
            db.execSQL(CREATE_UNIQUE_INDEX);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer){
            db.execSQL("drop table KORISNIK");
            Log.i("izmjena_verzije" , "dropa tablicu");
            onCreate(db);
        }
}