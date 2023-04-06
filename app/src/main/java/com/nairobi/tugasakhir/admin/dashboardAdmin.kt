package com.nairobi.tugasakhir.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.pedant.SweetAlert.SweetAlertDialog
import com.nairobi.tugasakhir.R
import com.nairobi.tugasakhir.admin.Kelocuti.listAllCuti
import com.nairobi.tugasakhir.admin.karyawan.ad_tambahKaryawan
import com.nairobi.tugasakhir.admin.karyawan.listKaryawan
import com.nairobi.tugasakhir.auth.LoginPage
import com.nairobi.tugasakhir.superadmin.keladmin.listDataAdmin
import kotlinx.android.synthetic.main.activity_dashboard_admin.*
import kotlinx.android.synthetic.main.activity_login_page_admin.*

class dashboardAdmin : AppCompatActivity(), View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard_admin)
        btnkelolacuti.setOnClickListener(this)
       btnkelolakaryawan.setOnClickListener(this)
        btnkelolaadmin.setOnClickListener(this)
        btn_logout.setOnClickListener(this)
    }
      override fun onClick(p0: View?) {
          when (p0?.id) {
              R.id.btnkelolaabsensi -> {
                  Intent(this, listDataAdmin::class.java).also {

                      startActivity(it)
                      finish()
                  }
              }
              R.id.btnkelolakaryawan -> {
                  Intent(this, listKaryawan::class.java).also {

                      startActivity(it)
                      finish()
                  }
              }
              R.id.btnkelolacuti -> {
                  Intent(this, listAllCuti::class.java).also {

                      startActivity(it)
                      finish()
                  }
              }
              R.id.btnkelolaadmin -> {
                  Intent(this, listDataAdmin::class.java).also {

                      startActivity(it)
                      finish()
                  }
              }
              R.id.btn_logout -> {
//                Toast.makeText(this, "tes", Toast.LENGTH_SHORT).show()
                  SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                      .setTitleText("Apakah anda yakin keluar aplikasi?")
                      .setConfirmButton("Ya", SweetAlertDialog.OnSweetClickListener {
                          val intent = Intent(this, LoginPage::class.java)
                          startActivity(intent)
                          finish()
                      })
                      .setCancelButton("Tidak", SweetAlertDialog.OnSweetClickListener {
                          it.dismissWithAnimation()
                      })
                      .show()
              }
          }

    }
}
