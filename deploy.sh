#!/bin/bash

# 权限监控器部署脚本

echo "🚀 权限监控器 - GitHub 部署脚本"
echo "=================================="

# 检查网络连接
echo "🌐 检查网络连接..."
if ! curl -s --connect-timeout 5 https://github.com > /dev/null; then
    echo "❌ 网络连接失败"
    echo ""
    echo "🔧 可能的解决方案:"
    echo "1. 检查网络连接"
    echo "2. 配置代理 (如果需要)"
    echo "3. 使用移动热点"
    echo "4. 查看 network-troubleshooting.md 获取详细解决方案"
    echo ""
    read -p "是否继续尝试? (y/N): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        echo "💡 提示: 你可以使用本地构建: ./build-local.sh"
        exit 1
    fi
fi

# 检查是否在 git 仓库中
if ! git rev-parse --git-dir > /dev/null 2>&1; then
    echo "❌ 错误: 当前目录不是 Git 仓库"
    echo "请先运行: git init"
    exit 1
fi

# 检查是否有远程仓库
if ! git remote get-url origin > /dev/null 2>&1; then
    echo "⚠️  警告: 没有配置远程仓库"
    echo "请先添加 GitHub 远程仓库:"
    echo "git remote add origin https://github.com/你的用户名/你的仓库名.git"
    echo ""
    echo "或者使用 SSH (推荐):"
    echo "git remote add origin git@github.com:你的用户名/你的仓库名.git"
    read -p "是否继续? (y/N): " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 1
    fi
fi

# 添加所有文件
echo "📁 添加文件到 Git..."
git add .

# 提交更改
echo "💾 提交更改..."
read -p "请输入提交信息 (默认: 'Initial commit'): " commit_message
commit_message=${commit_message:-"Initial commit"}
git commit -m "$commit_message"

# 推送到远程仓库
echo "🌐 推送到 GitHub..."
if git push -u origin main 2>/dev/null || git push -u origin master 2>/dev/null; then
    echo "✅ 推送成功!"
else
    echo "❌ 推送失败"
    echo ""
    echo "🔧 可能的解决方案:"
    echo "1. 检查网络连接"
    echo "2. 验证仓库 URL: git remote -v"
    echo "3. 尝试使用 SSH 替代 HTTPS"
    echo "4. 查看 network-troubleshooting.md"
    echo ""
    echo "💡 备选方案:"
    echo "1. 使用 GitHub Desktop"
    echo "2. 通过 GitHub Web 界面上传文件"
    echo "3. 使用本地构建: ./build-local.sh"
    exit 1
fi

echo ""
echo "✅ 部署完成!"
echo ""
echo "📋 接下来的步骤:"
echo "1. 访问你的 GitHub 仓库: https://github.com/Killjat/moblie_monitoring"
echo "2. 进入 Actions 页面查看构建状态"
echo "3. 构建完成后在 Actions 页面下载 APK"
echo "4. 或者创建 tag 来发布版本:"
echo "   git tag v1.0.0"
echo "   git push origin v1.0.0"
echo ""
echo "🎉 享受自动化构建的便利吧!"