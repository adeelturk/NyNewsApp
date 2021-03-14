package com.base.common.infrastructure


enum class AlertCategory(var value:Int){
    ALL(-1),
    PERSON_OF_INTEREST(1),
    WANTED_VEHICLE (2),
    OVER_SPEEDING ( 3),
    SEAT_BELT ( 4),
    DISTRACTED_DRIVER ( 5),
    CAMERA ( 6),
    SYSTEM_HEALTH ( 7),
    AUDIT_TRAIL ( 8),
    BROADCASTS ( 9),
    ISRIMAP ( 10)



}

enum class SharedPrefsKey(var value:String){
     LOGGED_IN_USER_DATA("loggedInUserData"),
     LOOK_UP_DATA("lookUpData"),
    USER_INTERACTION_TIME("userInteractionTime")
}
