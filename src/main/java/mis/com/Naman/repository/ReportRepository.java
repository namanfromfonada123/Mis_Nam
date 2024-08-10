package mis.com.Naman.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import mis.com.Naman.Modal.CallData;
import mis.com.Naman.pojos.ClientWiseReport;


@Repository
public interface ReportRepository extends JpaRepository<CallData, Long> {

	@Query(value = "SELECT  max(execution_date) as execution_date, " +
            "client_name as clientname, " +
            "user_name as username, " +
            "sum(total_msisdn) as totalmsisdn, " +
            "sum(valid_msisdn) as validmsisdn, " +
            "sum(ATTEMPTED_CALLS) as attemptedcalls, " +
            "sum(connected_calls) as connectedcalls, " +
            "sum(digit_pressed) as digitpressed, " +
            " round(avg(listen_rate),2) as listen_rate, " +
            "sum(total_bill_sec) as totalbillsec, " +
            "sum(credit_used) as creditused, " +
            "panel_name as panelname " +
            "FROM mis.call_data " +
            "GROUP BY client_name, panel_name, user_name " +
            "HAVING client_name = ?1", nativeQuery = true)
List<Object[]> getClientWistReport(String clientName);

@Query(value = "SELECT  max(execution_date) as execution_date, " +
        "client_name as clientname, " +
        "user_name as username, " +
        "sum(total_msisdn) as totalmsisdn, " +
        "sum(valid_msisdn) as validmsisdn, " +
        "sum(ATTEMPTED_CALLS) as attemptedcalls, " +
        "sum(connected_calls) as connectedcalls, " +
        "sum(digit_pressed) as digitpressed, " +
        " round(avg(listen_rate),2) as listen_rate, " +
        "sum(total_bill_sec) as totalbillsec, " +
        "sum(credit_used) as creditused, " +
        "panel_name as panelname " +
        "FROM mis.call_data " +
        "WHERE execution_date = CURDATE() - INTERVAL 1 DAY " +
        "GROUP BY client_name, panel_name, user_name " +
        "HAVING client_name = ?1", nativeQuery = true)
List<Object[]> getClientWistReportofyesterDay(String clientName);

}
