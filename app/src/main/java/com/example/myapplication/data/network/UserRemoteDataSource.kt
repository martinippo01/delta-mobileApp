package com.example.myapplication.data.network

import com.example.myapplication.data.network.api.ApiUserService
import com.example.myapplication.data.network.model.NetworkCredentials
import com.example.myapplication.data.network.model.NetworkPagedContent
import com.example.myapplication.data.network.model.NetworkRoutine
import com.example.myapplication.util.SessionManager
import com.example.myapplication.data.network.model.NetworkUser

class UserRemoteDataSource(
    private val apiUserService: ApiUserService,
    private val sessionManager: SessionManager
) : RemoteDataSource() {

    suspend fun login(username: String, password: String) {
        val response = handleApiResponse {
            apiUserService.login(NetworkCredentials(username, password))
        }
        sessionManager.saveAuthToken(response.token)
    }

    suspend fun logout() {
        handleApiResponse { apiUserService.logout() }
        sessionManager.removeAuthToken()
    }

    suspend fun getCurrentUser() : NetworkUser {
        return handleApiResponse { apiUserService.getCurrentUser() }
    }

    suspend fun getUserRoutines(id:Int) : NetworkPagedContent<NetworkRoutine> {
        return handleApiResponse {
            apiUserService.getUserRoutines(id)
        }
    }

}