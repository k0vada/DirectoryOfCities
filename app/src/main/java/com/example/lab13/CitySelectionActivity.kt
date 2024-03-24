package com.example.lab13

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class CitySelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_selection_activity)

        val adapter = CityAdapter(Common.cities) { selectedCity ->
            val intent = Intent()
            intent.putExtra("selectedCityTitle", selectedCity.title)
            intent.putExtra("selectedCityRegion", selectedCity.region)
            intent.putExtra("selectedCityLat", selectedCity.lat)
            intent.putExtra("selectedCityLon", selectedCity.lon)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        val recyclerView =findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }


}