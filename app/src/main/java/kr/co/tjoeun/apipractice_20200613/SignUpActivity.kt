package kr.co.tjoeun.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import kotlinx.android.synthetic.main.activity_sign_up.*
import kr.co.tjoeun.apipractice_20200613.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        nickNameEdt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                nickNameCheckResultTxt.text = "중복확인을 해주세요."
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