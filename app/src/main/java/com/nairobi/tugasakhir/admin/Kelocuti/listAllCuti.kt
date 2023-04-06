package com.nairobi.tugasakhir.admin.Kelocuti

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.SimpleAdapter
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot

import com.nairobi.tugasakhir.R
import com.nairobi.tugasakhir.admin.dashboardAdmin
import com.nairobi.tugasakhir.admin.karyawan.ad_tambahKaryawan
import com.nairobi.tugasakhir.karyawan.kelcuti.tbCuti
import kotlinx.android.synthetic.main.activity_list_all_cuti.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class listAllCuti : AppCompatActivity(), View.OnClickListener {

    lateinit var db : FirebaseFirestore
    lateinit var db1 : CollectionReference
    lateinit var alCuti :ArrayList<HashMap<String,Any>>
    lateinit var adaptercuti : SimpleAdapter

    var nikUser = ""
    val COLLECTION1 = "User"
    var F_USERNAME = "username"
    val F_PASSWORD = "password"
    val F_NAMA = "nama"
    val F_ROLE = "role"


    val kal   = Calendar.getInstance()
    val year  = kal[Calendar.YEAR]
    val month = kal[Calendar.MONTH]+1
    val day   = kal[Calendar.DAY_OF_MONTH]

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

    var emailAdmin = ""
    var docId = ""

    var searchId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_all_cuti)

        alCuti = ArrayList()

        btn_tambahcuti.setOnClickListener(this)
        lscuti.setOnItemClickListener(itemClick)
        btn_backdasboard_cuti.setOnClickListener(this)
//        var code: Bundle? = intent.extras
//        nikUser = (code?.getString("nikUser")!!)
        search()
    }

    val itemClick = AdapterView.OnItemClickListener { parent, view, position, id ->
        val hm : HashMap<String,Any> = alCuti.get(position)
        docId  = hm.get(idcuti).toString()
        Intent(this, accCuti::class.java).also {
            it.putExtra("code", "1")
            it.putExtra("id", docId)
            it.putExtra("CutiID", emailAdmin)
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
            finish()
        }
    }
    override fun onStart() {
        super.onStart()
        db = FirebaseFirestore.getInstance()
        db.collection(COLLECTION).addSnapshotListener { querySnapshot, e ->
            if (e != null) Log.d("firestore", e.message.toString())
            showDatacuti()
        }
    }

    fun search(){
        searchView_cuti.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                if (p0 != null) {
                    searchId = p0
//                    searchCode = "1"
                }
                db1 = FirebaseFirestore.getInstance().collection(COLLECTION)
                db1.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                    if (firebaseFirestoreException != null) {
                        firebaseFirestoreException.message?.let {
                            Log.e("firestore :", it)
                        }
                    }
                    showSearch()
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                db = FirebaseFirestore.getInstance()
                db.collection(COLLECTION).addSnapshotListener { querySnapshot, e ->
                    if (e != null) Log.d("firestore", e.message.toString())
                    showDatacuti()
                }
                return false
            }
        })
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.btn_tambahcuti -> {
                Intent(this,tbCuti::class.java).also {
                    it.putExtra("code", "0")
                    it.putExtra("username", emailAdmin)
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                    finish()
                }
            }
            R.id.btn_backdasboard_cuti -> {
                Intent(this, dashboardAdmin::class.java).also {
                    it.putExtra("email_admin", emailAdmin)
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                    finish()
                }
            }
        }
    }
    private fun showSearch(){
        val showDetail = db1.whereEqualTo("namacuti", searchId)
        showDetail.get().addOnSuccessListener { result ->
            alCuti.clear()
            for(doc: QueryDocumentSnapshot in result) {
                val hm = HashMap<String,Any>()
                hm.set(F_NAMAKARYAWANCUTI,doc.get(F_NAMAKARYAWANCUTI).toString())
                hm.set(F_TUJUANCUTI,doc.get(F_TUJUANCUTI).toString())
                hm.set(F_LAMACUTI, doc.get(F_LAMACUTI).toString())
                alCuti.add(hm)
            }
            adaptercuti = SimpleAdapter(this, alCuti, R.layout.allcutilist,
                arrayOf(F_NAMAKARYAWANCUTI, F_TUJUANCUTI, F_LAMACUTI),
                intArrayOf(R.id.ls_namacuti, R.id.ls_tujuan, R.id.ls_lama))
            lscuti.adapter = adaptercuti
            var jml : Int = alCuti.count()
            jmlah_cuti.setText(Integer.toString(jml))
        }
    }
    fun showDatacuti(){
        db.collection(COLLECTION).orderBy("namacuti", Query.Direction.ASCENDING).get().addOnSuccessListener { result ->
            alCuti.clear()
            for(doc: QueryDocumentSnapshot in result) {
                val hm = HashMap<String,Any>()
                hm.set(F_NAMAKARYAWANCUTI,doc.get(F_NAMAKARYAWANCUTI
                ).toString())
                hm.set(F_TUJUANCUTI,doc.get(F_TUJUANCUTI).toString())
                hm.set(F_LAMACUTI,doc.get(F_LAMACUTI).toString())
                hm.set(F_TGLCUTIAWAL,doc.get(F_TGLCUTIAWAL).toString())
                hm.set(F_TGLCUTIAKHIR,doc.get(F_TGLCUTIAKHIR).toString())
                alCuti.add(hm)
            }
            adaptercuti = SimpleAdapter(this, alCuti, R.layout.allcutilist,
                arrayOf(F_NAMAKARYAWANCUTI
                    , F_TUJUANCUTI, F_LAMACUTI,F_TGLCUTIAWAL, F_TGLCUTIAKHIR),
                intArrayOf( R.id.ls_namacuti, R.id.ls_tujuan, R.id.ls_lama, R.id.ls_awal , R.id.ls_akhir))
            lscuti.adapter = adaptercuti

        }
    }

}