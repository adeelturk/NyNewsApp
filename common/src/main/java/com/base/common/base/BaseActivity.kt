package com.base.common.base

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.base.common.customView.dialog.DialogSheet
import com.base.common.customView.dialog.MyProgressDialog
import com.base.common.customView.snackbar.SnackBarData
import com.base.common.customView.snackbar.SnackbarUtils
import com.base.common.infrastructure.BackButtonPressListener
import com.base.common.infrastructure.LocaleManager
import com.base.common.R
import com.base.common.error.ErrorEntity
import org.koin.android.ext.android.inject


/**
 *  @author Adeel Ur Rehman Turk
 * on 3/13/2018.
 */

/**
 *
 * This is a Base class of every activity  defined in the project .
 * This class handles back-stack and displays exit dialog when there is  nothing in back stack
 * This class have Progress Dialog if needed
 * Child must initialise @param [navController: NavController] so that back-stack can be maintained
 * Base Activity can handle errors from remote data source
 * Child can show snackbars using Base activity methods
 */

abstract class BaseActivity : AppCompatActivity() {

    private var quitDialog: DialogSheet? = null
    private var logoutAllDialog: DialogSheet? = null
    protected var navController: NavController? = null
    private val progressDialog: MyProgressDialog by inject()
    protected var showShowQuitDialog = true
    private var closePageDialog: DialogSheet? = null


    var backButtonPressListener: BackButtonPressListener? = null

    // protected val  splashNavigation: SplashNavigation by inject()


    fun showCloseDialog(message: String) {

        closePageDialog = DialogSheet(this)
        closePageDialog?.apply {

            setTitle(getString(R.string.note))
            setMessage(message)
            setButtonsColor(
                    ContextCompat.getColor(
                            this@BaseActivity,
                            R.color.colorAccent
                    )
            )
            setPositiveButton(R.string.yes) {
                finish()
            }

            setNegativeButton(R.string.no) {
                dismiss()
            }

            show()
        }


    }


    private var errorDialog: DialogSheet? = null

    /**
     *  Show Exception is bottom dialog
     *  @param message Pass message value to show in Dialog Sheet.
     *
     */
    private fun showException(message: String) {
        errorDialog = DialogSheet(this)

        errorDialog?.apply {

            setTitle(getString(R.string.note))
            setMessage(message)
            setButtonsColor(ContextCompat.getColor(this@BaseActivity, R.color.colorSecondary))
            setPositiveButton(R.string.yes) {
                finish()
            }

            setNegativeButton(R.string.no) {
                dismiss()
            }

            show()
        }

    }

    /**
     *  When user click back button and backstack is empty, A bottom dialog sheet will appear up with proper Quit message.
     *
     */
    private fun showQuitDialog() {

        quitDialog = DialogSheet(this)
        quitDialog?.apply {

            setTitle(getString(R.string.note))
            setMessage(getString(R.string.sure_to_quit))
            setButtonsColor(ContextCompat.getColor(this@BaseActivity, R.color.colorAccent))
            setPositiveButton(R.string.yes) {
                finish()
            }

            setNegativeButton(R.string.no) {
                dismiss()
            }

            show()
        }


    }

    /**
     *  use this method to show logout dialog.
     * @param logoutCallBack pass method as reference to get call back when user press positive button being
     * displayed on logout bottom dialog sheet.
     */

    fun showLogoutDialog(logoutCallBack: () -> Unit) {

        logoutAllDialog = DialogSheet(this)
        logoutAllDialog?.apply {

            setTitle(getString(R.string.logout))
            setMessage(getString(R.string.sure_to_logout))
            setButtonsColor(ContextCompat.getColor(this@BaseActivity, R.color.colorSecondary))
            setPositiveButton(R.string.yes) {
                logoutCallBack()
            }

            setNegativeButton(R.string.no) {
                dismiss()
            }

            show()
        }


    }

    /**
     *  use this method to show Snackbar with appropriate message and color. To set color and message use SnackBarData object.
     * @param snackbar pass SnackBarData containing message and SnackBarType to select color .
     */

    protected fun showSnackBar(snackbar: SnackBarData) {

        SnackbarUtils.showSnackbar(snackBarView(), snackbar, this)
    }

    open fun handleFailure(errorEntity: ErrorEntity) {

        hideProgressDialog()

        when (errorEntity) {
            is ErrorEntity.AuthError -> {
            }
            is ErrorEntity.NetworkConnection -> {
                showSnackBar(
                        SnackBarData(
                                getString(R.string.check_internet),
                                SnackBarData.ERROR
                        )
                )

            }
            else -> {
                showSnackBar(
                        SnackBarData(
                                getString(R.string.something_went_wrong),
                                SnackBarData.ERROR
                        )
                )


            }
        }
        onApiError()
    }

    open fun onApiError() {

    }


    protected fun setMoveTologinReason(reason: String) {

        moveToLoginReason = reason
    }

    protected fun resetMoveTologinReason() {

        moveToLoginReason = ""
    }


    val UPDATE_SPEED = "update_speed"
    private var moveToLoginReason: String = ""


    /**
     *  Override This method in child to set SnackBar view because sometimes you want to show
     *  snackbar on the top of any dialog which might have blocked the main view.
     *
     */
    abstract fun snackBarView(): View

    /**
     *
     *  use this method to hide Progress dialog if visible
     *
     */
    protected fun hideProgressDialog() {

        if (progressDialog.isShowing()) {
            progressDialog.dismiss()
        }
    }

    /**
     *
     *  use this method to show Progress dialog if is not  visible
     *
     */
    protected fun showProgressDialog(progressDialogMessage: String) {
        progressDialog.setMessage(progressDialogMessage)
        if (!progressDialog.isShowing()) {
            progressDialog.show(this.supportFragmentManager, "progressDialog")
        }
    }

    /**
     *
     *  use this method to hide keyboard explicitly
     *
     */
    @Suppress("unused")
    fun hideKeyboard() {


        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)


    }

    /**
     *
     *  use this method to hide keyboard from a specific view lets say you have edit text in a dialog.
     *
     */
    protected fun hideKeyboard(viewPassed: View?) {
        var view = viewPassed
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }

    /**
     *
     *  use this method to set Locale .. in case of multi language support
     *
     */
    protected fun setNewLocale(language: String) {
        LocaleManager.setNewLocale(this, language)
        /* val intent = Intent(mContext, SplashActivity::class.java)
         startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))*/
    }

    override fun onPause() {
        super.onPause()
        quitDialog?.run {

            dismiss()
        }
        logoutAllDialog?.run {

            dismiss()
        }
        errorDialog?.run {

            dismiss()
        }

        closePageDialog?.run {
            this.dismiss()
        }

    }

    override fun onBackPressed() {

        if (backButtonPressListener != null) {
            backButtonPressListener?.onBackButtonPressed()
            return
        }

        if (showShowQuitDialog) {


            if (navController?.graph?.startDestination == navController?.currentDestination?.id) {
                showQuitDialog()
            } else {
                super.onBackPressed()
            }

        } else {

            super.onBackPressed()
        }

    }


}