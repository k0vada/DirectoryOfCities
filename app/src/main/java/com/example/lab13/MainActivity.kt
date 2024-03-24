package com.example.lab13

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.startActivity

class MainActivity : AppCompatActivity() {

    var selectedCityTitle : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Common.initCities(this)

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                displaySelectedCity(data)
                selectedCityTitle = data?.getStringExtra("selectedCityTitle")!!

            }
        }

        val buttonChoose: Button = findViewById(R.id.buttonChooseCity)
        buttonChoose.setOnClickListener {
            val intent = Intent(this, CitySelectionActivity::class.java)
            resultLauncher.launch(intent)
        }

         val buttonShowOnMap: Button = findViewById(R.id.buttonShowOnMap)
         buttonShowOnMap.setOnClickListener {

             val selectedCity = Common.cities.find { it.title == selectedCityTitle }
             selectedCity?.let { city ->
                 val data = Uri.parse("geo:${city.lat},${city.lon}?q=${Uri.encode(city.title)}")
                 val coordinatesIntent = Intent(Intent.ACTION_VIEW, data)
                 try {
                     startActivity(coordinatesIntent)
                 } catch (e: ActivityNotFoundException) {
                     Toast.makeText(this@MainActivity, "Приложение для работы с координатами не найдено", Toast.LENGTH_SHORT).show()
                 }
             }
         }
    }

    private fun displaySelectedCity(data: Intent?) {
        val selectedCity = data?.getStringExtra("selectedCityTitle") //Получаем название выбранного города из данных интента

        selectedCity?.let { cityTitle ->
            val city = Common.cities.find { it.title == cityTitle }
            city?.let { displayCityInfo(it) }
        }
    }

    private fun displayCityInfo(city: City) {
        val textCity = findViewById<TextView>(R.id.textCity)
        val textFedOkr = findViewById<TextView>(R.id.textFedOkr)
        val textReg = findViewById<TextView>(R.id.textReg)
        val textMail = findViewById<TextView>(R.id.textMail)
        val textHours = findViewById<TextView>(R.id.textHours)
        val textPopul = findViewById<TextView>(R.id.textPopul)
        val textDate = findViewById<TextView>(R.id.textDate)

        textCity.text = "Город: ${city.title}"
        textFedOkr.text = "Федеральный округ: ${city.district}"
        textReg.text = "Регион: ${city.region}"
        textMail.text = "Почтовый индекс: ${city.postalCode}"
        textHours.text = "Часовой пояс: ${city.timezone}"
        textPopul.text = "Население: ${city.population}"
        textDate.text = "Дата основания: ${city.founded}"
    }
}