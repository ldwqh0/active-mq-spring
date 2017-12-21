package com.xyyh.mqtest.model;

import java.io.Serializable;

public class TModel implements Serializable {
	private static final long serialVersionUID = -921008687184331557L;

	private int count;

	public TModel(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "TModel [count=" + count + "]";
	}

}
