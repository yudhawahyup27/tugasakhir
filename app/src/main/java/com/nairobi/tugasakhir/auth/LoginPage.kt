package com.nairobi.tugasakhir.auth

import android.content.Intent
import cn.pedant.SweetAlert.SweetAlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.nairobi.tugasakhir.R
import com.nairobi.tugasakhir.admin.dashboardAdmin
import com.nairobi.tugasakhir.karyawan.Dashboardkaryawan
import com.nairobi.tugasakhir.superadmin.dashboardsuperadmin
import kotlinx.android.synthetic.main.activity_login_page_admin.*



class LoginPage : AppCompatActivity(), View.OnClickListener {


    val COLLECTION = "User"
    val F_USERNAME = "username"
    val F_PASSWORD = "password"
    val F_NAMA = "nama"
    val F_ROLE = "role"
    val spuser= "karyawan"
    val sppass =  "karyawan"

    var a = ""
    var b = ""
    var c = ""
    var d = ""

    lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page_admin)

        btn_logins.setOnClickListener(this)


    }

    override fun onStart() {
        super.onStart()
        db = FirebaseFirestore.getInstance()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_logins -> {

                val username = txin_username.text.toString()
                val password = txin_password.text.toString()

                db.collection(COLLECTION).get().addOnSuccessListener { result ->
                    for (doc: QueryDocumentSnapshot in result) {
                        if (doc.get(F_USERNAME).toString() == username) {
                            a = doc.get(F_USERNAME).toString()
                            b = doc.get(F_PASSWORD).toString()
                            c = doc.get(F_NAMA).toString()
                            d = doc.get(F_ROLE).toString()
                        }
                    }
                    if (username.isEmpty() && password.isEmpty()) {
                        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Masukkan Username dan Password terlebih dahulu")
                            .show()
//                        Toast.makeText(this, "Masukkan Username dan Password terlebih dahulu", Toast.LENGTH_SHORT).show()
                    }
                    else if (username == spuser && password == sppass ) {
                        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Login Berhasil")

                                .setConfirmButton("Masuk", SweetAlertDialog.OnSweetClickListener {
                                    Intent(this, Dashboardkaryawan::class.java).also {
                                        it.putExtra("karyawan", a)
                                        it.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(it)
                                        finish()
                                    }
                                })
                                .show()
                    }
                    else if (username == a && password == b) {
                        if (d == "admin") {
                            SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Login Berhasil")
                                .setConfirmButton("Masuk", SweetAlertDialog.OnSweetClickListener {
                                    Intent(this, dashboardAdmin::class.java).also {
                                        it.putExtra("emailPetugas", a)
                                        it.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(it)
                                        finish()
                                    }
                                })
                                .show()
                        } else if (d == "karyawan") {
                            SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Login Berhasil")
                                .setConfirmButton("Masuk", SweetAlertDialog.OnSweetClickListener {
                                    Intent(this, Dashboardkaryawan::class.java).also {
                                        it.putExtra("nikUser", a)
                                        it.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(it)
                                        finish()
                                    }
                                })
                                .show()


                        }
                    } else {
                            SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Username atau Password salah")
                                .show()
//                        Toast.makeText(this, "Username atau Password salah", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        }
    }
}