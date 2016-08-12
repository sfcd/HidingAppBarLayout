# HidingAppBarLayout

[![Twitter](https://img.shields.io/badge/twitter-SFCD-orange.svg)](https://twitter.com/sfcdteam?style=flat)

An AppBarLayout behavior that hides AppBar's first child

![Animation](preview.gif)

## Installation

Gradle:

Add a jCenter dependency to your build.gradle:
```groovy
compile 'com.sfcd.hiding-appbarlayout:hidingappbar-behaviour:1.0.6'
```

## Usage

Just define "com.sfcd.hidingappbar.HidingAppBarLayoutBehavior" behaviour for your android.support.design.widget.AppBarLayout

```xml
<android.support.design.widget.AppBarLayout
    android:id="@+id/app_bar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:expanded="false"

    android:fitsSystemWindows="true"

    app:layout_behavior="com.sfcd.hidingappbar.HidingAppBarLayoutBehavior"

    android:theme="@style/AppTheme.AppBarOverlay">

    <!-- First child will be influenced of hiding behaviour -->
    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:fitsSystemWindows="true"
        app:layout_scrollFlags="scroll">
        ...
    </FrameLayout>

    <android.support.v7.widget.Toolbar
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="?attr/actionBarSize"
        app:layout_scrollFlags="scroll|exitUntilCollapsed"
        app:popupTheme="@style/AppTheme.PopupOverlay" />


</android.support.design.widget.AppBarLayout>
```

## Contribution

Please feel free to ask questions, open issues and submit pull requests.

## Licence

HidingAppBarLayout is available under the MIT license. See the LICENSE file for more info.