package com.shortie.api.controller

import com.shortie.api.service.ResourceApiService
import org.springframework.core.io.Resource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Redirect Controller
 * 
 * Handles short code resolution and redirects/downloads.
 * 
 * IMPLEMENTATION STEPS:
 * 1. Parse short code from path
 * 2. Call ResourceApiService.registerVisit()
 * 3. For URLs: Return HTTP 302 redirect
 * 4. For files: Return file download with proper headers
 * 5. For expired/invalid: Return appropriate error
 * 
 * TODO:
 * - Add analytics tracking
 * - Add preview page option (show destination before redirect)
 * - Add password protection support
 */
@RestController
class RedirectController(
    private val resourceApiService: ResourceApiService
) {

    /**
     * Resolve short code and redirect/download
     * 
     * GET /s/{shortCode}
     * 
     * TODO:
     * 1. Call resourceApiService.registerVisit(shortCode)
     * 2. Check response.valid
     * 3. If URL type: Return RedirectView to original_url
     * 4. If FILE type: Return file as download
     * 5. If invalid: Return 404 or 410 (Gone)
     */
    @GetMapping("/s/{shortCode}")
    fun resolve(@PathVariable shortCode: String): ResponseEntity<Any> {
        // TODO: Implement
        // val result = resourceApiService.registerVisit(shortCode)
        // 
        // if (!result.valid) {
        //     return ResponseEntity.status(HttpStatus.GONE)
        //         .body(mapOf("error" to result.errorMessage))
        // }
        // 
        // return when (result.type) {
        //     ResourceType.URL -> ResponseEntity
        //         .status(HttpStatus.FOUND)
        //         .location(URI(result.redirectUrl))
        //         .build()
        //     ResourceType.FILE -> ResponseEntity.ok()
        //         .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"${result.filename}\"")
        //         .body(FileSystemResource(result.filePath))
        // }
        
        return ResponseEntity.notFound().build()
    }
}

