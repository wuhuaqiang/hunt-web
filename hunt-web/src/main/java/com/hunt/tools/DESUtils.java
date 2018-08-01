package com.hunt.tools;
import com.alibaba.druid.filter.config.ConfigTools;

/**
 * 数据库密码加密方法类
 * cwx
 */
public class DESUtils {
    /**
     *加密密钥
     */
    private static final String PRIVATE_KEY_STRING = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ4xPqg4LsjvKlgiokq66ZJAqs90EN/XGwkZHpsWJ+OAAuQwYYYh6VhpBKzC5nNLJBTaPcegmdejbJMHONTQKpStX93G5q0CQ229ghvGdeQU8m/bmZfXnxYG1qJWKzwVlxtfOiFn9XFyMv2C9/VOiavHywx6Skue45QbLORc6zUTAgMBAAECgYBuoFOIAlo9bHu5TOcfyZykCZMqJqnST7R5ZVaw8AqPHytmdqsMyVRM3oxFYLsWL5sY9hI0M4zCb2fzXh6RPM45N2QusjF3joSa/QHFZWLD1W5vry0aIPWUqVLt5VqOnKimX36ivz6snORfzvXoOMVP6mHW1XVYfr2cYvb+gZbIcQJBANE8Stm5UFrArTQN+XsTTC4yo5NgAbuWnG5xqkRuSTuEqbw+wtLifjqa6mL67k6etCqBaM2upJCIlDqsaSsmgdsCQQDBjHIY1C5xkBI939X/fDbDpwqVAIsIWy33BOShHDxnVXvXStSxhRQkVaDgwmjaOlY16evBrSoDY9uYKKpKRAspAkEAzFqeoFcl6/0TLSwY5ePLG7PJnz69coF+9z98lKlCTSccwAZsMZuUvZhgI5wA9Dh8rqcFvR09DQzX+RY7ATHy0QJAZl6JTnaTZf9ElrNYNXwWXx9vqmWSI8ZOJnPRFSGhFSqSiMmMe6QehiVAJQDOgnYOeQ+TYWncadScJfuELimVGQJAX4E0KUf4uWq68Y0qSqu9UhZT8IYGl4gzMMp4Hz8AK74VWGSg6uJMWnDF/Iyk9YmMBisveS9+KqJN8qNIaf/fkg==";

    public static  void demo() throws Exception {
        //密码明文，也就是数据库的密码
        String plainText = "hiway";
        plainText = "adminsystem";
        System.out.printf(ConfigTools.encrypt(PRIVATE_KEY_STRING, plainText));
    }
    public static void main(String []arges)throws Exception{
        demo();
    }
    }