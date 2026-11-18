package com.campuslab.platform.shared.domain.model.aggregates;

import lombok.Getter;
import java.time.Instant;

@Getter
public abstract class AbstractDomainAggregateRoot {

    protected Instant createdAt;
    protected Instant updatedAt;

    protected void initializeAuditTimestamps() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    protected void restoreAuditTimestamps(Instant createdAt, Instant updatedAt) {
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
