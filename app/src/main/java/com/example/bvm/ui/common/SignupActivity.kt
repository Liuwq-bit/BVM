package com.example.bvm.ui.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.app.ProgressDialog
import android.os.Handler
import android.util.Patterns
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.example.bvm.BVMApplication
import com.example.bvm.logic.model.User
import kotlinx.android.synthetic.main.activity_signup.*
import java.text.SimpleDateFormat
import java.util.*


class SignupActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(CommonViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.bvm.R.layout.activity_signup)
        signupBtn.setOnClickListener {
                signup()
        }
        loginLink.setOnClickListener {
            finish()
        }
    }

    fun signup() {
//        Log.d(TAG, "Signup")
        if (!validate()) {
            Toast.makeText(BVMApplication.context, "注册失败，请检查格式", Toast.LENGTH_SHORT).show()
            return
        }
//        signupBtn.setEnabled(false)
//        val progressDialog = ProgressDialog(
//            this@SignupActivity,
//            com.example.bvm.R.style.ThemeOverlay_AppCompat_Dialog
//        )
//        progressDialog.isIndeterminate = true
//        progressDialog.setMessage("Creating Account...")
//        progressDialog.show()
        val name = signupNameText.editText?.text.toString()
        val pwd = signupPwdText1.editText?.text.toString()
//        val password = signupPwdText1.editText?.text.toString()

//         TODO: Implement your own signup logic here.
//        Handler().postDelayed(
//            { // On complete call either onSignupSuccess or onSignupFailed
//                 depending on success
//                onSignupSuccess()
//                 onSignupFailed();
//                progressDialog.dismiss()
//            }, 3000

        val date = Date()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        val newUser = User(name, pwd, "https://pic4.zhimg.com/80/v2-5ef5906b9c7640b31f8e45e0382fe293_720w.jpg", dateFormat.format(date))
        viewModel.insertUser(newUser)

        Toast.makeText(BVMApplication.context, "注册成功，请登录", Toast.LENGTH_SHORT).show()
        finish()
    }


    private fun validate(): Boolean {
        // todo 输入格式提示
        var valid = true
        val name = signupNameText.editText?.text.toString()
        val pwd1 = signupPwdText1.editText?.text.toString()
        val pwd2 = signupPwdText2.editText?.text.toString()
        if (name.isEmpty() || name.length < 3) {
            valid = false
        } else {
            signupNameText.error = null
        }
        if (pwd1.isEmpty()) {
            signupPwdText2.error = "密码格式错误"
            valid = false
        } else {
            signupPwdText2.error = null
        }
        if (pwd1 != pwd2) {
            signupPwdText1.error = "两次输入的密码不一致"
            valid = false
        } else {
            signupPwdText1.error = null
        }
        return valid
    }
}