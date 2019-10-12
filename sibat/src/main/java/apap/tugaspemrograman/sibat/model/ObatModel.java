package apap.tugaspemrograman.sibat.model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name="obat")
public class ObatModel implements Serializable {

    @NotNull
    @UniqueElements
    @Size(max = 255)
    @Column(name="kode", nullable = false)
    private String kode;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idObat;

    @NotNull
    @UniqueElements
    @Size(max = 255)
    @Column(name="nomorRegistrasi", nullable = false)
    private String nomorRegistrasi;

    @NotNull
    @Size(max = 255)
    @Column(name="nama", nullable = false)
    private String nama;

    @NotNull
    @Column(name="idJenis", nullable = false)
    private Long idJenis;

    @NotNull
    @Size(max = 255)
    @Column(name="bentuk", nullable = false)
    private String bentuk;

    @NotNull
    @Column(name="tanggalTerbit", nullable = false)
    private Date tanggalTerbit;

    @NotNull
    @Column(name="harga", nullable = false)
    private Double harga;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public Long getIdObat() {
        return idObat;
    }

    public void setIdObat(Long idObat) {
        this.idObat = idObat;
    }

    public String getNomorRegistrasi() {
        return nomorRegistrasi;
    }

    public void setNomorRegistrasi(String nomorRegistrasi) {
        this.nomorRegistrasi = nomorRegistrasi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Long getJenis() {
        return idJenis;
    }

    public void setJenis(Long idJenis) {
        this.idJenis = idJenis;
    }

    public String getBentuk() {
        return bentuk;
    }

    public void setBentuk(String bentuk) {
        this.bentuk = bentuk;
    }

    public Date getTanggalTerbit() {
        return tanggalTerbit;
    }

    public void setTanggalTerbit(Date tanggalTerbit) {
        this.tanggalTerbit = tanggalTerbit;
    }

    public Double getHarga() {
        return harga;
    }

    public void setHarga(Double harga) {
        this.harga = harga;
    }
}
