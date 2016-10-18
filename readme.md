滴滴出行cordova插件
---
参考：[didisdk](https://github.com/Nealyang/didisdk)
原有的百度定位，现在改成了高德定位

Useage
---

	cordova plugin add https://github.com/web1992/didisdk#0.0.3 --save

发布的版本在 https://github.com/web1992/didisdk/releases 可以找到

或者可以把项目下载到本地,通过本地文件添加插件

	 cordova plugin add /data/www/didisdk/ --save
	 /data/www/didisdk/ 是你下载的目录

本地的插件在修改之后，需要删除插件，重新安装插件

	cordova plugin list #查询插件
	cordova plugin rm info.linlong.didisdk #删除插件
	cordova plugin add /data/www/didisdk/ --save #重新安装插件


EOF
---




