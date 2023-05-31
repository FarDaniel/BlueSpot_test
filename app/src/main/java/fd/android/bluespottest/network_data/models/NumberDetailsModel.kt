package fd.android.bluespottest.network_data.models

import com.google.gson.annotations.SerializedName
import fd.android.bluespottest.local_data.models.NumberDetails


data class NumberDetailsModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("image")
    val image: String
) {
    fun toLocal(): NumberDetails {
        return NumberDetails(
            name = name,
            text = text,
            image = image
        )
    }
}