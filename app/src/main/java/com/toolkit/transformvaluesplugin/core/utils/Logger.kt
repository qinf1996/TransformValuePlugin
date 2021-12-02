package com.toolkit.transformvaluesplugin.core.utils

object Logger {

    fun i(message:String){
        System.out.println(message)
    }

    fun w(message: String,throwable: Throwable? = null){
        System.out.println(message)
    }

    fun e(message: String,throwable: Throwable? = null){
        System.out.println(message)
    }
}