package com.nairobi.tugasakhir.admin.karyawan

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.nairobi.tugasakhir.R
import kotlinx.android.synthetic.main.activity_ad_tambah_karyawan.*
import java.util.*

class ad_tambahKaryawan : AppCompatActivity(), View.OnClickListener {


    lateinit var db: FirebaseFirestore
    lateinit var db1: CollectionReference

    val COLLECTION = "Karyawan"
    val F_NAMAKARYAWAN = "nama"
    val F_TELEPONKARYAWAN = "telepon_karyawan"
    val F_NIK = "nik"
    val F_TGLLAHIRKARYAWAN = "tgl_lahir"
    val F_ALAMATKARTAWAN = "Alamar_kar"
    val F_JENISKELAMINKARYAWAN = "jenis_kelamin"

    val COLLECTION1 = "User"
    val F_USERNAME = "username"
    val F_PASSWROD = "password"
    val F_NAMAAKUN = "nama"
    val F_ROLE = "role"

    var nik = ""
    var nama = ""
    var tgllhr = ""
    var tlp = ""
    var alamat = ""
    var jenis_kelamin = ""

    var tahun = 0
    var bulan = 0
    var hari = 0
    var code = ""
    var docId = ""
    var emailAdmin = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_tambah_karyawan)
        var paket: Bundle? = intent.extras
        code = (paket?.getString("code")!!)
        val cal: Calendar = Calendar.getInstance()
        tahun = cal.get(Calendar.YEAR)
        bulan = cal.get(Calendar.MONTH)
        hari = cal.get(Calendar.DAY_OF_MONTH)
        btn_tgllahir.setOnClickListener(this)
        btn_backdasboard_tambahkaryawan.setOnClickListener(this)
        btn_save_karyawan.setOnClickListener(this)
        btn_delete_karyawan.setOnClickListener(this)

        radioGroup()
        viewCondition()
    }

    var ubahTanggal = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        tgllhr = "$dayOfMonth - ${month + 1} - $year"
        txin_tgllahir.setText(tgllhr)

        tahun = year
        bulan = month + 1
        hari = dayOfMonth
    }

    override fun onCreateDialog(id: Int): Dialog {
        when (id) {
            10 -> return DatePickerDialog(this, ubahTanggal, tahun, bulan, hari)
        }
        return super.onCreateDialog(id)
    }

    override fun onStart() {
        super.onStart()
        db = FirebaseFirestore.getInstance()
        if (code == "0") {
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
                detailKaryawan()
            }
        }
    }


    private fun viewCondition() {
        //kondisi untuk tambah warga
        if (code == "0") {
            var paket: Bundle? = intent.extras
            emailAdmin = (paket?.getString("emailAdmin")!!)
        }
        //kondisi untuk detail warga
        else if (code == "1") {
            var paket: Bundle? = intent.extras
            emailAdmin = (paket?.getString("emailAdmin")!!)
            docId = (paket?.getString("nik")!!)
            btn_delete_karyawan.visibility = View.VISIBLE
            txin_nik.isEnabled = false
        }
    }
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_backdasboard_tambahkaryawan -> {
                Intent(this, listKaryawan::class.java).also {
                    it.putExtra("emailadmin", emailAdmin)
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                    finish()
                }
            }
            R.id.btn_tgllahir -> showDialog ( 10)
            R.id.btn_save_karyawan -> {

                nik = txin_nik.text.toString()
                nama = txin_nama.text.toString()
                alamat = txin_alamat_kar.text.toString()
                tlp = txin_telepon.text.toString()


                if (nik.isEmpty() || nama.isEmpty() || tgllhr.isEmpty() || jenis_kelamin.isEmpty() || alamat.isEmpty() || tlp.isEmpty()) {
                    SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Lengkapi data terlebih dahulu!")
                        .show()
                } else {
                    val hm = HashMap<String, Any>()
                    hm.set(F_NIK, txin_nik.text.toString())
                    hm.set(F_NAMAKARYAWAN, txin_nama.text.toString())
                    hm.set(F_TGLLAHIRKARYAWAN, txin_tgllahir.text.toString())
                    hm.set(F_ALAMATKARTAWAN, txin_alamat_kar.text.toString())
                    hm.set(F_TELEPONKARYAWAN, txin_telepon.text.toString())
//                    hm.set(F_JABATANKAR, jabaran_kar.text.to)
                    hm.set(F_JENISKELAMINKARYAWAN, jenis_kelamin)

                    val hm1 = HashMap<String, Any>()
                    hm1.set(F_USERNAME, txin_nik.text.toString())
                    hm1.set(F_PASSWROD, txin_nama.text.toString())
                    hm1.set(F_NAMAAKUN, txin_nama.text.toString())
                    hm1.set(F_ROLE, "karyawan")
                    db.collection(COLLECTION).document(txin_nik.text.toString()).set(hm)
                        .addOnSuccessListener {
                            SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Data Karyawan berhasil disimpan")
                                .setConfirmClickListener {
                                    Intent(this, listKaryawan::class.java).also {
                                        it.putExtra("emailadmin", emailAdmin)
                                        it.flags =
                                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        startActivity(it)
                                        finish()
                                    }
                                }
                                .show()
                        }.addOnFailureListener { e ->
                            SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Data Karyawan gagal disimpan!")
                                .show()
                        }
                    db.collection(COLLECTION1).document(txin_nik.text.toString()).set(hm1)
                }
            }
            R.id.btn_delete_karyawan -> {
                SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Apakah anda yakin untuk menghapus data?")
                    .setConfirmButton("Ya", SweetAlertDialog.OnSweetClickListener {
                        db.collection(COLLECTION).whereEqualTo(F_NIK,docId).get().addOnSuccessListener {
                                result ->
                            for (doc : QueryDocumentSnapshot in result){
                                db.collection(COLLECTION).document(doc.id).delete()
                                    .addOnSuccessListener {
                                        SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Data Karyawan berhasil dihapus")
                                            .setConfirmClickListener {
                                                Intent(this, listKaryawan::class.java).also {
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
                        db.collection("User").whereEqualTo(F_USERNAME,txin_nik.text.toString()).get()
                    })
                    .setCancelButton("Batal", SweetAlertDialog.OnSweetClickListener {
                        it.dismissWithAnimation()
                    })
                    .show()
            }
        }

    }
    private fun detailKaryawan() {

        tx_title.setText("DETAIL KARYAWAN")

        val showDetail = db1.whereEqualTo("nik", docId)
        showDetail.get().addOnSuccessListener { result ->
            for (doc in result) {
                nik = docId
                nama = doc.get(F_NAMAKARYAWAN).toString()
                tgllhr = doc.get(F_TGLLAHIRKARYAWAN).toString()
                jenis_kelamin = doc.get(F_JENISKELAMINKARYAWAN).toString()
                alamat = doc.get(F_ALAMATKARTAWAN).toString()
                tlp = doc.get(F_TELEPONKARYAWAN).toString()

                txin_nik.setText(docId)
                txin_nama.setText(nama)
                txin_tgllahir.setText(tgllhr)
                txin_telepon.setText(tlp)
                txin_alamat_kar.setText(alamat)
            }
            rbCheckedCondition()
        }
        nik = txin_nik.text.toString()
        nama = txin_nama.text.toString()
        tgllhr = txin_tgllahir.text.toString()
        jenis_kelamin
        tlp = txin_telepon.text.toString()
        alamat = txin_alamat_kar.text.toString()

    }
    private fun rbCheckedCondition() {
        //RB Jenis Kelamin
        if (jenis_kelamin == rbLaki.text.toString()) {
            rbLaki.isChecked = true
        } else {
            rbPr.isChecked = true
        }
    }
    private fun radioGroup() {
        rg_jeniskelamin.setOnCheckedChangeListener { radioGroup, i ->
            var rb = findViewById<RadioButton>(i)
            if (rb != null)
                jenis_kelamin = rb.text.toString()
        }
    }
}

