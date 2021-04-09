# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
# see http://proguard.sourceforge.net/manual/examples.html#beans
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

# We want to keep methods in Activity that could be used in the XML attribute onClick
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator CREATOR;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version.  We know about them, and they are safe.
-dontwarn android.support.**
-keep class android.support.**{*;}


-keep interface android.support.**{*;}

# Understand the @Keep support annotation.
-keep class android.support.annotation.Keep

-keep @android.support.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}
-keep interface okhttp3.**{*;}

#picasso
-dontwarn com.squareup.**
-keep class com.squareup.**{*;}
-keep interface com.squareup.**{*;}

#bouncycastle
-dontwarn org.bouncycastle.**
-keep class org.bouncycastle.**{*;}
-keep interface org.bouncycastle.**{*;}

#java
-dontwarn java.**
-keep class java.**{*;}
-keep interface java.**{*;}

#okio
-dontwarn okio.**
-keep class okio.**{*;}
-keep interface okio.**{*;}

-dontwarn com.alipay.**
-keep class com.alipay.**{*;}
-keep interface com.alipay.**{*;}

#loadlayout
-dontwarn com.android.**
-keep class com.android.**{*;}
-keep interface com.android.**{*;}

#Gson混淆配置
-dontwarn com.google.gson.**
-keep class sun.misc.Unsafe { *; }
-keep class com.idea.fifaalarmclock.entity.***
-keep class com.google.gson.** { *; }

#zxing
-dontwarn com.google.zxing.**
-keep class com.google.zxing.**{*;}
-keep interface com.google.zxing.**{*;}

#weichat
-dontwarn com.tencent.**
-keep class com.tencent.**{*;}
-keep interface com.tencent.**{*;}

#union
-dontwarn com.unionpay.**
-keep class com.unionpay.**{*;}
-keep interface com.unionpay.**{*;}

#yh
-dontwarn retrofit2.**
-keep class retrofit2.**{*;}
-keep interface retrofit2.**{*;}

-dontwarn io.reactivex.**
-keep class io.reactivex.**{*;}
-keep interface io.reactivex.**{*;}

#
-dontwarn com.tomyang.whpe.**
-keep class com.tomyang.whpe.**{*;}
-keep interface com.tomyang.whpe.**{*;}

-dontwarn kotlin.**
-keep class kotlin.**{*;}
-keep interface kotlin.**{*;}

-keepattributes SourceFile,LineNumberTable

#JavaBean
-dontwarn com.whpe.qrcode.jiangxi_jian.net.getbean.**
-keep class com.whpe.qrcode.jiangxi_jian.net.getbean.**{*;}
-keep interface com.whpe.qrcode.jiangxi_jian.net.getbean.**{*;}

#data
-dontwarn com.whpe.qrcode.jiangxi_jian.data.**
-keep class com.whpe.qrcode.jiangxi_jian.data.**{*;}
-keep interface com.whpe.qrcode.jiangxi_jian.data.**{*;}

#wxapi
-dontwarn com.whpe.qrcode.jiangxi_jian.wxapi.**
-keep class com.whpe.qrcode.jiangxi_jian.wxapi.**{*;}
-keep interface com.whpe.qrcode.jiangxi_jian.wxapi.**{*;}

#view
-dontwarn com.whpe.qrcode.jiangxi_jian.view.**
-keep class com.whpe.qrcode.jiangxi_jian.view.**{*;}
-keep interface com.whpe.qrcode.jiangxi_jian.view.**{*;}

-dontwarn com.whpe.qrcode.jiangxi_jian.toolbean.**
-keep class com.whpe.qrcode.jiangxi_jian.toolbean.**{*;}
-keep interface com.whpe.qrcode.jiangxi_jian.toolbean.**{*;}

-dontwarn com.whpe.qrcode.jiangxi_jian.parent.**
-keep class com.whpe.qrcode.jiangxi_jian.parent.**{*;}
-keep interface com.whpe.qrcode.jiangxi_jian.parent.**{*;}