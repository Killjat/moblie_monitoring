# Android APK 签名配置

如果你想要生成签名的 APK（用于发布到应用商店），需要配置签名密钥。

## 1. 生成签名密钥

```bash
keytool -genkey -v -keystore my-release-key.jks -keyalg RSA -keysize 2048 -validity 10000 -alias my-key-alias
```

## 2. 配置 GitHub Secrets

在你的 GitHub 仓库中，进入 Settings > Secrets and variables > Actions，添加以下 secrets：

- `KEYSTORE_BASE64`: 你的 keystore 文件的 base64 编码
- `SIGNING_KEY_ALIAS`: 密钥别名
- `SIGNING_KEY_PASSWORD`: 密钥密码
- `SIGNING_STORE_PASSWORD`: keystore 密码

### 生成 KEYSTORE_BASE64

```bash
base64 -i my-release-key.jks | pbcopy  # macOS
base64 -w 0 my-release-key.jks         # Linux
```

## 3. 修改 app/build.gradle

在 `android` 块中添加签名配置：

```gradle
android {
    signingConfigs {
        release {
            if (System.getenv("SIGNING_KEY_ALIAS")) {
                storeFile file("keystore.jks")
                storePassword System.getenv("SIGNING_STORE_PASSWORD")
                keyAlias System.getenv("SIGNING_KEY_ALIAS")
                keyPassword System.getenv("SIGNING_KEY_PASSWORD")
            }
        }
    }
    
    buildTypes {
        release {
            signingConfig signingConfigs.release
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}
```

## 4. 启用签名构建

取消注释 `.github/workflows/release.yml` 中的签名相关步骤。