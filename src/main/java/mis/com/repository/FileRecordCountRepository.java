package mis.com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mis.com.entity.FileRecordCountLog;

@Repository
public interface FileRecordCountRepository extends JpaRepository<FileRecordCountLog, Integer> {

}
