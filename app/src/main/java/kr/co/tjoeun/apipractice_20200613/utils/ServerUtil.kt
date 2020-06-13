package kr.co.tjoeun.apipractice_20200613.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

    //    어느 객체인지 관계 없이 기능/값만 잘 사용하면 되는 것들을 모아두는 영역
//    JAVA => static에 대응되는 개념
    companion object {
        
//        테스트용 커밋

//        어느 서버로 가야하는지 (HOST 주소) 적어두는 변수
        val BASE_URL = "http://15.165.177.142"

        fun postRequestLogin(context: Context, id:String, pw:String, handler:JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/user"

            val formData = FormBody.Builder()
                .add("email", id)
                .add("password", pw)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
//                .header()  // API가 헤더를 요구하면 여기서 첨부하자
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val json = JSONObject(bodyString)
                    Log.d("JSON응답", json.toString())
                    handler?.onResponse(json)

                }

            })

        }

    }

//    서버통신의 응답 내용 (JSON) 을 화면으로 전달해주기 위한 인터페이스
    interface JsonResponseHandler {
        fun onResponse(json: JSONObject)
    }


}