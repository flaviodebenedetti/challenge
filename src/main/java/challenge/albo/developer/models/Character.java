package challenge.albo.developer.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "characters")
public class Character implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column (nullable = false)
    private String comic;

    @NotEmpty
    @Column (nullable = false)
    private String name;

    @NotEmpty
    @Column (nullable = false)
    private Long heroe;

    private Date lastSync;

    @PrePersist
    public void prePersist(){
        lastSync = new Date();
    }

    public Long getHeroe() {
        return heroe;
    }

    public void setHeroe(Long heroe) {
        this.heroe = heroe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComic() {
        return comic;
    }

    public void setComic(String comic) {
        this.comic = comic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastSync() {
        return lastSync;
    }

    public void setLastSync(Date lastSync) {
        this.lastSync = lastSync;
    }
}
