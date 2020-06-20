package kr.co.tjoeun.apipractice_20200613.adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import kr.co.tjoeun.apipractice_20200613.R
import kr.co.tjoeun.apipractice_20200613.datas.TopicReply
import kr.co.tjoeun.apipractice_20200613.utils.ServerUtil
import org.json.JSONObject

class ReplyAdapter(
    val mContext:Context,
    val resId:Int,
    val mList:List<TopicReply>) : ArrayAdapter<TopicReply>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView

        tempRow?.let {

        }.let {
            tempRow = inf.inflate(R.layout.topic_reply_list_item, null)
        }

        val row = tempRow!!

//        XML에서 사용할 뷰 가져오기
        val writerNickNameTxt = row.findViewById<TextView>(R.id.writerNickNameTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contentTxt)
        val replyBtn = row.findViewById<Button>(R.id.replyBtn)
        val likeBtn = row.findViewById<Button>(R.id.likeBtn)
        val dislikeBtn = row.findViewById<Button>(R.id.dislikeBtn)

//        목록에서 뿌려줄 데이터 꺼내오기

        val data = mList[position]

//        데이터 / 뷰 연결 => 알고리즘

        writerNickNameTxt.text = data.user.nickName
        contentTxt.text = data.content

        replyBtn.text = "답글 : ${data.replyCount}"
        likeBtn.text = "좋아요 : ${data.likeCount}"
        dislikeBtn.text = "싫어요 : ${data.dislikeCount}"

//        좋아요 / 싫어요 이벤트 처리
        likeBtn.setOnClickListener {
//            좋아요 API 호출 => 좋아요 누르기 / 취소 처리

            ServerUtil.postRequestReplyLikeOrDislike(mContext, data.id, true, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                    val dataObj = json.getJSONObject("data")
                    val reply = dataObj.getJSONObject("reply")

                    data.likeCount = reply.getInt("like_count")
                    data.dislikeCount = reply.getInt("dislike_count")

                    Handler(Looper.getMainLooper()).post {
                        notifyDataSetChanged()
                    }
                }
            })

        }

        dislikeBtn.setOnClickListener {

//            싫어요 버튼 처리 진행 => 좋아요를 참고해서 진행
            ServerUtil.postRequestReplyLikeOrDislike(mContext, data.id, false, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                    val dataObj = json.getJSONObject("data")
                    val reply = dataObj.getJSONObject("reply")

                    val likeCount = reply.getInt("like_count")
                    val dislikeCount = reply.getInt("dislike_count")

                    data.likeCount = likeCount
                    data.dislikeCount = dislikeCount

                    Handler(Looper.getMainLooper()).post {
                        notifyDataSetChanged()
                    }


                }

            })

        }

        return row
    }

}