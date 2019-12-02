package com.nalexand.friendlocation.main

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.nalexand.friendlocation.R

class MainActivity : AppCompatActivity() {

	private lateinit var appBarConfiguration: AppBarConfiguration

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		setToolbar()
		setFab()
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

	private fun setFab() {
		val fab: FloatingActionButton = findViewById(R.id.fab)
		fab.setOnClickListener { view ->
			Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
				.setAction("Action", null).show()
		}
	}

	private fun setNavigation() {
		val drawerLayout: DrawerLayout = findViewById(R.id.dlMainDrawer)
		val navView: NavigationView = findViewById(R.id.nvMainNavView)
		val navController = findNavController(R.id.nvHostFragment)

		appBarConfiguration = AppBarConfiguration(
			setOf(
				R.id.nav_home,
				R.id.nav_add_member,
				R.id.nav_notes
			), drawerLayout
		)
		setupActionBarWithNavController(navController, appBarConfiguration)
		navView.setupWithNavController(navController)
	}
}