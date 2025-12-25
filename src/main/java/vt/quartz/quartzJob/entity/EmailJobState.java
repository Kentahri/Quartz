package vt.quartz.quartzJob.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_job_state")
@Getter @Setter
public class EmailJobState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "job_key", unique = true, nullable = false)
    private String jobKey;

    @Column(name = "to_emails", nullable = false)
    private String toEmails;

    @Column(nullable = false)
    private String subject;

    @Column(name = "body_template", columnDefinition = "NVARCHAR(MAX)")
    private String bodyTemplate;

    @Column(name = "run_count", nullable = false)
    private int runCount;

    @Column(name = "last_run_at")
    private LocalDateTime lastRunAt;

    @Column(nullable = false)
    private String status;

    @Version
    private int version;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}


