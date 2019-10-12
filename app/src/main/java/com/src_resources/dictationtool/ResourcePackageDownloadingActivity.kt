package com.src_resources.dictationtool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.ProgressBar
import android.widget.TextView
import java.lang.ref.WeakReference

class ResourcePackageDownloadingActivity : AppCompatActivity() {

    companion object {
        const val INTENT_EXTRA__DOWNLOADING_NAME =
            "com.src_resources.dictationtool.ResourcePackageDownloadingActivity.INTENT_EXTRA__DOWNLOADING_NAME"

        /**
         * arg1: The progress to be updated
         */
        private const val MESSAGE_WHAT__UPDATE_DOWNLOADING_PROGRESS_BAR = 0x0000
    }

    private class MyHandler(outerClassObj: ResourcePackageDownloadingActivity) : Handler() {
        private val outerClassObjRef = WeakReference(outerClassObj)
        val outerClassObj: ResourcePackageDownloadingActivity
                get() = outerClassObjRef.get() ?: throw IllegalStateException("outerClassObjRef is null")

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MESSAGE_WHAT__UPDATE_DOWNLOADING_PROGRESS_BAR -> {
                    if (msg.arg1 in 0 .. 100) {
                        val progressStr = "${msg.arg1}%"
                        outerClassObj.tvDownloadingProgress.text = progressStr
                        outerClassObj.pbDownloadingProgress.progress = msg.arg1
                    } else {
                        Log.e("MyHandler", "pbDownloadingProgress update failed because" +
                                " of a invalid progress number.")
                    }
                }
            }
        }
    }

    private class DownloadingThread(val outerClassObj: ResourcePackageDownloadingActivity) : Thread() {
        override fun run() {
            for (i in 1 .. 100) {
                val msg = outerClassObj.mHandler.obtainMessage()
                msg.what = MESSAGE_WHAT__UPDATE_DOWNLOADING_PROGRESS_BAR
                msg.arg1 = i
                outerClassObj.mHandler.sendMessage(msg)
                sleep(50)
            }
        }
    }

    private lateinit var tvDownloadingName: TextView
    private lateinit var tvDownloadingProgress: TextView
    private lateinit var pbDownloadingProgress: ProgressBar

    private lateinit var mHandler: MyHandler
    private lateinit var mDownloadingThread: DownloadingThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource_package_downloading)

        mHandler = MyHandler(this)

        val downloadingName = intent.getStringExtra(INTENT_EXTRA__DOWNLOADING_NAME)

        tvDownloadingName = findViewById(R.id.tvDownloadingName)
        tvDownloadingName.text = downloadingName

        tvDownloadingProgress = findViewById(R.id.tvDownloadingProgress)

        pbDownloadingProgress = findViewById(R.id.pbDownloadingProgress)

        mDownloadingThread = DownloadingThread(this)
        mDownloadingThread.start()
    }
}
