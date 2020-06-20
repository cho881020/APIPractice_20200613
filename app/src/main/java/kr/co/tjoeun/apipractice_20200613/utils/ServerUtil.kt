package kr.co.tjoeun.apipractice_20200613.utils

import android.content.Context
import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

    //    어느 객체인지 관계 없이 기능/값만 잘 사용하면 되는 것들을 모아두는 영역
//    JAVA => static에 대응되는 개념
    companion object {
        
//        테스트용 커밋

//        어느 서버로 가야하는지 (HOST 주소) 적어두는 변수
        val BASE_URL = "http://15.165.177.142"

        fun getRequestDuplicatedCheck(context: Context, checkType:String, inputVal:String, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

//            GET방식은 어디로 갈지 주소 + 어떤데이터를 보낼지 같이 표시됨.
//            주소를 만들때 데이터 첨부까지 같이 진행.

//            중복검사 주소 배치 => 이 뒤에 파라미터 첨부할 수 있도록 builder로 만듦
            val urlBuilder = "${BASE_URL}/user_check".toHttpUrlOrNull()!!.newBuilder()
//            만든 주소 변수에 파라미터 첨부
            urlBuilder.addEncodedQueryParameter("type", checkType)
            urlBuilder.addEncodedQueryParameter("value", inputVal)

//            첨부 데이터가 포함된 주소 확인
            val urlString = urlBuilder.build().toString()
            Log.d("완성된주소", urlString)

//            Request를 만들어서 최종 전송 정보 마무리
            val request = Request.Builder()
                .url(urlString)
                .get()
//                .header() // 헤더를 요구하면 추가
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

        fun getRequestMyInfo(context: Context, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

//            GET방식은 어디로 갈지 주소 + 어떤데이터를 보낼지 같이 표시됨.
//            주소를 만들때 데이터 첨부까지 같이 진행.

//            중복검사 주소 배치 => 이 뒤에 파라미터 첨부할 수 있도록 builder로 만듦
            val urlBuilder = "${BASE_URL}/user_info".toHttpUrlOrNull()!!.newBuilder()
//            만든 주소 변수에 파라미터 첨부
//            urlBuilder.addEncodedQueryParameter("type", checkType)
//            urlBuilder.addEncodedQueryParameter("value", inputVal)

//            첨부 데이터가 포함된 주소 확인
            val urlString = urlBuilder.build().toString()
            Log.d("완성된주소", urlString)

//            Request를 만들어서 최종 전송 정보 마무리
            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getUserToken(context)) // 헤더를 요구하면 추가
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

//        원하는 주제의 상세정보 보기 => 몇번주제? 화면에서 받아와야함
        fun getRequestTopicDetail(context: Context, topicId:Int, handler: JsonResponseHandler?) {

            val client = OkHttpClient()


//          4번주제? /topic/4  , 2번? /topic/2

            val urlBuilder = "${BASE_URL}/topic/${topicId}".toHttpUrlOrNull()!!.newBuilder()
//            urlBuilder.addEncodedQueryParameter("type", checkType)
//            urlBuilder.addEncodedQueryParameter("value", inputVal)

            val urlString = urlBuilder.build().toString()
            Log.d("완성된주소", urlString)

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getUserToken(context)) // 헤더를 요구하면 추가
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

        fun getRequestV2MainInfo(context: Context, handler: JsonResponseHandler?) {

            val client = OkHttpClient()

//            GET방식은 어디로 갈지 주소 + 어떤데이터를 보낼지 같이 표시됨.
//            주소를 만들때 데이터 첨부까지 같이 진행.

//            중복검사 주소 배치 => 이 뒤에 파라미터 첨부할 수 있도록 builder로 만듦
            val urlBuilder = "${BASE_URL}/v2/main_info".toHttpUrlOrNull()!!.newBuilder()
//            만든 주소 변수에 파라미터 첨부
//            urlBuilder.addEncodedQueryParameter("type", checkType)
//            urlBuilder.addEncodedQueryParameter("value", inputVal)

//            첨부 데이터가 포함된 주소 확인
            val urlString = urlBuilder.build().toString()
            Log.d("완성된주소", urlString)

//            Request를 만들어서 최종 전송 정보 마무리
            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getUserToken(context)) // 헤더를 요구하면 추가
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

        fun postRequestVote(context: Context, sideId:Int, handler:JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/topic_vote"

            val formData = FormBody.Builder()
                .add("side_id", sideId.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getUserToken(context))  // API가 헤더를 요구하면 여기서 첨부하자
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

        fun postRequestReplyLikeOrDislike(context: Context, replyId:Int, isLike:Boolean, handler:JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/topic_reply_like"

            val formData = FormBody.Builder()
                .add("reply_id", replyId.toString())
                .add("is_like", isLike.toString())
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getUserToken(context))  // API가 헤더를 요구하면 여기서 첨부하자
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

        fun postRequestReply(context: Context, topicId: Int, content:String, handler:JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/topic_reply"

            val formData = FormBody.Builder()
                .add("topic_id", topicId.toString())
                .add("content", content)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formData)
                .header("X-Http-Token", ContextUtil.getUserToken(context))  // API가 헤더를 요구하면 여기서 첨부하자
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

        fun putRequestSignUp(context: Context, email:String, pw:String, nick:String, handler:JsonResponseHandler?) {

            val client = OkHttpClient()

            val urlString = "${BASE_URL}/user"

            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .add("nick_name", nick)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .put(formData)
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