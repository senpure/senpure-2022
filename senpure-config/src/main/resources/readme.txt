
excel {0,0} java类名或lua文件名                                       必须
excel {0,1} java包名                                                  必须
excel {1，*}第二行 代码字段名                                         可以为空
excel {2，*}第三行 字段类型 String int long double boolean            可以为空
                            后面可以跟上 “[list,]”(不含引号)
                            表示是一个list其中逗号可以省略或换
                            成其他字符，表示为数组的分隔符
excel {3，*}第四行 字段说明                                            必须
excel {4，*} -excel {n，*} 填写具体的类容

excel {n+n，*}    可以写一下注释之类的东西

boolean 类型 可以用true false 或 1 0

applciation.properties 可以有一些简单的配置 注意中文需要编码
运行start-console.bat 需要安装java