# Encryption-And-Decryption-By-Yu
自制的密码学综合工具，综合了对称加密算法DES，AES，IDEA，公开加密算法RSA，ECC，散列算法MD5，SHA1，CRC32，以及一些数字签名验证。

这个是俺之前看到的一个小demo，感觉很骚气，就拿来当主界面骚一下。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200403233031654.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzczNDA5NQ==,size_16,color_FFFFFF,t_70)
点击打乱~
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200403234147470.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzczNDA5NQ==,size_16,color_FFFFFF,t_70)
**对称加密**中，DES、DES2表示DES算法的两种实现源码，AES、IDEA同理。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200403235540478.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzczNDA5NQ==,size_16,color_FFFFFF,t_70)
**公开加密**里，RSA、RSA2表示RSA的两种源码实现。ECC同理，不过ECC3好像忘了做了，不管了。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200403235511654.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzczNDA5NQ==,size_16,color_FFFFFF,t_70)
**散列算法**里包含了MD5，SHA1，CRC32。`withSalt`表示**加盐**。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200403235607256.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzczNDA5NQ==,size_16,color_FFFFFF,t_70)
数字签名暂时还没放到图形化界面上，但是项目里包含了可以运行的demo。

破解散列表的话，后来采用了 rainbowcrack 和 MD5Crack3.0，UltraCrackingMachine 等几个现成的软件，很好用，就没有放上去了。。

以后有机会把这个软件做完。（好像每个软件我都是这么想的）
