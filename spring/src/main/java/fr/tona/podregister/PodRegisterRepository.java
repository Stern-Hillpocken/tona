package fr.tona.podregister;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PodRegisterRepository extends JpaRepository<PodRegister, Long> {
}
