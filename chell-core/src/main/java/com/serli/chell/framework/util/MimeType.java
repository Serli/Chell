/*
 *  Copyright 2011-2012 SERLI (www.serli.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package com.serli.chell.framework.util;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class MimeType {

    public static final String APPLICATION_ANDREW_INSET = "application/andrew-inset";
    public static final String APPLICATION_ANNODEX = "application/annodex";
    public static final String APPLICATION_DOWNLOAD = "application/download";
    public static final String APPLICATION_JAVA = "application/java";
    public static final String APPLICATION_JAVA_ARCHIVE = "application/java-archive";
    public static final String APPLICATION_DSPTYPE = "application/dsptype";
    public static final String APPLICATION_HTA = "application/hta";
    public static final String APPLICATION_JAVASCRIPT = "application/javascript";
    public static final String APPLICATION_MAC_BINHEX40 = "application/mac-binhex40";
    public static final String APPLICATION_MATHEMATICA = "application/mathematica";
    public static final String APPLICATION_MATHML = "application/mathml+xml";
    public static final String APPLICATION_MSACCESS = "application/msaccess";
    public static final String APPLICATION_MSWORD = "application/msword";
    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
    public static final String APPLICATION_ODA = "application/oda";
    public static final String APPLICATION_OGG = "application/ogg";
    public static final String APPLICATION_PDF = "application/pdf";
    public static final String APPLICATION_PGP_KEYS = "application/pgp-keys";
    public static final String APPLICATION_PGP_SIGNATURE = "application/pgp-signature";
    public static final String APPLICATION_PICS_RULES = "application/pics-rules";
    public static final String APPLICATION_POSTSCRIPT = "application/postscript";
    public static final String APPLICATION_RAR = "application/rar";
    public static final String APPLICATION_RDF = "application/rdf+xml";
    public static final String APPLICATION_RSS = "application/rss+xml";
    public static final String APPLICATION_RTF = "application/rtf";
    public static final String APPLICATION_ANDROID_PACKAGE = "application/vnd.android.package-archive";
    public static final String APPLICATION_CINDERELLA = "application/vnd.cinderella";
    public static final String APPLICATION_XUL = "application/vnd.mozilla.xul+xml";
    public static final String APPLICATION_MSEXCEL = "application/vnd.ms-excel";
    public static final String APPLICATION_MSPOWERPOINT = "application/vnd.ms-powerpoint";
    public static final String APPLICATION_MSPKI = "application/vnd.ms-pki.stl";
    public static final String APPLICATION_OPENDOCUMENT_CHART = "application/vnd.oasis.opendocument.chart";
    public static final String APPLICATION_OPENDOCUMENT_DATABASE = "application/vnd.oasis.opendocument.database";
    public static final String APPLICATION_OPENDOCUMENT_FORMULA = "application/vnd.oasis.opendocument.formula";
    public static final String APPLICATION_OPENDOCUMENT_GRAPHICS = "application/vnd.oasis.opendocument.graphics";
    public static final String APPLICATION_OPENDOCUMENT_GRAPHICS_TEMPLATE = "application/vnd.oasis.opendocument.graphics-template";
    public static final String APPLICATION_OPENDOCUMENT_IMAGE = "application/vnd.oasis.opendocument.image";
    public static final String APPLICATION_OPENDOCUMENT_PRESENTATION = "application/vnd.oasis.opendocument.presentation";
    public static final String APPLICATION_OPENDOCUMENT_PRESENTATION_TEMPLATE = "application/vnd.oasis.opendocument.presentation-template";
    public static final String APPLICATION_OPENDOCUMENT_SPREADSHEET = "application/vnd.oasis.opendocument.spreadsheet";
    public static final String APPLICATION_OPENDOCUMENT_SPREADSHEET_TEMPATE = "application/vnd.oasis.opendocument.spreadsheet-template";
    public static final String APPLICATION_OPENDOCUMENT_TEXT = "application/vnd.oasis.opendocument.text";
    public static final String APPLICATION_OPENDOCUMENT_TEXT_MASTER = "application/vnd.oasis.opendocument.text-master";
    public static final String APPLICATION_OPENDOCUMENT_TEXT_TEMPLATE = "application/vnd.oasis.opendocument.text-template";
    public static final String APPLICATION_OPENDOCUMENT_TEXT_WEB = "application/vnd.oasis.opendocument.text-web";
    public static final String APPLICATION_OFFICEDOCUMENT_DOCUMENT = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
    public static final String APPLICATION_OFFICEDOCUMENT_DOCUMENT_TEMPLATE = "application/vnd.openxmlformats-officedocument.wordprocessingml.template";
    public static final String APPLICATION_OFFICEDOCUMENT_PRESENTATION = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    public static final String APPLICATION_OFFICEDOCUMENT_PRESENTATION_TEMPLATE = "application/vnd.openxmlformats-officedocument.presentationml.template";
    public static final String APPLICATION_OFFICEDOCUMENT_PRESENTATION_SLIDESHOW = "application/vnd.openxmlformats-officedocument.presentationml.slideshow";
    public static final String APPLICATION_OFFICEDOCUMENT_SPREADSHEET = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String APPLICATION_OFFICEDOCUMENT_SPREADSHEET_TEMPLATE = "application/vnd.openxmlformats-officedocument.spreadsheetml.template";
    public static final String APPLICATION_RIM_COD = "application/vnd.rim.cod";
    public static final String APPLICATION_SMAF = "application/vnd.smaf";
    public static final String APPLICATION_SUN_CALC = "application/vnd.sun.xml.calc";
    public static final String APPLICATION_SUN_CALC_TEMPLATE = "application/vnd.sun.xml.calc.template";
    public static final String APPLICATION_SUN_DRAW = "application/vnd.sun.xml.draw";
    public static final String APPLICATION_SUN_DRAW_TEMPLATE = "application/vnd.sun.xml.draw.template";
    public static final String APPLICATION_SUN_IMPRESS = "application/vnd.sun.xml.impress";
    public static final String APPLICATION_SUN_IMPRESS_TEMPLATE = "application/vnd.sun.xml.impress.template";
    public static final String APPLICATION_SUN_MATH = "application/vnd.sun.xml.math";
    public static final String APPLICATION_SUN_WRITER = "application/vnd.sun.xml.writer";
    public static final String APPLICATION_SUN_WRITER_GLOBAL = "application/vnd.sun.xml.writer.global";
    public static final String APPLICATION_SUN_WRITER_TEMPLATE = "application/vnd.sun.xml.writer.template";
    public static final String APPLICATION_WAP_WML = "application/vnd.wap.wmlc";
    public static final String APPLICATION_WAP_WMLSCRIPT = "application/vnd.wap.wmlscriptc";
    public static final String APPLICATION_VOICE_XML = "application/voicexml+xml";
    public static final String APPLICATION_WSPOLICY = "application/wspolicy+xml";
    public static final String APPLICATION_ABIWORD = "application/x-abiword";
    public static final String APPLICATION_AIM = "application/x-aim";
    public static final String APPLICATION_APPLE_DISKIMAGE = "application/x-apple-diskimage";
    public static final String APPLICATION_BCPIO = "application/x-bcpio";
    public static final String APPLICATION_BITTORRENT = "application/x-bittorrent";
    public static final String APPLICATION_CDF = "application/x-cdf";
    public static final String APPLICATION_CDLINK = "application/x-cdlink";
    public static final String APPLICATION_COMPRESS = "application/x-compress";
    public static final String APPLICATION_CPIO = "application/x-cpio";
    public static final String APPLICATION_CSH = "application/x-csh";
    public static final String APPLICATION_DEBIAN_PACKAGE = "application/x-debian-package";
    public static final String APPLICATION_DIRECTOR = "application/x-director";
    public static final String APPLICATION_DMS = "application/x-dms";
    public static final String APPLICATION_DVI = "application/x-dvi";
    public static final String APPLICATION_FLAC = "application/x-flac";
    public static final String APPLICATION_FONT = "application/x-font";
    public static final String APPLICATION_GTAR = "application/x-gtar";
    public static final String APPLICATION_GZIP = "application/x-gzip";
    public static final String APPLICATION_HDF = "application/x-hdf";
    public static final String APPLICATION_ICA = "application/x-ica";
    public static final String APPLICATION_INTERNET_SIGNUP = "application/x-internet-signup";
    public static final String APPLICATION_IPHONE = "application/x-iphone";
    public static final String APPLICATION_ISO9660_IMAGE = "application/x-iso9660-image";
    public static final String APPLICATION_JAVA_JNLP = "application/x-java-jnlp-file";
    public static final String APPLICATION_JMOL = "application/x-jmol";
    public static final String APPLICATION_KCHART = "application/x-kchart";
    public static final String APPLICATION_KILLUSTRATOR = "application/x-killustrator";
    public static final String APPLICATION_KOAN = "application/x-koan";
    public static final String APPLICATION_KPRESENTER = "application/x-kpresenter";
    public static final String APPLICATION_KSPREAD = "application/x-kspread";
    public static final String APPLICATION_KWORD = "application/x-kword";
    public static final String APPLICATION_LATEX = "application/x-latex";
    public static final String APPLICATION_LHA = "application/x-lha";
    public static final String APPLICATION_LZH = "application/x-lzh";
    public static final String APPLICATION_LZX = "application/x-lzx";
    public static final String APPLICATION_MAKER = "application/x-maker";
    public static final String APPLICATION_MIF = "application/x-mif";
    public static final String APPLICATION_MSWMD = "application/x-ms-wmd";
    public static final String APPLICATION_MSWMZ = "application/x-ms-wmz";
    public static final String APPLICATION_MSI = "application/x-msi";
    public static final String APPLICATION_NETCDF = "application/x-netcdf";
    public static final String APPLICATION_NS_PROXY_AUTOCONFIG = "application/x-ns-proxy-autoconfig";
    public static final String APPLICATION_NWC = "application/x-nwc";
    public static final String APPLICATION_OBJECT = "application/x-object";
    public static final String APPLICATION_OZ_APPLICATION = "application/x-oz-application";
    public static final String APPLICATION_PKCS12 = "application/x-pkcs12";
    public static final String APPLICATION_PKCS7_CERTREQRESP = "application/x-pkcs7-certreqresp";
    public static final String APPLICATION_PKCS7_CRL = "application/x-pkcs7-crl";
    public static final String APPLICATION_QUICKTIME_PLAYER = "application/x-quicktimeplayer";
    public static final String APPLICATION_SH = "application/x-sh";
    public static final String APPLICATION_SHAR = "application/x-shar";
    public static final String APPLICATION_SHOCKWAVE_FLASH = "application/x-shockwave-flash";
    public static final String APPLICATION_STUFFIT = "application/x-stuffit";
    public static final String APPLICATION_SV4CPIO = "application/x-sv4cpio";
    public static final String APPLICATION_SV4CRC = "application/x-sv4crc";
    public static final String APPLICATION_TAR = "application/x-tar";
    public static final String APPLICATION_TCL = "application/x-tcl";
    public static final String APPLICATION_TEX = "application/x-tex";
    public static final String APPLICATION_TEXINFO = "application/x-texinfo";
    public static final String APPLICATION_TROFF = "application/x-troff";
    public static final String APPLICATION_TROFF_MAN = "application/x-troff-man";
    public static final String APPLICATION_TROFF_ME = "application/x-troff-me";
    public static final String APPLICATION_USTAR = "application/x-ustar";
    public static final String APPLICATION_VISIO = "application/x-visio";
    public static final String APPLICATION_WAIS_SOURCE = "application/x-wais-source";
    public static final String APPLICATION_WINGZ = "application/x-wingz";
    public static final String APPLICATION_WEBARCHIVE = "application/x-webarchive";
    public static final String APPLICATION_X509_CA_CERT = "application/x-x509-ca-cert";
    public static final String APPLICATION_X509_USER_CERT = "application/x-x509-user-cert";
    public static final String APPLICATION_XCF = "application/x-xcf";
    public static final String APPLICATION_XFIG = "application/x-xfig";
    public static final String APPLICATION_XHTML = "application/xhtml+xml";
    public static final String APPLICATION_XML = "application/xml";
    public static final String APPLICATION_XML_DTD = "application/xml-dtd";
    public static final String APPLICATION_XSLT = "application/xslt+xml";
    public static final String APPLICATION_XSPF = "application/xspf+xml";
    public static final String APPLICATION_ZIP = "application/zip";
    public static final String AUDIO_AMR = "audio/amr";
    public static final String AUDIO_ANNODEX = "audio/annodex";
    public static final String AUDIO_BASIC = "audio/basic";
    public static final String AUDIO_FLAC = "audio/flac";
    public static final String AUDIO_MIDI = "audio/midi";
    public static final String AUDIO_MOBILE_XMF = "audio/mobile-xmf";
    public static final String AUDIO_MPEG = "audio/mpeg";
    public static final String AUDIO_MPEGURL = "audio/mpegurl";
    public static final String AUDIO_OGG = "audio/ogg";
    public static final String AUDIO_PRS_SID = "audio/prs.sid";
    public static final String AUDIO_AIFF = "audio/x-aiff";
    public static final String AUDIO_GSM = "audio/x-gsm";
    public static final String AUDIO_XMIDI = "audio/x-midi";
    public static final String AUDIO_XMPEG = "audio/x-mpeg";
    public static final String AUDIO_MS_WMA = "audio/x-ms-wma";
    public static final String AUDIO_MS_WAX = "audio/x-ms-wax";
    public static final String AUDIO_REALAUDIO = "audio/x-pn-realaudio";
    public static final String AUDIO_SCPLS = "audio/x-scpls";
    public static final String AUDIO_SD2 = "audio/x-sd2";
    public static final String AUDIO_WAV = "audio/x-wav";
    public static final String IMAGE_BMP = "image/bmp";
    public static final String IMAGE_GIF = "image/gif";
    public static final String IMAGE_ICO = "image/ico";
    public static final String IMAGE_IEF = "image/ief";
    public static final String IMAGE_JPEG = "image/jpeg";
    public static final String IMAGE_PCX = "image/pcx";
    public static final String IMAGE_PICT = "image/pict";
    public static final String IMAGE_PNG = "image/png";
    public static final String IMAGE_SVG = "image/svg+xml";
    public static final String IMAGE_TIFF = "image/tiff";
    public static final String IMAGE_DJVU = "image/vnd.djvu";
    public static final String IMAGE_WAP_WBMP = "image/vnd.wap.wbmp";
    public static final String IMAGE_CMU_RASTER = "image/x-cmu-raster";
    public static final String IMAGE_CORELDRAW = "image/x-coreldraw";
    public static final String IMAGE_CORELDRAW_PATTERN = "image/x-coreldrawpattern";
    public static final String IMAGE_CORELDRAW_TEMPLATE = "image/x-coreldrawtemplate";
    public static final String IMAGE_CORELPHOTO_PAINT = "image/x-corelphotopaint";
    public static final String IMAGE_JG = "image/x-jg";
    public static final String IMAGE_JNG = "image/x-jng";
    public static final String IMAGE_MACPAINT = "image/x-macpaint";
    public static final String IMAGE_PHOTOSHOP = "image/x-photoshop";
    public static final String IMAGE_PORTABLE_ANYMAP = "image/x-portable-anymap";
    public static final String IMAGE_PORTABLE_BITMAP = "image/x-portable-bitmap";
    public static final String IMAGE_PORTABLE_GRAYMAP = "image/x-portable-graymap";
    public static final String IMAGE_PORTABLE_PIXMAP = "image/x-portable-pixmap";
    public static final String IMAGE_QUICKTIME = "image/x-quicktime";
    public static final String IMAGE_RGB = "image/x-rgb";
    public static final String IMAGE_XBITMAP = "image/x-xbitmap";
    public static final String IMAGE_XPIXMAP = "image/x-xpixmap";
    public static final String IMAGE_XWINDOWDUMP = "image/x-xwindowdump";
    public static final String MODEL_IGES = "model/iges";
    public static final String MODEL_MESH = "model/mesh";
    public static final String TEXT_CALENDAR = "text/calendar";
    public static final String TEXT_COMMA_SEPARATED_VALUES = "text/comma-separated-values";
    public static final String TEXT_CSS = "text/css";
    public static final String TEXT_HTML = "text/html";
    public static final String TEXT_H323 = "text/h323";
    public static final String TEXT_IULS = "text/iuls";
    public static final String TEXT_MATHML = "text/mathml";
    public static final String TEXT_PLAIN = "text/plain";
    public static final String TEXT_RICHTEXT = "text/richtext";
    public static final String TEXT_TEXMACS = "text/texmacs";
    public static final String TEXT_TEXT = "text/text";
    public static final String TEXT_TAB_SEPARATED_VALUES = "text/tab-separated-values";
    public static final String TEXT_SUN_APP_DESCRIPTOR = "text/vnd.sun.j2me.app-descriptor";
    public static final String TEXT_WAP_WML = "text/vnd.wap.wml";
    public static final String TEXT_WAP_XMLSCRIPT = "text/vnd.wap.wmlscript";
    public static final String TEXT_BIBTEX = "text/x-bibtex";
    public static final String TEXT_BOO = "text/x-boo";
    public static final String TEXT_CPP_HDR = "text/x-c++hdr";
    public static final String TEXT_CPP_SRC = "text/x-c++src";
    public static final String TEXT_CHDR = "text/x-chdr";
    public static final String TEXT_COMPONENT = "text/x-component";
    public static final String TEXT_CSRC = "text/x-csrc";
    public static final String TEXT_DSRC = "text/x-dsrc";
    public static final String TEXT_HASKELL = "text/x-haskell";
    public static final String TEXT_LITERATE_HASKELL = "text/x-literate-haskell";
    public static final String TEXT_MOC = "text/x-moc";
    public static final String TEXT_PASCAL = "text/x-pascal";
    public static final String TEXT_PCS_GCD = "text/x-pcs-gcd";
    public static final String TEXT_SETEXT = "text/x-setext";
    public static final String TEXT_TEX = "text/x-tex";
    public static final String TEXT_VCALENDAR = "text/x-vcalendar";
    public static final String TEXT_VCARD = "text/x-vcard";
    public static final String TEXT_XML = "text/xml";
    public static final String VIDEO_3GPP = "video/3gpp";
    public static final String VIDEO_ANNODEX = "video/annodex";
    public static final String VIDEO_DL = "video/dl";
    public static final String VIDEO_DV = "video/dv";
    public static final String VIDEO_FLI = "video/fli";
    public static final String VIDEO_M4V = "video/m4v";
    public static final String VIDEO_MP4 = "video/mp4";
    public static final String VIDEO_MPEG = "video/mpeg";
    public static final String VIDEO_MPEG2 = "video/mpeg2";
    public static final String VIDEO_OGG = "video/ogg";
    public static final String VIDEO_QUICKTIME = "video/quicktime";
    public static final String VIDEO_MPEGURL = "video/vnd.mpegurl";
    public static final String VIDEO_LA_ASF = "video/x-la-asf";
    public static final String VIDEO_MNG = "video/x-mng";
    public static final String VIDEO_MS_ASF = "video/x-ms-asf";
    public static final String VIDEO_MS_WM = "video/x-ms-wm";
    public static final String VIDEO_MS_WMV = "video/x-ms-wmv";
    public static final String VIDEO_MS_WMX = "video/x-ms-wmx";
    public static final String VIDEO_MS_WVX = "video/x-ms-wvx";
    public static final String VIDEO_MS_VIDEO = "video/x-msvideo";
    public static final String VIDEO_RAD_SCREENPLAY = "video/x-rad-screenplay";
    public static final String VIDEO_SGI_MOVIE = "video/x-sgi-movie";
    public static final String CONFERENCE_COOLTALK = "x-conference/x-cooltalk";
    public static final String EPOC_SISX_APP = "x-epoc/x-sisx-app";
    public static final String WORLD_VRML = "x-world/x-vrml";


    private static final MimeType INSTANCE = new MimeType();

    private Map<String, String> extToMt = new HashMap<String, String>();
    private Map<String, String[]> mtToExt = new HashMap<String, String[]>();

    public static String getMimeTypeByExtension(String extension) {
        extension = extension.toLowerCase();
        String result = INSTANCE.extToMt.get(extension);
        if (result == null) {
            return APPLICATION_OCTET_STREAM;
        }
        return result;
    }

    public static String getMimeType(String filename) {
        int index = filename.lastIndexOf(".");
        if (index != -1) {
            String extension = filename.substring(index + 1);
            return getMimeTypeByExtension(extension);
        }
        return APPLICATION_OCTET_STREAM;
    }

    public static boolean canHaveExtensionForMimeType(String mimeType, String extension) {
        mimeType = mimeType.toLowerCase();
        extension = mimeType.toLowerCase();
        String[] listExtensions = INSTANCE.mtToExt.get(mimeType);
        if (listExtensions != null) {
            for (String ext : listExtensions) {
                if (ext.equals(extension)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String[] getExtensionsForMimeType(String mimeType) {
        mimeType = mimeType.toLowerCase();
        return INSTANCE.mtToExt.get(mimeType);
    }

    private MimeType() {
        addMapping(APPLICATION_ANDREW_INSET, "ez");
        addMapping(APPLICATION_ANNODEX, "anx");
        addMapping(APPLICATION_JAVA, "class");
        addMapping(APPLICATION_JAVA_ARCHIVE, "jar");
        addMapping(APPLICATION_DSPTYPE, "tsp");
        addMapping(APPLICATION_HTA, "hta");
        addMapping(APPLICATION_JAVASCRIPT, "js");
        addMapping(APPLICATION_MAC_BINHEX40, "hqx");
        addMapping(APPLICATION_MATHEMATICA, "nb");
        addMapping(APPLICATION_MATHML, "mathml");
        addMapping(APPLICATION_MSACCESS, "mdb");
        addMapping(APPLICATION_MSWORD, "doc", "dot");
        addMapping(APPLICATION_OCTET_STREAM, "bin", "exe");
        addMapping(APPLICATION_ODA, "oda");
        addMapping(APPLICATION_OGG, "ogx");
        addMapping(APPLICATION_PDF, "pdf");
        addMapping(APPLICATION_PGP_KEYS, "key");
        addMapping(APPLICATION_PGP_SIGNATURE, "pgp");
        addMapping(APPLICATION_PICS_RULES, "prf");
        addMapping(APPLICATION_POSTSCRIPT, "ai", "eps", "ps");
        addMapping(APPLICATION_RAR, "rar");
        addMapping(APPLICATION_RDF, "rdf");
        addMapping(APPLICATION_RSS, "rss");
        addMapping(APPLICATION_RTF, "rtf");
        addMapping(APPLICATION_ANDROID_PACKAGE, "apk");
        addMapping(APPLICATION_CINDERELLA, "cdy");
        addMapping(APPLICATION_XUL, "xul");
        addMapping(APPLICATION_MSEXCEL, "xls", "xlt");
        addMapping(APPLICATION_MSPOWERPOINT, "ppt", "pps", "pot");
        addMapping(APPLICATION_MSPKI, "stl");
        addMapping(APPLICATION_OPENDOCUMENT_CHART, "odc");
        addMapping(APPLICATION_OPENDOCUMENT_DATABASE, "odb");
        addMapping(APPLICATION_OPENDOCUMENT_FORMULA, "odf");
        addMapping(APPLICATION_OPENDOCUMENT_GRAPHICS, "odg");
        addMapping(APPLICATION_OPENDOCUMENT_GRAPHICS_TEMPLATE, "otg");
        addMapping(APPLICATION_OPENDOCUMENT_IMAGE, "odi");
        addMapping(APPLICATION_OPENDOCUMENT_PRESENTATION, "odp");
        addMapping(APPLICATION_OPENDOCUMENT_PRESENTATION_TEMPLATE, "otp");
        addMapping(APPLICATION_OPENDOCUMENT_SPREADSHEET, "ods");
        addMapping(APPLICATION_OPENDOCUMENT_SPREADSHEET_TEMPATE, "ots");
        addMapping(APPLICATION_OPENDOCUMENT_TEXT, "odt");
        addMapping(APPLICATION_OPENDOCUMENT_TEXT_MASTER, "odm");
        addMapping(APPLICATION_OPENDOCUMENT_TEXT_TEMPLATE, "ott");
        addMapping(APPLICATION_OPENDOCUMENT_TEXT_WEB, "oth");
        addMapping(APPLICATION_OFFICEDOCUMENT_DOCUMENT, "docx");
        addMapping(APPLICATION_OFFICEDOCUMENT_DOCUMENT_TEMPLATE, "dotx");
        addMapping(APPLICATION_OFFICEDOCUMENT_PRESENTATION, "pptx");
        addMapping(APPLICATION_OFFICEDOCUMENT_PRESENTATION_TEMPLATE, "potx");
        addMapping(APPLICATION_OFFICEDOCUMENT_PRESENTATION_SLIDESHOW, "ppsx");
        addMapping(APPLICATION_OFFICEDOCUMENT_SPREADSHEET, "xlsx");
        addMapping(APPLICATION_OFFICEDOCUMENT_SPREADSHEET_TEMPLATE, "xltx");
        addMapping(APPLICATION_RIM_COD, "cod");
        addMapping(APPLICATION_SMAF, "mmf");
        addMapping(APPLICATION_SUN_CALC, "sxc");
        addMapping(APPLICATION_SUN_CALC_TEMPLATE, "stc");
        addMapping(APPLICATION_SUN_DRAW, "sxd");
        addMapping(APPLICATION_SUN_DRAW_TEMPLATE, "std");
        addMapping(APPLICATION_SUN_IMPRESS, "sxi");
        addMapping(APPLICATION_SUN_IMPRESS_TEMPLATE, "sti");
        addMapping(APPLICATION_SUN_MATH, "sxm");
        addMapping(APPLICATION_SUN_WRITER, "sxw");
        addMapping(APPLICATION_SUN_WRITER_GLOBAL, "sxg");
        addMapping(APPLICATION_SUN_WRITER_TEMPLATE, "stw");
        addMapping(APPLICATION_WAP_WML, "wmlc");
        addMapping(APPLICATION_WAP_WMLSCRIPT, "wmlscriptc");
        addMapping(APPLICATION_VOICE_XML, "vxml");
        addMapping(APPLICATION_WSPOLICY, "wspolicy");
        addMapping(APPLICATION_ABIWORD, "abw");
        addMapping(APPLICATION_AIM, "aim");
        addMapping(APPLICATION_APPLE_DISKIMAGE, "dmg");
        addMapping(APPLICATION_BCPIO, "bcpio");
        addMapping(APPLICATION_BITTORRENT, "torrent");
        addMapping(APPLICATION_CDF, "cdf");
        addMapping(APPLICATION_CDLINK, "vcd");
        addMapping(APPLICATION_COMPRESS, "z");
        addMapping(APPLICATION_CPIO, "cpio");
        addMapping(APPLICATION_CSH, "csh");
        addMapping(APPLICATION_DEBIAN_PACKAGE, "deb", "udeb");
        addMapping(APPLICATION_DIRECTOR, "dcr", "dir", "dxr");
        addMapping(APPLICATION_DMS, "dms");
        addMapping(APPLICATION_DVI, "dvi");
        addMapping(APPLICATION_FLAC, "flac");
        addMapping(APPLICATION_FONT, "pfa", "pfb", "gsf", "pcf", "pcf.Z");
        addMapping(APPLICATION_GTAR, "gtar", "tgz", "taz");
        addMapping(APPLICATION_GZIP, "gz");
        addMapping(APPLICATION_HDF, "hdf");
        addMapping(APPLICATION_ICA, "ica");
        addMapping(APPLICATION_INTERNET_SIGNUP, "ins", "isp");
        addMapping(APPLICATION_IPHONE, "iii");
        addMapping(APPLICATION_ISO9660_IMAGE, "iso");
        addMapping(APPLICATION_JAVA_JNLP, "jnlp");
        addMapping(APPLICATION_JMOL, "jmz");
        addMapping(APPLICATION_KCHART, "chrt");
        addMapping(APPLICATION_KILLUSTRATOR, "kil");
        addMapping(APPLICATION_KOAN, "skp", "skd", "skt", "skm");
        addMapping(APPLICATION_KPRESENTER, "kpr", "kpt");
        addMapping(APPLICATION_KSPREAD, "ksp");
        addMapping(APPLICATION_KWORD, "kwd", "kwt");
        addMapping(APPLICATION_LATEX, "latex");
        addMapping(APPLICATION_LHA, "lha");
        addMapping(APPLICATION_LZH, "lzh");
        addMapping(APPLICATION_LZX, "lzx");
        addMapping(APPLICATION_MAKER, "frm", "maker", "frame", "fb", "book", "fbdoc");
        addMapping(APPLICATION_MIF, "mif");
        addMapping(APPLICATION_MSWMD, "wmd");
        addMapping(APPLICATION_MSWMZ, "wmz");
        addMapping(APPLICATION_MSI, "msi");
        addMapping(APPLICATION_NETCDF, "nc");
        addMapping(APPLICATION_NS_PROXY_AUTOCONFIG, "pac");
        addMapping(APPLICATION_NWC, "nwc");
        addMapping(APPLICATION_OBJECT, "o");
        addMapping(APPLICATION_OZ_APPLICATION, "oza");
        addMapping(APPLICATION_PKCS12, "p12");
        addMapping(APPLICATION_PKCS7_CERTREQRESP, "p7r");
        addMapping(APPLICATION_PKCS7_CRL, "crl");
        addMapping(APPLICATION_QUICKTIME_PLAYER, "qtl");
        addMapping(APPLICATION_SH, "sh");
        addMapping(APPLICATION_SHAR, "shar");
        addMapping(APPLICATION_SHOCKWAVE_FLASH, "swf");
        addMapping(APPLICATION_STUFFIT, "sit");
        addMapping(APPLICATION_SV4CPIO, "sv4cpio");
        addMapping(APPLICATION_SV4CRC, "sv4crc");
        addMapping(APPLICATION_TAR, "tar");
        addMapping(APPLICATION_TCL, "tcl");
        addMapping(APPLICATION_TEX, "tex");
        addMapping(APPLICATION_TEXINFO, "texi", "texinfo");
        addMapping(APPLICATION_TROFF, "roff", "t", "tr");
        addMapping(APPLICATION_TROFF_MAN, "man");
        addMapping(APPLICATION_TROFF_ME, "me");
        addMapping(APPLICATION_USTAR, "ustar");
        addMapping(APPLICATION_VISIO, "vsd");
        addMapping(APPLICATION_WAIS_SOURCE, "ms", "src");
        addMapping(APPLICATION_WINGZ, "wz");
        addMapping(APPLICATION_WEBARCHIVE, "webarchive");
        addMapping(APPLICATION_X509_CA_CERT, "cer");
        addMapping(APPLICATION_X509_USER_CERT, "crt");
        addMapping(APPLICATION_XCF, "xcf");
        addMapping(APPLICATION_XFIG, "fig");
        addMapping(APPLICATION_XHTML, "xht", "xhtml");
        addMapping(APPLICATION_XML, "xml", "xsl");
        addMapping(APPLICATION_XML_DTD, "dtd");
        addMapping(APPLICATION_XSLT, "xslt");
        addMapping(APPLICATION_XSPF, "xspf");
        addMapping(APPLICATION_ZIP, "zip");
        addMapping(AUDIO_AMR, "amr");
        addMapping(AUDIO_ANNODEX, "axa");
        addMapping(AUDIO_BASIC, "au", "snd", "ulw");
        addMapping(AUDIO_FLAC, "faca");
        addMapping(AUDIO_MIDI, "mid", "midi", "kar", "xmf");
        addMapping(AUDIO_MOBILE_XMF, "mxmf");
        addMapping(AUDIO_MPEG, "mp3", "m4a", "mp2", "mpga", "mpega");
        addMapping(AUDIO_MPEGURL, "m3u");
        addMapping(AUDIO_OGG, "oga", "ogg", "spx");
        addMapping(AUDIO_PRS_SID, "sid");
        addMapping(AUDIO_AIFF, "aif", "aifc", "aiff");
        addMapping(AUDIO_GSM, "gsm");
        addMapping(AUDIO_XMIDI, "smf");
        addMapping(AUDIO_XMPEG, "abs", "mp1", "mpa");
        addMapping(AUDIO_MS_WMA, "wma");
        addMapping(AUDIO_MS_WAX, "wax");
        addMapping(AUDIO_REALAUDIO, "ra", "rm", "ram");
        addMapping(AUDIO_SCPLS, "pls");
        addMapping(AUDIO_SD2, "sd2");
        addMapping(AUDIO_WAV, "wav");
        addMapping(IMAGE_BMP, "bmp", "dib");
        addMapping(IMAGE_GIF, "gif");
        addMapping(IMAGE_ICO, "cur", "ico");
        addMapping(IMAGE_IEF, "ief");
        addMapping(IMAGE_JPEG, "jpeg", "jpg", "jpe");
        addMapping(IMAGE_PCX, "pcx");
        addMapping(IMAGE_PICT, "pct", "pic", "pict");
        addMapping(IMAGE_PNG, "png");
        addMapping(IMAGE_SVG, "svg", "svgz");
        addMapping(IMAGE_TIFF, "tif", "tiff");
        addMapping(IMAGE_DJVU, "djvu", "djv");
        addMapping(IMAGE_WAP_WBMP, "wbmp");
        addMapping(IMAGE_CMU_RASTER, "ras");
        addMapping(IMAGE_CORELDRAW, "cdr");
        addMapping(IMAGE_CORELDRAW_PATTERN, "pat");
        addMapping(IMAGE_CORELDRAW_TEMPLATE, "cdt");
        addMapping(IMAGE_CORELPHOTO_PAINT, "cpt");
        addMapping(IMAGE_JG, "art");
        addMapping(IMAGE_JNG, "jng");
        addMapping(IMAGE_MACPAINT, "mac", "pnt");
        addMapping(IMAGE_PHOTOSHOP, "psd");
        addMapping(IMAGE_PORTABLE_ANYMAP, "pnm");
        addMapping(IMAGE_PORTABLE_BITMAP, "pbm");
        addMapping(IMAGE_PORTABLE_GRAYMAP, "pgm");
        addMapping(IMAGE_PORTABLE_PIXMAP, "ppm");
        addMapping(IMAGE_QUICKTIME, "qti", "qtif");
        addMapping(IMAGE_RGB, "rgb");
        addMapping(IMAGE_XBITMAP, "xbm");
        addMapping(IMAGE_XPIXMAP, "xpm");
        addMapping(IMAGE_XWINDOWDUMP, "xwd");
        addMapping(MODEL_IGES, "igs", "iges");
        addMapping(MODEL_MESH, "msh", "mesh", "silo");
        addMapping(TEXT_CALENDAR, "ics", "icz");
        addMapping(TEXT_COMMA_SEPARATED_VALUES, "csv");
        addMapping(TEXT_CSS, "css");
        addMapping(TEXT_HTML, "body", "htm", "html");
        addMapping(TEXT_H323, "323");
        addMapping(TEXT_IULS, "uls");
        addMapping(TEXT_MATHML, "mml");
        addMapping(TEXT_PLAIN, "java", "jsf", "jspf", "txt", "asc", "text", "diff", "po");
        addMapping(TEXT_RICHTEXT, "rtx");
        addMapping(TEXT_TEXMACS, "ts");
        addMapping(TEXT_TEXT, "phps");
        addMapping(TEXT_TAB_SEPARATED_VALUES, "tsv");
        addMapping(TEXT_SUN_APP_DESCRIPTOR, "jad");
        addMapping(TEXT_WAP_WML, "wml");
        addMapping(TEXT_WAP_XMLSCRIPT, "wmls");
        addMapping(TEXT_BIBTEX, "bib");
        addMapping(TEXT_BOO, "boo");
        addMapping(TEXT_CPP_HDR, "h++", "hpp", "hxx", "hh");
        addMapping(TEXT_CPP_SRC, "c++", "cpp", "cxx");
        addMapping(TEXT_CHDR, "h");
        addMapping(TEXT_COMPONENT, "htc");
        addMapping(TEXT_CSRC, "c");
        addMapping(TEXT_DSRC, "d");
        addMapping(TEXT_HASKELL, "hs");
        addMapping(TEXT_LITERATE_HASKELL, "lhs");
        addMapping(TEXT_MOC, "moc");
        addMapping(TEXT_PASCAL, "p", "pas");
        addMapping(TEXT_PCS_GCD, "gcd");
        addMapping(TEXT_SETEXT, "etx");
        addMapping(TEXT_TEX, "ltx", "sty", "cls");
        addMapping(TEXT_VCALENDAR, "vcs");
        addMapping(TEXT_VCARD, "vcf");
        addMapping(TEXT_XML, "xml");
        addMapping(VIDEO_3GPP, "3gpp", "3gp", "3g2");
        addMapping(VIDEO_ANNODEX, "axv");
        addMapping(VIDEO_DL, "dl");
        addMapping(VIDEO_DV, "dv", "dif");
        addMapping(VIDEO_FLI, "fli");
        addMapping(VIDEO_M4V, "m4v");
        addMapping(VIDEO_MP4, "mp4");
        addMapping(VIDEO_MPEG, "mpe", "mpeg", "mpg", "vob");
        addMapping(VIDEO_MPEG2, "mpv2");
        addMapping(VIDEO_OGG, "ogv");
        addMapping(VIDEO_QUICKTIME, "mov", "qt");
        addMapping(VIDEO_MPEGURL, "mxu");
        addMapping(VIDEO_LA_ASF, "lsf", "lsx");
        addMapping(VIDEO_MNG, "mng");
        addMapping(VIDEO_MS_ASF, "asf", "asx");
        addMapping(VIDEO_MS_WM, "wm");
        addMapping(VIDEO_MS_WMV, "wmv");
        addMapping(VIDEO_MS_WMX, "wmx");
        addMapping(VIDEO_MS_WVX, "wvx");
        addMapping(VIDEO_MS_VIDEO, "avi");
        addMapping(VIDEO_RAD_SCREENPLAY, "avx");
        addMapping(VIDEO_SGI_MOVIE, "movie");
        addMapping(CONFERENCE_COOLTALK, "ice");
        addMapping(EPOC_SISX_APP, "sisx");
        addMapping(WORLD_VRML, "wrl");
    }

    private void addMapping(String mimeType, String... extensions) {
        for (String extension : extensions) {
            if (!extToMt.containsKey(extension)) {
                extToMt.put(extension, mimeType);
            }
        }
        if (!mtToExt.containsKey(mimeType)) {
            mtToExt.put(mimeType, extensions);
        }
    }
}
