package hr.unidu.kz.sqlkorisnici;

import android.content.Context;
import android.database.Cursor;
import androidx.cursoradapter.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
/*
    Adapter koji pokazuje punjenje liste podacima kursora vraćenog iz baze
 */
public class PregledAdapter extends CursorAdapter {
    private Cursor cur;
    public PregledAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
        cur = cursor;
    }
    // Vraća objekt tipa View koji opisuje redak liste
    // U ovoj metode se NE PUNE PODACI RETKA LISTE.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.redak_liste, parent, false);
    }

    // Popunjavaju se polja retka podacima iz izvora podataka (kursora)
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Dohvati polja retka koja će se puniti podacima iz kursora
        TextView id = view.findViewById(R.id.id);
        TextView korisnik = view.findViewById(R.id.korisnik);
        TextView ime = view.findViewById(R.id.ime);
        // Dohvati vrijednosti iz kursora
        int vrijId = cursor.getInt(cursor.getColumnIndex("_id"));
        String vrijKor = cursor.getString(cursor.getColumnIndex("korisnik"));
        String vrijIme = cursor.getString(cursor.getColumnIndex("ime"));
        // Popuni polja vrijednostima dohvaćenima iz kursora
        korisnik.setText(vrijKor);
        ime.setText(vrijIme);
        id.setText(String.valueOf(vrijId));
    }
    // Kada se izabere redak liste vraća se objekt tipa Korisnik
    public Korisnik getItem(int position) {
        Korisnik k = new Korisnik();
        cur.moveToPosition(position);
        k.setId(cur.getInt(cur.getColumnIndex("_id")));
        k.setKorisnik(cur.getString(cur.getColumnIndex("korisnik")));
        k.setIme(cur.getString(cur.getColumnIndex("ime")));
        return k;
    }
}
