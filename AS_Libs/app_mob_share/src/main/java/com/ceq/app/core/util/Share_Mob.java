package com.ceq.app.core.util;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.framework.Framework_App;
import com.ceq.app_core.utils.core.Util_Dialog;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_File;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.extend.share.abstracts.Util_Share;
import com.ceq.app_core.utils.extend.share.enums.Enum_PlatformName_Share;
import com.example.app_share.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

import static com.ceq.app_core.framework.Framework_Activity.screenWidth;

/**
 * Created by Administrator on 2016/12/27.
 */

public class Share_Mob extends Util_Share {
    @Override
    public void init(Object... initParams) {
        ShareSDK.initSDK(Framework_App.getInstance());
    }

    @Override
    public void share(String shareContent, String shareUrl, String shareImgNamePath) {
        share(null, shareContent, shareUrl, shareImgNamePath);
    }

    @Override
    public void share(Enum_PlatformName_Share enum_platformName_share, String shareContent, String shareUrl, String shareImgNamePath) {
        share(enum_platformName_share, null, shareContent, shareUrl, shareImgNamePath);
    }

    @Override
    public void share(Enum_PlatformName_Share enum_platformName_share, String shareTitle, String shareContent, String shareUrl, String shareImgNamePath) {
        String title = (String) Util_Empty.isEmptyToReplace(shareTitle, "分享");
        String site = Framework_App.getInstance().getString(R.string.app_name);
        File file;
        if (shareImgNamePath == null) {
            String appLogo = "app_logo.png";
            file = new File(Util_File.fileToAppDir(), appLogo);
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(Framework_App.getInstance().getAssets().open(appLogo));
                bos = new BufferedOutputStream(new FileOutputStream(file));
                int ch;
                while ((ch = bis.read()) != -1)
                    bos.write(ch);
                bos.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bos != null)
                        bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (bis != null)
                        bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else
            file = new File(shareImgNamePath);
        if (enum_platformName_share == null) {
            OnekeyShare oks = new OnekeyShare();
            //关闭sso授权
            oks.disableSSOWhenAuthorize();
            // 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
            //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
            // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
            oks.setTitle(title);
            // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
            oks.setTitleUrl(shareUrl);
            // text是分享文本，所有平台都需要这个字段
            oks.setText(shareContent);
            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
            if (file != null)
                oks.setImagePath(file.getPath());//确保SDcard下面存在此张图片
            // url仅在微信（包括好友和朋友圈）中使用
            oks.setUrl(shareUrl);
            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
            oks.setComment(shareContent);
            // site是分享此内容的网站名称，仅在QQ空间使用
            oks.setSite(site);
            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
            oks.setSiteUrl(shareUrl);

            // 启动分享GUI
            oks.show(Framework_App.getInstance());
        } else {
            Platform platform = null;
            Platform.ShareParams shareParams = null;
            Util_Log.logShare(platform, shareParams, enum_platformName_share);
            switch (enum_platformName_share) {
                case QQ:
                    platform = ShareSDK.getPlatform(QQ.NAME);
                    shareParams = new QQ.ShareParams();
                    break;
                case Wechat:
                    platform = ShareSDK.getPlatform(Wechat.NAME);
                    shareParams = new Wechat.ShareParams();
                    shareParams.setShareType(shareImgNamePath == null ? Platform.SHARE_WEBPAGE : Platform.SHARE_IMAGE);
                    break;
                case WechatMoments:
                    platform = ShareSDK.getPlatform(WechatMoments.NAME);
                    shareParams = new WechatMoments.ShareParams();
                    shareParams.setShareType(shareImgNamePath == null ? Platform.SHARE_WEBPAGE : Platform.SHARE_IMAGE);
                    break;
                case SinaWeibo:
                    platform = ShareSDK.getPlatform(SinaWeibo.NAME);
                    shareParams = new SinaWeibo.ShareParams();
                    break;
                case QZone:
                    platform = ShareSDK.getPlatform(QZone.NAME);
                    shareParams = new QZone.ShareParams();
                    break;
            }
            shareParams.setImagePath(file.getPath());
            shareParams.setTitle(title);
            shareParams.setTitleUrl(shareUrl);
            shareParams.setText(shareContent);
            shareParams.setSite(site);
            shareParams.setSiteUrl(shareUrl);
            shareParams.setUrl(shareUrl);
            shareParams.setSiteUrl(shareUrl);
            shareParams.setComment(shareContent);
            platform.share(shareParams);
            Util_Log.logShare(title, shareUrl, shareContent, file);
            platform.setPlatformActionListener(new PlatformActionListener() {
                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                    Util_Log.logShare("onComplete", platform, i, hashMap);
                }

                @Override
                public void onError(Platform platform, int i, Throwable throwable) {
                    switch (throwable.toString()) {
                        case "cn.sharesdk.wechat.utils.WechatClientNotExistException":
                            Util_Toast.toast("请先安装微信客户端");
                            break;
                    }
                    Util_Log.logShare("onError", platform, throwable);
                }

                @Override
                public void onCancel(Platform platform, int i) {
                    Util_Log.logShare("onCancel", platform, i);
                }
            });
        }
    }

    @Override
    public void share(final View viewToken, final List<Enum_PlatformName_Share> platformNameShares, final String shareTitle, final String shareContent, final String shareUrl, final String shareImgName) {
        final List<Bean_Item> bean_items = new ArrayList<>();
        for (int i = 0, size = platformNameShares.size(); i < size; i++) {
            String shareName = null;
            int shareImgId = 0;
            Enum_PlatformName_Share bean = platformNameShares.get(i);
            switch (bean) {
                case QQ:
                    shareName = "QQ";
                    shareImgId = R.drawable.ssdk_oks_classic_qq;
                    break;
                case QZone:
                    shareName = "QQ空间";
                    shareImgId = R.drawable.ssdk_oks_classic_qzone;
                    break;
                case SinaWeibo:
                    shareName = "新浪微博";
                    shareImgId = R.drawable.ssdk_oks_classic_sinaweibo;
                    break;
                case Wechat:
                    shareName = "微信好友";
                    shareImgId = R.drawable.ssdk_oks_classic_wechat;
                    break;
                case WechatMoments:
                    shareName = "微信朋友圈";
                    shareImgId = R.drawable.ssdk_oks_classic_wechatmoments;
                    break;
            }
            bean_items.add(new Bean_Item(bean, shareName, shareImgId, false));
        }
        final int w = screenWidth / 4;
        final int height = (screenWidth / 10);
        final int width = (screenWidth / 10);
        final Activity activity = (Activity) viewToken.getContext();
        Util_Dialog.dialogByAct(viewToken, R.layout.app_dialog_share, true, false, false, false, new Util_Dialog.ActDialog() {
            @Override
            public void initDialogData() {

            }

            RecyclerView rv_share;
            Util_Dialog.DialogContext dialogContext;

            @Override
            public View initDialogView(final Util_Dialog.DialogContext dialogContext, View view) {
                this.dialogContext = dialogContext;
                rv_share = (RecyclerView) view.findViewById(R.id.rv_share);
                rv_share.setLayoutManager(new GridLayoutManager(activity, 4));
                rv_share.setAdapter(new RecyclerView.Adapter() {
                    class MyViewHolder extends RecyclerView.ViewHolder {
                        SimpleDraweeView sdv_img;
                        TextView tv_name;
                        LinearLayout ll_bg;
                        View v_bottomSplit, v_rightSplit;

                        public MyViewHolder(View itemView) {
                            super(itemView);
                            (sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img)).setVisibility(View.VISIBLE);
                            ViewGroup.LayoutParams layoutParams = sdv_img.getLayoutParams();
                            layoutParams.height = height;
                            layoutParams.width = width;
                            sdv_img.setLayoutParams(layoutParams);

                            (tv_name = (TextView) itemView.findViewById(R.id.tv_name)).setVisibility(View.VISIBLE);
                            tv_name.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);

                            ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ll_bg.getLayoutParams();
                            lp.width = lp.height = w;
                            ll_bg.setLayoutParams(lp);
                            tv_name.setTextColor(Color.rgb(50, 50, 50));

                            v_bottomSplit = itemView.findViewById(R.id.v_bottomSplit);
                            v_rightSplit = itemView.findViewById(R.id.v_rightSplit);
                            v_bottomSplit.setBackgroundColor(Color.rgb(225, 225, 225));
                            v_rightSplit.setBackgroundColor(Color.rgb(225, 225, 225));
                        }
                    }

                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        return new MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.app_item_rv_ico_text_vertical, parent, false));
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                        MyViewHolder mvh = (MyViewHolder) holder;
                        final Bean_Item bean = bean_items.get(position);

                        mvh.sdv_img.getHierarchy().setPlaceholderImage(bean.getImgId());
                        mvh.tv_name.setText(bean.getValue());

                        mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                share((Enum_PlatformName_Share) bean.getName(), shareTitle, shareContent, shareUrl, shareImgName);
                                dialogContext.dismiss();
                            }
                        });
                    }

                    @Override
                    public int getItemCount() {
                        return bean_items.size();
                    }
                });
                return view;
            }

            @Override
            public void onDismissListener() {

            }

            @Override
            public void onClick(View v) {

            }
        });
    }

}
