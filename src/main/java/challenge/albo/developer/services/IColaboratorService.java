package challenge.albo.developer.services;

import challenge.albo.developer.models.Colaborator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IColaboratorService {

    public List<Colaborator> findAll();

    public Page<Colaborator> findAll(Pageable pegeable);

    public Colaborator findById(Long id);

    public Colaborator save(Colaborator colaborator);

    public void deleteById(Long id);

    public List<Colaborator> findAllByHeroe(Long heroe);

    public Colaborator findByIdAndHeroe(Long id, Long heroe);

}
