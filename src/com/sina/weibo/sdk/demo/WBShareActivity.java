/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sina.weibo.sdk.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import weibo_mystring_service.FileService;
import weibo_mystring_service.Project;
import weibo_mystring_service.Projectservice;
import weibo_mystring_service.Temfriends;
import weibo_mystring_service.Temfriendsservice;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMessage;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.api.share.BaseResponse;
import com.sina.weibo.sdk.api.share.IWeiboDownloadListener;
import com.sina.weibo.sdk.api.share.IWeiboHandler;
import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
import com.sina.weibo.sdk.api.share.SendMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
import com.sina.weibo.sdk.api.share.WeiboShareSDK;
import com.sina.weibo.sdk.constant.WBConstants;
import com.sina.weibo.sdk.exception.WeiboShareException;
import com.sina.weibo.sdk.utils.Utility;

/**
 * 该类演示了第三方应用如何通过微博客户端分享文字、图片、视频、音乐等。
 * 执行流程： 从本应用->微博->本应用
 * 
 * @author SINA
 * @since 2013-10-22
 */
public class WBShareActivity extends Activity implements OnClickListener, IWeiboHandler.Response {
    @SuppressWarnings("unused")
    private static final String TAG = "WBShareActivity";

    /** 界面标题 */
    private TextView        mTitleView;
    /** 分享图片 */
    private ImageView       mImageView;
    /** 用于控制是否分享文本的 CheckBox */
    private CheckBox        mTextCheckbox;
    /** 用于控制是否分享图片的 CheckBox */
    private CheckBox        mImageCheckbox;
    /** 分享网页控件 */
    private WBShareItemView mShareWebPageView;
    /** 分享音乐控件 */
    private WBShareItemView mShareMusicView;
    /** 分享视频控件 */
    private WBShareItemView mShareVideoView;
    /** 分享声音控件 */
    private WBShareItemView mShareVoiceView;
    /** 分享按钮 */
    private Button          mSharedBtn;
    
    /** 微博微博分享接口实例 */
    private IWeiboShareAPI  mWeiboShareAPI = null;

    /**
     * @see {@link Activity#onCreate}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        initViews();

        // 创建微博分享接口实例
        mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this, Constants.APP_KEY);
        
        // 注册第三方应用到微博客户端中，注册成功后该应用将显示在微博的应用列表中。
        // 但该附件栏集成分享权限需要合作申请，详情请查看 Demo 提示
        // NOTE：请务必提前注册，即界面初始化的时候或是应用程序初始化时，进行注册
        mWeiboShareAPI.registerApp();
        
        // 如果未安装微博客户端，设置下载微博对应的回调
        if (!mWeiboShareAPI.isWeiboAppInstalled()) {
            mWeiboShareAPI.registerWeiboDownloadListener(new IWeiboDownloadListener() {
                @Override
                public void onCancel() {
                    Toast.makeText(WBShareActivity.this, 
                            R.string.weibosdk_demo_cancel_download_weibo, 
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
        
		// 当 Activity 被重新初始化时（该 Activity 处于后台时，可能会由于内存不足被杀掉了），
        // 需要调用 {@link IWeiboShareAPI#handleWeiboResponse} 来接收微博客户端返回的数据。
        // 执行成功，返回 true，并调用 {@link IWeiboHandler.Response#onResponse}；
        // 失败返回 false，不调用上述回调
        if (savedInstanceState != null) {
            mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
        }
    }
    
    /**
     * @see {@link Activity#onNewIntent}
     */	
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        
        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
        mWeiboShareAPI.handleWeiboResponse(intent, this);
    }

    /**
     * 接收微客户端博请求的数据。
     * 当微博客户端唤起当前应用并进行分享时，该方法被调用。
     * 
     * @param baseRequest 微博请求数据对象
     * @see {@link IWeiboShareAPI#handleWeiboRequest}
     */
    @Override
    public void onResponse(BaseResponse baseResp) {
        switch (baseResp.errCode) {
        case WBConstants.ErrorCode.ERR_OK:
            Toast.makeText(this, R.string.weibosdk_demo_toast_share_success, Toast.LENGTH_LONG).show();
            break;
        case WBConstants.ErrorCode.ERR_CANCEL:
            Toast.makeText(this, R.string.weibosdk_demo_toast_share_canceled, Toast.LENGTH_LONG).show();
            break;
        case WBConstants.ErrorCode.ERR_FAIL:
            Toast.makeText(this, 
                    getString(R.string.weibosdk_demo_toast_share_failed) + "Error Message: " + baseResp.errMsg, 
                    Toast.LENGTH_LONG).show();
            break;
        }
    }

    /**
     * 用户点击分享按钮，唤起微博客户端进行分享。
     */
    @Override
    public void onClick(View v) {
        if (R.id.share_to_btn == v.getId()) {
            try {
                // 检查微博客户端环境是否正常，如果未安装微博，弹出对话框询问用户下载微博客户端
                if (mWeiboShareAPI.checkEnvironment(true)) {                    
                    sendMessage(mTextCheckbox.isChecked(), 
                            mImageCheckbox.isChecked(), 
                            false,
                            false, 
                            false, 
                            false);
                }
            } catch (WeiboShareException e) {
                e.printStackTrace();
                Toast.makeText(WBShareActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

    /**
     * 初始化界面。
     */
    private void initViews() {
        mTitleView = (TextView) findViewById(R.id.share_title);
        mTitleView.setText(R.string.weibosdk_demo_share_to_weibo_title);
        mImageView = (ImageView) findViewById(R.id.share_imageview);
        mTextCheckbox = (CheckBox) findViewById(R.id.share_text_checkbox);
        mImageCheckbox = (CheckBox) findViewById(R.id.shared_image_checkbox);

        mSharedBtn = (Button) findViewById(R.id.share_to_btn);
        mSharedBtn.setOnClickListener(this);
        
        
       
    }

    /**
     * 监听 RadioButton 的点击事件。
     */
    private WBShareItemView.OnCheckedChangeListener mCheckedChangeListener = new WBShareItemView.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(WBShareItemView view, boolean isChecked) {
            mShareWebPageView.setIsChecked(false);
            mShareMusicView.setIsChecked(false);
            mShareVideoView.setIsChecked(false);
            mShareVoiceView.setIsChecked(false);
            
            view.setIsChecked(isChecked);
        }
    };

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * @throws Exception 
     * @see {@link #sendMultiMessage} 或者 {@link #sendSingleMessage}
     */
    private void sendMessage(boolean hasText, boolean hasImage, 
			boolean hasWebpage, boolean hasMusic, boolean hasVideo, boolean hasVoice) throws Exception {
        
        if (mWeiboShareAPI.isWeiboAppSupportAPI()) {
            int supportApi = mWeiboShareAPI.getWeiboAppSupportAPI();
            if (supportApi >= 10351 /*ApiUtils.BUILD_INT_VER_2_2*/) {
                sendMultiMessage(hasText, hasImage, false, false, false, false);
            } else {
                sendSingleMessage(hasText, hasImage, false, false, false/*, hasVoice*/);
            }
        } else {
            Toast.makeText(this, R.string.weibosdk_demo_not_support_api_hint, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * 注意：当 {@link IWeiboShareAPI#getWeiboAppSupportAPI()} >= 10351 时，支持同时分享多条消息，
     * 同时可以分享文本、图片以及其它媒体资源（网页、音乐、视频、声音中的一种）。
     * 
     * @param hasText    分享的内容是否有文本
     * @param hasImage   分享的内容是否有图片
     * @param hasWebpage 分享的内容是否有网页
     * @param hasMusic   分享的内容是否有音乐
     * @param hasVideo   分享的内容是否有视频
     * @param hasVoice   分享的内容是否有声音
     * @throws Exception 
     */
    private void sendMultiMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
            boolean hasMusic, boolean hasVideo, boolean hasVoice) throws Exception {
        
        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (hasText) {
            weiboMessage.textObject = getTextObj();
        }
        
        
        
        // 2. 初始化从第三方到微博的消息请求
        SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.multiMessage = weiboMessage;
        
        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(request);
    }

    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     * 当{@link IWeiboShareAPI#getWeiboAppSupportAPI()} < 10351 时，只支持分享单条消息，即
     * 文本、图片、网页、音乐、视频中的一种，不支持Voice消息。
     * 
     * @param hasText    分享的内容是否有文本
     * @param hasImage   分享的内容是否有图片
     * @param hasWebpage 分享的内容是否有网页
     * @param hasMusic   分享的内容是否有音乐
     * @param hasVideo   分享的内容是否有视频
     * @throws Exception 
     */
    private void sendSingleMessage(boolean hasText, boolean hasImage, boolean hasWebpage,
            boolean hasMusic, boolean hasVideo/*, boolean hasVoice*/) throws Exception {
        
        // 1. 初始化微博的分享消息
        // 用户可以分享文本、图片、网页、音乐、视频中的一种
        WeiboMessage weiboMessage = new WeiboMessage();
        if (hasText) {
            weiboMessage.mediaObject = getTextObj();
        }
        
        // 2. 初始化从第三方到微博的消息请求
        SendMessageToWeiboRequest request = new SendMessageToWeiboRequest();
        // 用transaction唯一标识一个请求
        request.transaction = String.valueOf(System.currentTimeMillis());
        request.message = weiboMessage;
        
        // 3. 发送请求消息到微博，唤起微博分享界面
        mWeiboShareAPI.sendRequest(request);
    }

    /**
     * 获取分享的文本模板。
     * 
     * @return 分享的文本模板
     * @throws Exception 
     */
    private String getSharedText() throws Exception {
        FileService file = new FileService(this);
        Projectservice ps = new Projectservice(this);
        Temfriendsservice tfs = new Temfriendsservice(this);
        Project pro = new Project();
        String pid = file.read1();
        int i;
        pro = ps.find(Integer.valueOf(pid).intValue());
        String data1="";
        String temp="";
        List<Temfriends> temfriends = tfs.getScrollData(Integer.valueOf(pid).intValue(),0, 200);
		for(Temfriends temfriend : temfriends){
			data1=temp+"@"+temfriend.getName()+temfriend.getPayprice()+"元.";
			temp=data1;
		}
		String data = "我今天在项目"+pro.getName()+"中，与"+pro.getMembernum()+"位朋友一起消费了"+pro.getPrice()+"元。"+data1;
    	//int formatId = R.string.weibosdk_demo_share_text_template;
        String format = data;
        String text = data;
        if (mTextCheckbox.isChecked() || mImageCheckbox.isChecked()) {
            format = data;
        }
        
        return text;
    }

    /**
     * 创建文本消息对象。
     * 
     * @return 文本消息对象。
     * @throws Exception 
     */
    private TextObject getTextObj() throws Exception {
        TextObject textObject = new TextObject();
        textObject.text = getSharedText();
        return textObject;
    }


    /**
     * 创建多媒体（网页）消息对象。
     * 
     * @return 多媒体（网页）消息对象。
     */
    private WebpageObject getWebpageObj() {
        WebpageObject mediaObject = new WebpageObject();
        mediaObject.identify = Utility.generateGUID();
        mediaObject.title = mShareWebPageView.getTitle();
        mediaObject.description = mShareWebPageView.getShareDesc();
        
        // 设置 Bitmap 类型的图片到视频对象里
        mediaObject.setThumbImage(mShareWebPageView.getThumbBitmap());
        mediaObject.actionUrl = mShareWebPageView.getShareUrl();
        mediaObject.defaultText = "Webpage 默认文案";
        return mediaObject;
    }

    /**
     * 创建多媒体（音乐）消息对象。
     * 
     * @return 多媒体（音乐）消息对象。
     */
    private MusicObject getMusicObj() {
        // 创建媒体消息
        MusicObject musicObject = new MusicObject();
        musicObject.identify = Utility.generateGUID();
        musicObject.title = mShareMusicView.getTitle();
        musicObject.description = mShareMusicView.getShareDesc();
        
        // 设置 Bitmap 类型的图片到视频对象里
        musicObject.setThumbImage(mShareMusicView.getThumbBitmap());
        musicObject.actionUrl = mShareMusicView.getShareUrl();
        musicObject.dataUrl = "www.weibo.com";
        musicObject.dataHdUrl = "www.weibo.com";
        musicObject.duration = 10;
        musicObject.defaultText = "Music 默认文案";
        return musicObject;
    }

    /**
     * 创建多媒体（视频）消息对象。
     * 
     * @return 多媒体（视频）消息对象。
     */
    private VideoObject getVideoObj() {
        // 创建媒体消息
        VideoObject videoObject = new VideoObject();
        videoObject.identify = Utility.generateGUID();
        videoObject.title = mShareVideoView.getTitle();
        videoObject.description = mShareVideoView.getShareDesc();
        
        // 设置 Bitmap 类型的图片到视频对象里
        videoObject.setThumbImage(mShareVideoView.getThumbBitmap());
        videoObject.actionUrl = mShareVideoView.getShareUrl();
        videoObject.dataUrl = "www.weibo.com";
        videoObject.dataHdUrl = "www.weibo.com";
        videoObject.duration = 10;
        videoObject.defaultText = "Vedio 默认文案";
        return videoObject;
    }

    /**
     * 创建多媒体（音频）消息对象。
     * 
     * @return 多媒体（音乐）消息对象。
     */
    private VoiceObject getVoiceObj() {
        // 创建媒体消息
        VoiceObject voiceObject = new VoiceObject();
        voiceObject.identify = Utility.generateGUID();
        voiceObject.title = mShareVoiceView.getTitle();
        voiceObject.description = mShareVoiceView.getShareDesc();
        
        // 设置 Bitmap 类型的图片到视频对象里
        voiceObject.setThumbImage(mShareVoiceView.getThumbBitmap());
        voiceObject.actionUrl = mShareVoiceView.getShareUrl();
        voiceObject.dataUrl = "www.weibo.com";
        voiceObject.dataHdUrl = "www.weibo.com";
        voiceObject.duration = 10;
        voiceObject.defaultText = "Voice 默认文案";
        return voiceObject;
    }
}
