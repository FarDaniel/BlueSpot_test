package fd.android.bluespottest.dependency_injection.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fd.android.bluespottest.local_data.SeenCardsDataStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private val Context.seenCardDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "seenCardsStore"
    )

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context) = context.seenCardDataStore

    @Provides
    @Singleton
    fun provideSeenCardsDataStore(preferences: DataStore<Preferences>) =
        SeenCardsDataStore(preferences)

}