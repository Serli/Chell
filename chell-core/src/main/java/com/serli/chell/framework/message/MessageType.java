
package com.serli.chell.framework.message;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public enum MessageType {
    INFO("portlet-msg-info"),
    ERROR("portlet-msg-error"),
    STATUS("portlet-msg-status"),
    ALERT("portlet-msg-alert"),
    SUCCESS("portlet-msg-success");

    private String cssClass;

    private MessageType(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return cssClass;
    }

    public String buildMessage(String message) {
        StringBuilder b = new StringBuilder();
        b.append("<div class=\"");
        b.append(cssClass).append("\">");
        b.append(message).append("</div>");
        return b.toString();
    }

    public static MessageType find(String name) {
        for (MessageType mt : values()) {
            if (mt.name().equalsIgnoreCase(name)) {
                return mt;
            }
        }
        return null;
    }
}
