package axell.belajarfirebase.Model;

/**
 * Created by axellageraldinc on 10/27/17.
 */

public class Makul {
    private String id, nama_makul, dosen;

    public Makul(){

    }
    public Makul(String nama_makul, String dosen){
        this.nama_makul = nama_makul;
        this.dosen = dosen;
    }
    public Makul(String id, String nama_makul, String dosen){
        this.id = id;
        this.nama_makul = nama_makul;
        this.dosen = dosen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_makul() {
        return nama_makul;
    }

    public void setNama_makul(String nama_makul) {
        this.nama_makul = nama_makul;
    }

    public String getDosen() {
        return dosen;
    }

    public void setDosen(String dosen) {
        this.dosen = dosen;
    }
}
