package challenge.albo.developer.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "colaborators")
public class Colaborator implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date lastSync;

    @NotEmpty
    @Column (nullable = false)
    private String editors;

    @NotEmpty
    @Column (nullable = false)
    private String writers;

    @NotEmpty
    @Column (nullable = false)
    private String colorists;

    @NotEmpty
    @Column (nullable = false)
    private Long heroe;

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

    public Date getLastSync() {
        return lastSync;
    }

    public void setLastSync(Date lastSync) {
        this.lastSync = lastSync;
    }

    public String getEditors() {
        return editors;
    }

    public void setEditors(String editors) {
        this.editors = editors;
    }

    public String getWriters() {
        return writers;
    }

    public void setWriters(String apellido) {
        this.writers = apellido;
    }

    public String getColorists() {
        return colorists;
    }

    public void setColorists(String profesion) {
        this.colorists = profesion;
    }
}
