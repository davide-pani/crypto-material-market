-keep class com.davidepani.cryptomaterialmarket.data.models.** { *; }

-keepclassmembers,allowobfuscation class * {
@com.google.gson.annotations.SerializedName <fields>;
}


#-keepattributes Signature
#-keep class kotlin.coroutines.Continuation
#
#-keep class kotlin.** { *; }
#-keep class kotlin.Metadata { *; }
#-dontwarn kotlin.**
#-keepclassmembers class **$WhenMappings {
#    <fields>;
#}
#-keepclassmembers class kotlin.Metadata {
#    public <methods>;
#}
#
#-keepclassmembers class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator CREATOR;
#}
#
#-keepclassmembers enum * {
#    public static **[] values();
##    public static ** valueOf(java.lang.String);
#}