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

package com.serli.chell.framework.upload;


/**
 * @author Vincent Michaud (vincent.michaud@serli.com)
 */
public class UploadException extends Exception {
    
    private static final Object[] EMPTY = new Object[0];

    private Object[] messageArguments;

    public UploadException(String messageKey) {
        this(messageKey, EMPTY);
    }

    public UploadException(String messageKey, Throwable throwable) {
        this(messageKey, throwable, EMPTY);
    }
    
    public UploadException(String messageKey, Object... messageArguments) {
        super(messageKey);
        this.messageArguments = messageArguments;
    }

    public UploadException(String messageKey, Throwable throwable, Object... messageArguments) {
        super(messageKey, throwable);
        this.messageArguments = messageArguments;
    }

    public Object[] getMessageArguments() {
        return messageArguments;
    }
}
