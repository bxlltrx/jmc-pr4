package com.example.libraryrest.model;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="event_type", nullable=false)
    private String eventType; // CREATE/UPDATE/DELETE

    @Column(name="entity_type", nullable=false)
    private String entityType; // Author/Book

    @Column(name="entity_id")
    private Long entityId;

    @Column(name="details", length = 2000)
    private String details;

    @Column(name="created_at", nullable=false)
    private OffsetDateTime createdAt;

    public AuditLog() {}

    public AuditLog(String eventType, String entityType, Long entityId, String details, OffsetDateTime createdAt) {
        this.eventType = eventType;
        this.entityType = entityType;
        this.entityId = entityId;
        this.details = details;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }

    public Long getEntityId() { return entityId; }
    public void setEntityId(Long entityId) { this.entityId = entityId; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
}
