package net.xbookmark;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;

/**
 * Tencent Cloud Sms Sendsms
 */
public class SendSms {
    public static void main(String[] args) {
        try {
            String secretId = "AKIDa8qMhsqfMsIPPGzWuAFJLG8rQq25wc8j";
            String secretKey = "UowtiQ3c4NfoX9fL8pWEycCf8gCYlvAG";
            String sdkAppId = "1400833310";
            String signName = "陆创科技";
            String templateId = "1849084";

            Credential cred = new Credential(secretId, secretKey);

            SmsClient client = new SmsClient(cred, "ap-guangzhou");
            SendSmsRequest req = new SendSmsRequest();
            req.setSmsSdkAppId(sdkAppId);
            req.setSignName(signName);
            req.setTemplateId(templateId);

            String[] templateParamSet = {"435322"};
            req.setTemplateParamSet(templateParamSet);

            String[] phoneNumberSet = {"18921121115"};
            req.setPhoneNumberSet(phoneNumberSet);

            /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
            SendSmsResponse res = client.SendSms(req);

            // 输出json格式的字符串回包
            System.out.println(SendSmsResponse.toJsonString(res));

        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
        }
    }
}
