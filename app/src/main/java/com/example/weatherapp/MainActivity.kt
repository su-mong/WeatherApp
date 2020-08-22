package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException

/*
    Weather ID 대분류
    2XX : 천둥번개 (비 오거나 안 오거나 다 포함)
    3xx: 가랑비
    5xx: 비(폭우 포함)
    6xx: 눈
    7xx: 안개
    800: 맑음
    80x: 구름
 */

/*
    Weather ID 상세분류 (여기서는 적용하지 않음.)
    801 : 구름 적음(11~25%)
    802 : 구름 좀 있음(25~50%)
    803 : 구름 꽤 있음(51-84%)
    804 : 구름 많음(85-100%)
 */

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var requestQueue = Volley.newRequestQueue(this)

        val url = "https://api.openweathermap.org/data/2.5/weather?q=Sejong&appid=4266d62a6df9ad4e37d140731842f5ba";
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener {
                response -> try {
                    val main_weather = response.getJSONArray("weather").getJSONObject(0).getInt("id")
                    val temp = (response.getJSONObject("main").getDouble("temp") - 273.15).toInt()
                    val humidity = response.getJSONObject("main").getDouble("humidity")
                    val wind_speed = response.getJSONObject("wind").getDouble("speed")
                    val clouds_all = response.getJSONObject("clouds").getDouble("all")

                    tvTemp.text = temp.toString()+"℃"
                    tvHumidity.text = humidity.toString()+"%"
                    tvWind.text = wind_speed.toString()+"m/s"
                    tvCloud.text = clouds_all.toString()+"%"

                    if(main_weather <= 299) { //2XX : 천둥번개 (비 오거나 안 오거나 다 포함)
                        ivBack.setImageResource(R.drawable.stomy01)
                    } else if(main_weather <= 599) { //3xx: 가랑비, 5xx: 비(폭우 포함)
                        ivBack.setImageResource(R.drawable.rainy04)
                    } else if(main_weather <= 699) { //6xx: 눈
                        ivBack.setImageResource(R.drawable.snowy01)
                    } else if(main_weather <= 799) { //7xx: 안개
                        ivBack.setImageResource(R.drawable.cloudy02)
                    } else if(main_weather == 800) { //800: 맑음
                        ivBack.setImageResource(R.drawable.sunny03)
                    } else { //80x: 구름
                        ivBack.setImageResource(R.drawable.cloudy01)
                    }

                    progressBar.visibility = View.GONE
                    ivBack.visibility = View.VISIBLE
                    ivTop.visibility = View.VISIBLE
                    ivBottom.visibility = View.VISIBLE
                    tvAddress.visibility = View.VISIBLE
                    tvTemp.visibility = View.VISIBLE
                    tvWind.visibility = View.VISIBLE
                    tvCloud.visibility = View.VISIBLE
                    tvHumidity.visibility = View.VISIBLE
                } catch (e: JSONException) {
                    Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            }, Response.ErrorListener {
                    error -> Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            })

        requestQueue.add(request)
    }
}