package kr.co.tjoeun.apipractice_20200613.adapters

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import kr.co.tjoeun.apipractice_20200613.R
import kr.co.tjoeun.apipractice_20200613.ViewReplyDetailActivity
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
        val selectedSideTitleTxt = row.findViewById<TextView>(R.id.selectedSideTitleTxt)

//        목록에서 뿌려줄 데이터 꺼내오기

        val data = mList[position]

//        데이터 / 뷰 연결 => 알고리즘

        writerNickNameTxt.text = data.user.nickName
        contentTxt.text = data.content

//        어떤 댓글을 옹호하는지? (진영이름) 양식으로 표현
        selectedSideTitleTxt.text = "(${data.selectedSide.title})"

        replyBtn.text = "답글 : ${data.replyCount}"
        likeBtn.text = "좋아요 : ${data.likeCount}"
        dislikeBtn.text = "싫어요 : ${data.dislikeCount}"

//        내 좋아요 / 싫어요 여부 표시
        if (data.isMyLike) {
//            내가 좋아요를 찍은 댓글일 경우
//            좋아요 빨간색 / 싫어요 회색
            likeBtn.setBackgroundResource(R.drawable.red_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.gray_border_box)

//            좋아요 글씨 색 : 빨간색 => res => colors => red를 사용
            likeBtn.setTextColor(mContext.resources.getColor(R.color.red))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))

        }
        else if (data.isMyDislike) {
//            내가 싫어요를 찍은 댓글일 경우
//            좋아요 회색 / 싫어요 파란색
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.blue_border_box)

//            버튼 글씨색 설정
            likeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.blue))
        }
        else {
//            아무것도 찍지 않은 경우
//            둘다 회색
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.gray_border_box)

//            버튼 글씨색 설정
            likeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))

        }

//        답글 버튼 눌림 처리
        replyBtn.setOnClickListener {
            val myIntent = Intent(mContext, ViewReplyDetailActivity::class.java)
//            어댑터에서 직접 startActivity 불가.
//            mContext의 도움을 받아서 실행
            mContext.startActivity(myIntent)
        }

//        좋아요 / 싫어요 이벤트 처리
        likeBtn.setOnClickListener {
//            좋아요 API 호출 => 좋아요 누르기 / 취소 처리

            ServerUtil.postRequestReplyLikeOrDislike(mContext, data.id, true, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(json: JSONObject) {

                    val dataObj = json.getJSONObject("data")
                    val reply = dataObj.getJSONObject("reply")

                    data.likeCount = reply.getInt("like_count")
                    data.dislikeCount = reply.getInt("dislike_count")

                    data.isMyLike = reply.getBoolean("my_like")
                    data.isMyDislike = reply.getBoolean("my_dislike")

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


                    data.isMyLike = reply.getBoolean("my_like")
                    data.isMyDislike = reply.getBoolean("my_dislike")

                    Handler(Looper.getMainLooper()).post {
                        notifyDataSetChanged()
                    }


                }

            })

        }

        return row
    }

}