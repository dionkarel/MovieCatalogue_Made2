package com.dicoding.core.di

import androidx.room.Room
import com.dicoding.core.BuildConfig
import com.dicoding.core.data.CatalogueRepository
import com.dicoding.core.data.local.LocalDataSource
import com.dicoding.core.data.local.room.CatalogueDB
import com.dicoding.core.data.remote.RemoteDataSource
import com.dicoding.core.data.remote.network.APIService
import com.dicoding.core.domain.repository.ICatalogueRepository
import com.dicoding.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory {
        get<CatalogueDB>().catalogueDAO()
    }

    single {
        val passphrase : ByteArray = SQLiteDatabase.getBytes("dicoding".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            CatalogueDB::class.java,"movie_entities.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

}

val networkModule = module {
    single {
        val hostname = "developers.themoviedb.org"
        val certificatePinier = CertificatePinner.Builder()
            .add(hostname, "sha256/iG3RcySOPXk22XlLdaWhzd63hSWN6gdoJkuezAQ8pF4=")
            .add(hostname, "sha256/jQJTbIh0grw0/1TkHSumWb+Fs0Ggogr621gT3PvPKG0=")
            .add(hostname, "sha256/qaSs0p8qGuDEMp4hu/DjNvJI7aZeBkew7OQzKhxUYiw=")
            .build()

        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinier)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(APIService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<ICatalogueRepository> {
        CatalogueRepository(get(), get(), get())
    }
}