package cat.petrushkacat.foursquare_data.di

import android.content.Context
import androidx.room.Room
import cat.petrushkacat.RepositoryImpl
import cat.petrushkacat.foursquare_core.Repository
import cat.petrushkacat.foursquare_data.api.Api
import cat.petrushkacat.foursquare_data.db.FoursquareDB
import cat.petrushkacat.foursquare_data.db.FoursquareDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideDB(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, FoursquareDB::class.java, "FoursquareDB").build()

    @Provides
    @Singleton
    fun provideFoursuareDao(
        db: FoursquareDB
    ) = db.foursquareDao()

    @Provides
    @Singleton
    fun provideRepository(
        foursquareDao: FoursquareDao,
        api: Api = Api()
    ): Repository = RepositoryImpl(foursquareDao, api)

}