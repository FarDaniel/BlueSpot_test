package fd.android.bluespottest.dependency_injection.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fd.android.bluespottest.network_data.apis.NumberApi
import fd.android.bluespottest.network_data.interactors.NumberInteractor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InteractorModule {

    @Provides
    @Singleton
    fun provideNumberListInteractor(numberApi: NumberApi) =
        NumberInteractor(numberApi)

}
