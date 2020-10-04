package com.example.finsights

import java.util.*

data class SmsData(val senderName:String,val date:String, val message: String) {
}

data class ExpenseData(val storeName:String, val amount:String, val category:String, val date:String)

data class Map(val key:String, val value:String)