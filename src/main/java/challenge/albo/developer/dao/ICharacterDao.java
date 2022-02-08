package challenge.albo.developer.dao;

import challenge.albo.developer.models.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICharacterDao extends JpaRepository<Character, Long> {
    List<Character> findByHeroe(Long heroe);
}
