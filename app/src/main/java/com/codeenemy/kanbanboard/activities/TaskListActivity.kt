package com.codeenemy.kanbanboard.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codeenemy.kanbanboard.R

class TaskListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
    }
}