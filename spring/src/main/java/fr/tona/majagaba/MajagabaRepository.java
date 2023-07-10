package fr.tona.majagaba;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajagabaRepository extends JpaRepository<Majagaba, Long> {
}
