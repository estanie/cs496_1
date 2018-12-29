package com.example.q.cs496_1.models

import android.net.Uri

class MyImage {
    var path: String? = null

    constructor(path: Uri) {
        this.path = path.path
    }
    constructor(path: String) {
        this.path = path
    }
}