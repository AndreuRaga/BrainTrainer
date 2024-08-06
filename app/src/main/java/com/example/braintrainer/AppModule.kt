package com.example.braintrainer

import android.content.Context
import androidx.credentials.CredentialManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.braintrainer.data.AuthRepositoryImpl
import com.example.braintrainer.data.UserRepositoryImpl
import com.example.braintrainer.data.AuthRepository
import com.example.braintrainer.data.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(firebaseAuth)

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository = UserRepositoryImpl()


    @Provides
    @Singleton
    fun provideCredentialManager(@ApplicationContext context: Context): CredentialManager = CredentialManager.create(context)
}