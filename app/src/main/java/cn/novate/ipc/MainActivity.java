package cn.novate.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Email: 2185134304@qq.com
 * Created by JackChen 2018/4/21 16:27
 * Version 1.0
 * Params:
 * Description:    这个是B应用，作为服务器端，让另一个客户端的A应用来调用B应用中获取用户名和密码的方法
*/
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // 客户端的代码，客户端一定要获取aidl的实例
    private UserAidl mUserAidl;

    private ServiceConnection mServiceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 连接好了  service其实就是服务器端给我们返回的 IBinder
            mUserAidl = UserAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 断开链接
        }
    };
    private Button btn_username;
    private Button btn_pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_username = (Button) findViewById(R.id.btn_username);
        btn_username.setOnClickListener(this);
        btn_pwd = (Button) findViewById(R.id.btn_pwd);
        btn_pwd.setOnClickListener(this);


        // B应用代码：启动一个服务，等待A应用过来连接
        startService(new Intent(this,MessageService.class));


        // A应用代码：为了方便，就把客户端（A应用的代码）和服务器端的代码都写到这里，都写到这个B应用里边
        // 隐士意图
        Intent intent = new Intent(this,MessageService.class);
        bindService(intent, mServiceConn, Context.BIND_AUTO_CREATE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_username:
                try {
                    Toast.makeText(this, mUserAidl.getUserName(), Toast.LENGTH_LONG).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_pwd:
                try {
                    Toast.makeText(this,mUserAidl.getUserPassword(),Toast.LENGTH_LONG).show();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
