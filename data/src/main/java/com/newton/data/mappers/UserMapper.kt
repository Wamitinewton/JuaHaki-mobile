package com.newton.data.mappers

import com.newton.data.dto.auth.JwtResponse
import com.newton.data.dto.auth.SignupRequest
import com.newton.data.dto.auth.UserDto
import com.newton.domain.models.auth.JwtData
import com.newton.domain.models.auth.SignupData
import com.newton.domain.models.auth.UserInfo

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
        userInfo = userDto.toUserDomain()
    )
}