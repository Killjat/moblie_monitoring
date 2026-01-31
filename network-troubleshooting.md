# 网络连接问题解决方案

## 问题描述
- GitHub 推送失败: `Failed to connect`
- Gradle 下载失败: `Couldn't connect to server`

## 解决方案

### 1. 检查网络连接
```bash
# 测试基本网络连接
ping google.com
ping github.com

# 测试 HTTPS 连接
curl -I https://github.com
```

### 2. 配置代理 (如果使用代理)
```bash
# Git 代理配置
git config --global http.proxy http://proxy.company.com:8080
git config --global https.proxy https://proxy.company.com:8080

# 或者使用 SOCKS5 代理
git config --global http.proxy socks5://127.0.0.1:1080
git config --global https.proxy socks5://127.0.0.1:1080

# 取消代理
git config --global --unset http.proxy
git config --global --unset https.proxy
```

### 3. 使用 SSH 替代 HTTPS
```bash
# 生成 SSH 密钥
ssh-keygen -t ed25519 -C "your_email@example.com"

# 添加到 ssh-agent
eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_ed25519

# 将公钥添加到 GitHub
cat ~/.ssh/id_ed25519.pub

# 更改远程 URL 为 SSH
git remote set-url origin git@github.com:Killjat/moblie_monitoring.git
```

### 4. 本地构建方案

如果网络问题无法解决，可以使用本地构建:

```bash
# 使用本地构建脚本
./build-local.sh
```

### 5. 离线 GitHub 上传

1. **压缩项目文件**:
   ```bash
   tar -czf moblie_monitoring.tar.gz . --exclude='.git' --exclude='build' --exclude='*.apk'
   ```

2. **通过 GitHub Web 界面上传**:
   - 访问 https://github.com/Killjat/moblie_monitoring
   - 点击 "Upload files"
   - 拖拽压缩文件或选择文件上传

3. **或者使用 GitHub Desktop**:
   - 下载 GitHub Desktop 应用
   - 克隆仓库到本地
   - 复制文件并提交推送

### 6. 移动热点测试
如果是网络环境问题，可以尝试:
- 使用手机热点
- 更换网络环境
- 联系网络管理员

### 7. DNS 问题解决
```bash
# 刷新 DNS 缓存 (macOS)
sudo dscacheutil -flushcache
sudo killall -HUP mDNSResponder

# 使用公共 DNS
# 在网络设置中将 DNS 改为:
# 8.8.8.8, 8.8.4.4 (Google)
# 或 1.1.1.1, 1.0.0.1 (Cloudflare)
```

## 当前状态检查

运行以下命令检查当前状态:
```bash
# 检查 Git 配置
git config --list | grep -E "(proxy|url)"

# 检查网络连接
curl -I https://api.github.com

# 检查 SSH 连接 (如果使用 SSH)
ssh -T git@github.com
```