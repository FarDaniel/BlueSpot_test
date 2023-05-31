package fd.android.bluespottest

import androidx.datastore.core.DataStore
import fd.android.bluespottest.local_data.SeenCardsDataStore
import fd.android.bluespottest.network_data.interactors.NumberInteractor
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class ListViewModelUnitTest {
    private var numberInteractor = Mockito.mock(NumberInteractor::class.java)
    private var seenCardDataStore = Mockito.mock(SeenCardsDataStore::class.java)

    @Test
    fun loadingMultipleNumbers() {

    }

    @Test
    fun addingNewCardToSeen() {

    }

}