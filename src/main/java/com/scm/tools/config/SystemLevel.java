package com.scm.tools.config;

/**
 * @author yangqi
 */

public enum SystemLevel {

    LEVEL_A("A"),LEVEL_B("B");

    private String level;
    private SystemLevel(String level){
        this.level = level;
    }
}
