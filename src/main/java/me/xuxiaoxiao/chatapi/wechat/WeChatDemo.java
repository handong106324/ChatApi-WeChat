package me.xuxiaoxiao.chatapi.wechat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.xuxiaoxiao.chatapi.wechat.entity.contact.WXContact;
import me.xuxiaoxiao.chatapi.wechat.entity.contact.WXGroup;
import me.xuxiaoxiao.chatapi.wechat.entity.contact.WXUser;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXMessage;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXText;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXUnknown;
import me.xuxiaoxiao.chatapi.wechat.entity.message.WXVerify;

import java.io.File;
import java.util.Map;
import java.util.Scanner;

public class WeChatDemo {
    public static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
    public  static String queryContactName = "";
    public  static String monitorContactId = "";
    /**
     * 新建一个模拟微信客户端，并绑定一个简单的监听器
     */
    public static WeChatClient WECHAT_CLIENT = new WeChatClient(new WeChatClient.WeChatListener() {
        @Override
        public void onQRCode(String qrCode) {
            System.out.println("onQRCode：" + qrCode);
        }

        @Override
        public void onLogin() {
            System.out.println(String.format("onLogin：您有%d名好友、活跃微信群%d个", WECHAT_CLIENT.userFriends().size(), WECHAT_CLIENT.userGroups().size()));
        }

        @Override
        public void onMessage(WXMessage message) {
//            System.out.println("获取到消息：" + GSON.toJson(message));
//
//            if (message instanceof WXVerify) {
//                //是好友请求消息，自动同意好友申请
//                WECHAT_CLIENT.passVerify((WXVerify) message);
//            } else if (message instanceof WXText && message.fromUser != null && !message.fromUser.id.equals(WECHAT_CLIENT.userMe().id)) {
//                //是文字消息，并且发送消息的人不是自己，发送相同内容的消息
//                if (message.fromGroup != null) {
//                    //群消息
//                    WECHAT_CLIENT.sendText(message.fromGroup, message.content);
//                } else {
//                    //用户消息
//                    WECHAT_CLIENT.sendText(message.fromUser, message.content);
//                }
//            }
        }

        @Override
        public void onContact(WXContact contact, int operate) {
            System.out.println(String.format("检测到联系人变更:%s:%s", operate == WeChatClient.ADD_CONTACT ? "新增" : (operate == WeChatClient.DEL_CONTACT ? "删除" : "修改"), contact.name));
            if (operate == WeChatClient.MOD_CONTACT && (contact.id.equals(monitorContactId))) {
                System.out.println(contact.name +" " + contact.id +" " + contact.avatarUrl +"");
            }
        }
    });

    public static void main(String[] args) {
        //启动模拟微信客户端
        WeChatDemo.queryContactName = "易天翔";

        WECHAT_CLIENT.startup();

    }
}