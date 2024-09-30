package mis.com.Naman.repository;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mis.com.Naman.Modal.CallData;
import mis.com.Naman.Modal.username_client_mapping;

import java.util.Date;
import java.util.List;

@Repository
public interface userClientRepository extends JpaRepository<username_client_mapping, Long> {

	@Query(value = "SELECT * FROM mis.username_client_mapping where panel_name=?1 and username=?2 limit 1", nativeQuery = true)
	Optional<username_client_mapping> findByPanel_nameAndUsername(String panel_name, String username);

	@Query(value = "Select distinct(client_name) from mis.username_client_mapping ", nativeQuery = true)
	List<String> findAllClients();

	@Query(value = "Select distinct(panel_name) from mis.username_client_mapping ", nativeQuery = true)
	List<String> findAllPanelName();

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO `mis`.`username_client_mapping` ( `client_name`, `panel_name`, `username`)"
			+ "VALUES (?1,?2,?3) "
			+ "ON DUPLICATE KEY UPDATE client_name = VALUES(client_name), panel_name = VALUES(panel_name),"
			+ "username = VALUES(username)", nativeQuery = true)
	void insertdataTouserClientMapping(String clientName, String panelName, String username);

	@Modifying
	@Transactional
	@Query(value = "INSERT IGNORE INTO `mis`.`username_client_mapping` (`client_name`, `panel_name`, `username`) VALUES (?1,?2,?3)", nativeQuery = true)
	void insertdataTouserClientMappingIgnorecase(String clientName, String panelName, String username);

}
