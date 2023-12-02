# PermissionHandler
[![Release](https://img.shields.io/github/v/release/KadirKuruca/PermissionHandler)](https://github.com/KadirKuruca/PermissionHandler/releases/tag/1.0)
[![Jitpack](https://jitpack.io/v/KadirKuruca/PermissionHandler.svg)](https://jitpack.io/#KadirKuruca/PermissionHandler)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)

## Overview

This library provides a convenient and easy-to-use solution for handling runtime permissions in Android applications. It is built with Kotlin, Coroutines, Flows, and Channels to provide a modern and asynchronous approach to managing permissions.

## Features

- **Simplified API:** Easily request and handle runtime permissions with a simple and concise API.
- **Coroutines Integration:** Leverage the power of Kotlin Coroutines to handle permissions asynchronously.
- **Flow-based Observables:** Use Flows to observe permission request results in a reactive and streamlined manner.
- **Channel Communication:** Communicate between components using Channels, enhancing communication and flexibility.

## Installation

Add the JitPack repository to your app level `build.gradle` file at the end of repositories:

```gradle
allprojects {
    repositories {
        // other repositories...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the following dependency to your project's `build.gradle` file:

```gradle
implementation 'com.github.KadirKuruca:PermissionHandler:1.0'
```

## Usage
First initialize the `PermissionRequestHandler` with `applicationContext`:
```kotlin
val permissionRequestHandler = PermissionRequestHandler.initialize(applicationContext)
```
Then request permission and collect result:
```kotlin
lifecycleScope.launch {
 permissionRequestHandler.requestPermission(Manifest.permission.CAMERA).collect {
   when(it) {
    is PermissionResult.Granted -> Unit // Granted Logic
    is PermissionResult.Rejected.NeedsRationale -> Unit // Needs Rationale Logic
    is PermissionResult.Rejected.RejectedPermanently -> Unit // Rejected Permanently Logic
    PermissionResult.Cancelled -> Unit // Cancelled Logic
    PermissionResult.Initial -> Unit // Initial State
  }
 }
}
```
## Contributing

Contributions are welcome! Feel free to open issues and pull requests.



## License
 
The MIT License (MIT)

Copyright (c) 2023 Kadir Kuruca

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
