package com.serli.chell.framework.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import javax.portlet.ResourceResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public final class WebUtils {

    private static final int BUFFER_SIZE = 4096;
    private static final String CACHE_CONTROL_KEY = "Cache-Control";
    private static final String CACHE_VALUE1 = "no-store, no-cache, must-revalidate";
    private static final String CACHE_VALUE2 = "post-check=0, pre-check=0";
    private static final String EXPIRES_KEY = "Expires";
    private static final String EXPIRES_DATE = initializeExpiresDate();
    private static final String PRAGMA_KEY = "Pragma";
    private static final String PRAGMA_VALUE = "no-cache";
    private static final String TIME_ZONE = "GMT";

    public static String readRequestBody(HttpServletRequest request) throws IOException {
        char[] buffer = new char[BUFFER_SIZE];
        StringBuilder b = new StringBuilder(BUFFER_SIZE);
        BufferedReader br = request.getReader();
        try {
            int length = br.read(buffer);
            while (length > 0) {
                b.append(buffer, 0, length);
                length = br.read(buffer);
            }
        } finally {
            br.close();
        }
        return b.toString();
    }

    public static String readRequestBody(HttpServletRequest request, String charsetEncoding) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        StringBuilder b = new StringBuilder(BUFFER_SIZE);
        ServletInputStream sos = request.getInputStream();
        try {
            int length = sos.read(buffer);
            while (length > 0) {
                b.append(new String(buffer, 0, length, charsetEncoding));
                length = sos.read(buffer);
            }
        } finally {
            sos.close();
        }
        return b.toString();
    }

    public static String readInputAsString(InputStream is, String charsetEncoding) throws IOException {
        try {
            InputStreamReader isReader = new InputStreamReader(is, charsetEncoding);
            BufferedReader buffer = new BufferedReader(isReader);
            StringWriter sw = new StringWriter();
            String line;
            while ((line = buffer.readLine()) != null) {
                sw.write(line);
            }
            return sw.toString();
        } finally {
            is.close();
        }
    }

    public static byte[] readInputAsByte(InputStream is) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copyStream(is, out);
        return out.toByteArray();
    }

    public static long copyStream(InputStream is, OutputStream os) throws IOException {
        long streamLength = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        try {
            int byteRead = is.read(buffer);
            while (byteRead > 0) {
                streamLength += byteRead;
                os.write(buffer, 0, byteRead);
                byteRead = is.read(buffer);
            }
        } finally {
            is.close();
            os.close();
        }
        return streamLength;
    }

    public static void sendFile(HttpServletResponse response, File file, String contentType) throws IOException {
        if (file != null) {
            int length = (int) file.length();
            if (length > 0) {
                response.setContentLength(length);
                if (contentType != null) {
                    response.setContentType(contentType);
                }
                response.setStatus(HttpServletResponse.SC_OK);
                ServletOutputStream out = response.getOutputStream();
                InputStream in = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);
                copyStream(in, out);
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    public static void sendContent(HttpServletResponse response, int status, String content, String contentType, String charsetEncoding) throws IOException {
        response.setStatus(status);
        if (contentType != null) {
            response.setContentType(contentType);
        }
        if (content == null || content.length() == 0) {
            response.setContentLength(0);
        } else {
            byte[] data;
            if (charsetEncoding != null) {
                response.setCharacterEncoding(charsetEncoding);
                data = content.getBytes(charsetEncoding);
            } else {
                data = content.getBytes();
            }

            response.setContentLength(data.length);
            ServletOutputStream sos = response.getOutputStream();
            try {
                sos.write(data);
            } finally {
                sos.close();
            }
        }
    }

    public static void sendContent(ResourceResponse response, String content, String contentType, String charsetEncoding) throws IOException {
        if (contentType != null) {
            response.setContentType(contentType);
        }
        if (content == null || content.length() == 0) {
            response.setContentLength(0);
        } else {
            byte[] data;
            if (charsetEncoding != null) {
                response.setCharacterEncoding(charsetEncoding);
                data = content.getBytes(charsetEncoding);
            } else {
                data = content.getBytes();
            }

            response.setContentLength(data.length);
            OutputStream sos = response.getPortletOutputStream();
            try {
                sos.write(data);
            } finally {
                sos.close();
            }
        }
    }

    public static void disableCaching(final HttpServletResponse resp) {
        resp.addHeader(EXPIRES_KEY, EXPIRES_DATE);
        resp.addHeader(CACHE_CONTROL_KEY, CACHE_VALUE1);
        resp.addHeader(CACHE_CONTROL_KEY, CACHE_VALUE2);
        resp.addHeader(PRAGMA_KEY, PRAGMA_VALUE);
    }

    public static String displayInfo(HttpServletRequest request, ServletContext context) {
        StringBuilder b = new StringBuilder();
        b.append("\nrequest.getContextPath() : ").append(request.getContextPath());
        b.append("\nrequest.getPathInfo() : ").append(request.getPathInfo());
        b.append("\nrequest.getPathTranslated() : ").append(request.getPathTranslated());
        b.append("\nrequest.getQueryString() : ").append(request.getQueryString());
        b.append("\nrequest.getRemoteUser() : ").append(request.getRemoteUser());
        b.append("\nrequest.getRequestURI() : ").append(request.getRequestURI());
        b.append("\nrequest.getRequestURL() : ").append(request.getRequestURL().toString());
        b.append("\nrequest.getRequestedSessionId() : ").append(request.getRequestedSessionId());
        b.append("\nrequest.getServletPath() : ").append(request.getServletPath());
        b.append("\nrequest.getServerName() : ").append(request.getServerName());
        b.append("\nrequest.getServerPort() : ").append(request.getServerPort());
        b.append("\nrequest.getProtocol() : ").append(request.getProtocol());
        b.append("\nrequest.getRemoteAddr() : ").append(request.getRemoteAddr());
        b.append("\nrequest.getRemoteHost() : ").append(request.getRemoteHost());
        b.append("\nrequest.getRemotePort() : ").append(request.getRemotePort());
        b.append("\nrequest.getLocalName() : ").append(request.getLocalName());
        b.append("\nrequest.getLocalAddr() : ").append(request.getLocalAddr());
        b.append("\nrequest.getLocalPort() : ").append(request.getLocalPort());
        if (context != null) {
            b.append("\ncontext.getServerInfo() : ").append(context.getServerInfo());
            b.append("\ncontext.getServletContextName() : ").append(context.getServletContextName());
            b.append("\ncontext.getMinorVersion() : ").append(context.getMinorVersion());
            b.append("\ncontext.getMajorVersion() : ").append(context.getMajorVersion());
        }
        return b.toString();
    }

    private static String initializeExpiresDate() {
        DateFormat dateFormat = new SimpleDateFormat("EEE',' dd MMMM yyyy HH:mm:ss z", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));
        return dateFormat.format(new Date(799761600000L));
    }
}
