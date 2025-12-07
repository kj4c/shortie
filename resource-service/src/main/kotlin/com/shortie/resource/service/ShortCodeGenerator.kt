package com.shortie.resource.service

import com.shortie.resource.repository.ResourceRepository
import org.springframework.stereotype.Service

/**
 * Short Code Generator
 * 
 * Validates and processes user-provided short codes.
 * Users choose their own custom codes (e.g., "promo2024", "my-link").
 * 
 * VALIDATION RULES:
 * - Length: 3-16 characters
 * - Characters: a-z, A-Z, 0-9, hyphen, underscore
 * - Must be unique (not already taken)
 * 
 * OPTIONAL: If no code provided, generate a random one.
 */
@Service
class ShortCodeGenerator(
    private val resourceRepository: ResourceRepository
) {

    companion object {
        private const val MIN_LENGTH = 3
        private const val MAX_LENGTH = 16
        // Allowed: letters, numbers, hyphen, underscore
        private val ALLOWED_PATTERN = Regex("^[a-zA-Z0-9_-]+$")
        // For random generation (if user doesn't provide code)
        private const val RANDOM_ALPHABET = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ23456789"
        private const val RANDOM_LENGTH = 7
    }

    /**
     * Validate and reserve a user-provided short code.
     * 
     * @param code The custom short code chosen by the user
     * @return The validated short code
     * @throws IllegalArgumentException if code is invalid or already taken
     */
    fun validateAndReserve(code: String): String {
        // Trim whitespace
        val trimmedCode = code.trim()
        
        // Check length
        if (trimmedCode.length < MIN_LENGTH) {
            throw IllegalArgumentException("Short code must be at least $MIN_LENGTH characters")
        }
        if (trimmedCode.length > MAX_LENGTH) {
            throw IllegalArgumentException("Short code must be at most $MAX_LENGTH characters")
        }
        
        // Check allowed characters
        if (!ALLOWED_PATTERN.matches(trimmedCode)) {
            throw IllegalArgumentException("Short code can only contain letters, numbers, hyphens, and underscores")
        }
        
        // Check if already taken
        if (resourceRepository.existsByShortCode(trimmedCode)) {
            throw IllegalArgumentException("Short code '$trimmedCode' is already taken")
        }
        
        return trimmedCode
    }

    /**
     * Generate a random short code (fallback if user doesn't provide one).
     * 
     * @return A unique random short code
     * @throws IllegalStateException if unable to generate unique code after retries
     */
    fun generateRandom(): String {
        repeat(10) {
            val code = (1..RANDOM_LENGTH)
                .map { RANDOM_ALPHABET.random() }
                .joinToString("")
            
            if (!resourceRepository.existsByShortCode(code)) {
                return code
            }
        }
        throw IllegalStateException("Failed to generate unique short code after 10 attempts")
    }
}
