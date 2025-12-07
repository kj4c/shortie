package com.shortie.resource.temporal

import io.temporal.activity.ActivityInterface
import io.temporal.activity.ActivityMethod

/**
 * Resource Expiry Activities Interface
 * 
 * Defines activities that the expiry workflow can execute.
 * Activities handle side effects (database updates, API calls, etc.)
 * 
 * TEMPORAL ACTIVITY CONCEPTS:
 * - Activities are for non-deterministic operations
 * - They can be retried automatically on failure
 * - They have timeouts and heartbeats
 * - They run in the worker process, not the workflow
 */
@ActivityInterface
interface ResourceExpiryActivities {

    /**
     * Expire a resource
     * 
     * This activity is called by the workflow when the expiry time is reached.
     * 
     * @param resourceId The UUID of the resource to expire
     */
    @ActivityMethod
    fun expireResource(resourceId: String)
}

