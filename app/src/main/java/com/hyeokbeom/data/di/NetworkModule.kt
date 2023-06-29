package com.hyeokbeom.data.di

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.hyeokbeom.data.datasource.RXCApi
import com.hyeokbeom.shared.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * NetworkModule
 * [Network 통신을 위한 작업]
 * Retrofit Instance 생성 후 DataModule 로 전달
 * @see DataModule
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    companion object {
        // server endpoint
        const val endpoint  = "https://mobile-task.rxc.co.kr/"
    }

    /**
     * loggerProvider
     * [Logger instance 생성]
     * - Json 형태의 response message 커스텀 로그 사용
     */
    @Provides
    @Singleton
    fun loggerProvider() = HttpLoggingInterceptor.Logger { message ->
        val label = "LOG RESULT"
        if (message.startsWith("{") || message.startsWith("[")) {
            try {
                val prettyPrintJson = GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(JsonParser.parseString(message))
                Log.i("$label : $prettyPrintJson")
            } catch (m: JsonSyntaxException) {
                Log.e("$label : $m")
            }
        } else {
            Log.w("$label : $message")
        }
    }

    /**
     * loggingInterceptorProvider
     * [Logger Interceptor instance 생성]
     * @param logger
     * - BODY Level 의 정보만 취함
     */
    @Provides
    @Singleton
    fun loggingInterceptorProvider(logger: HttpLoggingInterceptor.Logger) =
        HttpLoggingInterceptor(logger).apply { level = HttpLoggingInterceptor.Level.BODY }

    /**
     * okHttpClientProvider
     * [OkHttpClient instance 생성]
     * @param networkInterceptor
     */
    @Provides
    @Singleton
    fun okHttpClientProvider(networkInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(networkInterceptor)
            .build()


    /**
     * RXCApiProvider
     * [RXCAPI instance 생성]
     * @param okHttpClient
     *
     * 생성된 instance 는 RXCRepositoryImpl(구현부)로 의존성 주입
     */
    @Singleton
    @Provides
    fun RXCApiProvider(okHttpClient: OkHttpClient) =
        createAPI(endpoint, okHttpClient, RXCApi::class.java) as RXCApi

    /**
     * createAPI()
     * [API service instance 생성]
     * @param url 적용할 base url
     * @param client OkHttpClient, 제공받을 클라이언트 사용
     * @param cls Service Interface
     */
    private fun createAPI(
        url: String,
        client: OkHttpClient,
        cls: Class<*>,
    ): Any {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(cls)
    }
}