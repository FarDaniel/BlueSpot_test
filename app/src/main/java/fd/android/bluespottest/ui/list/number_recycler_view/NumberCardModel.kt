package fd.android.bluespottest.ui.list.number_recycler_view

import fd.android.bluespottest.local_data.enums.NumberStateEnum
import fd.android.bluespottest.local_data.models.NumberPreview

data class NumberCardModel(
    val numberPreview: NumberPreview,
    var state: NumberStateEnum
)