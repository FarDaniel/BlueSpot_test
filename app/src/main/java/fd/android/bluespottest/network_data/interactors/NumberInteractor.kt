package fd.android.bluespottest.network_data.interactors

import fd.android.bluespottest.network_data.apis.NumberApi
import fd.android.bluespottest.network_data.models.NumberDetailsModel
import fd.android.bluespottest.network_data.models.NumberPreviewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class NumberInteractor(private val numberApi: NumberApi) {

    fun getListOfNumbers(): Single<List<NumberPreviewModel>> {
        return numberApi.getListOfNumbers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getNumberDetails(name: String): Single<NumberDetailsModel> {
        return numberApi.getNumberDetails(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}