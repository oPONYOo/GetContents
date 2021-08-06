package com.example.getcontents.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.getcontents.App.Companion.sharedPref
import com.example.getcontents.R
import com.example.getcontents.assignment.AssignmentInterface
import com.example.getcontents.assignment.AssignmentResult
import com.example.getcontents.databinding.ActivityMainBinding
import com.example.getcontents.extensions.Extensions.startActivityWithFinish
import com.example.getcontents.network.dto.UnitsDto
import com.example.getcontents.network.dto.UserDto
import com.example.getcontents.storage.SharedPref
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var token: String
    val gson = Gson()
    private var dtoList :ArrayList<UnitsDto> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            lifecycleOwner = this@MainActivity
            activity = this@MainActivity
        }
        token = sharedPref.getToken(SharedPref.PREF_KEEP_LOGIN_TOKEN)
        Log.e("tok", token)
        sharedPref.getString(SharedPref.PREF_USER_INFO)?.let {
            val user = gson.fromJson(it, UserDto::class.java)
            binding.userNameTxtView.text = ("${user.name} 님,")
        }
        getAssignment()


    }

    private fun getAssignment() {
        val bToken = "Bearer $token"
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
        val assignmentService: AssignmentInterface =
            retrofit.create(AssignmentInterface::class.java)
        assignmentService.requestAssignment(
            bToken
        ).enqueue(object : Callback<AssignmentResult> {
            override fun onResponse(
                call: Call<AssignmentResult>,
                response: Response<AssignmentResult>
            ) {
                Log.e("res", "${response}")
                if (response.isSuccessful) {
                    val result = response.body()!!.result
                    Log.e("Result", " ${result}")
                    binding.nameTxtView2.text = result[0].name
                    binding.startTxtView2.text = result[0].start
                    binding.endTxtView2.text = result[0].end
                    for (i in 0 until result[0].units!!.size){
                        val dto = result[0].units!![i]
                        dtoList.add(dto)
                    }
                    Log.e("dtoList", "${dtoList}")
                }else {
                    //과제가 없을 경우
                    val dialog = AlertDialog.Builder(this@MainActivity)
                    dialog.setTitle("과제가 없습니다.")
                    dialog.setMessage("담당자에게 문의해주세요.")
                    dialog.setPositiveButton("확인",dialogListener)
                    dialog.show()
                }

            }

            override fun onFailure(call: Call<AssignmentResult>, t: Throwable) {
                Log.e("LOGIN", "$t")
                val dialog = AlertDialog.Builder(this@MainActivity)
                dialog.setTitle("에러")
                dialog.setMessage("호출실패했습니다.")
                dialog.show()
            }

        })
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.cardLayout -> {
                val intent =
                    Intent(this, ContentsListActivity::class.java)
                intent.apply {
                    putExtra(EXTRA_TITLE, dtoList)
                }
                startActivity(intent)
            }
        }
    }

    fun toast_p() {
        Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
        startActivityWithFinish(LoginActivity::class.java)
    }
    var dialogListener = object: DialogInterface.OnClickListener{
        override fun onClick(dialog: DialogInterface?, which: Int) {
            when(which){
                DialogInterface.BUTTON_POSITIVE ->
                    toast_p()
            }
        }
    }

    var lastTimeBackPressed : Long = 0
    override fun onBackPressed() {
        if(System.currentTimeMillis() - lastTimeBackPressed >= 1500){
            lastTimeBackPressed = System.currentTimeMillis()
            Toast.makeText(this,"'뒤로' 버튼을 한번 더 누르시면 종료됩니다.",Toast.LENGTH_LONG).show() }
        else {
            ActivityCompat.finishAffinity(this)
            System.runFinalization()
            System.exit(0)
        }
    }
    companion object {
        private const val BASE_URL = "https://api.super-brain.co.kr/"
        private const val EXTRA_PROGRESS = "EXTRA_PROGRESS"
        private const val EXTRA_IS_FINISHED = "EXTRA_IS_FINISHED"
        private const val EXTRA_SURVEY_LIST = "EXTRA_SURVEY_LIST"
        private const val EXTRA_UNIT = "EXTRA_UNIT"
        private const val EXTRA_ASSIGNMENT = "EXTRA_ASSIGNMENT"
        private const val EXTRA_SECTION = "EXTRA_SECTION"
        private const val EXTRA_TYPE = "EXTRA_TYPE"
        private const val EXTRA_TITLE = "EXTRA_TITLE"
        private const val EXTRA_IMAGE = "EXTRA_IMAGE"

    }
}