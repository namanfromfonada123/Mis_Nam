package mis.com.Naman.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mis.com.Naman.Modal.CallData;
import mis.com.Naman.pojos.ClientWiseReport;

@Repository
public interface ReportRepository extends JpaRepository<CallData, Long> {


	@Query(value = "SELECT  max(execution_date) as execution_date, " + "client_name as clientname, "
			+ "user_name as username, " + "sum(total_msisdn) as totalmsisdn, " + "sum(valid_msisdn) as validmsisdn, "
			+ "sum(ATTEMPTED_CALLS) as attemptedcalls, " + "sum(connected_calls) as connectedcalls, "
			+ "sum(digit_pressed) as digitpressed, " + " round(avg(listen_rate),2) as listen_rate, "
			+ "sum(total_bill_sec) as totalbillsec, " + "sum(credit_used) as creditused, " + "panel_name as panelname "
			+ "FROM mis.call_data " + "WHERE execution_date = CURDATE() - INTERVAL 1 DAY "
			+ "GROUP BY client_name, panel_name, user_name " + "HAVING client_name = ?1", nativeQuery = true)
	List<Object[]> getClientWistReportofyesterDay(String clientName);

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO `mis`.`call_data` (`attempted_calls`, `client_name`, `connected_calls`, `credit_used`, "
			+ "`digit_pressed`, `execution_date`, `listen_rate`, `panel_name`, `total_bill_sec`, `total_msisdn`, `user_name`, `valid_msisdn`)"
			+ "VALUES (?1,?2,?3,?4,?5,?6,?7,?8,?9,?10,?11,?12) "
			+ "ON DUPLICATE KEY UPDATE attempted_calls = VALUES(attempted_calls), connected_calls = VALUES(connected_calls),"
			+ "credit_used = VALUES(credit_used),digit_pressed = VALUES(digit_pressed),listen_rate = VALUES(listen_rate),total_bill_sec = VALUES(total_bill_sec),"
			+ "total_msisdn = VALUES(total_msisdn),valid_msisdn = VALUES(valid_msisdn)", nativeQuery = true)
	void insertdataToDB(int attemptedCalls, String clientName, int connectedCalls, int creditUsed, int digitPressed,
			Date executionDate, double listenRate, String panel_name, int totalBillSec, int totalMsisdn,
			String userName, int validMsisdn);
	
	
	@Query(value = "Select * from `mis`.`call_data` where execution_date =CURDATE() - interval 1 day and panel_name=?1 and user_name = ?2  and client_name=?3", nativeQuery = true)
	List<CallData> findcallDatabyClientPanelUser(String panelName, String username, String clientName );
	
//	@Query(value = "Select Distinct panel_name, user_name from  `mis`.`call_data` where client_name ", nativeQuery = true)
//	List<CallData> findCallDatabyPanelNameAndUsername(String panel_name, String user_name );
//	
	@Query(value = "Select  distinct client_name,  user_name, panel_name  from mis.call_data  where client_name=?1 and execution_date =CURDATE() - interval 1 day ", nativeQuery = true)
	List<Object[]> findNoMappingClient(String noMapping);
	
	@Query(value = "SELECT  max(execution_date) as execution_date, client_name as clientname,  sum(total_msisdn) as totalmsisdn,sum(valid_msisdn) as validmsisdn, \r\n"
			+ "sum(ATTEMPTED_CALLS) as attemptedcalls, sum(connected_calls) as connectedcalls,"
			+ "sum(digit_pressed) as digitpressed, round(avg(listen_rate),2) as listen_rate,"
			+ "sum(total_bill_sec) as totalbillsec, sum(credit_used) as creditused "
			+ "FROM mis.call_data WHERE execution_date = CURDATE() - INTERVAL 1 DAY  GROUP BY client_name", nativeQuery = true)
	List<Object[]> findAllClientData();
	
	@Query(value = "SELECT  max(execution_date) as execution_date, client_name as clientname, user_name, sum(total_msisdn) as totalmsisdn,sum(valid_msisdn) as validmsisdn,"
			+ "sum(ATTEMPTED_CALLS) as attemptedcalls, sum(connected_calls) as connectedcalls,"
			+ "sum(digit_pressed) as digitpressed, round(avg(listen_rate),2) as listen_rate,"
			+ "sum(total_bill_sec) as totalbillsec, sum(credit_used) as creditused "
			+ "FROM mis.call_data where execution_date=curdate()-interval 1 day GROUP BY  client_name, user_name;", nativeQuery = true)
	List<Object[]> findAllDataByClientNameAndUserName();
	
	
	@Query(value = "Select * from mis.call_data where execution_date = CURDATE() - interval 1 day and panel_name=?1 ", nativeQuery = true)
	List<CallData> findcallDatabyPanel(String panelName);
	
	
	@Query(value = "Select * from mis.call_data where execution_date = CURDATE() - interval 1 month  ", nativeQuery = true)
	List<CallData> findCallDataforMtd();
	
}
