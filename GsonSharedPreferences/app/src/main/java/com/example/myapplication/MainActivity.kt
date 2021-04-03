package com.example.myapplication

import android.content.Context
import android.graphics.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val rvAdapter = RecyclerViewAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            with(recyclerView) {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = rvAdapter
                addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            }
        }

        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback (
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT
                ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPos: Int = viewHolder.adapterPosition
                val toPos: Int = target.adapterPosition
                rvAdapter.swapData(fromPos, toPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                rvAdapter.removeData(viewHolder.layoutPosition)
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val icon: Bitmap
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView
                    val height = (itemView.bottom - itemView.top).toFloat()
                    val width = height / 4
                    val paint = Paint()
                    if (dX < 0) {
                        paint.color = Color.parseColor("#ff0000")
                        val background = RectF(itemView.right.toFloat() + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                        c.drawRect(background, paint)

                        icon = BitmapFactory.decodeResource(resources, R.drawable.ic_menu_delete)
                        val iconDst = RectF(itemView.right.toFloat() - 3 * width, itemView.top.toFloat() + width, itemView.right.toFloat() - width, itemView.bottom.toFloat() - width)
                        c.drawBitmap(icon, null, iconDst, null)


                    }
                }
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        }
        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.recyclerView)

        rvAdapter.addData("타일런트", Race.Zombie, 10, listOf(100,10,50), false)
        rvAdapter.addData("조커", Race.Human, 23, listOf(200,20,100), false)
        rvAdapter.addData("그렘린", Race.Goblin, 2, listOf(10,1,5), true)
        rvAdapter.addData("리오레우스", Race.Dragon, 2500, listOf(10000,1000,50000), false)
        rvAdapter.addData("사우론", Race.Human, 100, listOf(1000,200,1000),false)
        rvAdapter.addData("리바이어던", Race.Dragon, 50, listOf(2000,250,10000), true)

        binding.buttonLoad.setOnClickListener {
            loadPref()
            rvAdapter.notifyItemChanged(0, rvAdapter.dataSet.size)
        }
    }

    private fun savePref() {
        val sharedPreferences = getSharedPreferences(KEY_PREFS, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(rvAdapter.dataSet)
        editor.putString(KEY_DATA, json)
        editor.apply()
        Log.d("debug", "Data saved")
    }

    private fun loadPref() {
        val sharedPreferences = getSharedPreferences(KEY_PREFS, Context.MODE_PRIVATE)
        if (sharedPreferences.contains(KEY_DATA)) {
            val gson = Gson()
            val json = sharedPreferences.getString(KEY_DATA, "")
            try {
                val typeToken = object : TypeToken<ArrayList<Monster>>() {}.type
                rvAdapter.dataSet = gson.fromJson(json, typeToken)
            } catch (e: JsonParseException) {
                e.printStackTrace()
            }
            Log.d("debug", "Data loaded")
        }
    }

    override fun onStop() {
        super.onStop()
        savePref()
    }

    companion object {
        private const val KEY_PREFS = "shared_preferences"
        private const val KEY_DATA = "monster_data"
    }
}