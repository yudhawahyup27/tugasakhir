package com.nairobi.tugasakhir.karyawan.kelabsen

import android.Manifest
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.nairobi.tugasakhir.R
import kotlinx.android.synthetic.main.activity_absen.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class Absen : AppCompatActivity() {
    val COLLECTION = "User"
    var F_NamaAbsen = "NamaAbsen"
    val F_TanggalWaktu = "TGLWaktu"
    var F_Lokasi = "lokasi"
    val F_Keterangan = "keterangan"
    var REQ_CAMERA = 101
    var strCurrentLatitude = 0.0
    var strCurrentLongitude = 0.0
    var strLatitude = "0"
    var strLongitude = "0"
//    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_absen)
//        setCurrentLocation()
    setInitLayout()
}
    private fun setInitLayout() {
        inputTanggal.setOnClickListener {
            val tanggalAbsen = Calendar.getInstance()
            val date =
                DatePickerDialog.OnDateSetListener { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    tanggalAbsen[Calendar.YEAR] = year
                    tanggalAbsen[Calendar.MONTH] = monthOfYear
                    tanggalAbsen[Calendar.DAY_OF_MONTH] = dayOfMonth
                    val strFormatDefault = "dd MMMM yyyy HH:mm"
                    val simpleDateFormat = SimpleDateFormat(strFormatDefault, Locale.getDefault())
                    inputTanggal.setText(simpleDateFormat.format(tanggalAbsen.time))
                }
            DatePickerDialog(
                this@Absen, date,
                tanggalAbsen[Calendar.YEAR],
                tanggalAbsen[Calendar.MONTH],
                tanggalAbsen[Calendar.DAY_OF_MONTH]
            ).show()
        }
    }
//    private fun setCurrentLocation() {
//        progressDialog.show()
//        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//        if (ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//            && ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return
//        }
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener(this) { location ->
//                progressDialog.dismiss()
//                if (location != null) {
//                    strCurrentLatitude = location.latitude
//                    strCurrentLongitude = location.longitude
//                    val geocoder = Geocoder(this@Absen, Locale.getDefault())
//                    try {
//                        val addressList =
//                            geocoder.getFromLocation(strCurrentLatitude, strCurrentLongitude, 1)
//                        if (addressList != null && addressList.size > 0) {
//                            F_Lokasi = addressList[0].getAddressLine(0)
//                            inputLokasi.setText(F_Lokasi)
//                        }
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
//                } else {
//                    progressDialog.dismiss()
//                    Toast.makeText(this@Absen,
//                        "Ups, gagal mendapatkan lokasi. Silahkan periksa GPS atau koneksi internet Anda!",
//                        Toast.LENGTH_SHORT).show()
//                    strLatitude = "0"
//                    strLongitude = "0"
//                }
//            }
//    }

}