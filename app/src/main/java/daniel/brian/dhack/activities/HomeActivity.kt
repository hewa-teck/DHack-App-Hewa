package daniel.brian.dhack.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import daniel.brian.dhack.data.SensorData
import daniel.brian.dhack.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // function to collect data
        collectData()

        // navigating to maps
        binding.btnMap.setOnClickListener {
            val url = "https://shorturl.at/m8hwX"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        // logout
        binding.toolBar.setOnClickListener{
            val intent = Intent(this,MapActivity::class.java)
            startActivity(intent)
        }

    }

    private fun collectData() {
        val database = FirebaseDatabase.getInstance()
        val db = database.getReference("Sensor")

        db.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                // Check for expected structure (e.g., a map with keys matching properties)
                if (snapshot.value is Map<*, *>) {
                    val dataMap = snapshot.value as Map<*, *>
                    val no2 = dataMap["NO2"]?.toString() ?: "N/A"
                    val co = dataMap["CO"]?.toString() ?: "N/A"
                    val ethylAlcohol = dataMap["ETHYL_ALCOHOL"]?.toString() ?: "N/A"
                    val voc = dataMap["VOC"]?.toString() ?: "N/A"

                    binding.nitrogen.text = no2
                    binding.carbon.text = co
                    binding.ethyl.text = ethylAlcohol
                    binding.voc.text = voc

                    if (co > 400.toString()){
                        binding.overallValue.text = "High levels of CO detected"
                        binding.overallValue.setTextColor(ContextCompat.getColor(this@HomeActivity, android.R.color.holo_red_dark))
                    }else{
                        binding.overallValue.text = "Moderate CO levels"
                    }

                } else {
                    // Handle unexpected structure (log error, display a message)
                    Toast.makeText(this@HomeActivity,"Unexpected data structure in Sensor",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@HomeActivity,error.message,Toast.LENGTH_LONG).show()
            }

        })
    }
}