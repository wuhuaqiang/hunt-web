package com.hunt.tools;

import com.hunt.model.entity.SysOrganization;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Auther: cwx
 * @Date: 2018/6/8 16:31
 * @Description:
 */
public class MD5Utils {


    /**
     * 生成32位md5码 加盐后的md5
     * @param LoginName
     * @return
     */
    public static String md5LoginName(String LoginName) {

        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(LoginName.getBytes("UTF-8"));
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static void main(String [] arg){
        String st="";
       String s= md5LoginName(st);
        System.out.print(s);
    }
}
