package mis.com.Naman.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mis.com.Naman.Modal.username_client_mapping;
import java.util.List;


@Repository
public interface userClientRepository extends JpaRepository<username_client_mapping	, Long> {

	@Query(value = "SELECT * FROM mis.username_client_mapping where panel_name=?1 and username=?2 limit 1", nativeQuery = true)
	Optional<username_client_mapping>findByPanel_nameAndUsername(String panel_name, String username);
	
	@Query(value = "Select distinct(client_name) from mis.username_client_mapping ", nativeQuery = true)
	List<String> findAllClients();
	
	
}
