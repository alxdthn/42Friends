package com.nalexand.friendlocation.main

import android.os.Bundle
import android.os.Handler
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.nalexand.friendlocation.R
import com.nalexand.friendlocation.ui.add_user.AddUserFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


	@Inject
	lateinit var commonViewModel: CommonViewModel

	private lateinit var appBarConfiguration: AppBarConfiguration

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		inject()
		setToolbar()
		setNavigation()
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.main, menu)
		return true
	}

	override fun onSupportNavigateUp(): Boolean {
		val navController = findNavController(R.id.nvHostFragment)
		return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
	}

	private fun setToolbar() {
		val toolbar: Toolbar = findViewById(R.id.toolbar)
		setSupportActionBar(toolbar)
	}

	private fun setNavigation() {
		val drawerLayout: DrawerLayout = findViewById(R.id.dlMainDrawer)
		val navView: NavigationView = findViewById(R.id.nvMainNavView)
		val navController = findNavController(R.id.nvHostFragment)

		appBarConfiguration = AppBarConfiguration(
			setOf(R.id.nav_home), drawerLayout
		)
		setupActionBarWithNavController(navController, appBarConfiguration)
		navView.setupWithNavController(navController)
	}

	private fun inject() {
		val injector = (application as App).getAppComponent()
		injector.inject(this)
	}
}