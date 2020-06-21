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

class ReReplyAdapter(
    val mContext:Context,
    val resId:Int,
    val mList:List<TopicReply>) : ArrayAdapter<TopicReply>(mContext, resId, mList) {

    val inf = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView

        tempRow?.let {

        }.let {
            tempRow = inf.inflate(R.layout.topic_re_reply_list_item, null)
        }

        val row = tempRow!!

//        XML에서 사용할 뷰 가져오기
        val writerNickNameTxt = row.findViewById<TextView>(R.id.writerNickNameTxt)
        val contentTxt = row.findViewById<TextView>(R.id.contentTxt)
        val likeBtn = row.findViewById<Button>(R.id.likeBtn)
        val dislikeBtn = row.findViewById<Button>(R.id.dislikeBtn)
        val selectedSideTitleTxt = row.findViewById<TextView>(R.id.selectedSideTitleTxt)

//        목록에서 뿌려줄 데이터 꺼내오기

        val data = mList[position]

//        데이터 / 뷰 연결 => 알고리즘

        writerNickNameTxt.text = data.user.nickName
        selectedSideTitleTxt.text = "(${data.selectedSide.title})"
        contentTxt.text = data.content

//        좋아요 / 싫어요 버튼 관련 표시
//        몇명이 좋아요 ? 숫자 표시
        likeBtn.text = "좋아요 : ${data.likeCount}"
        dislikeBtn.text = "싫어요 : ${data.dislikeCount}"

//        내 좋아요 여부에 따라 모양 변경
        if (data.isMyLike) {
            likeBtn.setBackgroundResource(R.drawable.red_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.gray_border_box)

            likeBtn.setTextColor(mContext.resources.getColor(R.color.red))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))

        }
        else if (data.isMyDislike) {
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.blue_border_box)


            likeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.blue))
        }
        else {
            likeBtn.setBackgroundResource(R.drawable.gray_border_box)
            dislikeBtn.setBackgroundResource(R.drawable.gray_border_box)

            likeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
            dislikeBtn.setTextColor(mContext.resources.getColor(R.color.darkGray))
        }


        return row
    }

}