package com.vivalux.cyb.api;

public enum EnumModuleType {

	EYE(0), ARM(1), LEG(2);

	int type;

	EnumModuleType(int t) {
		this.type = t;
	}

    public int getType() {
        return type;
    }
}