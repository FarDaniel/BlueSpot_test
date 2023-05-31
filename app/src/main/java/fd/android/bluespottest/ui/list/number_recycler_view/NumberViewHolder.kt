package fd.android.bluespottest.ui.list.number_recycler_view

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fd.android.bluespottest.R
import fd.android.bluespottest.databinding.ItemNumberBinding

class NumberViewHolder(
    private val binding: ItemNumberBinding,
    val navigationCallback: (NumberViewHolder) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    lateinit var cardModel: NumberCardModel

    init {
        binding.root.setOnClickListener {
            navigationCallback(this)
        }
    }

    fun bind(item: NumberCardModel) {
        cardModel = item
        binding.itemNumberName.text = item.numberPreview.name
        binding.state = item.state
        Picasso.get().load(item.numberPreview.image)
            .placeholder(R.drawable.hourglass)
            .into(binding.itemNumberImage)

        binding.executePendingBindings()
    }

    fun updateView() {
        binding.itemNumberName.text = cardModel.numberPreview.name
        binding.state = cardModel.state

        binding.executePendingBindings()
    }

}