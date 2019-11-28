package nl.yaz.eurailtech

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import nl.yaz.eurailtech.ui.countdown.CountDownFragment
import nl.yaz.eurailtech.ui.map.MapFragment
import nl.yaz.eurailtech.ui.storage.LocalStorageFragment
import nl.yaz.eurailtech.ui.webview.WebViewFragment
import timber.log.Timber
import java.lang.IllegalArgumentException


private const val KEY_SELECTED_MENU_ID = "selected_menu_id"

class MainActivity : AppCompatActivity() {

    private lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener { menuItem ->
            Timber.d("selected $menuItem")
            val fragment: Fragment = when (menuItem.itemId) {
                R.id.navigation_map -> MapFragment()
                R.id.navigation_countdown -> CountDownFragment()
                R.id.navigation_local_storage -> LocalStorageFragment.newInstance()
                R.id.navigation_webview -> WebViewFragment()
                else -> throw IllegalArgumentException("wrong id")
            }
            supportActionBar?.title = menuItem.title.toString()
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit()
            return@setOnNavigationItemSelectedListener true
        }

        if (savedInstanceState == null) {
            navView.selectedItemId = R.id.navigation_map
        } else {
            supportActionBar?.title = navView.menu.findItem(savedInstanceState.getInt(KEY_SELECTED_MENU_ID)).title
        }
        navView.setOnNavigationItemReselectedListener {
            // do nothing on reselecting item
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_SELECTED_MENU_ID, navView.selectedItemId )
    }
}
