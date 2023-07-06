package fr.tona.pod;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PodRepository extends JpaRepository<Pod, Long> {
}
