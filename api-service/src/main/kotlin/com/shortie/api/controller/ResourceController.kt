package com.shortie.api.controller

import com.shortie.api.dto.CreateFileRequest
import com.shortie.api.dto.CreateUrlRequest
import com.shortie.api.dto.ResourceResponse
import com.shortie.api.service.ResourceApiService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

/**
 * Resource REST Controller
 * 
 * Exposes REST endpoints for creating and managing resources.
 * 
 * IMPLEMENTATION STEPS:
 * 1. Implement POST /api/resources/url
 *    - Validate URL format
 *    - Call ResourceApiService.createUrl()
 *    - Return short URL
 * 
 * 2. Implement POST /api/resources/file
 *    - Accept multipart file upload
 *    - Save file to /uploads directory
 *    - Call ResourceApiService.createFile()
 *    - Return short URL
 * 
 * TODO:
 * - Add request validation
 * - Add rate limiting
 * - Add authentication (optional)
 * - Add CORS configuration
 */
@RestController
@RequestMapping("/api/resources")
class ResourceController(
    private val resourceApiService: ResourceApiService
) {

    /**
     * Shorten a URL
     * 
     * POST /api/resources/url
     * Body: { "url": "https://example.com", "maxVisits": 100, "expiresInHours": 24 }
     * 
     * TODO:
     * 1. Parse and validate CreateUrlRequest
     * 2. Call resourceApiService.createUrl()
     * 3. Return ResourceResponse with short URL
     */
    @PostMapping("/url")
    fun createUrl(@RequestBody request: CreateUrlRequest): ResponseEntity<ResourceResponse> {
        val resource = resourceApiService.createUrl(request)
        return ResponseEntity.ok(resource)
    }

    /**
     * Upload a file
     * 
     * POST /api/resources/file
     * Multipart form: file, maxVisits (optional), expiresInHours (optional)
     * 
     * TODO:
     * 1. Save uploaded file to /uploads directory with UUID filename
     * 2. Call resourceApiService.createFile() with storage path
     * 3. Return ResourceResponse with short URL
     */
    @PostMapping("/file")
    fun createFile(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("maxVisits", required = false) maxVisits: Int?,
        @RequestParam("expiresInHours", required = false) expiresInHours: Int?
    ): ResponseEntity<ResourceResponse> {
        // TODO: Implement
        // val storagePath = saveFile(file)
        // val request = CreateFileRequest(storagePath, file.originalFilename, maxVisits, expiresInHours)
        // val resource = resourceApiService.createFile(request)
        // return ResponseEntity.ok(resource)
        return ResponseEntity.ok(ResourceResponse.placeholder())
    }

    // TODO: Add helper method to save file
    // private fun saveFile(file: MultipartFile): String {
    //     val filename = "${UUID.randomUUID()}_${file.originalFilename}"
    //     val path = Paths.get(uploadDir, filename)
    //     Files.copy(file.inputStream, path)
    //     return path.toString()
    // }
}

