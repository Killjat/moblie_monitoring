# 快速开始指南

## 🚨 网络问题解决方案

### 方案 1: 本地构建 (推荐)
```bash
# 直接在本地构建 APK
./build-local.sh
```

### 方案 2: 修复网络连接
```bash
# 检查网络
ping github.com

# 如果使用代理，配置 Git 代理
git config --global http.proxy http://your-proxy:port

# 或者使用 SSH 替代 HTTPS
git remote set-url origin git@github.com:Killjat/moblie_monitoring.git
```

### 方案 3: 手动上传到 GitHub
1. 压缩项目文件:
   ```bash
   tar -czf project.tar.gz . --exclude='.git' --exclude='build'
   ```
2. 访问 https://github.com/Killjat/moblie_monitoring
3. 点击 "Upload files" 上传压缩包

## 📱 APK 构建选项

### 选项 1: 本地构建
```bash
# 需要安装 Android Studio 或 Gradle
./build-local.sh
```

### 选项 2: GitHub Actions (需要网络)
```bash
# 推送代码后自动构建
./deploy.sh
```

### 选项 3: Android Studio
1. 用 Android Studio 打开项目
2. Build > Build Bundle(s) / APK(s) > Build APK(s)

## 🎯 最简单的方法

如果你只想快速获得 APK:

1. **安装 Android Studio**
2. **打开这个项目**
3. **点击绿色的运行按钮**
4. **选择 "Build APK"**

APK 会生成在 `app/build/outputs/apk/debug/` 目录。

## 📋 项目文件说明

- `app/` - Android 应用源码
- `build-local.sh` - 本地构建脚本
- `deploy.sh` - GitHub 部署脚本
- `.github/workflows/` - GitHub Actions 配置
- `network-troubleshooting.md` - 网络问题解决方案

## 🔧 故障排除

### Gradle 问题
```bash
# 清理构建缓存
gradle clean
# 或者
./gradlew clean
```

### 网络问题
查看 `network-troubleshooting.md` 文件获取详细解决方案。

### 权限问题
```bash
# 给脚本执行权限
chmod +x build-local.sh
chmod +x deploy.sh
```

## 📞 需要帮助?

1. 查看 `README.md` 了解项目详情
2. 查看 `network-troubleshooting.md` 解决网络问题
3. 使用 Android Studio 的内置构建功能