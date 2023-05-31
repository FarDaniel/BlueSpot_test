package fd.android.bluespottest.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fd.android.bluespottest.local_data.SeenCardsDataStore
import fd.android.bluespottest.local_data.enums.NumberStateEnum
import fd.android.bluespottest.network_data.interactors.NumberInteractor
import fd.android.bluespottest.network_data.models.NumberPreviewModel
import fd.android.bluespottest.ui.list.number_recycler_view.NumberCardModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val numberInteractor: NumberInteractor,
    private val seenCardsDataStore: SeenCardsDataStore
) : ViewModel() {
    val error = MutableSharedFlow<Throwable?>(0, 10)

    // The raw data from backend
    private val numberPreviews = MutableStateFlow<List<NumberPreviewModel>>(emptyList())

    // The active card's name
    private val activeNumber = MutableStateFlow<String?>(null)

    // Making cards from raw backend data
    val numberCards =
        combine(
            seenCardsDataStore.seenCardsFlow,
            numberPreviews,
            activeNumber
        ) { seenCards, previews, activeNumber ->
            previews.map { preview ->
                NumberCardModel(
                    numberPreview = preview.toLocal(),
                    state = when {
                        activeNumber == preview.name -> NumberStateEnum.ACTIVE
                        seenCards.contains(preview.name) -> NumberStateEnum.SEEN
                        else -> NumberStateEnum.NEW
                    }
                )
            }
        }

    fun loadNumbers() {
        val disposable = numberInteractor.getListOfNumbers().subscribe({ previewList ->
            numberPreviews.update { previewList }
        }, { throwable ->
            error.tryEmit(throwable)
        })

    }

    fun addCardToSeen(cardName: String) {
        activeNumber.update { cardName }
        viewModelScope.launch {
            seenCardsDataStore.addSeenCard(cardName)
        }
    }

}