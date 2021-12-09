package com.example.bvm.ui.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.bvm.BVMApplication
import com.example.bvm.logic.model.User
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    val viewModel by lazy { ViewModelProviders.of(this).get(CommonViewModel::class.java) }
    lateinit var user: User
    var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.bvm.R.layout.activity_login)


        viewModel.userLiveData.observe(this, Observer { result ->   // 动态查询数据
            val users = result.getOrNull()
            if (users != null) {
                val pwd = userPwdText.editText?.text.toString()
                for (i in 0..users.size-1) {
//                    Toast.makeText(BVMApplication.context, "输入密码：" + pwd + " 目标：" + users[i].user_pwd, Toast.LENGTH_SHORT).show()
                    if (users[i].user_pwd == pwd) {
//                        Toast.makeText(BVMApplication.context, "找到", Toast.LENGTH_SHORT).show()
                        BVMApplication.USER = users[i]
                        user = users[i]
                        flag = true
                        break
                    }
                }
                if (flag) {
                    val intent = Intent(BVMApplication.context, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(BVMApplication.context, "用户名或密码错误，请重新输入", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(BVMApplication.context, "用户名或密码错误，请重新输入", Toast.LENGTH_SHORT).show()
            }
        })

//        ButterKnife.inject(this)
        loginBtn.setOnClickListener {
            login()
//            var tmp = "0"
//            if (flag)   tmp = "1"
//            Toast.makeText(BVMApplication.context, tmp, Toast.LENGTH_SHORT).show()
//            if (flag) {
//                val intent = Intent(BVMApplication.context, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
        }
        signupLink.setOnClickListener {
            // Start the Signup activity
            val intent = Intent(BVMApplication.context, SignupActivity::class.java)
            startActivity(intent)
            // todo 去除已输入信息
        }
    }

    private fun login() {
//        Log.d(TAG, "Login")
        if (!validate()) {
            Toast.makeText(BVMApplication.context, "输入格式错误，请重新输入", Toast.LENGTH_SHORT).show()
            return
        }
        val name = userNameText.editText?.text.toString()
        viewModel.searchUserByName(name)
//        Toast.makeText(BVMApplication.context, name, Toast.LENGTH_SHORT).show()
    }


    private fun validate(): Boolean {
        // todo 增加格式判断
        var valid = true
        val name = userNameText.editText?.text.toString()
        val pwd = userPwdText.editText?.text.toString()
        if (name.isEmpty()) {
            userNameText.error = "用户名不能为空"
            valid = false
        } else {
            userNameText.error = null
        }
        if (pwd.isEmpty()) {
            userPwdText.error = "密码不能为空"
            valid = false
        } else {
            userPwdText.error = null
        }
        return valid
    }
}