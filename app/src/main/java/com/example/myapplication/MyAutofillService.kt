package com.example.myapplication

import android.os.Build
import android.service.autofill.AutofillService
import android.service.autofill.Dataset
import android.service.autofill.FillCallback
import android.service.autofill.FillContext
import android.service.autofill.FillRequest
import android.service.autofill.FillResponse
import android.service.autofill.SaveCallback
import android.service.autofill.SaveRequest
import android.util.Log
import android.view.View
import android.view.autofill.AutofillId
import android.view.autofill.AutofillValue
import androidx.annotation.RequiresApi
import androidx.core.os.CancellationSignal
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
class MyAutofillService : AutofillService() {


    @Inject
    lateinit var parser: StructureParser

    @Inject
    lateinit var responseBuilder: ResponseBuilder

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    override fun onFillRequest(request: FillRequest, cancellationSignal: android.os.CancellationSignal, callback: FillCallback) {
        val structure = request.fillContexts[request.fillContexts.size - 1].structure
        val appPackageName = structure.activityComponent.packageName

        val fields = parser.parse(structure)
        callback.onSuccess(responseBuilder.createResponse(this, appPackageName, fields))
    }

    override fun onSaveRequest(request: SaveRequest, callback: SaveCallback) {
    }

}

