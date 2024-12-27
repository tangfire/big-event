package com.fire.bigevent.config;


import com.fire.bigevent.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 * 用于创建AliOssUtil对象
 */
@Configuration
@Slf4j
public class OssConfiguration {

    // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
//    private static final String ENDPOINT = "oss-cn-guangzhou.aliyuncs.com";
//    // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量OSS_ACCESS_KEY_ID和OSS_ACCESS_KEY_SECRET。
////        EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
//    private static final String ACCESS_KEY_ID = "LTAI5tSBSPcZ5sqftNiVkZts";
//    private static final String ACCESS_KEY_SECRET = "a9Iwh9oS3crMmRoND4L1HMRZrqz0Z3";
//    // 填写Bucket名称，例如examplebucket。
//    private static final String BUCKETNAME = "sky-fire-itcast";



    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil() {
        log.info("开始创建阿里云文件上传工具类对象");



        return new AliOssUtil(ENDPOINT,
                ACCESS_KEY_ID,
                ACCESS_KEY_SECRET,
                BUCKETNAME);

    }
}
