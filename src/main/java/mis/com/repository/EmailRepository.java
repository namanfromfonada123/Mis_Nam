package mis.com.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mis.com.entity.EmailEntity;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, Integer> {

	List<EmailEntity> findAllByIsActive(String isActive);

	@Transactional
	@Query(value = "select * from email where is_active=1 and reports=:reports", nativeQuery = true)
	List<EmailEntity> findAllByIsActiveAndReports(@Param("reports") String reports);

}
