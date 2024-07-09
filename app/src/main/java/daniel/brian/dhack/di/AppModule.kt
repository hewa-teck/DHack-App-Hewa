package daniel.brian.dhack.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.database
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import daniel.brian.autoexpress.utils.Constants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseDatabase() = Firebase.firestore
//
//    @Provides
//    @Singleton
//    fun provideRealtimeDatabase() = Firebase.database

    @Provides
    fun provideIntroductionSP(
        application: Application
    ): SharedPreferences = application.getSharedPreferences(
        Constants.INTRODUCTION_SP,
        Context.MODE_PRIVATE
    )
}