#!/bin/bash

echo "🔧 本地构建脚本 - 权限监控器"
echo "================================"

# 检查是否安装了 gradle
if ! command -v gradle &> /dev/null; then
    echo "❌ 错误: 未找到 Gradle"
    echo ""
    echo "请安装 Gradle:"
    echo "macOS: brew install gradle"
    echo "或者下载 Android Studio 并使用内置的 Gradle"
    exit 1
fi

# 检查 Android SDK
if [ -z "$ANDROID_HOME" ] && [ -z "$ANDROID_SDK_ROOT" ]; then
    echo "⚠️  警告: 未设置 ANDROID_HOME 或 ANDROID_SDK_ROOT"
    echo "请设置 Android SDK 路径，例如:"
    echo "export ANDROID_HOME=/Users/\$USER/Library/Android/sdk"
    echo ""
    read -p "是否继续尝试构建? (y/N): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 1
    fi
fi

echo "🏗️  开始构建..."

# 清理之前的构建
echo "🧹 清理之前的构建..."
gradle clean

# 构建 Debug APK
echo "📱 构建 Debug APK..."
if gradle assembleDebug; then
    echo "✅ Debug APK 构建成功!"
    echo "📍 位置: app/build/outputs/apk/debug/"
    ls -la app/build/outputs/apk/debug/*.apk 2>/dev/null || echo "APK 文件未找到"
else
    echo "❌ Debug APK 构建失败"
    exit 1
fi

# 构建 Release APK
echo "📦 构建 Release APK..."
if gradle assembleRelease; then
    echo "✅ Release APK 构建成功!"
    echo "📍 位置: app/build/outputs/apk/release/"
    ls -la app/build/outputs/apk/release/*.apk 2>/dev/null || echo "APK 文件未找到"
else
    echo "⚠️  Release APK 构建失败 (可能需要签名配置)"
fi

echo ""
echo "🎉 构建完成!"
echo ""
echo "📋 接下来的步骤:"
echo "1. 在 app/build/outputs/apk/ 目录找到 APK 文件"
echo "2. 将 APK 传输到 Android 设备"
echo "3. 在设备上启用'未知来源'安装"
echo "4. 安装并测试应用"