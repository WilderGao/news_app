package com.qg.newsapp.utils;

/**
 * 管理员状态
 */
public enum ManagerStatus {
    NORMAL {
        public String getName() {
            return "正常"; // 可登录
        }
    },
    UNAPPROVAL {
        public String getName() {
            return "未审批";
        }
    },
    UNACTIVATION {
        public String getName() {
            return "未激活";
        }
    },
    BE_SEAL {
        public String getName() {
            return "被封号";
        }
    };
    public abstract String getName();
}
