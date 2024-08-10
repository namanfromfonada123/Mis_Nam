package mis.com.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mis.com.entity.UserIpMapping;

@Repository
@Transactional
public interface UserIPMappingRepository extends JpaRepository<UserIpMapping, Long> {

	UserIpMapping findOneById(Long id);

	UserIpMapping findFirstByIpAddressAndUserNameContainsAndCampaignName(String ipAddress, String userName,
			String campaignName);
	
	UserIpMapping findFirstByIpAddressAndUserNameAndCampaignName(String ipAddress, String userName,
			String campaignName);

	@Query(value = "SELECT *  FROM mis.user_ip_mapping where user_name=:userName and ip_address=:ipAddress and user_name!='user2' ORDER BY user_name DESC LIMIT 1", nativeQuery = true)
	UserIpMapping findFirstByIpAddressAndUserName(@Param("ipAddress") String ipAddress,
			@Param("userName") String userName);

	@Query(value = "SELECT ip_address FROM `user_ip_mapping` GROUP by ip_address order by ip_address desc", nativeQuery = true)
	List<String> findIpAddressByGroup();
	
	
	

}
