package fd.android.bluespottest

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import dagger.hilt.android.AndroidEntryPoint
import fd.android.bluespottest.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var isSecondFragmentOpened = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    // If in two fragments mode, simply closing second fragment
    override fun onBackPressed() {
        if (isSecondFragmentOpened) {
            closeSecondFragment()
        } else {
            super.onBackPressed()
        }
    }

    fun changeSecondFragment(fragment: Fragment) {
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(binding.activitySecondFragmentContainer.id, fragment)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        ft.commit()
    }

    fun openSecondFragment() {
        isSecondFragmentOpened = true
        binding.activitySecondFragmentContainer.visibility = View.VISIBLE
        binding.executePendingBindings()
    }

    private fun closeSecondFragment() {
        isSecondFragmentOpened = false
        binding.activitySecondFragmentContainer.visibility = View.GONE
        binding.executePendingBindings()
    }

}