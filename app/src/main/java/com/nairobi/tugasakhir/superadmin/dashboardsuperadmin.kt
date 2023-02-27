package com.nairobi.tugasakhir.superadmin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import cn.pedant.SweetAlert.SweetAlertDialog
import com.nairobi.tugasakhir.R
import com.nairobi.tugasakhir.superadmin.keladmin.listDataAdmin
import com.nairobi.tugasakhir.superadmin.keladmin.sp_tambahAdmin
import kotlinx.android.synthetic.main.activity_dashboardsuperadmin.*
import kotlinx.android.synthetic.main.activity_login_page_admin.*

class dashboardsuperadmin : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboardsuperadmin)

           btnkelolakantor.setOnClickListener(this)
       btnkelolakaryawan.setOnClickListener(this)
        btnkelolaadmin.setOnClickListener(this)
        btn_logout.setOnClickListener(this)

    }
    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btnkelolakantor -> {
                Intent(this, listDataAdmin::class.java).also {

                    startActivity(it)
                    finish()
                }
            }
            R.id.btnkelolakaryawan -> {
                Intent(this, sp_tambahAdmin::class.java).also {

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
                        val intent = Intent(this, login::class.java)
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