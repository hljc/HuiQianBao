package com.ceq.app.main.methods;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ceq.app.core.activity.Act_Main;
import com.ceq.app.core.activity.Act_Main_Module;
import com.ceq.app.core.activity.Act_Main_Web;
import com.ceq.app.core.application.Abstract_App;
import com.ceq.app.core.bean.Bean_Banner2;
import com.ceq.app.core.bean.Bean_Properties;
import com.ceq.app.core.bean.Bean_PushData;
import com.ceq.app.core.bean.Bean_PushMessage;
import com.ceq.app.core.bean.Bean_UserInfo;
import com.ceq.app.core.constants.Constant_Api;
import com.ceq.app.core.constants.Constant_Common;
import com.ceq.app.core.constants.Constant_International;
import com.ceq.app.main.activity.Act_BankCard_Add;
import com.ceq.app.main.activity.Act_FragActivity;
import com.ceq.app.main.activity.Act_Help;
import com.ceq.app.main.activity.Act_Home_PushMessage;
import com.ceq.app.main.activity.Act_Home_SetCollectionMoney;
import com.ceq.app.main.activity.Act_Mine_Certification;
import com.ceq.app.main.activity.Act_Mine_MyPurse;
import com.ceq.app.main.activity.Act_Mine_TransactionDetailed;
import com.ceq.app.main.activity.Act_MyPurse_PurseDetailed;
import com.ceq.app.main.activity.Act_Purse_Credit;
import com.ceq.app.main.activity.Act_Purse_Loan;
import com.ceq.app.main.activity.Act_Purse_MerchantCollection;
import com.ceq.app.main.activity.Act_Share_Upgrade;
import com.ceq.app.main.activity.Act_Upgrade_OnlineBuy;
import com.ceq.app.main.bean.Bean_BankCardInfo;
import com.ceq.app.main.bean.Bean_BaseRebate;
import com.ceq.app.main.bean.Bean_Channel;
import com.ceq.app.main.bean.Bean_ChannelExtraInfo;
import com.ceq.app.main.bean.Bean_Collection;
import com.ceq.app.main.bean.Bean_OemInfo;
import com.ceq.app.main.bean.Bean_PersonalInfo;
import com.ceq.app.main.bean.Bean_Production;
import com.ceq.app.main.bean.Bean_UserAccount;
import com.ceq.app.main.fragment.Frag_Module_Share$ZDQB;
import com.ceq.app.main.fragment.Frag_Purse_Function;
import com.ceq.app_core.bean.Bean_Banner;
import com.ceq.app_core.bean.Bean_CustomerUi;
import com.ceq.app_core.bean.Bean_Item;
import com.ceq.app_core.bean.Bean_Push;
import com.ceq.app_core.framework.Framework_Activity;
import com.ceq.app_core.framework.Framework_App;
import com.ceq.app_core.framework.Framework_Dialog;
import com.ceq.app_core.framework.Framework_Fragment;
import com.ceq.app_core.utils.core.Util_Animation;
import com.ceq.app_core.utils.core.Util_Apk;
import com.ceq.app_core.utils.core.Util_Dialog;
import com.ceq.app_core.utils.core.Util_Empty;
import com.ceq.app_core.utils.core.Util_Http;
import com.ceq.app_core.utils.core.Util_Log;
import com.ceq.app_core.utils.core.Util_Runnable;
import com.ceq.app_core.utils.core.Util_Toast;
import com.ceq.app_core.utils.core.Util_View;
import com.ceq.app_core.utils.extend.customer.abstracts.Util_Customer;
import com.ceq.app_core.utils.extend.push.abstracts.Util_Push;
import com.ceq.app_core.utils.extend.push.interfaces.callback.Inter_Push_Callback;
import com.ceq.app_core.utils.extend.share.abstracts.Util_Share;
import com.ceq.app_core.view.View_XRefreshLayout;
import com.ceq.app_core.view.View_XRefreshStatusView;
import com.ceq.app_photo.utils.Util_Image;
import com.ceq.app_xinli_onekey.R;
import com.ceq.app_xinli_onekey.core.modules.abstracts.Abstract_OkModule_Fragment;
import com.ceq.app_xinli_onekey.core.modules.enums.Enum_OKModule_Feature;
import com.ceq.app_xinli_onekey.core.modules.enums.Enum_OKModule_Template;
import com.ceq.dao.Bean_UserInfoDao;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.zhy.autolayout.AutoLayoutInfo;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.autolayout.attr.HeightAttr;
import com.zhy.autolayout.attr.MarginBottomAttr;
import com.zhy.autolayout.attr.PaddingBottomAttr;
import com.zhy.autolayout.attr.PaddingLeftAttr;
import com.zhy.autolayout.attr.PaddingRightAttr;
import com.zhy.autolayout.attr.PaddingTopAttr;
import com.zhy.autolayout.attr.TextSizeAttr;
import com.zhy.autolayout.attr.WidthAttr;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.alibaba.fastjson.JSON.parseObject;
import static com.ceq.app.core.activity.Act_Main_Module.YILIAN;
import static com.ceq.app.core.activity.Act_Main_Module.bean_oemInfo;
import static com.ceq.app.core.activity.Act_Main_Module.gradeRoleMaps;
import static com.ceq.app.core.activity.Act_Main_Module.util_fragment;
import static com.ceq.app.core.application.Abstract_App.bean_oneKeyBootstrap;
import static com.ceq.app.core.application.Abstract_App.bean_userInfo;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Data;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Name;
import static com.ceq.app.core.constants.Constant_Common.Http_Key_Toast;
import static com.ceq.app.core.constants.Constant_Common.Http_Value_Success;
import static com.ceq.app.main.activity.Act_Home_SelectChannel.skipToPayPage;
import static com.ceq.app.main.activity.Act_Mine_Rate.getRateData;
import static com.ceq.app_core.framework.Framework_Activity.screenWidth;
import static com.ceq.app_core.framework.Framework_Web.Extra_String_Title;
import static com.ceq.app_core.framework.Framework_Web.Extra_String_Url;
import static com.ceq.app_core.utils.core.Util_Dialog.showDefaultDialog;
import static com.ceq.app_core.utils.extend.share.enums.Enum_PlatformName_Share.QQ;
import static com.ceq.app_core.utils.extend.share.enums.Enum_PlatformName_Share.QZone;
import static com.ceq.app_core.utils.extend.share.enums.Enum_PlatformName_Share.Wechat;
import static com.ceq.app_core.utils.extend.share.enums.Enum_PlatformName_Share.WechatMoments;
import static com.ceq.app_xinli_onekey.core.modules.enums.Enum_OKModule_Template.赚道钱包;

/**
 * Created by Administrator on 2017/6/1.
 */

public class Method_Static {
    public enum Enum_RebateType {
        当日收益, 当月收益, 昨日收益, 所有收益
    }

    public enum Enum_PurseType {
        积分, 余额, 分润
    }

    public enum Enum_ProductionSpecType {
        升级说明, 收益说明,
    }

    public static void skipToFragActivity(Activity activity, final Fragment fragment) {
        Act_FragActivity.fragment = fragment;
        activity.startActivity(new Intent(activity, Act_FragActivity.class));

    }

    public static void appendNumByKeyboard(TextView tv_money, String num) {
        String src = tv_money.getText().toString();
        String number = src.concat(num), preNum, suffixNum;
        String[] nums;
        if (number.contains(".")) {
            nums = number.split("\\.");
            preNum = nums[0];
            if (preNum.length() > 10) {
                Util_Toast.toast("金额长度不得超过10位");
                return;
            }
            if (nums.length > 1) {
                suffixNum = nums[1];
                if (suffixNum.length() > 2) {
                    Util_Toast.toast("小数不得超过2位");
                    return;
                }
            }
        } else {
            if (src.equals("0") || src.length() == 0) {
                if (num.equals("00") || num.equals("0")) {
                    tv_money.setText("0");
                } else {
                    tv_money.setText(num);
                }
                return;
            }
            if (number.length() > 10) {
                Util_Toast.toast("整数不得超过10位");
                return;
            }
        }
        tv_money.setText(number);
    }

    public static void submitToCollection(final View viewToken, final TextView tv_money, LinearLayout ll_submit, List<Bean_Channel> bean_channelList, Bean_ChannelExtraInfo bean_channelExtraInfo) {
        if (Util_Empty.isEmptyToToast(bean_channelExtraInfo, "请先选择通道") || Util_Empty.isEmptyToToast(bean_channelExtraInfo.getPayChannel(), "请先选择通道"))
            return;
        final String money = tv_money.getText().toString();
        if (Util_Empty.isEmptyToToast(money, "请输入金额"))
            return;
        if (Float.parseFloat(money) == 0) {
            Util_Toast.toast("金额不能为0");
            return;
        }
        if (/*payChannel.toString().contains(scanCode) &&*/ money.contains(".")) {
            Util_Toast.toast("该通道不支持小数");
            return;
        }
        if (!bean_userInfo.getRealnameStatus().equals("1")) {
            showCertificationDialog(viewToken);
            return;
        }
        getChannelDataToValidate(viewToken, tv_money, bean_channelList, bean_channelExtraInfo);
    }

    public static void getChannelDataToValidate(final View viewToken, final TextView tv_money, final List<Bean_Channel> bean_channelList, final Bean_ChannelExtraInfo bean_channelExtraInfo) {
        final Activity activity = (Activity) viewToken.getContext();
        getRateData(activity, null, false, new Util_Runnable.Util_ArgsRunnable() {
            @Override
            public void run(Object... data) {
                final Bean_Collection bean_collection = new Bean_Collection();
                final String money = tv_money.getText().toString();
                List<Bean_Channel> beans = (List<Bean_Channel>) data[0];
                bean_channelList.clear();
                bean_channelList.addAll(beans);
                StringBuilder sb = new StringBuilder("提示：该通道费率为");
                Bean_Channel bean_channel = null;
                String channelTag = bean_channelExtraInfo.getPayChannel().toString();
                for (int i = 0, size = bean_channelList.size(); i < size; i++) {
                    if (bean_channelList.get(i).getChannelTag().equals(channelTag)) {
                        String singleMinLimit = (String) Util_Empty.isEmptyToReplace(bean_channelList.get(i).getSingleMinLimit(), "0");
                        String singleMaxLimit = (String) Util_Empty.isEmptyToReplace(bean_channelList.get(i).getSingleMaxLimit(), "0");
                        BigDecimal minLimit = new BigDecimal(singleMinLimit);
                        BigDecimal maxLimit = new BigDecimal(singleMaxLimit);
                        BigDecimal currentMoney = new BigDecimal(money);
                        if (currentMoney.compareTo(minLimit) == -1) {
                            Util_Toast.toast(new StringBuilder("该通道单笔金额不得低于").append(singleMinLimit).append("元"));
                            Util_Http.dismiss();
                            return;
                        }
                        if (currentMoney.compareTo(maxLimit) == 1) {
                            Util_Toast.toast(new StringBuilder("该通道单笔金额不得超过").append(singleMaxLimit).append("元"));
                            Util_Http.dismiss();
                            return;
                        }
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        decimalFormat.setRoundingMode(RoundingMode.DOWN);
                        bean_collection.setMoney(decimalFormat.format(new BigDecimal(money)));
                        bean_collection.setRemark(bean_channelExtraInfo.getOrderDescription());
                        bean_collection.setChannelTag(channelTag);
                        sb.append(Util_Empty.isEmpty(bean_channelList.get(i).getRate()) ? "-" : new BigDecimal(bean_channelList.get(i).getRate()).multiply(new BigDecimal("100")).stripTrailingZeros().toPlainString()).append("%");
                        bean_channel = bean_channelList.get(i);
                        break;
                    }
                }
                if (bean_channel == null) {
                    Util_Toast.toast("未获取到该通道信息，请稍后重试");
                    Util_Http.dismiss();
                    return;
                }
                if (!checkChannelIsAvailable(bean_channel, null))
                    return;
                tv_money.setText("");
                final Bean_Channel finalBean_channel = bean_channel;
                final Bean_Channel finalBean_channel1 = bean_channel;
                getPersonalInfoData(activity, null, false, new Util_Runnable.Util_ArgsRunnable() {
                    @Override
                    public void run(Object... data) {
                        Bean_PersonalInfo bean = (Bean_PersonalInfo) data[0];
                        if (Util_Empty.isEmptyToToast(bean.getRealname(), "未获取到真实姓名")) {
                            Util_Http.dismiss();
                            return;
                        }
                        bean_collection.setRealName(bean.getRealname());
                        if (finalBean_channel1.getChannelNo().equals("1"))
                            Act_Home_SetCollectionMoney.getCollectionURL(activity, bean_collection, finalBean_channel, null);
                        else
                            getRechargeCardDataToSelect(viewToken, new Util_Runnable.Util_ArgsRunnable() {
                                @Override
                                public void run(Object... data) {
                                    Act_Home_SetCollectionMoney.getCollectionURL(activity, bean_collection, finalBean_channel, (Bean_BankCardInfo) data[0]);
                                }
                            });
                    }
                });
                /*Act_PayKeyboard.showPayKeyboard(getActivity(), ll_submit.getId(), remark, bean_collection.getMoney(), sb.toString(), new Act_PayKeyboard.OnFinishInputListener() {
                    @Override
                    public void OnPasswordInputFinish(PopEnterPassword popEnterPassword, String password) {
                        popEnterPassword.dismiss();
                        if (verificationPayCode(getActivity(), password)) {
                            if (payChannel.toString().contains("QRCODE"))
                                bean_collection.setMoney(bean_collection.getMoney().substring(0, bean_collection.getMoney().indexOf(".")));
                            Act_Home_SetCollectionMoney.getCollectionURL(getActivity(), bean_collection);
                        }
                    }
                });*/
            }
        });
    }

    public static void getRechargeCardDataToSelect(final View viewToken, final Util_Runnable.Util_ArgsRunnable util_Args_runnableBankCardData) {
        Util_Dialog.dialogByAct(viewToken, R.layout.dialog_select_bankcard, true, false, false, false, new Util_Dialog.ActDialog() {
            RecyclerView rv_bank;
            ImageView iv_cancel;
            Util_Dialog.DialogContext dialogContext;
            RecyclerView.Adapter rva_bank;
            View_XRefreshLayout xrv;
            final List<Bean_BankCardInfo> bean_bankCardInfoList = new ArrayList<>();
            final Activity activity = (Activity) viewToken.getContext();

            @Override
            public void initDialogData() {
            }

            @Override
            public View initDialogView(final Util_Dialog.DialogContext dialogContext, final View view) {
                this.dialogContext = dialogContext;
                rv_bank = (RecyclerView) view.findViewById(R.id.rv_bank);
                rv_bank.setLayoutManager(new LinearLayoutManager(activity));
                iv_cancel = (ImageView) view.findViewById(R.id.iv_cancel);
                iv_cancel.setColorFilter(new LightingColorFilter(Color.BLACK, Framework_App.getInstance().getResources().getColor(R.color.title_fontColor)));

                xrv = (View_XRefreshLayout) view.findViewById(R.id.xrv);

                rv_bank.setAdapter(rva_bank = new RecyclerView.Adapter() {

                    class MyViewHolder extends RecyclerView.ViewHolder {
                        TextView tv_name, tv_subName, tv_right;
                        LinearLayout ll_bg;
                        SimpleDraweeView sdv_img;
                        View v_mark;

                        public MyViewHolder(View itemView) {
                            super(itemView);
                            itemView.findViewById(R.id.rl_img).setVisibility(View.VISIBLE);

                            sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);
                            setImageLayoutParams(sdv_img, 120, 0);

                            v_mark = itemView.findViewById(R.id.v_mark);
                            setImageLayoutParams(v_mark, 80, 0);

                            ll_bg = (AutoLinearLayout) itemView.findViewById(R.id.ll_bg);
                            ll_bg.setPadding(ll_bg.getPaddingLeft(), ll_bg.getPaddingTop() + 10, ll_bg.getPaddingRight(), ll_bg.getPaddingBottom() + 10);

                            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                            tv_name.setHint("未获取到银行卡");
                            tv_name.setVisibility(View.VISIBLE);
                            tv_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, screenWidth / 25);
                            tv_name.setTextColor(Framework_App.getInstance().getResources().getColor(R.color.btn_press4_off));
                            tv_name.setHintTextColor(Framework_App.getInstance().getResources().getColor(R.color.text_color_2));

                            tv_subName = (TextView) itemView.findViewById(R.id.tv_subName);
                            tv_subName.setHint("未获取到银行卡信息");
                            tv_subName.setVisibility(View.VISIBLE);
                            tv_subName.setTextSize(TypedValue.COMPLEX_UNIT_PX, screenWidth / 28);
                            tv_subName.setTextColor(Framework_App.getInstance().getResources().getColor(R.color.text_color_3));
                            tv_subName.setHintTextColor(Framework_App.getInstance().getResources().getColor(R.color.text_color_2));

                            tv_right = (TextView) itemView.findViewById(R.id.tv_right);
                            tv_right.setVisibility(View.VISIBLE);
                            tv_right.setTextSize(TypedValue.COMPLEX_UNIT_PX, screenWidth / 34);
                            tv_right.setTextColor(Framework_App.getInstance().getResources().getColor(R.color.text_color_3));
                            tv_right.setHintTextColor(Framework_App.getInstance().getResources().getColor(R.color.text_color_2));

                            LinearLayout ll_left = (LinearLayout) itemView.findViewById(R.id.ll_left);
                            LinearLayout ll_right = (LinearLayout) itemView.findViewById(R.id.ll_right);
                            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) ll_left.getLayoutParams();
                            lp.addRule(RelativeLayout.LEFT_OF, ll_right.getId());
                            ll_left.setLayoutParams(lp);
                        }
                    }

                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        return new MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.app_item_rv_switch_or_item, parent, false));
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                        MyViewHolder mvh = (MyViewHolder) holder;

                        final Bean_BankCardInfo bean = bean_bankCardInfoList.get(position);
                        if (bean == null) return;
                        mvh.tv_name.setText(bean.getBankName());
                        mvh.tv_subName.setText(new StringBuilder("**** **** **** ").append(bean.getCardNo().substring(bean.getCardNo().length() - 4)));
                        mvh.tv_right.setText(bean.getCardType());
                        mvh.sdv_img.setImageURI(bean.getLogo());

                        mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                util_Args_runnableBankCardData.run(bean);
                                dialogContext.dismiss();
                            }
                        });
                    }

                    @Override
                    public int getItemCount() {
                        return bean_bankCardInfoList.size();
                    }
                });
                xrv.setOnRefreshHttpListener(false, 0, bean_bankCardInfoList, rva_bank, new View_XRefreshLayout.OnRefreshHttpListener() {
                    @Override
                    public void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, final List list, RecyclerView.Adapter adapter) {
                        Act_Home_SetCollectionMoney.selectRechargeCardToSkip(activity, view_xRefreshStatusView, new Util_Runnable.Util_ArgsRunnable() {
                            @Override
                            public void run(Object... data) {
                                list.addAll((Collection) data[0]);
                                if (list.isEmpty()) {
                                    viewToken.setEnabled(true);
                                    skipToAddBankCard(activity, "您还未添加充值卡，是否前往添加？");
                                }
                            }
                        });
                    }
                });
                Util_View.viewOnClick(this, iv_cancel);
                return view;
            }

            @Override
            public void onDismissListener() {

            }

            @Override
            public void onClick(View v) {
                if (v == iv_cancel) dialogContext.dismiss();
            }

        });
    }

    public static void skipToAddBankCard(final Activity activity, String content) {
        showDefaultDialog(new View(activity), content, "马上添加", "暂不添加", new Util_Dialog.DialogListener() {
            @Override
            public void onCancel(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                framework_dialog.dismiss();
            }

            @Override
            public void onConfirm(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                activity.startActivity(new Intent(activity, Act_BankCard_Add.class));
                framework_dialog.dismiss();
            }

            @Override
            public void onDismissListener() {

            }
        });
    }

    public static void setOrderDescription(Bean_Channel bean_channel, Bean_ChannelExtraInfo bean_channelExtraInfo) {
        bean_channelExtraInfo.setOrderDescription(new StringBuilder("在线收款【").append(bean_channel.getName()).append(" (").append(bean_channel.getChannelParams()).append(")】").toString());
    }

    public static void showCollectionDialog(final View viewToken, final List<Bean_Channel> bean_channelList, final Bean_ChannelExtraInfo bean_channelExtraInfo, final Util_Runnable.Util_ArgsRunnable util_filterData, final Util_Runnable.Util_ArgsRunnable util_click) {
        final Activity activity = (Activity) viewToken.getContext();
        Util_Dialog.dialogByAct(viewToken, R.layout.dialog_selectchannel, true, false, false, false, new Util_Dialog.ActDialog() {
            RecyclerView rv_channel;
            ImageView iv_cancel;
            Util_Dialog.DialogContext dialogContext;
            RecyclerView.Adapter rva_channel;
            View_XRefreshLayout xrv;

            @Override
            public void initDialogData() {
            }

            @Override
            public View initDialogView(final Util_Dialog.DialogContext dialogContext, View view) {
                this.dialogContext = dialogContext;
                rv_channel = (RecyclerView) view.findViewById(R.id.rv_channel);
                rv_channel.setLayoutManager(new LinearLayoutManager(activity));
                iv_cancel = (ImageView) view.findViewById(R.id.iv_cancel);
                iv_cancel.setColorFilter(new LightingColorFilter(Color.BLACK, activity.getResources().getColor(R.color.title_fontColor)));

                xrv = (View_XRefreshLayout) view.findViewById(R.id.xrv);

                rv_channel.setAdapter(rva_channel = new RecyclerView.Adapter() {

                    class MyViewHolder extends RecyclerView.ViewHolder {
                        TextView tv_name, tv_subName, tv_right;
                        LinearLayout ll_bg;
                        SimpleDraweeView sdv_img;
                        View v_mark;

                        public MyViewHolder(View itemView) {
                            super(itemView);
                            itemView.findViewById(R.id.rl_img).setVisibility(View.VISIBLE);

                            sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img);
                            setImageLayoutParams(sdv_img, 120, 20);

                            v_mark = itemView.findViewById(R.id.v_mark);
                            setImageLayoutParams(v_mark, 80, 0);

                            ll_bg = (AutoLinearLayout) itemView.findViewById(R.id.ll_bg);
                            ll_bg.setPadding(ll_bg.getPaddingLeft(), ll_bg.getPaddingTop() + 10, ll_bg.getPaddingRight(), ll_bg.getPaddingBottom() + 10);

                            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                            tv_name.setHint("未获取到通道信息");
                            tv_name.setVisibility(View.VISIBLE);
                            tv_name.setTextSize(TypedValue.COMPLEX_UNIT_PX, screenWidth / 25);
                            tv_name.setTextColor(activity.getResources().getColor(R.color.btn_press4_off));
                            tv_name.setHintTextColor(activity.getResources().getColor(R.color.text_color_2));

                            tv_subName = (TextView) itemView.findViewById(R.id.tv_subName);
                            tv_subName.setHint("未获取到费率信息");
                            tv_subName.setVisibility(View.VISIBLE);
                            tv_subName.setTextSize(TypedValue.COMPLEX_UNIT_PX, screenWidth / 28);
                            tv_subName.setTextColor(activity.getResources().getColor(R.color.text_color_3));
                            tv_subName.setHintTextColor(activity.getResources().getColor(R.color.text_color_2));

                            tv_right = (TextView) itemView.findViewById(R.id.tv_right);
                            tv_right.setHint("未获取到限额信息");
                            tv_right.setVisibility(View.VISIBLE);
                            tv_right.setTextSize(TypedValue.COMPLEX_UNIT_PX, screenWidth / 34);
                            tv_right.setTextColor(activity.getResources().getColor(R.color.text_color_3));
                            tv_right.setHintTextColor(activity.getResources().getColor(R.color.text_color_2));
                        }
                    }

                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        return new MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.app_item_rv_switch_or_item, parent, false));
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                        MyViewHolder mvh = (MyViewHolder) holder;

                        final Bean_Channel bean = bean_channelList.get(position);
                        if (bean == null) return;
                        String name = (String) Util_Empty.isEmptyToReplace(bean.getName(), "");
                        StringBuilder sb = new StringBuilder();
                        mvh.tv_name.setText(sb.append(name).append("(").append((String) Util_Empty.isEmptyToReplace(bean.getChannelParams(), "")).append(")"));

                        mvh.tv_subName.setText("费率:".concat(Util_Empty.isEmpty(bean.getRate()) ? "-" : new BigDecimal(bean.getRate()).multiply(new BigDecimal(100)).stripTrailingZeros().toPlainString().concat("%")));

                        mvh.tv_right.setText(new StringBuilder("单笔限额：").append(Util_Empty.isEmptyToReplace(bean.getSingleMinLimit(), "0")).append("-").append(Util_Empty.isEmptyToReplace(bean.getSingleMaxLimit(), "0")));

                        mvh.sdv_img.setImageURI(bean.getLog());
                        mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (util_click != null)
                                    util_click.run(bean);
                                setOrderDescription(bean, bean_channelExtraInfo);
                                dialogContext.dismiss();
                                bean_channelExtraInfo.setPayChannel(bean.getChannelTag());
                            }
                        });
                        setImageMark(mvh.v_mark, bean);
                    }

                    @Override
                    public int getItemCount() {
                        return bean_channelList.size();
                    }
                });
                xrv.setOnRefreshHttpListener(false, 0, bean_channelList, rva_channel, new View_XRefreshLayout.OnRefreshHttpListener() {
                    @Override
                    public void onHttpStart(int currentPagePosition, View_XRefreshStatusView view_xRefreshStatusView, List list, RecyclerView.Adapter adapter) {
                        if (util_filterData != null)
                            util_filterData.run(view_xRefreshStatusView, list, dialogContext);
                    }
                });
                Util_View.viewOnClick(this, iv_cancel);
                return view;
            }

            @Override
            public void onDismissListener() {

            }

            @Override
            public void onClick(View v) {
                if (v == iv_cancel) dialogContext.dismiss();
            }

        });
    }

    public static void delMoney(TextView tv_money) {
        String num = tv_money.getText().toString();
        if (num.length() > 0)
            tv_money.setText(num.substring(0, num.length() - 1));
    }

    /**
     * 【收款】第一步
     *
     * @param tv_money
     * @param filter
     */
    public static void collection(final View viewToken, final TextView tv_money, final List<Bean_Channel> bean_channelList, final Bean_ChannelExtraInfo bean_channelExtraInfo, final Util_Runnable.Util_ArgsRunnable filter) {
        if (Util_Empty.isEmptyToToast(tv_money.getText().toString(), "请输入金额"))
            return;
        if (!bean_userInfo.getRealnameStatus().equals("1")) {
            showCertificationDialog(viewToken);
            return;
        }
        bean_channelList.clear();
        showCollectionDialog(viewToken, bean_channelList, bean_channelExtraInfo, new Util_Runnable.Util_ArgsRunnable() {
            @Override
            public void run(final Object... data) {
                bean_channelList.clear();
                getRateData(((Util_Dialog.DialogContext) data[2]).getDialog(), (View_XRefreshStatusView) data[0], true, new Util_Runnable.Util_ArgsRunnable() {
                    @Override
                    public void run(Object... obj) {
                        bean_channelList.addAll((Collection<? extends Bean_Channel>) obj[0]);
                        if (filter != null)
                            filter.run();
                    }
                });
            }
        }, new Util_Runnable.Util_ArgsRunnable() {
            @Override
            public void run(Object... data) {
                Bean_Channel bean = (Bean_Channel) data[0];
                if (!checkChannelIsAvailable(bean, null))
                    return;
                bean_channelExtraInfo.setPayChannel(bean.getChannelTag());
                setOrderDescription(bean, bean_channelExtraInfo);
                submitToCollection(viewToken, tv_money, null, bean_channelList, bean_channelExtraInfo);
            }
        });
    }

    public static boolean checkChannelIsAvailable(Bean_Channel bean_channel, String toastContent) {
        if (bean_channel != null) {
            switch (bean_channel.getStatus()) {
                case "1":
                    return true;
                default:
               /*     if (Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().getBrandId() == 2)
                        return true;*/
                    Util_Toast.toast(toastContent == null ? "该通道维护中,请尝试其他通道!" : toastContent);
                    Util_Http.dismiss();
                    break;
            }
        }
        return false;
    }

    public static void setImageMark(View v_mark, Bean_Channel bean) {
        if (bean.getStatus().equals("1")) {
            v_mark.setBackgroundColor(Color.argb(0, 0, 0, 0));
        } else {
            v_mark.setBackgroundResource(R.mipmap.icon_collection_mark);
        }
    }

    public static void skipToHelp(Activity activity, String title, int imgId) {
        activity.startActivity(new Intent(activity, Act_Help.class).putExtra(Extra_String_Title, title).putExtra(Act_Help.Extra_Int_Img, imgId));
    }

    public static void setImageLayoutParams(View view, int size, int padding) {
        AutoLayoutInfo autoLayoutInfo = null;
        if (view.getLayoutParams() instanceof AutoRelativeLayout.LayoutParams) {
            AutoRelativeLayout.LayoutParams layoutParams = (AutoRelativeLayout.LayoutParams) view.getLayoutParams();
            autoLayoutInfo = layoutParams.getAutoLayoutInfo();
        } else if (view.getLayoutParams() instanceof AutoLinearLayout.LayoutParams) {
            AutoLinearLayout.LayoutParams layoutParams = (AutoLinearLayout.LayoutParams) view.getLayoutParams();
            autoLayoutInfo = layoutParams.getAutoLayoutInfo();
        }
        if (autoLayoutInfo == null)
            return;
        autoLayoutInfo.addAttr(new WidthAttr(size, 0, 0));
        autoLayoutInfo.addAttr(new HeightAttr(size, 0, 0));
        autoLayoutInfo.addAttr(new PaddingBottomAttr(padding, 0, 0));
        autoLayoutInfo.addAttr(new PaddingTopAttr(padding, 0, 0));
        autoLayoutInfo.addAttr(new PaddingLeftAttr(padding, 0, 0));
        autoLayoutInfo.addAttr(new PaddingRightAttr(padding, 0, 0));
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        view.setLayoutParams(lp);
        view.setVisibility(View.VISIBLE);
    }

    public static void callTelephone(Activity activity, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse(new StringBuilder("tel:").append(phone).toString());
        intent.setData(data);
        activity.startActivity(intent);
    }

    public static void callTelephone(Activity activity) {
        callTelephone(activity, bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().getTelephone());
    }


    public static void skipToScanCodeShare(Activity activity) {
        activity.startActivity(new Intent(activity, Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().getQRCodeShareActivity()));
    }

    public static RecyclerView.Adapter setShareAdapter(final Activity activity, RecyclerView rv_share, final List<Bean_Item> bean_itemList, final Util_Runnable.Util_ArgsRunnable util_Args_runnable) {
        RecyclerView.Adapter rva_share = new RecyclerView.Adapter() {

            class MyViewHolder extends RecyclerView.ViewHolder {
                SimpleDraweeView sdv_img;
                TextView tv_name, tv_value;
                LinearLayout ll_bg;

                public MyViewHolder(View itemView) {
                    super(itemView);
                    (sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img)).setVisibility(View.VISIBLE);
                    AutoLinearLayout.LayoutParams lp2 = (AutoLinearLayout.LayoutParams) sdv_img.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo = lp2.getAutoLayoutInfo();
                    autoLayoutInfo.addAttr(new WidthAttr(120, 0, 0));
                    autoLayoutInfo.addAttr(new HeightAttr(120, 0, 0));
                    autoLayoutInfo.addAttr(new PaddingBottomAttr(5, 0, 0));
                    ViewGroup.LayoutParams lp = sdv_img.getLayoutParams();
                    sdv_img.setLayoutParams(lp);

                    tv_value = (TextView) itemView.findViewById(R.id.tv_value);

                    (tv_name = (TextView) itemView.findViewById(R.id.tv_name)).setVisibility(View.VISIBLE);
                    AutoLinearLayout.LayoutParams layoutParams1 = (AutoLinearLayout.LayoutParams) tv_name.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo1 = layoutParams1.getAutoLayoutInfo();
                    autoLayoutInfo1.addAttr(new TextSizeAttr(40, 0, 0));
                    ViewGroup.LayoutParams lp1 = tv_name.getLayoutParams();
                    tv_name.setLayoutParams(lp1);
                    tv_name.setTextColor(activity.getResources().getColor(R.color.text_color_3));
                    tv_name.setTypeface(Typeface.DEFAULT_BOLD);

                    ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                    AutoRelativeLayout.LayoutParams layoutParams4 = (AutoRelativeLayout.LayoutParams) ll_bg.getLayoutParams();
                    AutoLayoutInfo autoLayoutInfo4 = layoutParams4.getAutoLayoutInfo();
                    autoLayoutInfo4.addAttr(new PaddingTopAttr(30, 0, 0));
                    autoLayoutInfo4.addAttr(new PaddingBottomAttr(30, 0, 0));
                    ViewGroup.LayoutParams lp4 = ll_bg.getLayoutParams();
                    ll_bg.setLayoutParams(lp4);
                    ll_bg.setBackgroundResource(R.drawable.bg_keyboard);

                }
            }

            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.app_adapter_gv, parent, false));
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                final MyViewHolder mvh = (MyViewHolder) holder;
                final Bean_Item bean = bean_itemList.get(position);
                mvh.tv_name.setText(bean.getName().toString());
                mvh.sdv_img.setImageResource(bean.getImgId());
                if (Util_Empty.isEmpty(bean.getValue())) {
                    mvh.tv_value.setVisibility(View.GONE);
                } else {
                    mvh.tv_value.setVisibility(View.VISIBLE);
                    mvh.tv_value.setText(bean.getValue());
                }

                mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        util_Args_runnable.run(position, mvh.ll_bg);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return bean_itemList.size();
            }
        };
        rv_share.setAdapter(rva_share);
        return rva_share;
    }

    private static void getProductionIdToBuy(View viewToken, String grade, List<Bean_Production> bean_productionList) {
        for (int i = 0, size = bean_productionList.size(); i < size; i++) {
            if (bean_productionList.get(i).getGrade().equals(grade)) {
                getProductionDataToBuy(viewToken, bean_productionList.get(i));
                break;
            }
        }
    }


    public static void getProductionList(Activity activity, View_XRefreshStatusView view_xRefreshStatusView, final Util_Runnable.Util_ArgsRunnable util_Args_runnable) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_QUERY_PRODUCTION_LIST_GET.concat(String.valueOf(bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().get$brandId())), Util_Http.RequestMethod.GET, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {

            }
        }, new Util_Http.HttpDealStringListener(activity, view_xRefreshStatusView, null, false, false) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        util_Args_runnable.run(data);
                        break;
                    default:
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }


    public static void withdraw(final Activity activity, final String order_desc, final String phone, final String amount, final String channe_tag) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_WITHDRAWALS_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("order_desc", order_desc);
                httpParams.put("channe_tag", channe_tag);
                httpParams.put("phone", phone);
                httpParams.put("amount", amount);

            }
        }, new Util_Http.HttpDealStringListener(activity, Constant_International.message_net_request, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                    case "666666"://等待处理
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        activity.onBackPressed();
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;

                }
            }
        });
    }

    public static void rollOut(final Activity activity, final String order_desc, final String phone, final String amount) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_RAKE_BACK_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("phone", phone);
                httpParams.put("amount", amount);
                httpParams.put("order_desc", order_desc);

            }
        }, new Util_Http.HttpDealStringListener(activity, Constant_International.message_net_request, true, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        Util_Toast.toast("分润提现成功");
                        activity.onBackPressed();
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }

    public static void getPersonalInfoData(Activity activity, View_XRefreshStatusView view_xRefreshStatusView, boolean isAutoDismiss, final Util_Runnable.Util_ArgsRunnable util_Args_runnable) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_USER_INFO_POST.concat(bean_userInfo.getUserToken()), Util_Http.RequestMethod.GET, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {

            }
        }, new Util_Http.HttpDealStringListener(activity, view_xRefreshStatusView, isAutoDismiss ? Constant_International.message_net_query : null, isAutoDismiss, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        util_Args_runnable.run(JSON.parseObject(JSON.parseObject(data).getString(Http_Key_Data), Bean_PersonalInfo.class));
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }


    public static void obtainUserPurseBaseInfo(Activity activity, View_XRefreshStatusView view_xRefreshStatusView, boolean isAutoDismiss, final Util_Runnable.Util_TypeRunnable<Bean_UserAccount> purseBaseInfoRunnable) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_OBTAIN_USER_INFO_GET.concat(bean_userInfo.getUserToken()), Util_Http.RequestMethod.GET, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {

            }
        }, new Util_Http.HttpDealStringListener(activity, view_xRefreshStatusView, null, isAutoDismiss, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        purseBaseInfoRunnable.run(JSON.parseObject(JSON.parseObject(data).getString(Http_Key_Data), Bean_UserAccount.class));
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }

    public static String getUserGradeRole(String grade) {
        return gradeRoleMaps.get(grade) == null ? "普通用户" : gradeRoleMaps.get(grade).getName();
    }

    public static Bean_Production getUserGradeInfo(String grade) {
        return gradeRoleMaps.get(grade);
    }

    @NonNull
    public static String getRealNameStatus() {
        String realNameStatus = "未实名";
        switch (bean_userInfo.getRealnameStatus()) {
            case "0":
                realNameStatus = "实名中";
                break;
            case "1":
                realNameStatus = "已实名";
                break;
            case "2":
                realNameStatus = "实名失败";
                break;
            case "3":
                realNameStatus = "未实名";
                break;
        }
        return realNameStatus;
    }


    public static void skipToCollection(Act_Main_Module act_main_module) {
        for (int i = 0, size = act_main_module.bean_items.size(); i < size; i++) {
            act_main_module.bean_items.get(i).setChecked(false);
        }
        int position = getModuleFragmentPosition(Enum_OKModule_Feature.收款);
        if (position == -1)
            return;
        act_main_module.bean_items.get(position).setChecked(true);
        act_main_module.rva_module.notifyDataSetChanged();
        util_fragment.fragmentToShow(position);
    }

    public static void skipToTransactionDetailed(Activity activity) {
        activity.startActivity(new Intent(activity, Act_Mine_TransactionDetailed.class));
    }

    public static void skipToMerchantCertification(final Activity activity) {
        getPersonalInfoData(activity, null, true, new Util_Runnable.Util_ArgsRunnable() {

            @Override
            public void run(Object... data) {
                Bean_PersonalInfo bean = (Bean_PersonalInfo) data[0];
                if (bean.getUsershopStatus() != null)
                    switch (bean.getUsershopStatus()) {
                        case "0":
                            Util_Toast.toast("商家审核中");
                            break;
                        case "1":
                            activity.startActivity(new Intent(activity, Act_Purse_MerchantCollection.class));
                            /*switch (Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().getBrandId()) {
                                case 3:
                                    startActivity(new Intent(getActivity(), Act_Purse_MerchantCollection.class));
                                    break;
                                default:
                                    Util_Toast.toast("商家已审核通过");
                                    break;
                            }
*/
                            break;
                        case "2":
                        case "3":
                            activity.startActivity(new Intent(activity, Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().getMerchantCertificationActivity()));
                            break;
                    }
                else
                    Util_Toast.toast("未获取到商家审核信息");
            }


        });
    }

    public static void skipToMyPurse(Activity activity) {
        activity.startActivity(new Intent(activity, Act_Mine_MyPurse.class));
    }

    public static void skipToPurseDetailed(Activity activity, int position) {
        activity.startActivity(new Intent(activity, Act_MyPurse_PurseDetailed.class).putExtra(Act_MyPurse_PurseDetailed.Extra_Int_MyPurse, position));
    }


    public static void skipToCredit(Activity activity) {
        activity.startActivity(new Intent(activity, Act_Purse_Credit.class));
    }

    public static void skipToLoan(Activity activity) {
        activity.startActivity(new Intent(activity, Act_Purse_Loan.class));
    }


    public static MarqueeFactory setMarqueeView(MarqueeView marqueeView, List<String> data) {
        final Activity activity = (Activity) marqueeView.getContext();
        marqueeView.startFlipping();
        MarqueeFactory marqueeFactory = null;
        if (marqueeFactory == null) {
            marqueeFactory = new MarqueeFactory<TextView, String>(activity) {
                @Override
                public TextView generateMarqueeItemView(String data) {
                    TextView textView = new TextView(activity);
                    textView.setText(data);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, Framework_Activity.screenWidth / 27);
                    textView.setLines(1);
                    textView.setFocusableInTouchMode(true);
                    textView.setTextColor(activity.getResources().getColor(R.color.text_color_3));
                    textView.setFocusable(true);
                    textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                    return textView;
                }

            };
            marqueeFactory.setOnItemClickListener(new MarqueeFactory.OnItemClickListener<TextView, String>() {
                @Override
                public void onItemClickListener(MarqueeFactory.ViewHolder<TextView, String> holder) {
                    Util_Runnable.Util_ArgsRunnable click = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().getMarqueeClickRunnable();
                    if (click != null)
                        click.run(activity, holder.data);
                    else
                        Util_Toast.toast(holder.data);
                }
            });
            marqueeView.setMarqueeFactory(marqueeFactory);
        }
        marqueeFactory.resetData(data);
        return marqueeFactory;
    }

    public static void resetMarqueeView(MarqueeFactory marqueeFactory, List<String> data) {
        if (marqueeFactory != null)
            marqueeFactory.resetData(data);
    }

    public static void setBanner(final Activity activity, Banner banner, boolean isWelcomeBanner) {
        int carouselHeight = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().getCarouselHeight();
        if (!isWelcomeBanner && carouselHeight != 0) {
            AutoLinearLayout.LayoutParams lp = (AutoLinearLayout.LayoutParams) banner.getLayoutParams();
            AutoLayoutInfo ali = lp.getAutoLayoutInfo();
            ali.addAttr(new HeightAttr(carouselHeight, 0, 0));
            banner.setLayoutParams(banner.getLayoutParams());
        }

        banner.setImages(isWelcomeBanner ? bean_oneKeyBootstrap.getBean_welcomeList() : bean_oneKeyBootstrap.getBean_carouselList()).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                if (path instanceof Bean_Banner2) {
                    final Bean_Banner2 bean_banner2 = (Bean_Banner2) path;
                    Util_Image.imageToShowByFresco((SimpleDraweeView) imageView, Uri.parse(bean_banner2.getImgurl()), 500, 1000);
                    if (!Util_Empty.isEmpty(bean_banner2.getUrl()))
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activity.startActivity(new Intent(activity, Act_Main_Web.class).putExtra(Extra_String_Url, bean_banner2.getUrl()));
                            }
                        });
                } else if (path instanceof Bean_Banner) {
                    final Bean_Banner bean_banner = (Bean_Banner) path;
                    Util_Image.imageToShowByFresco((SimpleDraweeView) imageView, Uri.parse(bean_banner.getImg()), 500, 1000);
                    if (!Util_Empty.isEmpty(bean_banner.getHref()))
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activity.startActivity(new Intent(activity, Act_Main_Web.class).putExtra(Extra_String_Url, bean_banner.getHref()));
                            }
                        });
                }
            }

            @Override
            public ImageView createImageView(Context context) {
                return new SimpleDraweeView(context);
            }
        }).setDelayTime(Constant_Common.bannerDelayTime);
        if (isWelcomeBanner)
            banner.setBannerStyle(bean_oneKeyBootstrap.getBean_welcomeOptions().getBannerConfig()).isAutoPlay(false);
        banner.start();
    }

    public static void setViewPager(ViewPager viewPager, FragmentManager fragmentManager, final List<Framework_Fragment> fragmentList, Frag_Purse_Function frag_Purse_Function1, Frag_Purse_Function frag_Purse_Function2) {
        if (fragmentList.isEmpty()) {
            fragmentList.add(frag_Purse_Function1);
            fragmentList.add(frag_Purse_Function2);
            viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
                @Override
                public Fragment getItem(int position) {
                    return fragmentList.get(position);
                }

                @Override
                public int getCount() {
                    return fragmentList.size();
                }
            });
            viewPager.setOffscreenPageLimit(2);
            viewPager.setCurrentItem(0);
        }
    }

    public static void skipToPushMessage(Activity activity) {
        activity.startActivity(new Intent(activity, Act_Home_PushMessage.class));
    }

    public static void setViewPagerIndicator(final ViewPager viewPager, final ImageView iv_indicator1, final ImageView iv_indicator2) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        iv_indicator1.setImageResource(R.mipmap.icon_vp_on);
                        iv_indicator2.setImageResource(R.mipmap.icon_vp_off);
                        break;
                    case 1:
                        iv_indicator1.setImageResource(R.mipmap.icon_vp_off);
                        iv_indicator2.setImageResource(R.mipmap.icon_vp_on);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public static void getMarqueeData(Activity activity, final MarqueeFactory marqueeFactory, final List<String> marqueeList) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_PUSH_MESSAGE_PLATFORM_GET.concat(bean_userInfo.getUserToken()), Util_Http.RequestMethod.GET, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("page", 0);
                httpParams.put("size", 10);

            }
        }, new Util_Http.HttpDealStringListener(activity, null, false, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        marqueeList.clear();
                        List<Bean_PushMessage> bean_pushMessageList = JSON.parseArray(JSON.parseObject(data).getJSONObject(Http_Key_Data).getString("content"), Bean_PushMessage.class);
                        for (int i = 0, size = bean_pushMessageList.size(); i < size; i++) {
                            marqueeList.add(bean_pushMessageList.get(i).getContent());
                        }
                        resetMarqueeView(marqueeFactory, marqueeList);
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }

    public static void setBaseRebateData(Bean_Item item, Bean_BaseRebate baseRebate) {
        switch ((Method_Static.Enum_RebateType) item.getObject()) {
            case 当日收益:
                item.setValue(String.valueOf(baseRebate.getTodayRebate()));
                break;
            case 昨日收益:
                item.setValue(String.valueOf(baseRebate.getYesterdayRebate()));
                break;
            case 当月收益:
                item.setValue(String.valueOf(baseRebate.getMonthRebate()));
                break;
            case 所有收益:
                item.setValue(String.valueOf(baseRebate.getAllRebate()));
                break;
        }
    }

    public static void getRebateBaseData(Activity activity, View_XRefreshStatusView xRefreshStatusView, final Util_Runnable.Util_TypeRunnable<Bean_BaseRebate> typeRunnable) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_GET_BASE_REBATE_INFO_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("user_id", bean_userInfo.getId());

            }
        }, new Util_Http.HttpDealStringListener(activity, xRefreshStatusView, null, false, false) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        typeRunnable.run(JSON.parseObject(JSON.parseObject(data).getString(Http_Key_Data), Bean_BaseRebate.class));
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }

    public static void skipToUpgrade(Activity activity, boolean T_Act$F_Frag) {
        if (T_Act$F_Frag)
            activity.startActivity(new Intent(activity, Act_Upgrade_OnlineBuy.class));
        else {
            List<Abstract_OkModule_Fragment> fragmentList = bean_oneKeyBootstrap.getAbstract_okModule_fragmentList();
            boolean hasUpgradeModule = false;
            for (int i = 0, size = fragmentList.size(); i < size; i++) {
                if (fragmentList.get(i).getEnum_okModule_feature() == Enum_OKModule_Feature.升级) {
                    hasUpgradeModule = true;
                    break;
                }
            }
            if (!hasUpgradeModule) {
                activity.startActivity(new Intent(activity, Act_Share_Upgrade.class));
            } else if (activity instanceof Act_Main_Module) {
                Act_Main_Module act = (Act_Main_Module) activity;
                for (int i = 0, size = act.bean_items.size(); i < size; i++) {
                    act.bean_items.get(i).setChecked(false);
                }
                int position = getModuleFragmentPosition(Enum_OKModule_Feature.升级);
                if (position == -1)
                    return;
                act.bean_items.get(position).setChecked(true);
                act.rva_module.notifyDataSetChanged();
                util_fragment.fragmentToShow(position);
            }
        }
    }

    public static void skipToUpgradeAuto(Activity activity) {
        int position = getModuleFragmentPosition(Enum_OKModule_Feature.升级);
        if (position == -1) {
            activity.startActivity(new Intent(activity, Act_Upgrade_OnlineBuy.class));
            return;
        }
        Act_Main_Module act = (Act_Main_Module) activity;
        for (int i = 0, size = act.bean_items.size(); i < size; i++) {
            act.bean_items.get(i).setChecked(false);
        }
        act.bean_items.get(position).setChecked(true);
        Act_Main_Module.moduleFragmentPosition = position;
        act.rva_module.notifyDataSetChanged();
        util_fragment.fragmentToShow(position);
    }

    public static int getModuleFragmentPosition(Enum_OKModule_Feature enum_okModule_feature) {
        return getModuleFragmentPosition(enum_okModule_feature, null);
    }

    public static int getModuleFragmentPosition(Enum_OKModule_Feature enum_okModule_feature, Enum_OKModule_Template enum_okModule_template) {
        Map<Integer, Fragment> maps = util_fragment.getTm_fragment();
        int position = -1;
        for (Map.Entry<Integer, Fragment> e : maps.entrySet()) {
            Fragment fragment = e.getValue();
            if (fragment instanceof Abstract_OkModule_Fragment && ((Abstract_OkModule_Fragment) fragment).getEnum_okModule_feature() == enum_okModule_feature) {
                if (enum_okModule_template != null && ((Abstract_OkModule_Fragment) fragment).getEnum_okModule_template() == enum_okModule_template)
                    position = e.getKey();
                else
                    position = e.getKey();
                break;
            }
        }
        return position;
    }

    public static void callCustomerService(Activity activity, Act_Main_Module.Enum_Customer enum_customer) {
        switch (enum_customer) {
            case 美洽:
                Bean_CustomerUi bean_customerUi = new Bean_CustomerUi();
                bean_customerUi.setBackArrowIconResId(R.mipmap.app_arrow_left_white);
                bean_customerUi.setTitleBackgroundResId(R.color.title_background);
                bean_customerUi.setTitleTextColorResId(R.color.title_fontColor);
                Util_Customer.getInstance().skipToOnlineCustomerAct(activity, bean_customerUi);
                break;
            case QQ:
                Util_Apk.skipToQQ(activity, bean_oemInfo.getBrandQQ());
                break;
            case 微信:
                Util_Apk.skipToWX(activity, bean_oemInfo.getBrandQQ());
                break;
        }
    }

    public static void skipToShareDialog$JFB(final Activity activity) {
        Util_Dialog.dialogByAct(new View(activity), R.layout.dialog_share_xfw, Color.argb(200, 0, 0, 0), true, false, false, false, new Util_Dialog.ActDialog() {
            ImageView iv_cancel;
            Util_Dialog.DialogContext dialogContext;
            RecyclerView rv_share;
            List<Bean_Item> bean_itemList = new ArrayList<>();
            LinearLayout ll_upgrade, ll_document, ll_bottom, ll_top;

            @Override
            public void onClick(View v) {
                if (v == iv_cancel) {
                    dialogContext.dismiss();
                } else if (v == ll_upgrade) {
                    skipToUpgradeAuto(activity);
                    dialogContext.dismiss();
                } else if (v == ll_document)
                    activity.startActivity(new Intent(activity, Act_Main_Web.class).putExtra(Extra_String_Url, new StringBuilder("http://1.xinli2017.applinzi.com/%E5%B9%B3%E5%8F%B0%E5%BA%94%E7%94%A8/py.html?phone=").append(bean_userInfo.getPhone()).append("&brandid=").append(bean_userInfo.getBrandId()).toString()));

            }

            @Override
            public void initDialogData() {
                bean_itemList.add(new Bean_Item("微信", R.mipmap.share_wechat_shadow, true));
                bean_itemList.add(new Bean_Item("朋友圈", R.mipmap.share_friend_shadow, true));
                bean_itemList.add(new Bean_Item("QQ", R.mipmap.share_qq_shadow, true));
                bean_itemList.add(new Bean_Item("空间", R.mipmap.share_zone_shadow, true));
                bean_itemList.add(new Bean_Item("二维码", R.mipmap.share_erweima_shadow, true));
                bean_itemList.add(new Bean_Item("短信", R.mipmap.share_sms_shadow, true));
            }

            @Override
            public View initDialogView(Util_Dialog.DialogContext dialogContext, View view) {
                this.dialogContext = dialogContext;
                iv_cancel = (ImageView) view.findViewById(R.id.iv_cancel);
                ll_bottom = (LinearLayout) view.findViewById(R.id.ll_bottom);
                ll_top = (LinearLayout) view.findViewById(R.id.ll_top);
                ll_upgrade = (LinearLayout) view.findViewById(R.id.ll_upgrade);
                ll_document = (LinearLayout) view.findViewById(R.id.ll_document);
                rv_share = (RecyclerView) view.findViewById(R.id.rv_share);
                rv_share.setLayoutManager(new GridLayoutManager(activity, 3));
                RecyclerView.Adapter rva_share = new RecyclerView.Adapter() {
                    class MyViewHolder extends RecyclerView.ViewHolder {
                        SimpleDraweeView sdv_img;
                        TextView tv_name, tv_value;
                        LinearLayout ll_bg;

                        public MyViewHolder(View itemView) {
                            super(itemView);
                            (sdv_img = (SimpleDraweeView) itemView.findViewById(R.id.sdv_img)).setVisibility(View.VISIBLE);
                            AutoLinearLayout.LayoutParams lp2 = (AutoLinearLayout.LayoutParams) sdv_img.getLayoutParams();
                            AutoLayoutInfo autoLayoutInfo = lp2.getAutoLayoutInfo();
                            autoLayoutInfo.addAttr(new WidthAttr(120, 0, 0));
                            autoLayoutInfo.addAttr(new HeightAttr(120, 0, 0));
                            autoLayoutInfo.addAttr(new PaddingBottomAttr(5, 0, 0));
                            autoLayoutInfo.addAttr(new MarginBottomAttr(20, 0, 0));
                            ViewGroup.LayoutParams lp = sdv_img.getLayoutParams();
                            sdv_img.setLayoutParams(lp);

                            tv_value = (TextView) itemView.findViewById(R.id.tv_value);

                            (tv_name = (TextView) itemView.findViewById(R.id.tv_name)).setVisibility(View.VISIBLE);
                            AutoLinearLayout.LayoutParams layoutParams1 = (AutoLinearLayout.LayoutParams) tv_name.getLayoutParams();
                            AutoLayoutInfo autoLayoutInfo1 = layoutParams1.getAutoLayoutInfo();
                            autoLayoutInfo1.addAttr(new TextSizeAttr(45, 0, 0));
                            ViewGroup.LayoutParams lp1 = tv_name.getLayoutParams();
                            tv_name.setLayoutParams(lp1);
                            tv_name.setTextColor(Color.WHITE);

                            ll_bg = (LinearLayout) itemView.findViewById(R.id.ll_bg);
                            AutoRelativeLayout.LayoutParams layoutParams4 = (AutoRelativeLayout.LayoutParams) ll_bg.getLayoutParams();
                            AutoLayoutInfo autoLayoutInfo4 = layoutParams4.getAutoLayoutInfo();
                            autoLayoutInfo4.addAttr(new PaddingTopAttr(30, 0, 0));
                            autoLayoutInfo4.addAttr(new PaddingBottomAttr(30, 0, 0));
                            ViewGroup.LayoutParams lp4 = ll_bg.getLayoutParams();
                            ll_bg.setLayoutParams(lp4);

                        }
                    }

                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        return new MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.app_adapter_gv, parent, false));
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
                        final MyViewHolder mvh = (MyViewHolder) holder;
                        final Bean_Item bean = bean_itemList.get(position);
                        mvh.tv_name.setText(bean.getName().toString());
                        mvh.sdv_img.setImageResource(bean.getImgId());
                        if (Util_Empty.isEmpty(bean.getValue())) {
                            mvh.tv_value.setVisibility(View.GONE);
                        } else {
                            mvh.tv_value.setVisibility(View.VISIBLE);
                            mvh.tv_value.setText(bean.getValue());
                        }
                        final String shareTitle = bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareTitle();
                        final String shareContent = bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareContent();
                        final String shareUrl = bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_share().getShareUrl();
                        mvh.ll_bg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (position) {
                                    case 0:
                                        Util_Share.getInstance().share(Wechat, shareTitle, shareContent, shareUrl, null);
                                        break;
                                    case 1:
                                        Util_Share.getInstance().share(WechatMoments, shareTitle, shareContent, shareUrl, null);
                                        break;
                                    case 2:
                                        Util_Share.getInstance().share(QQ, shareTitle, shareContent, shareUrl, null);
                                        break;
                                    case 3:
                                        Util_Share.getInstance().share(QZone, shareTitle, shareContent, shareUrl, null);
                                        break;
                                    case 4:
                                        skipToScanCodeShare(activity);
                                        break;
                                    case 5:
                                        Uri smsToUri = Uri.parse("smsto:");
                                        Intent sendIntent = new Intent(Intent.ACTION_VIEW, smsToUri);
                                        //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
                                        //短信内容
                                        sendIntent.putExtra("sms_body", new StringBuilder("【").append(activity.getResources().getString(R.string.app_name)).append("】").append(shareContent).append(shareUrl).toString());
                                        sendIntent.setType("vnd.android-dir/mms-sms");
                                        activity.startActivityForResult(sendIntent, 1002);
                                        break;
                                }
                            }
                        });
                    }

                    @Override
                    public int getItemCount() {
                        return bean_itemList.size();
                    }
                };
                rv_share.setAdapter(rva_share);


                Util_View.viewOnClick(this, iv_cancel, ll_upgrade, ll_document);

                Util_Animation.animationToTranslate(ll_upgrade, Util_Animation.AnimationTranslateOrientation.ANIMATION_TRANSLATE_HORIZONTAL, 500, -1.2f, 0, null);
                Util_Animation.animationToTranslate(ll_document, Util_Animation.AnimationTranslateOrientation.ANIMATION_TRANSLATE_HORIZONTAL, 500, 1.2f, 0, null);
                Util_Animation.animationToTranslate(ll_bottom, Util_Animation.AnimationTranslateOrientation.ANIMATION_TRANSLATE_VERTICAL, 500, 1.2f, 0, null);
                return view;
            }

            @Override
            public void onDismissListener() {

            }
        });
    }


    public static void getOEMData(Activity activity) {
        Util_Http.httpToRequestSync(activity, Constant_Api.URL_GET_OEM_INFO_GET, Util_Http.RequestMethod.GET, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("brand_id", bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().get$brandId());

            }
        }, new Util_Http.HttpDealString() {
            @Override
            public void onDealResult(String data, Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        bean_oemInfo = JSON.parseObject(JSON.parseObject(data).getString(Http_Key_Data), Bean_OemInfo.class);
                        String phone = bean_oemInfo.getBrandPhone();
                        if (!Util_Empty.isEmpty(phone))
                            bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().setTelephone(phone);
                        int position = getModuleFragmentPosition(Enum_OKModule_Feature.分享, 赚道钱包);
                        if (position == -1)
                            return;
                        Frag_Module_Share$ZDQB fragment = (Frag_Module_Share$ZDQB) Act_Main_Module.util_fragment.getTm_fragment().get(position);
                        fragment.upgradePeoples = Integer.parseInt(bean_oemInfo.getAutoUpgradePeople());
                        break;
                    default:
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }

    public static void getCarouselData(Activity activity) {
        Util_Http.httpToRequestSync(activity, Constant_Api.URL_GET_CAROUSEL_INFO_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("brand_id", bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().get$brandId());

            }
        }, new Util_Http.HttpDealString() {
            @Override
            public void onDealResult(String data, Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        bean_oneKeyBootstrap.getBean_carouselList().clear();
                        bean_oneKeyBootstrap.setBean_carouselList(JSON.parseArray(JSON.parseObject(data).getString(Http_Key_Data), Bean_Banner2.class));
                        break;
                    default:
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }

    public static void selectCustomerService(final Activity activity) {
        final List<Bean_Item> bean_itemList = new ArrayList<Bean_Item>();
        Util_Runnable.Util_ArgsRunnable util_Args_runnable = bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().get$customerServiceRunnable();
        if (util_Args_runnable == null)
            return;
        util_Args_runnable.run(activity, bean_itemList);
        if (bean_itemList.size() == 1)
            bean_itemList.get(0).getUtil_Args_runnable().run();
        else
            Util_Dialog.dialogByAct(new View(activity), R.layout.dialog_share, false, false, false, false, new Util_Dialog.ActDialog() {
                final String noOpen = "暂未开放";
                RecyclerView rv_share;
                TextView tv_cancel, tv_title;
                Util_Dialog.DialogContext dialogContext;

                @Override
                public void onClick(View v) {
                    if (v == tv_cancel)
                        dialogContext.dismiss();

                }

                @Override
                public void initDialogData() {
                    String qqValue = "需添加好友";
                    String wxValue = "需添加微信";
                    if (bean_oemInfo == null) {
                        qqValue = wxValue = noOpen;
                    } else {
                        if (Util_Empty.isEmpty(bean_oemInfo.getBrandQQ()))
                            qqValue = noOpen;
                        if (Util_Empty.isEmpty(bean_oemInfo.getBrandWeiXin()))
                            wxValue = noOpen;
                    }
                    for (int i = 0, size = bean_itemList.size(); i < size; i++) {
                        Bean_Item bean = bean_itemList.get(i);
                        if (bean.getObject() == Act_Main_Module.Enum_Customer.QQ)
                            bean.setValue(qqValue);
                        if (bean.getObject() == Act_Main_Module.Enum_Customer.微信)
                            bean.setValue(wxValue);
                    }
                }

                @Override
                public View initDialogView(final Util_Dialog.DialogContext dialogContext, View view) {
                    this.dialogContext = dialogContext;
                    rv_share = (RecyclerView) view.findViewById(R.id.rv_share);
                    rv_share.setLayoutManager(new GridLayoutManager(activity, 4));

                    tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
                    tv_title = (TextView) view.findViewById(R.id.tv_title);
                    tv_title.setText("客服联系方式");

                    Util_View.viewOnClick(this, tv_cancel);
                    setShareAdapter(activity, rv_share, bean_itemList, new Util_Runnable.Util_ArgsRunnable() {
                        @Override
                        public void run(Object... data) {
                            int position = (int) data[0];
                            Bean_Item bean = bean_itemList.get(position);
                            if (bean.getValue().equals(noOpen)) {
                                Util_Toast.toast(noOpen.concat(" ,请尝试其他方式"));
                                return;
                            }
                            bean.getUtil_Args_runnable().run();
                            dialogContext.dismiss();
                        }
                    });
                    return view;
                }

                @Override
                public void onDismissListener() {
                }
            });
    }

    public static void getRoleData(Activity activity, final Util_Runnable.Util_ArgsRunnable util_argsRunnable) {
        Util_Http.httpToRequestSync(activity, Constant_Api.URL_QUERY_PRODUCTION_LIST_GET.concat(String.valueOf(bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().get$brandId())), Util_Http.RequestMethod.GET, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {

            }
        }, new Util_Http.HttpDealString() {

            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        if (util_argsRunnable != null)
                            util_argsRunnable.run(JSONObject.parseArray(parseObject(data).getString(Http_Key_Data), Bean_Production.class));
                        break;
                    default:
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }

    public static void showCertificationDialog(View viewToken) {
        String title = "您未实名认证，是否立刻实名！", confirmText = "立刻实名", cancelText = "暂不实名";
        if (bean_userInfo.getRealnameStatus() != null) {
            switch (bean_userInfo.getRealnameStatus()) {
                case "0":
                    title = "正在实名审核中，请等待审核通过!";
                    cancelText = "确定";
                    showCertificationDialog2(viewToken, title, null, cancelText);
                    break;
                case "1":
                    Util_Runnable.Util_ArgsRunnable runnable = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().getMainUIRunnable();
                    if (runnable != null)
                        runnable.run(viewToken, getFirstPushMessageData((Activity) viewToken.getContext()));
                    break;
                case "2":
                    title = "实名审核失败，请重新实名!";
                    showCertificationDialog2(viewToken, title, confirmText, cancelText);
                    break;
                case "3":
                    showCertificationDialog2(viewToken, title, confirmText, cancelText);
                    break;
            }
        } else {
            showCertificationDialog2(viewToken, title, confirmText, cancelText);
        }
    }

    static boolean showCustomDialogOnlyOneOnMainUI;
    static boolean showPushDialogOnlyOneOnMainUI;

    public static void showCustomDialogOnlyOneOnMainUI(final View viewToken, final String contentText, final String confirmText, final String cancelText, final Util_Runnable.Util_TypeRunnable<Framework_Dialog> confirmRunnable, final Util_Runnable.Util_TypeRunnable<Framework_Dialog> cancelRunnable) {
        if (!showCustomDialogOnlyOneOnMainUI)
            showDefaultDialog(viewToken, contentText, confirmText, cancelText, new Util_Dialog.DialogListener() {

                @Override
                public void onCancel(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                    cancelRunnable.run(framework_dialog);
                }

                @Override
                public void onConfirm(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                    confirmRunnable.run(framework_dialog);
                }

                @Override
                public void onDismissListener() {
                    showCustomDialogOnlyOneOnMainUI = true;
                }
            });
    }

    public static void showPushDialogOnlyOneOnMainUI(final View viewToken, String contentText) {
        if (!showPushDialogOnlyOneOnMainUI)
            if (Util_Empty.isEmpty(contentText))
                showPushDialogOnlyOneOnMainUI = true;
            else
                showDefaultDialog(viewToken, contentText, "我知道了", null, new Util_Dialog.DialogListener() {

                    @Override
                    public void onCancel(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                    }

                    @Override
                    public void onConfirm(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                        framework_dialog.dismiss();
                    }

                    @Override
                    public void onDismissListener() {
                        showPushDialogOnlyOneOnMainUI = true;
                    }
                });
    }

    private static void showCertificationDialog2(final View viewToken, final String title, final String confirmText, final String cancelText) {
        showDefaultDialog(viewToken, title, confirmText, cancelText, new Util_Dialog.DialogListener() {

            @Override
            public void onCancel(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                framework_dialog.dismiss();
            }

            @Override
            public void onConfirm(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                framework_dialog.startActivity(new Intent(framework_dialog, Act_Mine_Certification.class));
                framework_dialog.dismiss();
            }

            @Override
            public void onDismissListener() {
                Util_Runnable.Util_ArgsRunnable runnable = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().getMainUIRunnable();
                if (runnable != null)
                    runnable.run(viewToken, getFirstPushMessageData((Activity) viewToken.getContext()));
            }
        });
    }

    public static void getPurseBaseInfo(Activity activity, boolean isAutoDismiss, View_XRefreshStatusView view_xRefreshStatusView, final Util_Runnable.Util_TypeRunnable<Bean_UserAccount> purseBaseInfoRunnable) {
        obtainUserPurseBaseInfo(activity, view_xRefreshStatusView, isAutoDismiss, new Util_Runnable.Util_TypeRunnable<Bean_UserAccount>() {
            @Override
            public void run(Bean_UserAccount data) {
                if (purseBaseInfoRunnable != null)
                    purseBaseInfoRunnable.run(data);
            }
        });
    }

    public static void initModuleFragmentDataAndPush(final View viewToken) {
        final Activity activity = (Activity) viewToken.getContext();
        getRoleData(activity, new Util_Runnable.Util_ArgsRunnable() {
            @Override
            public void run(Object... data) {
                List<Bean_Production> productionList = (List<Bean_Production>) data[0];
                for (int i = 0, size = productionList.size(); i < size; i++) {
                    Bean_Production bean = productionList.get(i);
                    gradeRoleMaps.put(bean.getGrade(), bean);
                }
            }
        });
        getOEMData(activity);
        getCarouselData(activity);
        showCertificationDialog(viewToken);
        if (bean_userInfo.getId() != null)
            Util_Push.getInstance().bindUserId(bean_userInfo.getId(), new Inter_Push_Callback() {
                @Override
                public void onCustomPush(Bean_Push bean_push) {
                    Util_Log.logPush("onCustomPush", bean_push);
                }

                @Override
                public void onNotificationPush(Bean_Push bean_push) {
                    Util_Log.logPush("onNotificationPush", bean_push);
                    Bean_PushData bean_pushData;
                    if (bean_push != null && bean_push.getExtra_extra() != null) {
                        bean_pushData = JSON.parseObject(bean_push.getExtra_extra(), Bean_PushData.class);
                        Util_Log.logPush("onNotificationPush", bean_pushData);
                        if (bean_pushData.getRealnameStatus() != null)
                            bean_userInfo.setRealnameStatus(bean_pushData.getRealnameStatus());
                        if (bean_pushData.getGrade() != null)
                            bean_userInfo.setGrade(bean_pushData.getGrade());
                        if (bean_pushData.getAndroidVersion() != null)
                            Act_Main.updateApk(viewToken, false, null);
                        Fragment fragment = Act_Main_Module.util_fragment.getTm_fragment().get(getModuleFragmentPosition(Enum_OKModule_Feature.我的));
                        if (fragment != null && fragment instanceof Abstract_OkModule_Fragment)
                            ((Abstract_OkModule_Fragment) fragment).updateViewByPushMessage(bean_pushData, bean_push);
                    }
                }

                @Override
                public void onNotificationOpened(Bean_Push bean_push) {
                    activity.startActivity(new Intent(activity, Act_Home_PushMessage.class));
                }
            });
    }

    private static String getFirstPushMessageData(Activity activity) {
        final String[] pushMessage = new String[1];
        Util_Http.httpToRequestSync(activity, Constant_Api.URL_PUSH_MESSAGE_PLATFORM_GET.concat(Abstract_App.bean_userInfo.getUserToken()), Util_Http.RequestMethod.GET, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }


            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("page", 0);
                httpParams.put("size", 1);

            }
        }, new Util_Http.HttpDealString() {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (JSON.parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        pushMessage[0] = JSON.parseArray(JSON.parseObject(data).getJSONObject(Http_Key_Data).getString("content"), Bean_PushMessage.class).get(0).getContent();
                        break;
                    default:
                        Util_Toast.toast(JSON.parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
        return pushMessage[0];
    }

    public static void updateMarqueeView(Activity activity, List<String> marqueeList, Bean_PushData bean_pushData, Bean_Push bean_push) {
        if (bean_pushData.getMarquee() != null) {
            marqueeList.add(bean_pushData.getMarquee());
            showDefaultDialog(new View(activity), bean_push.getExtra_alert(), "我知道了", null, new Util_Dialog.DialogListener() {
                @Override
                public void onCancel(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {

                }

                @Override
                public void onConfirm(Framework_Dialog framework_dialog, TextView tv_content, TextView tv_confirm, TextView tv_cancel, View v_split) {
                    framework_dialog.dismiss();
                }

                @Override
                public void onDismissListener() {

                }
            });
        }
    }


    private static void getProductionIdToBuy(View viewToken, List<Bean_Production> bean_productionList, String grade) {
        for (int i = 0, size = bean_productionList.size(); i < size; i++) {
            if (bean_productionList.get(i).getGrade().equals(grade)) {
                getProductionDataToBuy(viewToken, bean_productionList.get(i));
                break;
            }
        }
    }

    public static void getProductionDataToBuy(final View viewToken, final Bean_Production bean_production) {
        Activity activity = (Activity) viewToken.getContext();
      /*  if (bean_channel != null && !checkChannelIsAvailable(bean_channel, "购买产品功能正在维护升级,请耐心等待!"))
            return;*/
        Util_Http.httpToRequest(activity, Constant_Api.URL_QUERY_PRODUCTION_INFO_GET.concat(bean_production.getId()), Util_Http.RequestMethod.GET, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {

            }
        }, new Util_Http.HttpDealStringListener(activity, null, false, true) {
            @Override
            public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        Bean_Production bean = parseObject(parseObject(data).getString(Http_Key_Data), Bean_Production.class);
                        final Bean_Collection bean_collection = new Bean_Collection();
                        bean_collection.setRemark(new StringBuilder("购买产品【").append(bean.getName()).append("】").toString());
                        bean_collection.setMoney(bean_production.getMoney());
                        bean_collection.setProduct_id(bean_production.getId());
                        bean_collection.setChannelTag(YILIAN);
                        buyProduction(viewToken, bean, bean_collection);
                        break;
                    default:
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }

        });
    }

    public static void buyProduction(View viewToken, final Bean_Production bean, final Bean_Collection bean_collection) {
        final Activity activity = (Activity) viewToken.getContext();
        if (!bean_userInfo.getRealnameStatus().equals("1")) {
            showCertificationDialog(viewToken);
            return;
        }
        if (Integer.parseInt(bean_userInfo.getGrade()) >= Integer.parseInt(bean.getGrade())) {
            Util_Toast.toast(new StringBuilder("您已经是").append(getUserGradeRole(bean_userInfo.getGrade())).append(",无需再购买此产品"));
            return;
        }
        getPersonalInfoData(activity, null, false, new Util_Runnable.Util_ArgsRunnable() {
            @Override
            public void run(Object... data) {
                Bean_PersonalInfo bean = (Bean_PersonalInfo) data[0];
                if (Util_Empty.isEmptyToToast(bean.getRealname(), "未获取到真实姓名")) {
                    Util_Http.dismiss();
                    return;
                }
            }
        });
        getRechargeCardDataToSelect(viewToken, new Util_Runnable.Util_ArgsRunnable() {
            @Override
            public void run(final Object... data) {
                Util_Http.httpToRequest(activity, Constant_Api.URL_PURCHASE_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
                    @Override
                    public void addHeaders(HttpHeaders httpHeaders) {
                    }

                    @Override
                    public void addParams(HttpParams httpParams) {
                        httpParams.put("order_desc", bean_collection.getRemark());
                        httpParams.put("phone", bean_userInfo.getPhone());
                        httpParams.put("amount", (int) Float.parseFloat(bean_collection.getMoney()));
                        httpParams.put("channe_tag", bean_collection.getChannelTag().toString());
                        httpParams.put("product_id", bean_collection.getProduct_id());
                        Bean_BankCardInfo bean_bankCardInfo = (Bean_BankCardInfo) data[0];
                        if (bean_bankCardInfo != null)
                            httpParams.put("bank_card", bean_bankCardInfo.getCardNo());

                    }
                }, new Util_Http.HttpDealStringListener(activity, "正在获取产品信息 ，请稍后！", true, true) {
                    @Override
                    public void onDealResult(String data, com.lzy.okgo.model.Response response) {
                        switch (parseObject(data).getString(Http_Key_Name)) {
                            case Http_Value_Success:
                                Bean_Channel bean_channel = new Bean_Channel();
                                bean_channel.setChannelNo("2");
                                skipToPayPage(activity, data, bean_collection, bean_channel);
                                break;
                            default:
                                Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                                break;
                        }
                    }

                });
            }
        });

    }

    public static void getProductionToBuy(View viewToken, String grade, Bean_Production bean_production, List<Bean_Production> bean_productionList) {
        if (grade != null)
            getProductionIdToBuy(viewToken, grade, bean_productionList);
        else if (bean_production != null)
            getProductionDataToBuy(viewToken, bean_production);
    }

    public static void loginByHttp(final Activity activity, final String phone, final String password, final boolean rememberPassword) {
        Util_Http.httpToRequest(activity, Constant_Api.URL_LOGIN_POST, Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("phone", phone);
                httpParams.put("password", password);
                httpParams.put("brand_id", Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().get$brandId());

            }
        }, new Util_Http.HttpDealStringListener(activity, Constant_International.message_net_login, false, true) {
            @Override
            public void onDealResult(final String data, com.lzy.okgo.model.Response response) {
                final Bean_UserInfo bean = parseObject(parseObject(data).getString(Http_Key_Data), Bean_UserInfo.class);
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        Util_Runnable.Util_ArgsRunnable runnable = Abstract_App.bean_oneKeyBootstrap.getBean_oneKeyProps().getBean_okProp_oem().getLoginFilterRunnable();
                        if (runnable != null)
                            runnable.run(activity, bean, rememberPassword);
                        else
                            saveUserDataByGreenDao(activity, bean, rememberPassword);
                       /* Util_IM.getInstance().login(bean_userInfo.getImid(), Util_Encrypt.md5(username.concat("imkeyrent"), false), new Inter_IM_CallBack() {
                            @Override
                            public void onSuccess() {
                                startActivity(new Intent(getActivity(), Util_Empty.isEmpty(bean_userInfo.getNick()) || Util_Empty.isEmpty(bean_userInfo.getPortrait()) ? Act_Register_PerfectInfo.class : Act_Main_Module.class));
                                Util_Http.dismiss();
                                finish();
                            }
                            @Override
                            public void onError(int code, String message) {
                                Util_Toast.toast("登录失败，请稍后重试");
                                Util_Http.dismiss();
                            }
                        });*/
                        break;
                    default:
                        Util_Http.dismiss();
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }

    public static void showActivationCodeDialog(final Activity activity, final Bean_UserInfo bean, final boolean rememberPassword) {
        Util_Http.dismiss();
        switch (bean.getVerifyStatus()) {
            case "0":
                Util_Dialog.dialogByAct(new View(activity), R.layout.dialog_invite_code, false, false, false, false, new Util_Dialog.ActDialog() {
                    Util_Dialog.DialogContext dialogContext;
                    EditText et_inviteCode;
                    TextView tv_confirm, tv_cancel, tv_title;
                    LinearLayout ll_bg;

                    @Override
                    public void initDialogData() {
                    }

                    @Override
                    public View initDialogView(Util_Dialog.DialogContext dialogContext, View view) {
                        this.dialogContext = dialogContext;
                        Framework_Activity framework_activity = dialogContext.getDialog();
                        et_inviteCode = framework_activity.util_init.initEditText(view, R.id.icd_inviteCode, R.id.et_input, "请输入激活码码激活本产品", null, InputType.TYPE_CLASS_NUMBER, 11, View.VISIBLE);
                        framework_activity.util_init.initTextView(view, R.id.icd_inviteCode, R.id.tv_left, null, "激活码", View.VISIBLE);
                        framework_activity.util_init.initView(view, R.id.icd_inviteCode, R.id.v_split, View.VISIBLE);
                        tv_title = (TextView) view.findViewById(R.id.tv_title);
                        tv_title.setText("请先激活");

                        ll_bg = (LinearLayout) view.findViewById(R.id.ll_bg);
                        tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
                        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

                        ll_bg.post(new Runnable() {
                            @Override
                            public void run() {
                                int w = (int) (ll_bg.getMeasuredWidth() / 2.5);
                                tv_cancel.setMinWidth(w);
                                tv_confirm.setMinWidth(w);
                            }
                        });

                        Util_View.viewOnClick(dialogContext.getDialog(), tv_confirm, tv_cancel);
                        return view;
                    }

                    @Override
                    public void onDismissListener() {

                    }

                    @Override
                    public void onClick(View v) {
                        if (v == tv_confirm) {
                            if (Util_Empty.isEmptyToToast(et_inviteCode.getText().toString(), et_inviteCode.getHint().toString()))
                                return;
                            adjustIsFirstLogin(activity, bean, et_inviteCode.getText().toString(), rememberPassword);
                        } else if (v == tv_cancel) dialogContext.dismiss();
                    }
                });
                break;
            default:
                saveUserDataByGreenDao(activity, bean, rememberPassword);
                break;
        }
    }

    public static void showInputDialog(final Activity activity, final String title, final String hint, final String prefix, final int inputType, final int inputLength, final Util_Runnable.Util_ArgsRunnable runnable) {
        Util_Dialog.dialogByAct(new View(activity), R.layout.dialog_invite_code, false, false, false, false, new Util_Dialog.ActDialog() {
            Util_Dialog.DialogContext dialogContext;
             EditText et_inviteCode;
            TextView tv_confirm, tv_cancel, tv_title;
            LinearLayout ll_bg;

            @Override
            public void initDialogData() {
            }

            @Override
            public View initDialogView(Util_Dialog.DialogContext dialogContext, View view) {
                this.dialogContext = dialogContext;
                Framework_Activity framework_activity = dialogContext.getDialog();
                et_inviteCode = framework_activity.util_init.initEditText(view, R.id.icd_inviteCode, R.id.et_input, hint, null,inputType, inputLength, View.VISIBLE);
                framework_activity.util_init.initTextView(view, R.id.icd_inviteCode, R.id.tv_left, null, prefix, View.VISIBLE);
                framework_activity.util_init.initView(view, R.id.icd_inviteCode, R.id.v_split, View.VISIBLE);
                tv_title = (TextView) view.findViewById(R.id.tv_title);
                tv_title.setText(title);

                ll_bg = (LinearLayout) view.findViewById(R.id.ll_bg);
                tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
                tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

                ll_bg.post(new Runnable() {
                    @Override
                    public void run() {
                        int w = (int) (ll_bg.getMeasuredWidth() / 2.5);
                        tv_cancel.setMinWidth(w);
                        tv_confirm.setMinWidth(w);
                    }
                });

                Util_View.viewOnClick(dialogContext.getDialog(), tv_confirm, tv_cancel);
                return view;
            }

            @Override
            public void onDismissListener() {

            }

            @Override
            public void onClick(View v) {
                if (v == tv_confirm) {
                    if (Util_Empty.isEmptyToToast(et_inviteCode.getText().toString(), et_inviteCode.getHint().toString()))
                        return;
                    if (runnable != null) runnable.run(dialogContext,et_inviteCode.getText().toString());
                } else if (v == tv_cancel) dialogContext.dismiss();
            }
        });
    }

    public static void saveUserDataByGreenDao(final Activity activity, final Bean_UserInfo data, final boolean rememberPassword) {
        new Thread() {
            @Override
            public void run() {
                bean_userInfo = data;
                bean_userInfo.setModifyDate(System.currentTimeMillis());
                if (Abstract_App.daoSession.getBean_UserInfoDao().count() <= 0 || Abstract_App.daoSession.getBean_UserInfoDao().queryBuilder().where(Bean_UserInfoDao.Properties.Phone.eq(bean_userInfo.getPhone())).count() == 0) {
                    Bean_Properties bean_properties = new Bean_Properties();
                    bean_properties.setRememberPassword(rememberPassword);
                    bean_userInfo.setBean_properties(bean_properties);
                    Abstract_App.daoSession.getBean_PropertiesDao().insertOrReplaceInTx(bean_properties);
                    Abstract_App.daoSession.getBean_UserInfoDao().insertOrReplaceInTx(bean_userInfo);
                }
                Util_Http.dismiss();
                activity.startActivity(new Intent(activity, Act_Main_Module.class));
                //activity.finish();
            }
        }.start();

    }

    public static void adjustIsFirstLogin(final Activity activity, final Bean_UserInfo bean_userInfo, final String userId, final boolean rememberPassword) {
        Util_Http.httpToRequestSync(activity, Constant_Api.URL_ADJUST_IS_FIRST_LOGIN_POST.concat(bean_userInfo.getUserToken()), Util_Http.RequestMethod.POST, new Util_Http.HttpHeadersAndParams() {
            @Override
            public void addHeaders(HttpHeaders httpHeaders) {
            }

            @Override
            public void addParams(HttpParams httpParams) {
                httpParams.put("user_id", userId);

            }
        }, new Util_Http.HttpDealString() {
            @Override
            public void onDealResult(String data, Response response) {
                switch (parseObject(data).getString(Http_Key_Name)) {
                    case Http_Value_Success:
                        saveUserDataByGreenDao(activity, bean_userInfo, rememberPassword);
                        break;
                    default:
                        Util_Toast.toast(parseObject(data).getString(Http_Key_Toast));
                        break;
                }
            }
        });
    }

    public static void initUserPurseBaseInfo(final View_XRefreshLayout xrl, final boolean isAutoDismiss, final RecyclerView.Adapter adapter, final List<Bean_Item> purseInfoList, final Util_Runnable.Util_ArgsRunnable callbackRunnable) {
        xrl.setOnRefreshHttpListener(false, false, 0, new ArrayList(), adapter, new View_XRefreshLayout.OnRefreshHttpListener() {
            @Override
            public void onHttpStart(int currentPagePosition, final View_XRefreshStatusView view_xRefreshStatusView, List list, final RecyclerView.Adapter adapter) {
                getPurseBaseInfo((Activity) xrl.getContext(), isAutoDismiss, view_xRefreshStatusView, new Util_Runnable.Util_TypeRunnable<Bean_UserAccount>() {
                    @Override
                    public void run(Bean_UserAccount data) {
                        DecimalFormat decimalFormat = new DecimalFormat("0.00");
                        decimalFormat.setRoundingMode(RoundingMode.DOWN);
                        String integral = data.getCoin();
                        String balance = decimalFormat.format(new BigDecimal(data.getBalance()));
                        String rakeBack = decimalFormat.format(new BigDecimal(data.getRebateBalance()));
                        for (int i = 0, size = purseInfoList.size(); i < size; i++) {
                            Bean_Item item = purseInfoList.get(i);
                            switch ((Enum_PurseType) item.getObject()) {
                                case 积分:
                                    item.setValue(integral);
                                    break;
                                case 余额:
                                    item.setValue(balance);
                                    break;
                                case 分润:
                                    item.setValue(rakeBack);
                                    break;
                            }
                        }
                        if (adapter != null)
                            adapter.notifyDataSetChanged();
                        if (callbackRunnable != null)
                            callbackRunnable.run(view_xRefreshStatusView, integral, balance, rakeBack);
                    }
                });
            }
        });
    }
}
