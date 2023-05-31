package fd.android.bluespottest.ui.details

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fd.android.bluespottest.local_data.models.NumberDetails
import fd.android.bluespottest.network_data.interactors.NumberInteractor
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val numberInteractor: NumberInteractor
) : ViewModel() {
    val numberDetails = MutableStateFlow<NumberDetails?>(null)
    val error = MutableSharedFlow<Throwable?>(0, 10)

    fun loadDetails(name: String) {
        val disposable = numberInteractor.getNumberDetails(name).subscribe({ detailsModel ->
            numberDetails.update { detailsModel.toLocal() }
        }, { throwable ->
            error.tryEmit( throwable )
        })

    }
}