package com.newton.database.mappers

import com.newton.database.entities.UserEntity
import com.newton.domain.models.auth.UserInfo

fun UserInfo.toUserEntity(): UserEntity =
    UserEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        userName = username,
    )

fun UserEntity.toUserInfo(): UserInfo =
    UserInfo(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        username = userName,
    )
