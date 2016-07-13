# listViewLoadmore  listview分页请求网络接口数据实例【新手可以学习下，高手请无视】
* android listview 分页加载更多的例子，服务端使用php开发的接口方便本地调试
* 使用lurcache缓存图片，listView中使用viewHolder复用
* 完美解决图片错乱，闪烁问题
* 简单几句，搭建好php服务器，本地调用接口数据
* 图文列表+图文详情

# 服务端为php模拟数据
* 命令行进入server目录，开启 php  -S 0.0.0.0:8000 即可
* 默认的网站ip为192.168.145.162，可以根据自己的实际需求改成对应ip
* api-page.php为分页文件
* api-detail.php为内容详情文件
