package com.anne.zhihudaily

import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.anne.zhihudaily.databinding.ActivityMain2Binding
import com.anne.zhihudaily.ui.DailyInfoFragment
import com.google.android.material.navigation.NavigationView

class MainActivity2 : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMain2Binding
    private lateinit var mCurrentFragment: Fragment

    companion object {
        val LOG_TAG: String = MainActivity2::class.java.simpleName
    }
    private var mDailyInfoFragment: DailyInfoFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState?.let {
            Log.d(LOG_TAG, "savedInstanceState is not null.")
            mDailyInfoFragment = supportFragmentManager.findFragmentByTag(DailyInfoFragment::class.java.simpleName) as DailyInfoFragment
            Log.d(LOG_TAG, "DailyInfoFragment is null ${mDailyInfoFragment == null}, ThemeInfoFragment is null")
        }
        setSupportActionBar(binding.appBarMain.toolbar)

//        binding.appBarMain.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        initDefaultFragment()
    }

    private fun initDefaultFragment() {
        mDailyInfoFragment?: let {
            mDailyInfoFragment = DailyInfoFragment()
        }

        mDailyInfoFragment!!.setScrollCallback(object: DailyInfoFragment.ScrollCallback {
            override fun onTitleChanged(text: String) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                mToolbar.title = text
            }
        })

        setCurrentFragment(mDailyInfoFragment!!)
        toFragment(mDailyInfoFragment!!)
    }
    private fun setCurrentFragment(fragment: Fragment) {
        mCurrentFragment = fragment
    }

    private fun toFragment(toFragment: Fragment) {
        mCurrentFragment.let {
            Log.d(LOG_TAG, "switchFragment current fragment is ${mCurrentFragment.javaClass.simpleName}, to fragment is ${toFragment.javaClass.simpleName}")
            Log.d(LOG_TAG, "to fragment is Add? ${toFragment.isAdded}, tag is ${toFragment.tag}")

            if (toFragment.isAdded) {
                supportFragmentManager.beginTransaction()
                    .hide(mCurrentFragment)
                    .show(toFragment)
                    .commit()
            } else {
//                supportFragmentManager.beginTransaction()
//                    .hide(mCurrentFragment)
//                    .add(R.i, toFragment, toFragment.javaClass.simpleName)
//                    .show(toFragment)
//                    .commit()
            }
            setCurrentFragment(toFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}