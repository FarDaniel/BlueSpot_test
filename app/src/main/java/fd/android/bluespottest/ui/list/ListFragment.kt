package fd.android.bluespottest.ui.list

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import fd.android.bluespottest.MainActivity
import fd.android.bluespottest.R
import fd.android.bluespottest.databinding.FragmentListBinding
import fd.android.bluespottest.local_data.enums.NumberStateEnum
import fd.android.bluespottest.network_data.utils.ConnectionUtils
import fd.android.bluespottest.ui.details.DetailsFragment
import fd.android.bluespottest.ui.details.DetailsFragmentArgs
import fd.android.bluespottest.ui.list.number_recycler_view.NumberAdapter
import fd.android.bluespottest.ui.list.number_recycler_view.NumberNavigationHelper
import fd.android.bluespottest.ui.list.number_recycler_view.NumberViewHolder
import kotlinx.coroutines.launch
import kotlin.properties.Delegates


@AndroidEntryPoint
class ListFragment : Fragment(), NumberNavigationHelper {
    private val viewModel: ListViewModel by viewModels()
    private lateinit var navController: NavController
    private lateinit var binding: FragmentListBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var numberAdapter: NumberAdapter

    // For updating the view without all list search
    private var lastActiveCardHolder: NumberViewHolder? = null
    private var lastCardStateBackup: NumberStateEnum? = null
    private var isTablet by Delegates.notNull<Boolean>()
    private var isLandscape by Delegates.notNull<Boolean>()

    // Used in two fragment mode
    private val detailsFragment = DetailsFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        mainActivity = activity as MainActivity
        numberAdapter = NumberAdapter(this)
        binding.listRecyclerView.adapter = numberAdapter

        val args = DetailsFragmentArgs("")
        detailsFragment.arguments = args.toBundle()

        mainActivity.changeSecondFragment(detailsFragment)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
        binding.listRecyclerView.layoutManager = layoutManager

        navController = findNavController()
        isTablet = resources.getBoolean(R.bool.isTablet)
        isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        initListeners()

        viewModel.loadNumbers()
    }

    // Navigate to or load details fragment
    override fun openDetails(cardItem: NumberViewHolder) {
        viewModel.addCardToSeen(cardItem.cardModel.numberPreview.name)

        if (isTablet && isLandscape) {
            detailsFragment.resetData(cardItem.cardModel.numberPreview.name)
            mainActivity.openSecondFragment()
        } else {
            navController.navigate(
                ListFragmentDirections.actionListFragmentToDetailsFragment(
                    cardItem.cardModel.numberPreview.name
                )
            )
        }
        cardItem.updateView()
    }

    private fun initListeners() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect { exception ->
                    if (exception != null) {
                        binding.listErrorPopup.itemErrorDescription.text =
                            if (ConnectionUtils.isConnected(context)) {
                                exception.message
                            } else {
                                context?.resources?.getString(R.string.no_connection)
                            }
                        binding.listErrorPopup.root.visibility = View.VISIBLE
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.numberCards.collect { cards ->
                    binding.listProgressBar.visibility = View.GONE
                    numberAdapter.set(cards)
                }

            }
        }

        binding.listErrorPopup.itemErrorRefreshButton.setOnClickListener {
            binding.listProgressBar.visibility = View.VISIBLE
            binding.listErrorPopup.root.visibility = View.GONE
            viewModel.loadNumbers()
        }
    }

}