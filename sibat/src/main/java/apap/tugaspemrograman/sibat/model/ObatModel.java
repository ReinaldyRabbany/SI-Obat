package apap.tugaspemrograman.sibat.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="obat")
public class ObatModel implements Serializable {

    @NotNull
    @Size(max = 255)
    @Column(name="kode", nullable = false, unique = true)
    private String kode;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    @Column(name="nomorRegistrasi", nullable = false, unique = true)
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
    @DateTimeFormat(iso= DateTimeFormat.ISO.DATE)
    private Date tanggalTerbit;

    @NotNull
    @Column(name="harga", nullable = false)
    private Double harga;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "gudang_obat",
            joinColumns = { @JoinColumn(name = "idObat") },
            inverseJoinColumns = { @JoinColumn(name = "idGudang") })
    private List<GudangModel> gudangList;

    public List<GudangModel> getGudangList() {
        return gudangList;
    }

    public void setGudangList(List<GudangModel> gudangList) {
        this.gudangList = gudangList;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public Long getIdObat() {
        return id;
    }

    public void setIdObat(Long id) {
        this.id = id;
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

    public Long getIdJenis() {
        return idJenis;
    }

    public void setIdJenis(Long idJenis) {
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
