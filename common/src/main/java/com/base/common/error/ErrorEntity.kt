package com.base.common.error

import com.base.common.extensions.empty

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureRmsError] class.
 *
 *
 * This class should belong from domain layer because this is handling the error of the whole application
 */
@Suppress("unused")
sealed class ErrorEntity {

    lateinit var message: ErrorMessage
//    = ErrorMessage(false, Error(1, "", ""))
    var errorMessage: String = String.empty()

    object NetworkConnection : ErrorEntity()
    object ServerError : ErrorEntity()
    object AuthError : ErrorEntity()
    object BadRequest : ErrorEntity()
    object NotFound : ErrorEntity()
    object NoDataFound : ErrorEntity()
    object UnSupportedMediaType : ErrorEntity()
    object MalFormedJson : ErrorEntity()
    object IllegalStateException : ErrorEntity()
    object JsonSyntaxException : ErrorEntity()
    object InternalServerError : ErrorEntity()
    object AndroidError : ErrorEntity()
    object UniqueConstraintError : ErrorEntity()
    object UserNotFound : ErrorEntity()
    object FacebookLoginError : ErrorEntity()

    sealed class BasicError : ErrorEntity() {

        object NoInternet : BasicError()
        object UnknownError : BasicError()
        object UnAuthorized : BasicError()
        object NoError : BasicError()

    }

    sealed class LoginError : ErrorEntity() {
        object EmptyUserName : LoginError()
        object EmptyPassword : LoginError()
        object UsernameLength : LoginError()
        object PasswordLength : LoginError()
        object UsernameRegexMismatch : LoginError()
    }


    sealed class DriverErrors : ErrorEntity() {
        object InvalidLicenseNumber : DriverErrors()
        object InvalidLicenseNumberLength : DriverErrors()
        object LicenseNumberRegexMismatch : DriverErrors()
    }

    sealed class VehicleErrors : ErrorEntity() {
        object InvalidLPlateNumber : VehicleErrors()
        object InvalidPlateNumberLength : VehicleErrors()
        object PlateNumberRegexMismatch : VehicleErrors()
    }

    sealed class ViolationErrors:ErrorEntity(){
        object EmptyViolationList : ViolationErrors()
    }

    sealed class SubmitTicketError:ErrorEntity(){
        object RepeatedCautionary : SubmitTicketError()
    }


}







