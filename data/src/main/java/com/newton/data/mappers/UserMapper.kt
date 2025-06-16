package com.newton.data.mappers

import com.newton.data.dto.auth.JwtResponse
import com.newton.data.dto.auth.LoginRequest
import com.newton.data.dto.auth.RefreshTokenRequest
import com.newton.data.dto.auth.SignupRequest
import com.newton.data.dto.auth.UserDto
import com.newton.data.dto.auth.VerifyOtpRequest
import com.newton.domain.models.auth.JwtData
import com.newton.domain.models.auth.LoginData
import com.newton.domain.models.auth.RefreshTokenData
import com.newton.domain.models.auth.SignupData
import com.newton.domain.models.auth.UserInfo
import com.newton.domain.models.auth.VerifyOtpData

fun SignupData.toRequestDto(): SignupRequest {
    return SignupRequest(
        firstName = firstName,
        lastName = lastName,
        username = username,
        phoneNumber = phoneNumber,
        email = email,
        password = password
    )
}

fun UserDto.toUserDomain(): UserInfo {
    return UserInfo(
        firstName = firstName,
        lastName = lastName,
        username = username,
        phoneNumber = phoneNumber,
        email = email,
        id = id,
    )
}

fun JwtResponse.toJwtData(): JwtData {
    return JwtData(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userInfo = user.toUserDomain()
    )
}

fun LoginData.toLoginRequest(): LoginRequest {
    return LoginRequest(
        usernameOrEmail = usernameOrEmail,
        password = password
    )
}

fun RefreshTokenData.toRefreshTokenRequest(): RefreshTokenRequest {
    return RefreshTokenRequest(
        refreshToken = refreshToken
    )
}

fun VerifyOtpData.toVerifyOtpRequest(): VerifyOtpRequest {
    return VerifyOtpRequest(
        email = email,
        otp = otp
    )
}