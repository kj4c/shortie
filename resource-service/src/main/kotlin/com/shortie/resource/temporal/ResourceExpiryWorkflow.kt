package com.shortie.resource.temporal

import io.temporal.workflow.WorkflowInterface
import io.temporal.workflow.WorkflowMethod

/**
 * Resource Expiry Workflow Interface
 * 
 * Temporal workflow that handles resource expiration.
 * 
 * WORKFLOW BEHAVIOR:
 * 1. Workflow is started when a resource with expiresAt is created
 * 2. Workflow sleeps until the expiration time
 * 3. When awakened, calls activity to expire the resource
 * 4. Workflow completes
 * 
 * WHY TEMPORAL?
 * - Durable timers that survive service restarts
 * - Exactly-once execution guarantee
 * - Built-in retry and error handling
 * - Visibility into workflow state
 * 
 * TODO:
 * - Add cancellation support (if resource is deleted early)
 * - Add update support (if expiration time is changed)
 */
@WorkflowInterface
interface ResourceExpiryWorkflow {

    /**
     * Start the expiry workflow
     * 
     * @param resourceId The UUID of the resource to expire
     * @param expiresAtMillis The timestamp when the resource should expire
     */
    @WorkflowMethod
    fun startExpiry(resourceId: String, expiresAtMillis: Long)
}

