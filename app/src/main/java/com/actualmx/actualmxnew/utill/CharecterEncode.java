package com.actualmx.actualmxnew.utill;

/**
 * Created by Office on 18-09-2015.
 */
public class CharecterEncode {

    public static String decodeChar(String title) {

        title = title.replace("Â", "'");
        title = title.replace("&#039;", "'");
        title = title.replace("&#40;", "(");
        title = title.replace("&#41;", ")");
        title = title.replace("&#63", "?");
        title = title.replace("&#34;", "\"");
        title = title.replace("Ã", "");
        title = title.replace("¢", "");
        title = title.replace("â", "");
        title = title.replace("", "");
        title = title.replace("¬t", "");
        title = title.replace("¬", "");
        title = title.replace("¬s", "");
        title = title.replace("â¦", "...");
        title = title.replace("€", "");
        title = title.replace("™", "");
        title = title.replace("\\", "");
        title = title.replace("&#8217;", "'");
        title = title.replace("&#8220;", "\"");
        title = title.replace("&quot;", "\"");

        title = title.replace("&#8221;", "\"");
        title = title.replace("&#8211;", "-");
        title = title.replace("&nbsp;", "	");
        title = title.replace("&iexcl;", "i");
        char c =191;
        title =title.replace("&iquest;",c+"");
        char aa= 201;
        title = title.replace("&Eacute;",aa+"");
        char acute = 193;
        title = title.replace("&Aacute;",acute+"");
        char acute1 = 233;
        title = title.replace("&eacute;",acute1+"");
        char acute11 = 237;
        title = title.replace("&iacute;",acute11+"");

        return title;

    }
}
