package hr.unidu.kz.sqlkorisnici;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AzuriranjeActivity extends AppCompatActivity {
    private TextView id, korisnik, ime;
    private Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azuriranje);
        id = findViewById(R.id.id);
        korisnik = findViewById(R.id.korisnik);
        ime = findViewById(R.id.ime);
        con = this;
        Intent intent = getIntent();
        if (intent != null) {

            id.setText(String.format("%d",intent.getIntExtra("id",0)));
            korisnik.setText(intent.getStringExtra("korisnik"));
            ime.setText(intent.getStringExtra("ime"));
        }
    }

    // Unos novog zapisa u bazu
    public void unos(View v){
        // provjera unesenih podataka
        if (korisnik.getText().toString().length() < 1 ||
            ime.getText().toString().length() < 1){
            Toast.makeText(con,"Morate unijeti korisničko ime i ime",Toast.LENGTH_SHORT).show();
            return;
        }
        KorisniciHelper databaseHelper = new KorisniciHelper(con);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues korisnikValues = new ContentValues();
        korisnikValues.put("korisnik", korisnik.getText().toString());
        korisnikValues.put("ime", ime.getText().toString());
        long rowid = db.insert("KORISNIK", null, korisnikValues);
        if (rowid <= 0){
            Toast.makeText(con,"Korisnik "+korisnik.getText().toString()+ " već postoji!",Toast.LENGTH_SHORT).show();
            db.close();
            return;
        }
        id.setText(Long.toString(rowid));
        Toast.makeText(con,"Unos uspješan! "+rowid,Toast.LENGTH_SHORT).show();
        db.close();
    }
    // Izmjena zapisa u bazu
    public void izmjena(View v){
        // provjera unesenih podataka
        if (id.getText().toString().length() < 1 ||
                korisnik.getText().toString().length() < 1 ||
                ime.getText().toString().length() < 1){
            Toast.makeText(con,"Morate unijeti id, korisničko ime i ime",Toast.LENGTH_SHORT).show();
            return;
        }
        KorisniciHelper databaseHelper = new KorisniciHelper(con);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues korisnikValues = new ContentValues();
        korisnikValues.put("ime", ime.getText().toString());
        int brojAzuriranih = db.update("KORISNIK", korisnikValues, "_id = ? ", new String[] {id.getText().toString()});
        db.close();
        if (brojAzuriranih > 0){
            Toast.makeText(con,"Izmjena uspješna! "+brojAzuriranih,Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(con, "Korisnik " + korisnik.getText().toString() + " već postoji!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
    // Brisanje zapisa iz baze
    public void brisanje(View v) {
        AlertDialog brisanjeBox = izvrsiBrisanje();
        brisanjeBox.show();
    }

    private void obrisi(){
        // provjera unesenih podataka
        if (id.getText().toString().length() < 1){
            Toast.makeText(con,"Morate unijeti id",Toast.LENGTH_SHORT).show();
            return;
        }
        KorisniciHelper databaseHelper = new KorisniciHelper(con);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int brojObrisanih = db.delete("KORISNIK", "_id = ? ", new String[] {id.getText().toString()});
        if (brojObrisanih > 0) {
            id.setText("");
            korisnik.setText("");
            ime.setText("");
            id.requestFocus();
            Toast.makeText(con, "Brisanje uspješno! " + brojObrisanih, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(con, "Nije obrisan niti jedan podatak! Pogrešna šifra!", Toast.LENGTH_SHORT).show();
        }
            db.close();

    }
    // Čitanje zapisa iz baze za zadani id
    public void citaj(View v){
        // provjera unesenih podataka
        if (id.getText().toString().length() < 1){
            Toast.makeText(con,"Morate unijeti id",Toast.LENGTH_SHORT).show();
            return;
        }
        KorisniciHelper databaseHelper = new KorisniciHelper(con);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        TextView id = findViewById(R.id.id);
        // Vezanje tablica metodom query - hack
        // A. Gupta, Doing a table join in Android without using rawQuery
        //https://blog.championswimmer.in/2015/12/doing-a-table-join-in-android-without-using-rawquery/

        // Kada se tablice vežu, onda je jednostavnije koristiti metodu rawQuery
        //String upit = "SELECT korisnik, ime FROM KORISNIK WHERE _id = ?";
        //Cursor c = db.rawQuery(upit, new String[] {id.getText().toString()});
        // Pomoću query metode
        Cursor c = db.query("KORISNIK", new String[] {"korisnik", "ime"}, "_id = ?",
                new String[] {id.getText().toString()}, null, null, null);
        if ((c != null) && (c.getCount() > 0)) {
            c.moveToFirst(); // skoči na prvi redak kursora
            korisnik.setText(c.getString(c.getColumnIndex("korisnik")));
            ime.setText(c.getString(c.getColumnIndex("ime")));
        }
        else {
            Toast.makeText(con,"Ne postoji zapis s id-jem! "+id.getText().toString(),Toast.LENGTH_SHORT).show();
            // Skoči na polje korisnik
            korisnik.requestFocusFromTouch();
            korisnik.setText("");
            ime.setText("");
        }
        c.close();
        db.close();
    }
    // Čitanje zapisa iz baze za zadanu šifru korisnika
    public void citajKorisnika(View v){
        // provjera unesenih podataka
        if (korisnik.getText().toString().length() < 1){
            Toast.makeText(con,"Morate unijeti korisnika",Toast.LENGTH_SHORT).show();
            return;
        }
        KorisniciHelper databaseHelper = new KorisniciHelper(con);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        TextView id = findViewById(R.id.id);
        String upit = "SELECT _id, korisnik, ime FROM KORISNIK WHERE korisnik = ?";
        Cursor c = db.rawQuery(upit, new String[] {korisnik.getText().toString()});
        if ((c != null) && (c.getCount() > 0)) {
            c.moveToFirst(); // skoči na prvi redak
            id.setText(Integer.toString(c.getInt(c.getColumnIndex("_id"))));
            ime.setText(c.getString(c.getColumnIndex("ime")));
        }
        else {
            Toast.makeText(con,"Ne postoji zapis s izabranim korisnikom! "+korisnik.getText().toString(),Toast.LENGTH_SHORT).show();
            id.setText("");
            ime.setText("");
        }
        c.close();
        db.close();
    }

    /*
        Otvara prozorčić potvrde brisanje
     */
    private AlertDialog izvrsiBrisanje(){
        AlertDialog dialogBrisanje = new AlertDialog.Builder(this)
               .setTitle("Brisanje")
               .setMessage("Želite li obrisati zapis?")
               .setIcon(android.R.drawable.ic_delete)
               .setPositiveButton("DA", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        obrisi(); // programski kod brisanja
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("NE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return dialogBrisanje;
    }
}
