package com.example.main.diarylist

import android.R.attr.data
import android.app.Activity
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.main.DiarySubActivity
import com.example.main.R
import com.example.main.databinding.ActivityDiary2Binding
import com.example.main.diarylist.adapter.DiaryAdapter
import com.example.main.diarylist.dia.Diary
import com.example.main.diarylist.sqlite.DiaryOpenHelper
import com.example.main.diarylist.viewmodel.MainModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*


class diary2_fragment : Fragment() {

    private lateinit var todies : Vector<Diary>
    private lateinit var adapter : DiaryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_diary2_fragment, container, false) as ViewGroup

        val daylist: RecyclerView = rootView.findViewById(R.id.recyclerView) as RecyclerView
        val addNewTodoFab: FloatingActionButton = rootView.findViewById(R.id.addNewTodoFab) as FloatingActionButton
        //val diarydel: ImageButton = rootView.findViewById(R.id.diary_del) as ImageButton

        daylist.layoutManager =
            LinearLayoutManager(
                context,
                LinearLayoutManager.VERTICAL,
                false
            )
        todies = Vector()
        val openHelper = DiaryOpenHelper(context as Activity)
        val db = openHelper.writableDatabase
        val cursor = db.rawQuery("select * from diarylist", null)


        val count = cursor.count
        if (count >= 1) {
            while (cursor.moveToNext()) {
                val title = cursor.getString(0)
                val content = cursor.getString(1)
                val time = cursor.getString(2)
                todies.add(Diary(title, content, time))
            }
        } else {
            todies.add(Diary("등록된 다이어리가 없습니다.", "",""))
        }

        cursor.close()
        adapter = DiaryAdapter(todies, context as Activity)
        daylist.adapter = adapter


        //추가 버튼 누르면 편집 새창 뜨기
        addNewTodoFab.setOnClickListener {
            activity?.let {
                val intent = Intent(context, DiarySubActivity2::class.java)
                startActivity(intent)
            }
        }

        return rootView
    }


    companion object {
        private const val MAIN = R.layout.fragment_diary2_fragment
    }
}
