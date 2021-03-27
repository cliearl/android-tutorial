package com.example.usektlint

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // No semicolons.
        val v = ""
        println(v)

        // Consistent string templates.
        val a = "class = ${String::class}"
        val b = "not $a"

        // Consistent spacing.
        // multiple spaces after "val long" for vertical alignment
        val short = ""
        val long = ""

        // no spacing around operators
        val v = a - b * c

        // no space after the comma
        class A : B, C

        // no spacing after keyword ("if" in this case)
        if (true) {}

        // incorrect spacing around ":"
        @file: JvmName("Main")
        class A : B
        call(object : C() {})
        fun fn(@field: F a: Any, b: Any, c: Any): Any
        val v: String = str()

        // missing spacing around "{" and before "}"
        if (true) { /* .. */ }
        // missing spacing after "{" and before "}"
        fn({ v -> f(v) * g(v) }!!)
        // unnecessary space after "}"
        emptyList().find { true }!!.hashCode()
        find { it.default ?: false }?.phone
    }
}

// No consecutive blank lines.
class A

class B

// 4 spaces for indentation*.
data class C(
    val a: Any,
    val b: Any = 0,
    val c: Any
)
