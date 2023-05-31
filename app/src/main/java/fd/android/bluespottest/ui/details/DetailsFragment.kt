package fd.android.bluespottest.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import fd.android.bluespottest.R
import fd.android.bluespottest.databinding.FragmentDetailsBinding
import fd.android.bluespottest.network_data.utils.ConnectionUtils
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()
    lateinit var binding: FragmentDetailsBinding
    private lateinit var numberName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragment = this
        initListeners()

        if (args.numberName != "") {
            resetData(args.numberName)
        }
    }

    fun resetData(numberName: String) {
        this.numberName = numberName
        viewModel.loadDetails(numberName)
    }

    private fun initListeners() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.numberDetails.filterNotNull().collect { numberDetails ->
                    Picasso.get().load(numberDetails.image)
                        .placeholder(R.drawable.hourglass)
                        .into(binding.detailsImage)
                    binding.detailsProgressBar.visibility = View.GONE
                    binding.detailsText.text = numberDetails.text
                    binding.detailsName.text = numberDetails.name
                    binding.executePendingBindings()
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collect { exception ->
                    if (exception != null) {
                        binding.detailsProgressBar.visibility = View.GONE
                        binding.detailsErrorPopup.itemErrorDescription.text =
                            if (ConnectionUtils.isConnected(context)) {
                                exception.message
                            } else {
                                context?.resources?.getString(R.string.no_connection)
                            }
                        binding.detailsErrorPopup.root.visibility = View.VISIBLE
                    }
                }
            }
        }

        binding.detailsErrorPopup.itemErrorRefreshButton.setOnClickListener {
            binding.detailsProgressBar.visibility = View.VISIBLE
            binding.detailsErrorPopup.root.visibility = View.GONE
            viewModel.loadDetails(numberName)
        }
    }
}