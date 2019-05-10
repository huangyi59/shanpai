package com.jzkj.shanpai.study.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jzkj.shanpai.R;
import com.jzkj.shanpai.study.android.bean.UserS;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Android系统分为4层：应用层、framework、系统库、Linux内核
 * 安卓系统会为每个应用分配一个唯一的ShareUID，两个应用的shareUID相同可以跑在同一个进程
 * <p>
 * 多进程造成的问题 1.静态成员和单例模式完全失效  2.线程同步机制完全失效 3.ShreaPreferences的可靠性下降 4.Application会多次创建
 * 运行在同一个进程中的组件是属于同一个虚拟机和同一个Application,运行在不同进程中的组件是属于两个不同的虚拟机和Application
 *  ：开头为私有进程 不以冒号开头为全局进程
 * </p>
 *
 * <p>
 * Serializable JAVA提供,使用简单，开销大，序列化和反序列化的过程需要大量的IO操作，
 * Parcelable是android自带的，Parcelable主要用在内存的序列化上，使用麻烦，效率高
 * </p>
 *
 * Binder是客服端和服务端进行通信的媒介
 * asInterface用于将服务端的Binder对象转换成客户端所需的AIDL接口类型的对象
 *
 * 客户端请求binder（挂起）,调用stup类中的代理类Proxy中对应的方法，transact-》Service调用onTrassact 传入 parcel _data parcel _reply (flag)默认为0 写入结果
 * 返回数据 唤醒Client
 *
 * 关于cache database/sharedpreference 在包名data/data/packagename/database
 *
 * <p>
 * Socket通信 分为流式套接字和用户数据报套接字，分别对应网络传输控制层中的TCP和UDP协议
 * TCP ->协议是面向连接的协议，建立连接需要经过"三次握手"，本身提供了超时重传机制，提供稳定的双向通信功能，
 * 大致分为4个层面 应用层 传输层 网络层 数据链路层 TCP/UPD对应传输层 IP对应网络层 应用层HTTP处理request或response
 * TCP头格式 源端口号 16bit
 *          目的端口号 16bit
 *          Sequence Number 顺序号码 32bit 防止通信中数据错乱
 *          Acknowledgment Number 确认号码 上次通信成功收到的顺序号码 32bit
 *          offset 报文头长度20byte
 *          Reserved 6bit
 *          TCP FLAGS 每个长度均为1
 *
 *           CWR：压缩，TCP Flags值0x80。
 *           ECE：拥塞，0x40。
 *           URG：紧急，0x20。当URG=1时，表示报文段中有紧急数据，应尽快传送。
 *           ACK：确认，0x10。当ACK = 1时，代表这是一个确认的TCP包，取值0则不是确认包。
 *           PSH：推送，0x08。当发送端PSH=1时，接收端尽快的交付给应用进程。
 *           RST：复位，0x04。当RST=1时，表明TCP连接中出现严重差错，必须释放连接，再重新建立连接。
 *           SYN：同步，0x02。在建立连接是用来同步序号。SYN=1， ACK=0表示一个连接请求报文段。SYN
 *
 * 建立连接3次握手
 *          第一次握手 客服端向服务端发送请求包 标志位syn置为1 顺序号码seq=0
 *          第二次握手 服务端收到客服端的报文，并向客服端发送一个SYN和ACK为1的TCP报文
 *          第三次握手 客服端收到服务端发来的包后检查确认号码是否正确，以及标志位SYN是否为1 若正确 服务端再次发送确认包
 *          ACK置为1，SYN置为0
 * 断开连接4次握手 多了一次ack校验，服务端不会立即关闭连接，进行一次数据校验
 *          第一次握手 发送FIN报文 ACK确认号码置为1 seq顺序号码置为1
 *          第二次握手 服务端收到FIN后，发送ACK, 确认号码为收到的顺序号码加1 ACK==seq+1 顺序号码为收到的确认号码 客服端进入等待的状态
 *          第三次握手 向客服端发送FIN报文
 *          第四次握手 客服端收到FIN后，发回ACK确认
 *
 *          http协议是建立在TCP协议基础之上的，Http会通过TCP建立起一个到服务器的连接通道，
 *          当一个网页打开完成后，客户端和服务器之间用于传输HTTP数据的TCP连接不会关闭
 *          Http是无状态的短连接，而TCP是有状态的长连接
 *
 *          TCP和UDP的区别
 *          TCP面向连接的，UDP非面向连接的
 *          TCP有确认 窗口 重传 拥塞 控制机制比较耗时，UDP速度快，没有TCP的机制
 *          TCP保证数据的准确性，UDP传输过程容易丢失数据容易丢包
 *
 *          http和https的区别
 *          连接端口 http 80 https 443
 *          传输方式 http明文传输 https密文传输
 *          费用     http无需费用 https需要到CA申请证书
 *          工作耗时 tcp握手 tcp+ssl握手
 *
 *          ssl网络安全协议 位于应用层和传输层之间
 * </p>
 *
 */
public class ProgressActivity extends AppCompatActivity {

    private static final String TAG = "AppCompatActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        saveUser();
        UserS user = readUser();
        Log.e(TAG,user.toString());
    }

    private void saveUser(){
        try {
            UserS user = new UserS(0x01,"张三",false);
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("cache.txt"));
            out.writeObject(user);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private UserS readUser(){
        UserS user = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("cache.txt"));
            user = (UserS) in.readObject();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
    }

    private void aboutCache(){
        //data/data/packagename
        String dataPath = getCacheDir().getAbsolutePath();
        //storage
        String sotrePath = getExternalCacheDir().getAbsolutePath();
    }

}
