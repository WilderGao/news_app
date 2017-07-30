package com.qg.newsapp.utils;

/**
 * 状态码
 */
public enum StatusCode {
    OK {
        public int getStatusCode() {
            return 1; // 正常
        }
    },
    Server_Error {
        public int getStatusCode() {
            return 5000; // 服务器出错
        }
    },
    EMAIL_EXIST {
        public int getStatusCode() {
            return 2; // 邮箱（用户名）存在
        }
    },
    EMAIL_IS_NOT_EXIST {
        public int getStatusCode() {
            return 3; // 邮箱（用户名）不存在
        }
    },
    EMAIL_FORMAT_IS_ERROR {
        public int getStatusCode() {
            return 6; // 邮箱格式不正确
        }
    },
    PASSWORD_IS_ERROR {
        public int getStatusCode() {
            return 8; // 密码错误
        }
    },
    ACCOUNT_IS_NOT_ACTIVE {
        public int getStatusCode() {
            return 9; // 账户未激活
        }
    },
    ACCOUNT_IS_NOT_APPROVED {
        public int getStatusCode() {
            return 10; // 账户未审批
        }
    },
    ACCOUNT_IS_CLOSED {
        public int getStatusCode() {
            return 11; // 账户被封
        }
    },
    NEWS_TITLE_IS_EMPTY {
        public int getStatusCode() {
            return 12; // 新闻标题为空
        }
    },
    NEWS_DETIAL_IS_EMPTY {
        public int getStatusCode() {
            return 13; // 新闻主题内容为空
        }
    },
    NEWS_AUTHOR_IS_EMPTY {
        public int getStatusCode() {
            return 14; // 新闻作者为空
        }
    },
    NEWS_COVER_IS_EMPTY {
        public int getStatusCode() {
            return 15; // 新闻封面为空
        }
    },
    VERIFYCODE_IS_ERROR {
        public int getStatusCode() {
            return 16; // 验证码错误
        }
    };

    public abstract int getStatusCode();
}
