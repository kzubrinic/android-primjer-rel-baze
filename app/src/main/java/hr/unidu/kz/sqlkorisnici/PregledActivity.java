package hr.unidu.kz.sqlkorisnici;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class PregledActivity extends ListActivity {
    private SQLiteDatabase db;
    private Cursor c;
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        napuniPodatke();
    }
    public void onResume(){
        super.onResume();
        napuniPodatke();
    }
    public void onListItemClick(ListView l, View v, int position, long id) {
        Korisnik izabrani = (Korisnik) getListAdapter().getItem(position);
        Intent intent = new Intent(this, AzuriranjeActivity.class);
        intent.putExtra("id", izabrani.getId());
        intent.putExtra("korisnik", izabrani.getKorisnik());
        intent.putExtra("ime", izabrani.getIme());
        startActivity(intent);
        Toast.makeText(this, "Ime: "+izabrani.getIme() , Toast.LENGTH_SHORT).show();
    }
    private void napuniPodatke(){
        KorisniciHelper databaseHelper = new KorisniciHelper(this);
        db = databaseHelper.getWritableDatabase();
        c = db.query("KORISNIK", new String[] {"_id","korisnik", "ime"}, null, null, null, null, "korisnik");
        c.moveToFirst(); // skoči na prvi redak
        PregledAdapter adapter = new PregledAdapter(this, c);
        // adapter se povezuje s listom
        setListAdapter(adapter);

    }
    @Override
    protected void onStop (){
        super.onStop();
        c.close();
        db.close();
    }
}
