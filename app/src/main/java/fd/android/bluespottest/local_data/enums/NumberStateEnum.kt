package fd.android.bluespottest.local_data.enums

enum class NumberStateEnum(val serializedValue: Int) {
    // Never opened number card
    NEW(0),

    // Already opened number card
    SEEN(1),

    // Currently opened card
    ACTIVE(2);

    companion object {
        fun getFromSerialized(serializedValue: Int): NumberStateEnum {
            return when (serializedValue) {
                0 -> NEW
                1 -> SEEN
                2 -> ACTIVE
                else -> NEW
            }
        }
    }
}