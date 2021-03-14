package com.base.common.base

import androidx.lifecycle.ViewModel
import com.base.common.customView.snackbar.SnackBarData
import com.base.common.error.ErrorMessage
import com.base.common.error.ErrorEntity
import com.base.common.infrastructure.SingleLiveEvent

/**
 *
 * @author Adeel Ur Rehman Turk
 *
 * This is a Base class of every viewModel defined in the project .
 * Child must override all function and provide all required data
 * This class have some common LiveData variable which notifies View about Process status , Error messages etc
 * Extending this class child must follow clean MVVM architecture
 *
 *
 * <p>
 */
@Suppress("unused")
abstract class BaseViewModel() : ViewModel() {

    //region Props

    //This is used for general application failures e.g regex,no internet,
    val errorEntityIndicator: SingleLiveEvent<ErrorEntity> = SingleLiveEvent()


    // This is specifically used in the case if the error messages came from API call or the repository layer
    var errorMessage: SingleLiveEvent<ErrorMessage> = SingleLiveEvent()

    var baseProcessProcessStatus: BaseProcessType = BaseProcessType()

    var progressIndicatorReason: BaseProgressIndicatorReasonType = BaseProgressIndicatorReasonType.DEFAULT
    var progressIndicator: SingleLiveEvent<Boolean> = SingleLiveEvent()

    var snackBackDataObservable: SingleLiveEvent<SnackBarData> = SingleLiveEvent()


    var isOnlineStatus: SingleLiveEvent<Boolean> = SingleLiveEvent()

    private var isDataAvailable = true
    private var initialised = false
    private var isRtl = true

    //endregion


    //region Failures Handling
    /**
     *  This method handles Failure coming from remote data source and set error indicator values to notify View if it is one of the common error,
     *  else it will set error aas unknown
     *
     *@param errorEntity Failure object having error type to act accordingly
     */
    protected open fun handleFailure(errorEntity: ErrorEntity) {

        hideProgressDialog()
        setProgressIndicatorReason(BaseProgressIndicatorReasonType.HIDE_SWIPE_REFRESH.reason)
        when (errorEntity) {

            is ErrorEntity.InternalServerError -> pushErrorMessageData(errorEntity.message)
            is ErrorEntity.AuthError -> errorEntityIndicator.value = ErrorEntity.BasicError.UnAuthorized
            is ErrorEntity.NetworkConnection -> {
                errorEntityIndicator.value = ErrorEntity.BasicError.NoInternet
                isOnlineStatus.value = false
            }
            else -> errorEntityIndicator.value = ErrorEntity.BasicError.UnknownError

        }
    }

    /**
     *  Use this method To set error messages values and notify View so that view can show error message in snackbar
     *
     *@param data ErrorMessage object having error type to act accordingly
     */
    fun pushErrorMessageData(data: ErrorMessage) {

        errorMessage.value = data

    }

    //endregion

    // regionInternet Check
    /**
     *  Use this method to set boolean value for mobile app being connected to network
     *  This boolean value can be used to view appropriate UI etc
     **/
    fun isOnline(isOnline: Boolean) {

        isOnlineStatus.value = isOnline
    }

    // endregion

    //region Initializer
    open fun init() {
        //baseProcessProcessStatus=BaseProcessType.DEFAULT
        isDataAvailable = true
        initialised = false
        isRtl = true
    }

    //endregion


    //region RTL
    fun isRtl(): Boolean {

        return isRtl
    }

    fun setRtl(rtl: Boolean) {

        this.isRtl = rtl
    }
    //endregion

    // region Error Indicator setter and Getter
    private fun resetfailureIndicator() {

        errorEntityIndicator.value = ErrorEntity.BasicError.NoError
    }

    fun setError(error: ErrorEntity) {

        errorEntityIndicator.value = error
        resetfailureIndicator()

    }

    //endregion

    //region Setting Process Statuses

    fun setProcessStatus(processStatus: String?) {

        baseProcessProcessStatus.value = processStatus!!

    }

    fun resetProcessStatus() {

        baseProcessProcessStatus.value = BaseProcessType.DEFAULT.value
    }

    //endregion

    // region Progress Indicator Message
    fun setProgressIndicatorReason(reason: String) {

        progressIndicatorReason.value = reason
    }

    //endregion


    // region Progress Dialog Setter & Getter
    fun showProgressDialog() {

        progressIndicator.value = true
    }

    fun hideProgressDialog() {
        progressIndicator.value = false
    }

    //endregion

    //region Snack Bar Message

    /**
     * This method is used to Show Snack bar when the observable is changed
     */
    fun setSnackbarData(snackBarData: SnackBarData) {

        snackBackDataObservable.value = snackBarData

    }

    //endregion

    fun getIsDataAvailable(): Boolean {

        return isDataAvailable
    }

    @Suppress("UNUSED_PARAMETER")
    fun setIsDataAvailable(flag: Boolean) {

        isDataAvailable
    }


//    private var failureObserver: SingleLiveEvent<Failure> = SingleLiveEvent() // the failure should just emit once. We don't want failures to emit when an observer resubscribes to this property.
//
//    fun failure(failureError: Failure) {
//
//        failureObserver.value = failureError
//
//    }





}