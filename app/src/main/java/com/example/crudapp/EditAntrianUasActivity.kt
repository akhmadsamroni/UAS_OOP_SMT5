package com.example.crudapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.crudapp.Database.AppRoomDB
import com.example.crudapp.Database.Constant
import com.example.crudapp.Database.AntrianUas
import kotlinx.android.synthetic.main.activity_edit_antrian_uas.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditAntrianUasActivity : AppCompatActivity() {

    val db by lazy { AppRoomDB(this) }
    private var antrianId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_antrian_uas)
        setupListener()
        setupView()
    }

    fun setupListener(){
        btn_saveAntrianuas.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.AntrianDao().addAntrian(
                    AntrianUas(0, txt_nama.text.toString(), Integer.parseInt(txt_no.text.toString()), Integer.parseInt(txt_kelas.text.toString()) )
                )
                finish()
            }
        }
        btn_updateAntrianuas.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                db.AntrianDao().updateAntrian(
                    AntrianUas(antrianId, txt_nama.text.toString(), Integer.parseInt(txt_no.text.toString()), Integer.parseInt(txt_kelas.text.toString()) )
                )
                finish()
            }
        }
    }

    fun setupView() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val intentType = intent.getIntExtra("intent_type", 0)
        when (intentType) {
            Constant.TYPE_CREATE -> {
                btn_updateAntrianuas.visibility = View.GONE
            }
            Constant.TYPE_READ -> {
                btn_saveAntrianuas.visibility = View.GONE
                btn_updateAntrianuas.visibility = View.GONE
                getAntrianuas()
            }
            Constant.TYPE_UPDATE -> {
                btn_saveAntrianuas.visibility = View.GONE
                getAntrianuas()
            }
        }
    }

    fun getAntrianuas() {
        antrianId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
           val antrians =  db.AntrianDao().getAntrian( antrianId )[0]
            txt_nama.setText( antrians.nama )
            txt_no.setText( antrians.no.toString() )
            txt_kelas.setText( antrians.no.toString() )
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}