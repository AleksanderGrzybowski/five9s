package pl.kelog.five9s.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformedCheckRepository extends CrudRepository<PerformedCheckInfo, Integer> {
}
