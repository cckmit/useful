package me.andpay.ac.wpn.web.session;

import me.andpay.ac.web.lib.session.SessionAuthChecker;

import javax.servlet.http.HttpSession;

/**
 * Created by cen on 2017/2/22.
 */
public class WpnSessionAuthChecker implements SessionAuthChecker {
    @Override
    public boolean checkAuth(HttpSession httpSession) {
        return true;
    }
}
