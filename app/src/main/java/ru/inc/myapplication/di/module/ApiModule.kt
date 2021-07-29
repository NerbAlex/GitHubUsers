package ru.inc.myapplication.di.module

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.inc.myapplication.mvp.model.api.IDataSource
import ru.inc.myapplication.mvp.model.network.INetworkStatus
import ru.inc.myapplication.ui.App
import ru.inc.myapplication.ui.network.AndroidNetworkStatus
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun apiProvide(client: OkHttpClient, gson: Gson): IDataSource = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(IDataSource::class.java)

    @Provides
    @Singleton
    fun gsonProvide(): Gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .excludeFieldsWithoutExposeAnnotation()
        .create()

    @Provides
    @Singleton
    fun clientProvide(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    @Provides
    @Singleton
    fun networkStatusProvide(app: App): INetworkStatus = AndroidNetworkStatus(app)


}