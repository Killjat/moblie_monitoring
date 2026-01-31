# 权限监控器 (Permission Monitor)

一个Android应用，用于检测哪些应用正在访问你的通讯录和图片等敏感信息。

## 功能特性

- 🔍 检测访问通讯录权限的应用
- 📸 检测访问图片/存储权限的应用
- 🔐 输入验证码"helloworld"启动检测
- 📱 显示应用图标、名称和权限信息
- 🔄 支持刷新功能

## 构建说明

### 方式一：GitHub Actions 自动构建 (推荐)

1. **Fork 或上传项目到 GitHub**
2. **自动构建**: 每次推送代码时会自动构建 APK
3. **下载 APK**: 在 Actions 页面下载构建好的 APK 文件
4. **发布版本**: 创建 tag (如 `v1.0.0`) 会自动创建 Release 并上传 APK

```bash
# 创建并推送 tag 来触发发布
git tag v1.0.0
git push origin v1.0.0
```

### 方式二：本地构建

#### 环境要求
- Android Studio Arctic Fox 或更高版本
- Android SDK API 24+ (Android 7.0)
- Kotlin 1.8.20+

#### 构建步骤

1. 在Android Studio中打开项目
2. 等待Gradle同步完成
3. 连接Android设备或启动模拟器
4. 点击"Run"按钮或使用快捷键 Ctrl+R (Mac: Cmd+R)

#### 生成APK

```bash
# 生成Debug APK
./gradlew assembleDebug

# 生成Release APK (需要签名)
./gradlew assembleRelease
```

APK文件将生成在 `app/build/outputs/apk/` 目录下。

### GitHub Actions 功能

- ✅ 自动构建 Debug 和 Release APK
- ✅ 缓存 Gradle 依赖以加速构建
- ✅ 自动上传 APK 作为 Artifacts
- ✅ 创建 tag 时自动发布 Release
- ✅ 支持手动触发构建

## 使用方法

1. 安装应用到Android设备
2. 打开应用
3. 在输入框中输入 "helloworld"
4. 点击"开始检测"按钮
5. 授予必要的权限（使用情况访问权限）
6. 查看检测结果

## 权限说明

应用需要以下权限：
- `PACKAGE_USAGE_STATS`: 用于访问应用使用统计信息
- `QUERY_ALL_PACKAGES`: 用于查询所有已安装的应用包信息

## 注意事项

- 首次使用需要手动授予"使用情况访问"权限
- 部分系统应用可能不会显示在列表中
- 检测结果基于应用声明的权限，实际使用情况可能有所不同

## 技术栈

- Kotlin
- Android Jetpack
- Material Design Components
- RecyclerView
- ViewBinding