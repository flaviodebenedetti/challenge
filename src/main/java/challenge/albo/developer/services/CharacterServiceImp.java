package challenge.albo.developer.services;

import challenge.albo.developer.dao.ICharacterDao;
import challenge.albo.developer.models.Character;
import challenge.albo.developer.models.Colaborator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class CharacterServiceImp implements ICharacterService{

    @Autowired
    private ICharacterDao iCharacterDao;

    @Override
    @Transactional (readOnly = true)
    public List<Character> findAll() {
        return iCharacterDao.findAll();
    }

    @Override
    public Character findById(Long id) {
        return iCharacterDao.findById(id).orElse(null);
    }

    @Override
    public Character save(Character character) {
        return iCharacterDao.save(character);
    }

    @Override
    public void deleteById(Long id) {
        iCharacterDao.deleteById(id);
    }

    @Transactional (readOnly = true)
    public List<Character> findAllByHeroe(Long value) {
        return iCharacterDao.findByHeroe(value);
    }
}
