package com.codewithharry.countriesapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codewithharry.countriesapp.api.Country
import com.codewithharry.countriesapp.api.CountryRetrofitInstance
import com.codewithharry.countriesapp.roomhelpers.AppDatabase
import com.codewithharry.countriesapp.roomhelpers.CountryEntity

class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity"

    private var countriesRecyclerView: RecyclerView? = null
    private var button: Button? = null
    private var progressBar: ProgressBar? = null

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        countriesRecyclerView = findViewById(R.id.countries_recycler_view)
        countriesRecyclerView?.layoutManager = LinearLayoutManager(this@MainActivity)
       val adapter: CountriesAdapter = CountriesAdapter(this@MainActivity)
        countriesRecyclerView?.adapter = adapter

        button = findViewById(R.id.button)
        button?.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                val db = AppDatabase.getInstance(this@MainActivity)
                val countryDao = db.countryDao()
                countryDao.deleteAll()

                Toast.makeText(this@MainActivity,"Complete data deleted",Toast.LENGTH_SHORT).show()
            }
        }

        lifecycleScope.launchWhenCreated {
            val db = AppDatabase.getInstance(this@MainActivity)
            val countryDao = db.countryDao()
            val response = try {
                CountryRetrofitInstance.retrofit.getRides()
            } catch (e: Exception) {
                val countriesList: List<CountryEntity> = countryDao.getAll()
                progressBar?.visibility = View.INVISIBLE
                adapter.setCountryEntityList(countriesList)
                return@launchWhenCreated
            }

            if(response.isSuccessful && response.body() != null) {
                val countriesList: List<Country> = response.body()!!
                val countryEntityList: ArrayList<CountryEntity> = ArrayList()
                for(items in countriesList){
                    countryEntityList.add(CountryEntity(
                        0,
                        items.name?.common,
                        items.capital?.get(0),
                        items.flags?.png,
                        items.region,
                        items.subregion,
                        items.population
                    ))
                }
                countryDao.insertAll(countryEntityList)
                progressBar?.visibility = View.INVISIBLE
                adapter.setCountryList(countriesList)
            } else {
                Toast.makeText(this@MainActivity,"Some error occurred",Toast.LENGTH_SHORT).show()
            }
        }

    }
}