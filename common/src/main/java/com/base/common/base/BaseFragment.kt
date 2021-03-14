package com.base.common.base

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.base.common.customView.dialog.DialogSheet
import com.base.common.customView.dialog.MyProgressDialog
import com.base.common.customView.snackbar.SnackBarData
import com.base.common.customView.snackbar.SnackbarUtils
import com.base.common.extensions.observe
import com.base.common.infrastructure.BackButtonPressListener
import com.base.common.R
import com.base.common.error.ErrorMessage
import com.base.common.error.ErrorEntity


/**
 * Created by Turk
 * on 3/13/2018.
 */

/**
 *
 * @author Adeel Ur Rehman Turk
 *
 * This is a Base class of every fragment defined in the project .
 * Child must override all function and provide all required data
 * it handles following stuff
 * Ui data binding
 * Viewmodel initialisation
 * lifecycle binding with UI
 * observe some common options/actions from view model (we have a base viewmodel )
 * Extending this class child must follow clean MVVM arhcitecture
 *
 */
// abstract class BaseFragment<T : ViewDataBinding> : Fragment(), BaseView<T>
@Suppress("unused")
abstract class BaseFragment : Fragment() {

    private var isInProgress = true
    private val IS_PROGRESS_VISIBLE = "isInProgress"
    private val progressDialog = MyProgressDialog()
    private lateinit var progressDialogMessage: String
    protected lateinit var binding: ViewDataBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, layoutResource(), container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        snackBarView = binding.root
        return binding.root

    }

    protected fun setScreenTitle(title: String, subtitle: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
        (activity as AppCompatActivity).supportActionBar?.subtitle = subtitle

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getViewModel().init()
        progressDialogMessage = getString(R.string.please_wait)
        setProcessStatusCall()
        setProgressIndicatorCallback()
        progressIndicatorObserver()
        setFailureIndicatorCallback()
        setSnackBarCallBack()
        observeErrorMessage()
    }

    private var successDialogSheet: DialogSheet?=null

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
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialise()
    }

     fun setBackButtonListener(backButtonListener: BackButtonPressListener?) {
        (activity as BaseActivity).backButtonPressListener=backButtonListener
    }

    /**
     *  This method is observing error message live data object to notify show proper message on snackbar
     *
     */

    private fun observeErrorMessage() {

        observe(getViewModel().errorMessage) {

            getViewModel().setSnackbarData(
                SnackBarData(
                    it.errors.message,
                    SnackBarData.ERROR
                )
            )
            onServerError(it)
        }
    }


    /**
     *  This method is observing maintenance_plan status live data object to notify View about any Process status started , going on or ended
     *  so that View can act accordingly for example show progress or close UI or move forward to next screen etc.
     *
     */
    private fun setProcessStatusCall() {

        observe(getViewModel().baseProcessProcessStatus) {

            when (it) {
                BaseProcessType.DEFAULT.value -> {
                    //Do Nothing
                    Log.d("Process Status", "BaseProcessType.DEFAULT.value")
                }
                else -> {
                    onViewModelProcessStatus(it)
                }

            }
        }
    }

    /**
     *  This method is observing Progress Indicator Reasons live data object to notify View so that View can show
     *  proper message while show progress dialog. for example Please wait.. or Uploading data please wait., etc
     *
     */

    private fun setProgressIndicatorCallback() {

        observe(getViewModel().progressIndicatorReason) {

            when (it) {

                BaseProgressIndicatorReasonType.DEFAULT.reason -> {
                    //Do Nothing
                }
                BaseProgressIndicatorReasonType.LOADING.reason -> {
                    progressDialogMessage = getString(R.string.loading)
                }
                BaseProgressIndicatorReasonType.PLEASE_WAIT.reason -> progressDialogMessage =
                    getString(R.string.please_wait)
                else -> {
                    onProgressIndicatorReason(it)
                }
            }

        }
    }

    /**
     *  This method is observing Progress Indicator Reaons live data object to notify View so that View can show
     *  proper message while show progress dialog. for example Please wait.. or Uploading data please wait., etc
     *
     */

    private fun progressIndicatorObserver() {

        observe(getViewModel().progressIndicator) { showProgress ->


//            TODO("Run is not working here learn WHY?")
            try {
//                progressDialog.run {
                    if (showProgress) {

//                        childFragmentManager.run {
                            progressDialog.setMessage(progressDialogMessage)
                            if (!progressDialog.isShowing()) {
                                progressDialog.show(parentFragmentManager, "progressDialog")
//                            }

                        }

                    } else {

                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss()
                        }

//                    }


                }

            } catch (ex: Exception) {

                ex.printStackTrace()
            }


        }
    }


    /**
     *  use this to hide Keyboard explicitly
     *
     */
    @Suppress("unused")
    fun hideKeyboard() {
        activity?.apply {

            val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(this)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }

    /**
     *
     *  use this method to hide keyboard from a specific view lets say you have edit text in a dialog.
     *
     */
    fun hideKeyboard(viewPassed: View?) {
        var view = viewPassed

        activity?.apply {

            val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(this)
            }
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
        }


    }

    /**
     *  This method is observing Error live data object to notify View about error happened in view model so that View can
     *  take appropriate action for example showing error message or retying the same action etc
     *
     */

    private fun setFailureIndicatorCallback() {

        observe(getViewModel().errorEntityIndicator) { error ->

            when (error) {
                ErrorEntity.BasicError.NoError -> {
                }
                ErrorEntity.BasicError.NoInternet -> {
                    getViewModel().setSnackbarData(
                        SnackBarData(
                            getString(R.string.check_internet),
                            SnackBarData.ERROR
                        )
                    )
                    onNoInternetError()
                }

                ErrorEntity.BasicError.UnknownError -> {
                    getViewModel().setSnackbarData(
                        SnackBarData(
                            getString(R.string.something_went_wrong),
                            SnackBarData.ERROR
                        )
                    )
                    onUnknownError()

                }

                ErrorEntity.BasicError.UnAuthorized -> {
                    getViewModel().setSnackbarData(
                        SnackBarData(
                            getString(R.string.something_went_wrong),
                            SnackBarData.ERROR
                        )
                    )
                    onUnknownError()
                    onUnauthorisedCall()
                }
                else -> {
                    onViewModelError(error)

                }

            }


        }

        // LoginErrorType.EMPTY_USERNAME.type
    }


    /**
     *  This method will be invoked on internet disconnection so that View can take appropriate action
     *  forexample showing offline notification etc
     *
     */

    open fun onNoInternetError() {

    }

    open fun onUnknownError() {

    }

    open fun onUnauthorisedCall() {

        getViewModel().setSnackbarData(
            SnackBarData(
                getString(R.string.please_logout_no_access),
                SnackBarData.INFO
            )
        )

    }

    /**
     *  This method is observing SnackBarData in viewModel to show snackbar when required
     *
     */
    private fun setSnackBarCallBack() {

        observe(getViewModel().snackBackDataObservable) { snackBarData ->

            when (snackBarData.messageType) {
                SnackBarData.DEFAULT_TYPE -> {
                }
                else -> {

                    SnackbarUtils.showSnackbar(snackBarView, snackBarData, activity)
//                    getViewModel().setSnackbarData(
//                        SnackBarData(
//                            "",
//                            SnackBarData.DEFAULT_TYPE
//                        )
//                    )
                }

            }

        }

    }

    override fun onPause() {
        super.onPause()
        progressDialog.run {

            try {
                if(progressDialog.isShowing()) {
                    progressDialog.dismiss()
                }
            } catch (ex: Exception) {

                ex.printStackTrace()
            }
        }

    }

    override fun onStop() {
        super.onStop()
        successDialogSheet?.dismiss()
    }


    protected var snackBarView: View? = null


    /**
     *  Override this method to set Res layout id to bind it with fragment
     *
     */
    protected abstract fun layoutResource(): Int
    /**
     *  Override this method to set Fragment object
     *
     */
    protected abstract fun getFragment(): BaseFragment

    /**
     *  Override this method an set Res layout id to bind it with fragment
     *
     */
    /**
     *  Override this method to set ViewModel object
     *
     */
    protected abstract fun getViewModel(): BaseViewModel
    /**
     *  Override this method an set Res layout id to bind it with fragment
     *
     */
    protected abstract fun initialise()
    /**
     *  This method wil be invoke when error message from the API call or the repository layer is displayed via snackbar
     *@param errorMessage ErrorMessage have error_code and error message
     */
    protected open fun onServerError(errorMessage: ErrorMessage)
    {

    }
    /**
     *  This method wil be invoke when any error happens in viewmodel , It have Error Type in paramter to compare an act accordingly
     * @param error ErrorType data
     */
    protected open fun onViewModelError(error: ErrorEntity){

    }
    protected open fun onViewModelProcessStatus(processStatus: String)
    {

    }
    protected open fun onProgressIndicatorReason(indicatorReason: String){

    }


    override fun onResume() {
        super.onResume()
    }

    private fun hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        activity?.window?.decorView?.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }


}