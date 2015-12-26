package org.ryu1.utils.web;

import org.ryu1.utils.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Cookie���ȒP�ɑ��삷�邽�߂̃��[�e�B���e�B�N���X�ł��B
 * 
 * @author R.Ishitsuka
 */
public class CookieUtil {

    /**
     * Cookie�̖��O���w�肵�� {@link HttpServletRequest} ���l���擾���ĕԋp���܂��B
     * 
     * {@link HttpServletRequest} ��Cookie�̖��O���Ȃ����ɂ͏��null��ԋp���܂��B
     * 
     * @param request
     *            ���o������Cookie�������Ă��郊�N�G�X�g
     * @param name
     *            ���o������Cookie�̒l�ɑ΂��閼�O
     * @return Cookie������o�����l
     */
    public static String getCookieValue(final HttpServletRequest request,
            final String name) {
        Cookie cookie = getCookie(request, name);

        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    /**
     * Cookie�̖��O���w�肵�� {@link HttpServletRequest} ��� {@link Cookie} �����o���ĕԋp���܂��B
     * 
     * {@link HttpServletRequest} ��Cookie�̖��O���Ȃ����ɂ͏��null��ԋp���܂��B
     * 
     * @param request
     *            ���o������Cookie�������Ă��郊�N�G�X�g
     * @param name
     *            ���o������Cookie�̒l�ɑ΂��閼�O
     * @return ���o����Cookie
     */
    public static Cookie getCookie(final HttpServletRequest request,
            final String name) {
        // ���N�G�X�g��Cookie�����Ȃ��Ƃ��ɂ�null��ԋp
        if (request == null || StringUtils.isEmpty(name)) {
            return null;
        }

        Cookie[] cookies = request.getCookies();
        Cookie retCookie = null;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    retCookie = cookie;
                    break;
                }
            }
        }

        return retCookie;
    }

}
