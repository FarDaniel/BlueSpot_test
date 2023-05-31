package fd.android.bluespottest.local_data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SeenCardsDataStore(private val preferences: DataStore<Preferences>) {
    private val seenCardsName = stringSetPreferencesKey("seen_cards")
    val seenCardsFlow: Flow<List<String>> = preferences.data.map {
        it[seenCardsName]?.toList() ?: emptyList()
    }

    suspend fun addSeenCard(name: String) {
        preferences.edit {
            val data = it[seenCardsName] ?: emptySet()
            it[seenCardsName] = data.plus(name)
        }
    }
}