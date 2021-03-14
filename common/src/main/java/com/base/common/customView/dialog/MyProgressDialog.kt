package com.base.common.customView.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.base.common.R
import java.util.*

class MyProgressDialog : BaseDialogFragment() {
        var rootView: View? = null
            private set
        private var message: String? = null
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            rootView = LayoutInflater.from(this.context).inflate(R.layout.progress_dialog_view, view as ViewGroup?, false)
            // creating the fullscreen dialog
            val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(rootView!!)
            Objects.requireNonNull(dialog.window)?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.setCancelable(false)
            dialog.setCanceledOnTouchOutside(false)
            val messageTv = rootView!!.findViewById<TextView>(R.id.message)
            messageTv.text = message
            return dialog
        }

        @SuppressLint("LogNotTimber")
        override fun show(manager: FragmentManager, tag: String?) {
            try {
                val ft = manager.beginTransaction()
                ft.add(this, tag)
                ft.commitAllowingStateLoss()
            } catch (e: IllegalStateException) {
                Log.d("ABSDIALOGFRAG", "Exception", e)
            }
        }

        /**
         * Method to update progress value
         * @param message Pass message to explain the reason of blocking ui and making user wait
         */
        fun setMessage(message: String?) {
            this.message = message
        }

        override fun onDismiss(dialog: DialogInterface) {
            super.onDismiss(dialog)
        }
    }





