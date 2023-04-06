package com.nairobi.tugasakhir.admin.Kelocuti

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.nairobi.tugasakhir.R
import kotlinx.android.synthetic.main.activity_acc_cuti.*
import com.nairobi.tugasakhir.superadmin.keladmin.listDataAdmin
import kotlinx.android.synthetic.main.activity_acc_cuti.btn_deletecuti
import kotlinx.android.synthetic.main.activity_acc_cuti.radiogroup
import kotlinx.android.synthetic.main.activity_acc_cuti.rbsetuju
import kotlinx.android.synthetic.main.activity_acc_cuti.rbtdk
import kotlinx.android.synthetic.main.activity_acc_cuti.rg_cuti
import kotlinx.android.synthetic.main.activity_acc_cuti.title_cuti
import kotlinx.android.synthetic.main.activity_acc_cuti.txin_NikKaryawan
import kotlinx.android.synthetic.main.activity_acc_cuti.txin_lamaCuti
import kotlinx.android.synthetic.main.activity_acc_cuti.txin_namaKaryawan
import kotlinx.android.synthetic.main.activity_acc_cuti.txin_tglakhir
import kotlinx.android.synthetic.main.activity_acc_cuti.txin_tglawal
import kotlinx.android.synthetic.main.activity_acc_cuti.txin_tujuanCuti
import kotlinx.android.synthetic.main.activity_tb_cuti.*
import java.util.*
import kotlin.collections.HashMap

class accCuti : AppCompatActivity(), View.OnClickListener{
    lateinit var db1 : CollectionReference
    lateinit var db : FirebaseFirestore


    val COLLECTION = "cuti"
    val F_NAMAKARYAWANCUTI = "namacuti"
    val idcuti= "CutiID"
    val F_NIKCUTI = "NIKCuti"
    val F_LAMACUTI = "lamacuti"
    val F_TUJUANCUTI = "tujuancuti"
    val F_TGLCUTIAWAL = "tglcutiawal"
    val F_TGLCUTIAKHIR = "tglcutiakhir"
    val F_status = "statuscuti"
    val F_CURRENTTIME = "current_time"

    var docId = ""
    var tglawal = ""
    var tglakhir = ""
    var nikUser = ""
    var nama = ""
    var nik =""

    var id = ""
    var status_cuti = ""
    var lamacuti = ""
    var tujuancuti =""
    var awalcuti =""
    var akhircuti = ""
    var statuscuti = ""
    var emailAdmin = ""
    var code = ""
    var tahun = 0
    var bulan = 0
    var hari = 0
    var tahun1 = 0
    var bulan1 = 0
    var hari1 = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acc_cuti)

        val cal: Calendar = Calendar.getInstance()
        tahun = cal.get(Calendar.YEAR)
        bulan = cal.get(Calendar.MONTH)
        hari = cal.get(Calendar.DAY_OF_MONTH)
        backdasboard_accCuti.setOnClickListener(this)
            viewCondition()
        var paket: Bundle? = intent.extras
        code = (paket?.getString("code")!!)

    }
        var ubahTanggal = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            tglawal = "$dayOfMonth - ${month + 1} - $year"
            txin_tglawal.setText(tglawal)
            tahun = year
            bulan = month + 1
            hari = dayOfMonth
        }

        var ubahTanggal1 = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            tglakhir = "$dayOfMonth - ${month + 1} - $year"
            txin_tglakhir.setText(tglakhir)
            tahun1 = year
            bulan1 = month + 1
            hari1= dayOfMonth
        }

        override fun onCreateDialog(id: Int): Dialog {
            when (id) {
                10 -> return DatePickerDialog(this, ubahTanggal, tahun, bulan, hari)
                11 -> return DatePickerDialog(this, ubahTanggal1, tahun1, bulan1, hari1)
            }
            return super.onCreateDialog(id)
        }

        private fun viewCondition() {
            if (code == "0"){
                var paket: Bundle? = intent.extras
                emailAdmin = (paket?.getString("CutiID")!!)
            }
            //kondisi untuk detail warga
            if (code == "1") {
                var paket: Bundle? = intent.extras
                emailAdmin = (paket?.getString("CutiID")!!)
                docId = (paket?.getString("id")!!)
                btn_deletecuti.visibility = View.VISIBLE
                txin_NikKaryawan.isEnabled = false
                radiogroup.visibility = View.VISIBLE
            }
        }
    override fun onStart() {
        super.onStart()
        if (code == "0"){
            db = FirebaseFirestore.getInstance()
        } else if (code == "1") {
            db = FirebaseFirestore.getInstance()
            db1 = FirebaseFirestore.getInstance().collection(COLLECTION)
            db1.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    firebaseFirestoreException.message?.let {
                        Log.e("firestore :", it)
                    }
                }
                detailCuti()
            }
        }
    }

    private fun detailCuti() {

        title_cuti.setText("DETAIL CUTI")

        val showDetail = db1.whereEqualTo("CutiID", code )
        showDetail.get().addOnSuccessListener { result ->
            for (doc in result) {
                nama = doc.get(F_NAMAKARYAWANCUTI).toString()
                nik = doc.get(F_NIKCUTI).toString()
                tglawal = doc.get(F_TGLCUTIAWAL).toString()
                tglakhir = doc.get(F_TGLCUTIAKHIR).toString()
                tujuancuti = doc.get(F_TUJUANCUTI).toString()
                lamacuti = doc.get(F_LAMACUTI).toString()

                txin_namaKaryawan.setText(nama)
                txin_tglawal.setText(tglawal)
                txin_NikKaryawan.setText(nik)
                txin_tglakhir.setText(tglakhir)
                txin_tujuanCuti.setText(tujuancuti)
                txin_lamaCuti.setText(lamacuti)
            }
            rbCheckedCondition()
        }
        nik = txin_NikKaryawan.text.toString()
    }
        private fun rbCheckedCondition() {
            //RB Jenis Kelamin
            if (status_cuti == rbsetuju.text.toString()) {
                rbsetuju.isChecked = true
            } else {
                rbtdk.isChecked = true
            }
        }
        private fun radioGroup() {
            rg_cuti.setOnCheckedChangeListener { radioGroup, i ->
                var rb = findViewById<RadioButton>(i)
                if (rb != null)
                    status_cuti = rb.text.toString()
            }
        }
        override fun onClick(p0: View?) {
            when(p0?.id) {
            R.id.backdasboard_accCuti -> {
                Intent(this, listDataAdmin::class.java).also {
                    it.putExtra("emailAdmin", emailAdmin)
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                    finish()
                }
            }
                R.id.btn_tglawal-> showDialog ( 10)
                R.id.btn_tglakhir-> showDialog ( 11)
                R.id.btn_savecuti -> {
                    tujuancuti = txin_tujuanCuti.text.toString()
                    awalcuti = txin_tglawal.text.toString()
                    akhircuti = txin_tglakhir.text.toString()
                    lamacuti = txin_lamaCuti.text.toString()
                    nama = txin_namaKaryawan.text.toString()

                    if (awalcuti.isEmpty() || tujuancuti.isEmpty() || lamacuti.isEmpty() || akhircuti.isEmpty() ) {
                        SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Lengkapi data terlebih dahulu!")
                            .show()
                    }
                    else {
                        val hm1 = HashMap<String, Any>()
                        hm1.set(F_NAMAKARYAWANCUTI, nama)
                        hm1.set(F_NIKCUTI, nikUser)
                        hm1.set(F_TUJUANCUTI, txin_tujuanCuti.text.toString())
                        hm1.set(F_LAMACUTI, txin_lamaCuti.text.toString())
                        hm1.set(F_TGLCUTIAWAL, txin_tglawal.text.toString())
                        hm1.set(F_TGLCUTIAKHIR, txin_tglakhir.text.toString())
                        hm1.set (F_status, status_cuti)
                        db.collection(COLLECTION).document(nikUser).set(hm1)
                            .addOnSuccessListener {
                                SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Pengajuan Cuti Berhasil")
                                    .setConfirmClickListener {
                                        Intent(this, listDataAdmin::class.java).also {
                                            it.putExtra("emailAdmin", emailAdmin)
                                            it.flags =
                                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            startActivity(it)
                                            finish()
                                        }
                                    }
                                    .show()
                            }.addOnFailureListener { e ->
                                SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("Data admin gagal disimpan!")
                                    .show()
                            }
                    }
                }
                R.id.btn_delete_Admin -> {
                    SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Apakah anda yakin untuk menghapus data?")
                        .setConfirmButton("Ya", SweetAlertDialog.OnSweetClickListener {
                            db.collection(COLLECTION).whereEqualTo(F_NIKCUTI,nikUser).get().addOnSuccessListener {
                                    result ->
                                for (doc : QueryDocumentSnapshot in result){
                                    db.collection(COLLECTION).document(doc.id).delete()
                                        .addOnSuccessListener {
                                            SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                                .setTitleText("Data Cuti berhasil dihapus")
                                                .setConfirmClickListener {
                                                    Intent(this, listDataAdmin::class.java).also {
                                                        it.putExtra("emailAdmin", emailAdmin)
                                                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                        startActivity(it)
                                                        finish()
                                                    }
                                                }
                                                .show()
                                        }
                                }
                            }
                            db.collection("cuti").whereEqualTo(F_NIKCUTI,txin_NikKaryawan.text.toString()).get()
                        })
                        .setCancelButton("Batal", SweetAlertDialog.OnSweetClickListener {
                            it.dismissWithAnimation()
                        })
                        .show()
                }
            }
        }
}