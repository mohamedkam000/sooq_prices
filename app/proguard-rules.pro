-keepattributes *Annotation*, InnerClasses, Signature

-keep class com.sooq.price.data.** { *; }

-keepclassmembers class com.sooq.price.data.** {
    static <fields>;
}

-keep class kotlinx.serialization.** { *; }
-keepclassmembers class * implements kotlinx.serialization.KSerializer { *; }