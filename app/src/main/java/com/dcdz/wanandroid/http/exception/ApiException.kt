package com.dcdz.wanandroid.http.exception

/**
 * Created by LJW on 2018/11/6.
 */
class ApiException : RuntimeException {

    private var code: Int? = null

    constructor(throwable: Throwable, code: Int) : super(throwable) {
        this.code = code
    }

    constructor(message: String) : super(Throwable(message))
}