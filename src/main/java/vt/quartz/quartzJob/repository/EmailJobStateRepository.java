//package vt.quartz.quartzJob.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import vt.quartz.quartzJob.entity.EmailJobState;
//
//import java.util.Optional;
//
//@Repository
//public interface EmailJobStateRepository extends JpaRepository<EmailJobState,Long> {
//    Optional<EmailJobState> findByJobKey(String jobKey);
//
//    boolean existsByJobKey(String jobKey);
//}
