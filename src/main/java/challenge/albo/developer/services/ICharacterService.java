package challenge.albo.developer.services;

import challenge.albo.developer.models.Character;
import challenge.albo.developer.models.Colaborator;
import challenge.albo.developer.models.Heroe;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;

public interface ICharacterService {
    public List<Character> findAll();

    public Character findById(Long id);

    public Character save(Character character);

    public void deleteById(Long id);
}
