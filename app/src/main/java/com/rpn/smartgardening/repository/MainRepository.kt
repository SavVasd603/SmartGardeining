package com.rpn.smartgardening.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.rpn.smartgardening.model.GardenInfo
import com.rpn.smartgardening.model.Manual
import com.rpn.smartgardening.utils.SettingsUtility
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainRepository() : KoinComponent {

    val settingsUtility by inject<SettingsUtility>()
    val TAG = "MainRepository"

    val database = Firebase.database
    val myRef = database.getReference("Garden Info")

    fun getGardenInfo(
        uid: String = settingsUtility.userId,
        onComplete: (data: GardenInfo?) -> Unit
    ) {
        val childNodeRef = myRef.child(uid)
        Log.d(TAG, "uid: $uid\ndatabase: $childNodeRef")

        childNodeRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d(TAG, "onDataChange: ${dataSnapshot.value}")
                val data = dataSnapshot.getValue(GardenInfo::class.java)

                if (data != null) {
                    Log.d(
                        TAG, "onDataChange: \n" +
                                "manual = ${data.info?.manual?.manualMode}\n" +
                                "weather = ${data.info?.weather}\n" +
                                "controller = ${data.info?.controller?.motorStatus}\n" +
                                "weatherReport = ${data.info?.weatherReport?.size}\n" +
                                "gas = ${data.info?.weather?.gas}\n" +
                                "light = ${data.info?.weather?.light}\n" +
                                "ph = ${data.info?.weather?.ph}\n"
                    )
                } else {
                    Log.e(TAG, "onDataChange: Data is null")
                }

                onComplete(data)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Data retrieval failed: ${error.message}")
            }
        })
    }

    fun setManualMode(
        uid: String = settingsUtility.userId,
        manualMode: Int = 0,
        onComplete: (data: Manual?) -> Unit
    ) {
        val manualMode = Manual(manualMode)
        val childNodeRef = myRef.child(uid)
        Log.d(TAG, "uid: $uid\ndatabase: $childNodeRef")
        childNodeRef.child("info").child("manual").setValue(manualMode)
            .addOnSuccessListener {
                onComplete(manualMode)
                Log.d(TAG, "onDataChange: successful ${it}")
            }
            .addOnFailureListener {
                onComplete(null)
                Log.d(TAG, "onDataChange: failed ${it.message}")
            }
    }
}
