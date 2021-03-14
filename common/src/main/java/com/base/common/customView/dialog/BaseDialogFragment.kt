package com.base.common.customView.dialog

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.base.common.customView.snackbar.SnackBarData
import com.base.common.customView.snackbar.SnackbarUtils
import com.base.common.R
import com.base.common.error.ErrorEntity

abstract class BaseDialogFragment : DialogFragment() {


     var snackView :View?=null

    fun isShowing():Boolean{
        var isShowing=false;
        dialog?.run {

            isShowing=this.isShowing && !isRemoving && !this@BaseDialogFragment.isRemoving

        }

        return isShowing
    }

     fun handleFailureInDialog(errorEntity: ErrorEntity)
    {
        handleError()
        snackView?.run {


            when (errorEntity) {
                is ErrorEntity.InternalServerError -> {
                    showErrorSnackbar(this,errorEntity.message.errors.message!!)
                }
                /* is Failure.AuthError -> enforcementUnitViewModel.errorIndicator.value =BaseErrorType.UN_AUTH_ERROR*/
                is ErrorEntity.NetworkConnection ->{
                    showErrorSnackbar(this,getString(R.string.check_internet))
                }
                else->{
                    showErrorSnackbar(this,getString(R.string.something_went_wrong))
                }

            }
        }

    }

     fun showErrorSnackbar(view:View,msg:String){

        SnackbarUtils.showSnackbar(view, SnackBarData(msg,
            SnackBarData.ERROR),requireContext())
    }

    open fun  handleError(){

    }


    private var successDialogSheet:DialogSheet?=null

    protected fun showSuccessDialog(titleText: String, msg: String, okBtnClickCallback: () -> Unit){
        successDialogSheet= DialogSheet(requireContext())
        successDialogSheet?.let {

            it.setCancelable(false)
            val view: View =
                LayoutInflater.from(activity).inflate(R.layout.succes_dialog_sheet, null, false)

            var title=view.findViewById<TextView>(R.id.title)
            var msgTxtView=view.findViewById<TextView>(R.id.msgTxtView)
            var ok_btn=view.findViewById<TextView>(R.id.ok_btn)

            title.text=titleText
            msgTxtView.text=msg

            ok_btn.setOnClickListener {
                successDialogSheet?.dismiss()
                okBtnClickCallback()

            }
            it.setView(view)
            it.show()
        }

    }

}