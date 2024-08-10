package mis.com.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import mis.com.entity.MisCallDetailSummaryReport;

@Repository
@Transactional
public interface MisCallDetailsSummaryRepository extends JpaRepository<MisCallDetailSummaryReport, Long> {

	@Transactional
	@Query(value = "SELECT * FROM mis_call_detail_summary_report WHERE execution_dt=:currentDate  group by account_name,user_type ORDER BY account_name ASC;", nativeQuery = true)
	public List<MisCallDetailSummaryReport> getMisCallDetailByGivenDate(@Param("currentDate") String currentDate);

	@Transactional
	@Query(value = "SELECT * FROM mis_call_detail_summary_report WHERE execution_dt>=:fromMonth and execution_dt<=:endMonth   ORDER BY execution_dt, account_name;", nativeQuery = true)
	public List<MisCallDetailSummaryReport> getMisCallDetaiByGivenStartAndEndDate(@Param("fromMonth") String startFrom,
			@Param("endMonth") String endMonth);

	@Transactional
	@Query(value = "SELECT account_name,user_type,FLOOR(sum(totalmsisdn)),FLOOR(sum(validmsisdn)),FLOOR(sum(attempted_calls)),FLOOR(sum(connected_calls)),FLOOR(sum(total_bill_sec)),FLOOR(sum(credit_used)),execution_dt FROM mis.mis_call_detail_summary_report where execution_dt=:currentDate  group by account_name,user_type ORDER BY account_name ASC; ", nativeQuery = true)
	public List<Object[]> getDailySummaryMisCallDetailsByCurrentDate(@Param("currentDate") String currentDate);

	@Transactional
	@Query(value = "SELECT account_name as monthlyAccountName,user_type as monthlyUserName,sum(totalmsisdn) as monthlyTotalMSISDN,sum(validmsisdn) as monthlyValidMSISDN,sum(attempted_calls) as monthlyAttemptedCalls,sum(connected_calls) as monthlyConnectedCalls,sum(total_bill_sec) as monthlyTotalBillSec,sum(credit_used) as monthlyCreditUsed,execution_dt as monthlyExecutionDt FROM mis.mis_call_detail_summary_report where execution_dt>=:fromMonth and execution_dt<=:endMonth  group by account_name,user_type ORDER BY account_name ASC; ", nativeQuery = true)
	public List<Object[]> getDailySummaryMisCallDetaiMonthly(@Param("fromMonth") String fromMonth,
			@Param("endMonth") String endMonth);

	@Transactional
	@Query(value = "SELECT * FROM mis_call_detail_summary_report WHERE execution_dt>=:fromMonth and execution_dt<=:endMonth and account_name!='Celetel' ORDER BY execution_dt, account_name;", nativeQuery = true)
	public List<MisCallDetailSummaryReport> getMonthlyByGivenStartAndEndDate(@Param("fromMonth") String startFrom,
			@Param("endMonth") String endMonth);

	@Transactional
	@Query(value = "SELECT account_name FROM mis.mis_call_detail_summary_report where account_name!='Celetel' group by account_name order by account_name;", nativeQuery = true)
	public List<String> getAccountList();

	@Transactional
	@Query(value = "SELECT ip_address FROM mis.mis_call_detail_summary_report where execution_dt =:execution_dt GROUP BY ip_address order by ip_address desc;", nativeQuery = true)
	public List<String> getIpAddressList(@Param("execution_dt") String execution_dt);

	@Transactional
	@Query(value = "select account_name,user_type,sum(totalmsisdndaily)totalmsisdndaily,sum(validmsisdndaily)validmsisdndaily,sum(attempted_callsdaily)attempted_callsdaily,\r\n"
			+ "sum(connected_callsdaily)connected_callsdaily,sum(total_bill_secdaily)total_bill_secdaily,sum(credit_useddaily)credit_useddaily,sum(totalmsisdnmtd)totalmsisdnmtd,\r\n"
			+ "sum(validmsisdnmtd)validmsisdnmtd,sum(attempted_callsmtd)attempted_callsmtd,sum(connected_callsmtd)connected_callsmtd,sum(total_bill_secmtd)total_bill_secmtd,\r\n"
			+ "sum(credit_usedmtd)credit_usedmtd,sum(totalmsisdn)totalmsisdn,sum(validmsisdn)validmsisdn,sum(attempted_calls)attempted_calls,\r\n"
			+ "sum(connected_calls)connected_calls,sum(total_bill_sec)total_bill_sec,sum(credit_used)credit_used from\r\n"
			+ "\r\n" + "(SELECT account_name,user_type,\r\n"
			+ "case when execution_dt= :currentDate  then sum(totalmsisdn) else 0 end as totalmsisdndaily,\r\n"
			+ "case when execution_dt= :currentDate  then sum(validmsisdn) else 0 end as validmsisdndaily,\r\n"
			+ "case when execution_dt= :currentDate  then sum(attempted_calls) else 0 end as attempted_callsdaily,\r\n"
			+ "case when execution_dt= :currentDate  then sum(connected_calls) else 0 end as connected_callsdaily,\r\n"
			+ "case when execution_dt= :currentDate  then sum(total_bill_sec) else 0 end as total_bill_secdaily,\r\n"
			+ "case when execution_dt= :currentDate  then sum(credit_used) else 0 end as credit_useddaily,\r\n"
			+ "case when execution_dt between :currentMonthFirstDate and  :currentDate  then sum(totalmsisdn) else 0 end as totalmsisdnmtd,\r\n"
			+ "case when execution_dt between :currentMonthFirstDate and  :currentDate  then sum(validmsisdn) else 0 end as validmsisdnmtd,\r\n"
			+ "case when execution_dt between :currentMonthFirstDate and  :currentDate  then sum(attempted_calls) else 0 end as attempted_callsmtd,\r\n"
			+ "case when execution_dt between :currentMonthFirstDate and  :currentDate  then sum(connected_calls) else 0 end as connected_callsmtd,\r\n"
			+ "case when execution_dt between :currentMonthFirstDate and  :currentDate  then sum(total_bill_sec) else 0 end as total_bill_secmtd,\r\n"
			+ "case when execution_dt between :currentMonthFirstDate and  :currentDate  then sum(credit_used) else 0 end as credit_usedmtd,\r\n"
			+ "case when execution_dt between :lastMonthFirstDate and  :lastMonthLastDate  then sum(totalmsisdn) else 0 end as totalmsisdn,\r\n"
			+ "case when execution_dt between :lastMonthFirstDate and  :lastMonthLastDate  then sum(validmsisdn) else 0 end as validmsisdn,\r\n"
			+ "case when execution_dt between :lastMonthFirstDate and  :lastMonthLastDate  then sum(attempted_calls) else 0 end as attempted_calls,\r\n"
			+ "case when execution_dt between :lastMonthFirstDate and  :lastMonthLastDate  then sum(connected_calls) else 0 end as connected_calls,\r\n"
			+ "case when execution_dt between :lastMonthFirstDate and  :lastMonthLastDate  then sum(total_bill_sec) else 0 end as total_bill_sec,\r\n"
			+ "case when execution_dt between :lastMonthFirstDate and  :lastMonthLastDate  then sum(credit_used) else 0 end as credit_used \r\n"
			+ "FROM mis.mis_call_detail_summary_report \r\n"
			+ "where execution_dt between :lastMonthFirstDate and :currentDate and ip_address!='Panel-UAT70' group by account_name,user_type ,execution_dt\r\n"
			+ ")as a group by account_name,user_type order by account_name", nativeQuery = true)
	public List<Object[]> getObdSummaryReportOfFTDandMTDandLMTD(@Param("currentDate") String currentDate,
			@Param("currentMonthFirstDate") String currentMonthFirstDate,
			@Param("lastMonthFirstDate") String lastMonthFirstDate,
			@Param("lastMonthLastDate") String lastMonthLastDate);

	@Transactional
	@Query(value = "SELECT * FROM mis.mis_call_detail_summary_report where account_name='Zero Account' and ip_address!='Panel-UAT70' group by ip_address,user_name,campaign_name order by account_name;", nativeQuery = true)
	public List<MisCallDetailSummaryReport> getZeroAccountList();

	@Modifying
	@Query(value = "update mis.mis_call_detail_summary_report set account_name = :account_name  , user_type = :user_type where ip_address = :ip_address and user_name = :user_name and campaign_name = :campaign_name and account_name ='Zero Account'", nativeQuery = true)
	public void updateZeroAccountList(@Param("ip_address") String ip_address,
			@Param("user_name") String user_name, @Param("campaign_name") String campaign_name,
			@Param("account_name") String account_name, @Param("user_type") String user_type);

}
