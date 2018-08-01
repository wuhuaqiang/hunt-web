package com.hunt.tools;

import com.alibaba.druid.filter.config.ConfigTools;
import com.alibaba.druid.util.DruidPasswordCallback;
import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

/**
 * 数据库密码回调解密
 * chenwexu
 */
public class DBPasswordCallback extends DruidPasswordCallback {
    //上述生成的公钥
    public static final String PUBLIC_KEY_STRING = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCeMT6oOC7I7ypYIqJKuumSQKrPdBDf1xsJGR6bFifjgALkMGGGIelYaQSswuZzSyQU2j3HoJnXo2yTBzjU0CqUrV/dxuatAkNtvYIbxnXkFPJv25mX158WBtaiVis8FZcbXzohZ/VxcjL9gvf1Tomrx8sMekpLnuOUGyzkXOs1EwIDAQAB";
    public void setProperties(Properties properties) {
        super.setProperties(properties);
        String pwd = properties.getProperty("password");
        if (StringUtils.isNotBlank(pwd)) {
            try {
                //这里的password是将jdbc.properties配置得到的密码进行解密之后的值
                //所以这里的代码是将密码进行解密
                //TODO 将pwd进行解密;
                String password = ConfigTools.decrypt(PUBLIC_KEY_STRING, pwd);
                System.err.println("password---"+password);
                setPassword(password.toCharArray());
            } catch (Exception e) {
                setPassword(pwd.toCharArray());
            }
        }
    }

    // 请使用该方法加密后，把密文写入classpath:/config/jdbc.properties
    public static void main(String[] args) {

    }
}
