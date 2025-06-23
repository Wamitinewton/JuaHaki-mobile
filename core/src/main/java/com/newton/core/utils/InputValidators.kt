package com.newton.core.utils

/**
 * Sealed class representing different types of input validation errors
 */
sealed class ValidationError(val message: String) {
    object EmptyField : ValidationError("This field is required")
    object InvalidEmail : ValidationError("Please enter a valid email address")
    object InvalidPhoneNumber : ValidationError("Phone number must be 10 digits starting with 07 or 01")
    object InvalidName : ValidationError("Name must contain only letters and be at least 2 characters")
    object InvalidUsername : ValidationError("Username must be 3-20 characters, alphanumeric and underscores only")
    object WeakPassword : ValidationError("Password must be at least 8 characters with uppercase, lowercase, number and special character")
    object PasswordTooShort : ValidationError("Password must be at least 8 characters")
    object PasswordNoUppercase : ValidationError("Password must contain at least one uppercase letter")
    object PasswordNoLowercase : ValidationError("Password must contain at least one lowercase letter")
    object PasswordNoNumber : ValidationError("Password must contain at least one number")
    object PasswordNoSpecialChar : ValidationError("Password must contain at least one special character")
    object InvalidOtp : ValidationError("Please enter a valid 6-character verification code")
    object PasswordMismatch : ValidationError("Passwords do not match")
}

/**
 * Sealed class representing validation result
 */
sealed class ValidationResult {
    object Valid : ValidationResult()
    data class Invalid(val error: ValidationError) : ValidationResult()
}


object InputValidator {

    /**
     * Validates email format
     */
    fun validateEmail(email: String): ValidationResult {
        return when {
            email.trim().isEmpty() -> ValidationResult.Invalid(ValidationError.EmptyField)
            !isValidEmailFormat(email.trim()) -> ValidationResult.Invalid(ValidationError.InvalidEmail)
            else -> ValidationResult.Valid
        }
    }

    /**
     * Validates phone number (Kenyan format: 07xxxxxxxx or 01xxxxxxxx)
     */
    fun validatePhoneNumber(phoneNumber: String): ValidationResult {
        val cleanedPhone = phoneNumber.trim().replace(" ", "")
        return when {
            cleanedPhone.isEmpty() -> ValidationResult.Invalid(ValidationError.EmptyField)
            !isValidKenyanPhoneNumber(cleanedPhone) -> ValidationResult.Invalid(ValidationError.InvalidPhoneNumber)
            else -> ValidationResult.Valid
        }
    }

    /**
     * Validates name (first name or last name)
     */
    fun validateName(name: String): ValidationResult {
        val trimmedName = name.trim()
        return when {
            trimmedName.isEmpty() -> ValidationResult.Invalid(ValidationError.EmptyField)
            !isValidName(trimmedName) -> ValidationResult.Invalid(ValidationError.InvalidName)
            else -> ValidationResult.Valid
        }
    }

    /**
     * Validates username
     */
    fun validateUsername(username: String): ValidationResult {
        val trimmedUsername = username.trim()
        return when {
            trimmedUsername.isEmpty() -> ValidationResult.Invalid(ValidationError.EmptyField)
            !isValidUsername(trimmedUsername) -> ValidationResult.Invalid(ValidationError.InvalidUsername)
            else -> ValidationResult.Valid
        }
    }

    /**
     * Validates password with detailed feedback
     */
    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isEmpty() -> ValidationResult.Invalid(ValidationError.EmptyField)
            password.length < 8 -> ValidationResult.Invalid(ValidationError.PasswordTooShort)
            !password.any { it.isUpperCase() } -> ValidationResult.Invalid(ValidationError.PasswordNoUppercase)
            !password.any { it.isLowerCase() } -> ValidationResult.Invalid(ValidationError.PasswordNoLowercase)
            !password.any { it.isDigit() } -> ValidationResult.Invalid(ValidationError.PasswordNoNumber)
            !password.any { it in "!@#$%^&*()_+-=[]{}|;:,.<>?" } -> ValidationResult.Invalid(ValidationError.PasswordNoSpecialChar)
            else -> ValidationResult.Valid
        }
    }

    /**
     * Validates all signup fields at once
     */
    fun validateSignupData(
        firstName: String,
        lastName: String,
        username: String,
        phoneNumber: String,
        email: String,
        password: String
    ): Map<String, ValidationError?> {
        return mapOf(
            "firstName" to (validateName(firstName) as? ValidationResult.Invalid)?.error,
            "lastName" to (validateName(lastName) as? ValidationResult.Invalid)?.error,
            "username" to (validateUsername(username) as? ValidationResult.Invalid)?.error,
            "phoneNumber" to (validatePhoneNumber(phoneNumber) as? ValidationResult.Invalid)?.error,
            "email" to (validateEmail(email) as? ValidationResult.Invalid)?.error,
            "password" to (validatePassword(password) as? ValidationResult.Invalid)?.error
        )
    }



    private fun isValidEmailFormat(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
        return email.matches(emailRegex.toRegex())
    }

    private fun isValidKenyanPhoneNumber(phoneNumber: String): Boolean {
        val phoneRegex = "^(07|01)\\d{8}$"
        return phoneNumber.matches(phoneRegex.toRegex())
    }

    private fun isValidName(name: String): Boolean {
        // Name should contain only letters and spaces, at least 2 characters
        val nameRegex = "^[A-Za-z\\s]{2,50}$"
        return name.matches(nameRegex.toRegex()) && name.isNotBlank()
    }

    private fun isValidUsername(username: String): Boolean {
        // Username: 3-20 characters, alphanumeric and underscores only
        val usernameRegex = "^[A-Za-z0-9_]{3,20}$"
        return username.matches(usernameRegex.toRegex())
    }

    fun validateOtp(otp: String): ValidationResult {
        return when {
            otp.isBlank() -> ValidationResult.Invalid(ValidationError.EmptyField)
            otp.length != 6 -> ValidationResult.Invalid(ValidationError.InvalidOtp)
            !otp.all { it.isLetterOrDigit() } -> ValidationResult.Invalid(ValidationError.InvalidOtp)
            else -> ValidationResult.Valid
        }
    }

    fun validatePasswordMatch(password: String, confirmPassword: String): ValidationResult {
        return when {
            confirmPassword.isBlank() -> ValidationResult.Invalid(ValidationError.EmptyField)
            password != confirmPassword -> ValidationResult.Invalid(ValidationError.PasswordMismatch)
            else -> ValidationResult.Valid
        }
    }
}