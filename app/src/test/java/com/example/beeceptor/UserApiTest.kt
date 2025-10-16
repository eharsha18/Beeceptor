package com.example.beeceptor

import com.example.beeceptor.api.UserApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserApiTest {

    lateinit var mockWebserver : MockWebServer
    lateinit var userApi: UserApi

    @Before
    fun setup() {
        mockWebserver = MockWebServer()
        userApi = Retrofit.Builder()
            .baseUrl(mockWebserver.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build().
           create(UserApi::class.java)
    }

    @Test
    fun getUserTest() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("[]")
        mockWebserver.enqueue(mockResponse)
        val response = userApi.getUsers()
        mockWebserver.takeRequest()
        Assert.assertEquals(true,response.body()!!.isEmpty())
    }

    @Test
    fun getUserErrorTest() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("Something went wrong")
        mockResponse.setResponseCode(400)
        mockWebserver.enqueue(mockResponse)
        val response = userApi.getUsers()
        mockWebserver.takeRequest()

        Assert.assertEquals(false,response.isSuccessful)
        Assert.assertEquals(400,response.code())
    }

    @Test
    fun getUserdata() = runTest {
        val mockResponse = MockResponse()
        mockResponse.setBody("[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Raymond Dooley\",\n" +
                "    \"company\": \"Waters - Howe\",\n" +
                "    \"username\": \"Genoveva97\",\n" +
                "    \"email\": \"Nikolas_Jakubowski37@gmail.com\",\n" +
                "    \"address\": \"3275 Runte River\",\n" +
                "    \"zip\": \"21399-4831\",\n" +
                "    \"state\": \"California\",\n" +
                "    \"country\": \"Tanzania\",\n" +
                "    \"phone\": \"1-397-629-3135 x302\",\n" +
                "    \"photo\": \"https://json-server.dev/ai-profiles/86.png\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"name\": \"Lorenz Faker Attribute Error: person.astName is not supported\",\n" +
                "    \"company\": \"McLaughlin, Bauch and Kutch\",\n" +
                "    \"username\": \"Leora_Stiedemann\",\n" +
                "    \"email\": \"Nash.Stiedemann@yahoo.com\",\n" +
                "    \"address\": \"7258 S Elm Street\",\n" +
                "    \"zip\": \"28251-1939\",\n" +
                "    \"state\": \"Kansas\",\n" +
                "    \"country\": \"Saint Helena\",\n" +
                "    \"phone\": \"705.597.2802\",\n" +
                "    \"photo\": \"https://json-server.dev/ai-profiles/40.png\"\n" +
                "  }]")
        mockResponse.setResponseCode(200)
        mockWebserver.enqueue(mockResponse)
        val response = userApi.getUsers()
        mockWebserver.takeRequest()

        Assert.assertEquals(2,response.body()!!.size)
        Assert.assertEquals(200,response.code())
    }

    @After
    fun tearDown(){
        mockWebserver.shutdown()
    }
}