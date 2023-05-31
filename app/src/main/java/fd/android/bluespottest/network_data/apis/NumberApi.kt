package fd.android.bluespottest.network_data.apis

import fd.android.bluespottest.network_data.models.NumberDetailsModel
import fd.android.bluespottest.network_data.models.NumberPreviewModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NumberApi {

    companion object {
        // Not needed, only here for the demonstration of the technique
        private const val BASE_PATH = ""
        private const val JSON_PATH = "${BASE_PATH}json.php"
    }

    @GET(JSON_PATH)
    fun getListOfNumbers(): Single<List<NumberPreviewModel>>


    @GET(JSON_PATH)
    fun getNumberDetails(
        @Query("name") name: String
    ): Single<NumberDetailsModel>

}