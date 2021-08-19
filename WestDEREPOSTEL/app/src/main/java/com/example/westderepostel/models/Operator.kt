package com.example.westderepostel.models

import android.os.Parcelable
import android.provider.ContactsContract
import kotlinx.android.parcel.Parcelize


class Operator(val oid         : String? = null,
               val operatorName: String? = null,
               val logoImageUrl: String? = null,
               val activity    : String? = null,
               val location    : String? = null,
               val accessCode  : String? = null,
               val email       : String? = null,
               val phone       : String? = null) {

    //constructor() : this(oid = "", operatorName = "", logoImageUrl = "", activity = "", location = "", accessCode = "")
            }
//User(val firstName : String? = null,val lastName : String? = null,val age : String? = null,val userName : String? = null){
//
//
//}