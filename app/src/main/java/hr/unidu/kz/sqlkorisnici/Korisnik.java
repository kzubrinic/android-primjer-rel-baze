package hr.unidu.kz.sqlkorisnici;

public class Korisnik {
    private int id;
    private String korisnik;
    private String ime;

    Korisnik() {}
    public Korisnik(int id, String korisnik, String ime) {
        this.id = id;
        this.korisnik = korisnik;
        this.ime = ime;
    }

    int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    String getKorisnik() {
        return korisnik;
    }

    void setKorisnik(String korisnik) {
        this.korisnik = korisnik;
    }

    String getIme() {
        return ime;
    }

    void setIme(String ime) {
        this.ime = ime;
    }

}
