package com.ssafy.memo

object MemoItemMgr {

    private var memos = arrayListOf<MemoItem>()

    fun getList():ArrayList<MemoItem> {
        return memos;
    }

    fun add(item : MemoItem) {
        memos.add(item)
    }

    fun search(index : Int) : MemoItem {
        return memos[index]
    }

    fun update(index : Int, item : MemoItem) {
        memos[index] = item
    }

    fun remove(index : Int) {
        memos.remove(memos[index])
    }

    fun clear() {
        memos.clear()
    }
}