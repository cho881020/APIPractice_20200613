package kr.co.tjoeun.apipractice_20200613

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_view_reply_detail.*
import kr.co.tjoeun.apipractice_20200613.datas.TopicReply
import kr.co.tjoeun.apipractice_20200613.utils.ServerUtil
import org.json.JSONObject

class ViewReplyDetailActivity : BaseActivity() {

    var mReplyId = -1
    lateinit var mReply : TopicReply

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_reply_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mReplyId = intent.getIntExtra("reply_id", -1)

//        서버에서 의견 상세 현황 가져오기
        getReplyDetailFromServer()
    }

    fun getReplyDetailFromServer() {
        ServerUtil.getRequestReplyDetail(mContext, mReplyId, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(json: JSONObject) {

                val data = json.getJSONObject("data")
                val reply = data.getJSONObject("reply")
                mReply = TopicReply.getTopicReplyFromJson(reply)

                runOnUiThread {
                    sideTitleTxt.text = mReply.selectedSide.title
                    writerNickNameTxt.text = mReply.user.nickName
                    contentTxt.text = mReply.content
                }

            }
        })
    }

}