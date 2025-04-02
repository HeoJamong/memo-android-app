package com.ssafy.memo

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.memo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ArrayAdapter<MemoItem>
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    val addLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        adapter.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "메뉴"
        // 어댑터 연결
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, MemoItemMgr.getList())
        binding.listview.adapter = adapter

        registerForContextMenu(binding.listview)

        // 메모 등록 런처


        // 메모 수정 런처
        val editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            adapter.notifyDataSetChanged()
        }

//        // 등록 버튼
//        binding.btnRegister.setOnClickListener {
//            val intent = Intent(this, MemoEditActivity::class.java)
//            addLauncher.launch(intent)
//        }

        // 리스트 아이템 클릭 시 수정 화면으로
        binding.listview.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, MemoEditActivity::class.java).apply {
                putExtra("position", position)
            }
            editLauncher.launch(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_option, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addItem -> {
                val intent = Intent(this, MemoEditActivity::class.java)
                addLauncher.launch(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo

        return when (item.itemId) {
            R.id.delete -> {
                MemoItemMgr.remove(info.position)
                adapter.notifyDataSetChanged()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
