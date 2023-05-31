package fd.android.bluespottest.ui.list.number_recycler_view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fd.android.bluespottest.databinding.ItemNumberBinding

class NumberAdapter(private val navigationHelper: NumberNavigationHelper) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: ArrayList<NumberCardModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val binding = ItemNumberBinding.inflate(layoutInflater, parent, false)
        return NumberViewHolder(binding) { navigationHelper.openDetails(it) }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        // Enables polymorphism if needed
        if (holder is NumberViewHolder) {
            holder.bind(item)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun set(items: List<NumberCardModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

}