package hr.unidu.kz.sqlkorisnici;

public class Korisnik {
    private int id;
    private String korisnik;
    private String ime;

    public Korisnik() {}
    public Korisnik(int id, String korisnik, String ime) {
        this.id = id;
        this.korisnik = korisnik;
        this.ime = ime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

}
