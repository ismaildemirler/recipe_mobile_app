package com.ismail.openapi.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ismail.openapi.models.AccountProperties
import com.ismail.openapi.models.AuthToken

@Dao
interface AuthTokenDao {

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    fun insertAndReplace(authToken: AuthToken):Long

    @Query("UPDATE auth_token SET token = null WHERE account_pk = :pk")
    fun nullifyToken(pk: Int): Int
}