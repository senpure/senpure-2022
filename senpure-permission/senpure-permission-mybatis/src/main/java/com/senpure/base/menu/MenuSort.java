package com.senpure.base.menu;

import java.util.Comparator;

public class MenuSort implements Comparator<Menu> {

	
	@Override
	public int compare(Menu one, Menu two) {
		if (one.getParentId() == two.getParentId()) {
			return Integer.compare(one.getSort(), two.getSort());
		}
		return 0;
	}

}
