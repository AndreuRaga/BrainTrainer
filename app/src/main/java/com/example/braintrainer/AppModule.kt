package com.example.braintrainer

import android.content.Context
import androidx.credentials.CredentialManager
import com.example.braintrainer.data.dataSources.GameCategoryDataSource
import com.example.braintrainer.data.dataSources.GameCategoryDataSourceImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.braintrainer.data.repositories.AuthRepositoryImpl
import com.example.braintrainer.data.repositories.UserRepositoryImpl
import com.example.braintrainer.data.repositories.AuthRepository
import com.example.braintrainer.data.repositories.GameCategoryRepository
import com.example.braintrainer.data.repositories.GameCategoryRepositoryImpl
import com.example.braintrainer.data.repositories.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
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
    fun provideFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    @Singleton
    fun provideGameCategoryDataSource(db: FirebaseFirestore): GameCategoryDataSource =
        GameCategoryDataSourceImpl(db)

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository = AuthRepositoryImpl(firebaseAuth)

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository = UserRepositoryImpl()

    @Provides
    @Singleton
    fun provideGameCategoryRepository(dataSource: GameCategoryDataSource): GameCategoryRepository = GameCategoryRepositoryImpl(dataSource)

    @Provides
    @Singleton
    fun provideCredentialManager(@ApplicationContext context: Context): CredentialManager = CredentialManager.create(context)
}