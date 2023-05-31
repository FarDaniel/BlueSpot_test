package fd.android.bluespottest.dependency_injection.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fd.android.bluespottest.network_data.apis.NumberApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    private const val TEST_URL = "http://dev.tapptic.com/test/"

    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    fun provideCallAdapterFactory(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .build()
    }

    @Provides
    @Singleton
    fun provideNumberListApi(
        converter: GsonConverterFactory,
        callAdapterFactory: RxJava2CallAdapterFactory,
        okHttpClient: OkHttpClient
    ): NumberApi {
        return Retrofit.Builder()
            .baseUrl(TEST_URL)
            .addConverterFactory(converter)
            .addCallAdapterFactory(callAdapterFactory)
            .client(okHttpClient)
            .build()
            .create(NumberApi::class.java)
    }
}
