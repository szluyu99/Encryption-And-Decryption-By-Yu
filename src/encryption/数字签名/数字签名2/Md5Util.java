package encryption.数字签名.数字签名2;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Md5 工具类
 *
 * @author: Andy
 * @time: 2019/5/10 17:27
 * @since
 */
public class Md5Util {
//    private static final Logger LOGGER = LoggerFactory.getLogger(Md5Util.class);

    /**
     * 简单 MD5_2 加密
     *
     * @param str
     * @return: {@link String }
     * @author: Andy
     * @time: 2019/4/29 15:28
     * @since
     */
    public static String encrypt(String str) {
        try {
            return DigestUtils.md5Hex(str).toUpperCase();
        } catch (Exception e) {
//            LOGGER.error("MD5加密失败！", e);
        }
        return null;
    }
}

