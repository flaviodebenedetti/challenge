package challenge.albo.developer.services;

import challenge.albo.developer.dao.IColaboratorDao;
import challenge.albo.developer.models.Colaborator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class ColaboratorServiceImp implements IColaboratorService {

    @Autowired
    private IColaboratorDao iColaboratorDao;

    @Override
    @Transactional (readOnly = true)
    public List<Colaborator> findAll() {
        return (List<Colaborator>) iColaboratorDao.findAll();
    }

    @Override
    public Page<Colaborator> findAll(Pageable pegeable) {
        return iColaboratorDao.findAll(pegeable);
    }

    @Override
    @Transactional (readOnly = true)
    public List<Colaborator> findAllByHeroe(Long heroe) {
        return iColaboratorDao.findAllByHeroe(heroe);
    }

    @Override
    public Colaborator findByIdAndHeroe(Long id, Long heroe) {
        return iColaboratorDao.findByIdAndHeroe(id, heroe);
    }

    @Override
    @Transactional (readOnly = true)
    public Colaborator findById(Long id) {
        return iColaboratorDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Colaborator save(Colaborator colaborator) {
        return iColaboratorDao.save(colaborator);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        iColaboratorDao.deleteById(id);
    }

}
