package fd.android.bluespottest.network_data.models

import com.google.gson.annotations.SerializedName
import fd.android.bluespottest.local_data.models.NumberPreview

data class NumberPreviewModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String
) {
    fun toLocal(): NumberPreview {
        return NumberPreview(
            name = name,
            image = image
        )
    }
}
