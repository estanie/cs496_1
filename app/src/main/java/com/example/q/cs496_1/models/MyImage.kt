package com.example.q.cs496_1.models

import android.net.Uri

class MyImage {
    var image: Uri? = null

    constructor(image: Uri) {
        this.image = image
    }
}