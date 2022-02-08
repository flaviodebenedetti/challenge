package challenge.albo.developer.dao;

import challenge.albo.developer.models.Colaborator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IColaboratorDao extends JpaRepository<Colaborator, Long> {
    List<Colaborator> findAllByHeroe(Long heroe);

    Colaborator findByIdAndHeroe(Long id, Long heroe);
}
