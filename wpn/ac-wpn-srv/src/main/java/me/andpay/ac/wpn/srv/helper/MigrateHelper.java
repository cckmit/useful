package me.andpay.ac.wpn.srv.helper;

import me.andpay.ti.vault.api.TokenResponse;
import me.andpay.ti.vault.client.SimpleTokenClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by QUYONG on 17/12/21.
 */
public class MigrateHelper {

    @Autowired
    @Qualifier("mobileTokenClient")
    private SimpleTokenClient simpleTokenClient;

    /**
     * 还原
     *
     * @param userName
     * @return
     */
    public String convertOutUserName(String userName) {
        if (userName == null) {
            return null;
        }

        if (userName.length() == 11) {
            return userName;
        }

        return simpleTokenClient.deExternalToken(userName);
    }

    /**
     * 脱敏
     *
     * @param userName
     * @return
     */
    public String convertInUserName(String userName) {
        if (userName == null) {
            return null;
        }

        if (userName.length() != 11) {
            return userName;
        }

        TokenResponse tokenResponse = simpleTokenClient.token(userName);
        return tokenResponse.getExternalToken();
    }
}
