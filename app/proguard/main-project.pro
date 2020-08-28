-keep class com.tenten.yugibrick.BuildConfig { *; }

-keep public class androidx.appcompat.widget.** { *; }
-keep public class androidx.appcompat.view.menu.** { *; }

-keep public class * extends androidx.core.view.ActionProvider {
    public <init>(android.content.Context);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# keep model classes for retrofit
-keep class com.tenten.yugibrick.domain.model.** { *; }

#unresolved classes
-dontwarn com.fasterxml.jackson.databind.ext.DOMSerializer
-dontwarn com.fasterxml.jackson.databind.ext.Java7SupportImpl
-dontwarn java8.util.DelegatingSpliterator
-dontwarn java8.util.DelegatingSpliterator$ConsumerDelegate
-dontwarn java8.util.Spliterators
-dontwarn org.bouncycastle.jce.provider.X509LDAPCertStoreSpi
-dontwarn org.bouncycastle.x509.util.LDAPStoreHelper
-dontwarn org.slf4j.LoggerFactory
-dontwarn org.slf4j.MDC
-dontwarn org.slf4j.MarkerFactory
-dontwarn jnr.posix.JavaPOSIX$SunMiscSignalHandler

#proguard bug: https://stackoverflow.com/questions/32185060/android-proguard-failing-with-value-i-is-not-a-reference-value/32615580
-optimizations !class/unboxing/enum