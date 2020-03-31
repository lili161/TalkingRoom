# TalkingRoom

一个基于Java Socket的 多人聊天小程序

#使用方法#
##
注：客户端的socket IP地址 应为自己的服务器的ip地址，本例是采用的本地地址 127.0.0.1，若修改，请自行修改；
##

1.IDE中··


  1）直接打开，编译运行Server.java Client.java(tips:必须先运行服务端Server，否则客户端会连接不上。)
  2）①先运行Server ②运行Client。
  3) 进入客户端后会分配一个 用户号，发送消息是会显示在其他用户，自己不显示；以及进入退出房间，会显示在其他用户。
  4）编辑消息 按 Enter 即可发送。
  5）退出则 编辑 “END” 后 输入Enter 即可退出聊天室。 
  
2.在命令行窗口


  1)cd到Server.java User.java Client.java 的目录。
  2）javac Server.java User.java Client.java。
  3）服务器 执行 java Server 客户端 执行 java Client。
  4）其它同上。

