package pl.kelog.five9s.db;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformedCheckRepository extends CrudRepository<PerformedCheckInfo, Integer> {
    
    @Query("select c from PerformedCheckInfo c where c.name = ?1 order by c.timestamp")
    List<PerformedCheckInfo> findAll(String serviceName);
    
    @Query("select c.log from PerformedCheckInfo c where c.name = ?1 and c.timestamp = " +
            "(select max(c.timestamp) from PerformedCheckInfo c where c.name = ?1 )"
    )
    String lastMessageForService(String serviceName);
}
