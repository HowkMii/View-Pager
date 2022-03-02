package com.example.mrp_initial_path

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import java.text.FieldPosition
import java.time.ZoneOffset

class MainActivity : AppCompatActivity() {
    private lateinit var myAdapter: MyAdapter
    private lateinit var dotsTv: Array<TextView?>
    private lateinit var layouts: IntArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isFirsTimeAppStart()){
            setAppStartStatus(true)
        }
        setContentView(R.layout.activity_main)

        val actionBar: ActionBar?=supportActionBar
        actionBar!!.hide()
        stausBarTransparent()
        btn_next.setOnClickListener{
            val currentPage:Int=viewPager.currentItem
            if (currentPage<layouts.size){
                viewPager.currentItem=currentPage+1
            }else{
                setAppStartStatus(true)
                startActivity(Intent(this,AnotherActivity::class.java))
                finish()
            }
        }
        btn_skip.setOnClickListener{
            setAppStartStatus(true)
            startActivity(Intent(this,AnotherActivity::class.java))
            finish()
        }
        layouts= intArrayOf(R.layout.slide_1,R.layout.slide_2,R.layout.slide_3)
        myAdapter= MyAdapter(layouts, applicationContext)
        viewPager.adapter = myAdapter
        viewPager.addOnPagerChangeistner(object:ViewPager.OnPageChangeistener{
            override fun onPageScrollStateChanged(state:Int){

            }
            override fun onPageSrolled(position: Int,positionOffset: Float,positionOffsetPixels: Int){

            }
            override fun onPageSelected(position: Int){
                if (position==layouts.size-1){
                    btn_next.text="START"
                    btn_skip.visiblity=View.GONE
                }else{
                    btn_next.text="NEXT"
                    btn_skip.visiblity=View.VISIBLE
                }
                setDots(position)
            }
        })
        setDots(0)
    }
    private fun isFirsTimeAppStart():Boolean{
        val pref= applicationContext.getSharedPreferences("SLIDER_APP",Context.MODE_PRIVATE)
        return pref.getBoolean("APP_START",true)
    }
    private fun setAppStartStatus(status:Boolean){
        val pref= applicationContext.getSharedPreferences("SLIDER_APP",Context.MODE_PRIVATE)
        val editor : SharedPreferences.Editor= pref.edit()
        editor.putBoolean("APP_START",status)
        editor.apply()
    }
    private fun stausBarTransparent(){
        if (Build.VERSION.SDK_INT >=21){
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor= Color.TRANSPARENT
        }
    }
    private  fun setDots(page:Int){
        dotsLayout.removeAllViews()
        dotsTv = arrayOfNulls(layouts.size)
        for (i in dotsTv.indices){
            dotsTv[i]= TextView(this)
            dotsTv[i]!!.text= Html.fromHtml("&#8226;")
            dotsTv[i]!!.textSize= 30f
            dotsTv[i]!!.setTextColor(Color.parseColor("#a9b4bb"))
            dotsLayout.addView(dotsTv[i])
        }
        if (dotsTv.isNotEmpty()){
            dotsTv[page]!!.setTextColor(Color.parseColor("#ffffff"))
        }
    }

}