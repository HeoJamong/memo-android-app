package com.ssafy.memo

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.memo.databinding.ActivityEditBinding
import java.text.SimpleDateFormat
import java.util.*

class MemoEditActivity : AppCompatActivity() {

    private val binding by lazy { ActivityEditBinding.inflate(layoutInflater) }
    private var position = -1
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 넘겨받은 position으로 수정 모드 구분
        position = intent.getIntExtra("position", -1)
        isEditMode = position >= 0

        if (isEditMode) {
            val memo = MemoItemMgr.search(position)
            binding.editTitle.setText(memo.title)
            binding.editContent.setText(memo.content)
            binding.editTime.setText(memo.regDate)
            binding.editTime.isEnabled = false
        } else {
            binding.timeLayout.visibility = android.view.View.GONE
            binding.btnDelete.visibility = android.view.View.GONE
        }

        binding.btnSave.setOnClickListener {
            val title = binding.editTitle.text.toString().trim()
            val content = binding.editContent.text.toString().trim()

            if (title.isEmpty() || content.isEmpty()) {
                Toast.makeText(this, "빈칸을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val memo = MemoItem(title, content, getCurrentTime())
            if (isEditMode) {
                MemoItemMgr.update(position, memo)
            } else {
                MemoItemMgr.add(memo)
            }

            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.btnDelete.setOnClickListener {
            if (isEditMode) {
                MemoItemMgr.remove(position)
            }
            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.btnUnset.setOnClickListener {
            finish()
        }
    }

    private fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("MM-dd HH:mm", Locale.getDefault())
        return sdf.format(Date())
    }
}
