package com.example.libraryrest.jms;

import java.time.OffsetDateTime;

public class ChangeEvent {

    private String eventType;   // CREATE / UPDATE / DELETE
    private String entityType;  // Author / Book
    private Long entityId;      // id сущности
    private String details;     // что поменялось (текст)
    private OffsetDateTime timestamp;

    public ChangeEvent() {}

    public ChangeEvent(String eventType, String entityType, Long entityId, String details, OffsetDateTime timestamp) {
        this.eventType = eventType;
        this.entityType = entityType;
        this.entityId = entityId;
        this.details = details;
        this.timestamp = timestamp;
    }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }

    public Long getEntityId() { return entityId; }
    public void setEntityId(Long entityId) { this.entityId = entityId; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public OffsetDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }
}
