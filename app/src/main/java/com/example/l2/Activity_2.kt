package com.example.l2

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.logging.Handler
import kotlin.random.Random

class Activity_2 : AppCompatActivity() {

    private var tv_info : TextView? = null
    private var et_ans : EditText? = null
    private var bt_action : Button? = null
    private var first : Int = 0
    private var second : Int = 0


    fun generate(flag : Int) {
        if (flag == 0) {
            first = Random.nextInt(2, 10)
        }
        second = Random.nextInt(2, 10)
        tv_info?.text = first.toString() + " * " + second.toString() + " = "
    }

    fun get_res(flag : Int, ans_my: MutableList<Int>, ans_true : MutableList<Int>, i : Int) : Int {
        val ans = et_ans?.text.toString()
        if (ans.trim() == "") {
            Toast.makeText(this, "Введите ответ", Toast.LENGTH_SHORT).show()
            return 0
        }
        else {
            ans_my.add(ans.toInt())
            ans_true.add(first * second)
            if (ans.toInt() == first * second) {
                Toast.makeText(this, "Правильно", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "Неправильно", Toast.LENGTH_SHORT).show()
            }
            if (i < 20) {
                generate(flag)
                println("Generating at iteration " + i.toString())
            }
            return 1
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var flag : Int = 0

        var started : Boolean = false
        tv_info = findViewById(R.id.TV_info)
        et_ans = findViewById(R.id.ET_ans)
        bt_action = findViewById(R.id.BT_action)
        var iteration : Int = 0
        var ans_my : MutableList<Int> = mutableListOf<Int>()
        var ans_true : MutableList<Int> = mutableListOf<Int>()
        var data = getIntent().getStringExtra("data")
        if (data == "part") {
            tv_info?.text = "Введите тестовое число: "
            tv_info?.visibility = View.VISIBLE
            et_ans?.visibility = View.VISIBLE
            flag = 1
        }
        else {
            flag = 0
        }
        println(data)
        bt_action?.setOnClickListener{
            if (!started) {
                if (flag == 1 && !(et_ans?.text?.toString()?.trim()?.toInt() in 2..9)) {
                    Toast.makeText(this, "Введите число от 2 до 9", Toast.LENGTH_SHORT).show()
                }
                else {
                    if (flag == 1) {
                        first = et_ans?.text.toString().trim().toInt()
                        et_ans?.text?.clear()
                    }
                    started = true
                    tv_info?.text = ""
                    bt_action?.text = "Ответить"
                    tv_info?.visibility = View.VISIBLE
                    et_ans?.visibility = View.VISIBLE
                    iteration = 1
                    generate(flag)
                }
            }
            else {

                if (iteration >= 20) {
                    iteration += get_res(flag, ans_my, ans_true, iteration)
                    println(ans_my)
                    println(ans_true)

                    var correct : Int = 0
                    for (idx in 0..19) {
                        if (ans_my[idx] == ans_true[idx]) {
                            correct += 1
                        }
                    }
                println(correct)


                    openAlertDialog(correct)

                }
                else {
                    iteration += get_res(flag, ans_my, ans_true, iteration)
                    et_ans?.text?.clear()
                }
            }
        }

    }

    fun openAlertDialog(pts : Int) {
        // Create an AlertDialog.Builder
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Результат")
        builder.setMessage("Вы набрали " + String.format("${pts * 100 / 20.0} %% правильных ответов"))
        // Set a positive button and its click listener
        builder.setPositiveButton("OK") { dialog, which ->
            // Handle positive button click if needed
            dialog.dismiss()
            bt_action?.text = "Начать тестирование"
            startActivity(Intent(this@Activity_2, MainActivity::class.java))
        }
        // Create the AlertDialog
        val alertDialog = builder.create()
        // Show the AlertDialog
        alertDialog.show()
        // Schedule the dismissal after a certain delay

    }

}

