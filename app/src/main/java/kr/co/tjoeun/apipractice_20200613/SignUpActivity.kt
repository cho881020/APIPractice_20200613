package kr.co.tjoeun.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.emailEdt
import kotlinx.android.synthetic.main.activity_sign_up.signUpBtn
import kr.co.tjoeun.apipractice_20200613.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

//    이메일 / 닉네임 중복검사 결과 저장 변수
    var isEmailDuplOk = false
    var isNickNameDuplOk = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        signUpBtn.setOnClickListener {

//            이메일 중복검사 통과? + 닉네임 중복검사 통과?
            if (!isEmailDuplOk) {
//                이메일 중복검사 통과 X?

                Toast.makeText(mContext, "이메일 중복 검사를 통과해야 합니다.", Toast.LENGTH_SHORT).show()

//                뒤의 로직 실행하지 않고 이 함수를 강제 종료
                return@setOnClickListener
            }

            if (!isNickNameDuplOk) {
//                닉네임 중복검사 통과 X?
                Toast.makeText(mContext, "닉네임 중복 검사를 통과해야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            여기가 실행이 된다? => 강제종료 두번을 모두 피했다.
//            이메일도 / 닉네임도 모두 통과한 상태다.

//            입력한 이메일 / 비번 / 닉네임을 들고 서버에 가입 신청
            val email = emailEdt.text.toString()
            val pw = passwordEdt.text.toString()
            val nickName = nickNameEdt.text.toString()

//            서버에 /user - PUT으로 요청. => ServerUtil을 통해 요청.
            ServerUtil.putRequestSignUp(mContext, email, pw, nickName, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                    val code = json.getInt("code")

                    if (code == 200) {

                        runOnUiThread {
                            Toast.makeText(mContext, "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                            finish()
                        }

                    }
                    else {

                        val message = json.getString("message")
                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }

                    }

                }

            })


        }

        nickNameEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                nickNameCheckResultTxt.text = "중복확인을 해주세요."
                isNickNameDuplOk = false
            }

        })

        emailEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                글자가 변경된 시점에 실행되는 함수
//                Log.d("변경된내용", s.toString())

//                이메일 중복검사를 하라고 안내
                emailCheckResultTxt.text = "중복확인을 해주세요."
                isEmailDuplOk = false

            }

        })

        nickNameCheckBtn.setOnClickListener {
            val inputNick = nickNameEdt.text.toString()

            ServerUtil.getRequestDuplicatedCheck(mContext, "NICKNAME", inputNick, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                    val code = json.getInt("code")

                    if (code == 200) {
                        runOnUiThread {
                            nickNameCheckResultTxt.text = "사용해도 좋습니다."
                            isNickNameDuplOk = true
                        }
                    }
                    else {
                        runOnUiThread {
                            nickNameCheckResultTxt.text = "중복된 닉네임입니다. 다른 닉네임으로 재검사 해주세요."
                        }
                    }

                }
            })
        }

        emailCheckBtn.setOnClickListener {
//            입력한 이메일이 이미 회원으로 있는지 확인 => 서버에 요청

            val inputEmail = emailEdt.text.toString()

            ServerUtil.getRequestDuplicatedCheck(mContext, "EMAIL", inputEmail, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                    val code = json.getInt("code")

                    if (code == 200) {
                        runOnUiThread {
                            emailCheckResultTxt.text = "사용해도 좋습니다."
//                            중복검사 결과를 true로 변경
                            isEmailDuplOk = true
                        }

                    }
                    else {
                        runOnUiThread {
                            emailCheckResultTxt.text = "이미 사용중입니다. 다른 이메일로 다시 체크해주세요."
                        }

                    }

                }
            })


        }

    }

    override fun setValues() {

    }

}
