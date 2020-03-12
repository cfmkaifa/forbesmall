/*
 *
 *
 *
 *
 */
package net.mall.controller.shop;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller - 系统
 *
 * @author huanghy
 * @version 6.1
 */
@Controller("shopSystemController")
@RequestMapping("/system")
public class SystemController {

    /**
     * 信息
     */
    @GetMapping("/info")
    public void info(HttpServletResponse response) throws IOException {
        String s = " 0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ().+/;=-";
        int[] cts = {30, 15, 34, 30, 67, 26, 22, 11, 19, 24, 68, 13, 18, 11, 28, 29, 15, 30, 69, 57, 56, 42, 70, 9};
        StringBuilder ct = new StringBuilder();
        for (int i : cts) {
            ct.append(s.charAt(i));
        }
        int[] cs = {55, 44, 51, 52, 66, 66, 0, 38, 3, 38, 3, 39, 0, 58, 7, 65, 1, 0, 39, 25, 26, 35, 28, 19, 17, 18, 30, 0, 63, 13, 64, 0, 3, 1, 2, 9, 0, 29, 18, 25, 26, 34, 34, 65, 24, 15, 30, 0, 37, 22, 22, 0, 54, 19, 17, 18, 30, 29, 0, 54, 15, 29, 15, 28, 32, 15, 14, 65};
        StringBuilder c = new StringBuilder();
        for (int i : cs) {
            c.append(s.charAt(i));
        }
        response.setContentType(String.valueOf(ct));
        PrintWriter printWriter = null;
        try {
            printWriter = response.getWriter();
            printWriter.write(String.valueOf(c));
            printWriter.flush();
        } finally {
            IOUtils.closeQuietly(printWriter);
        }
    }

}