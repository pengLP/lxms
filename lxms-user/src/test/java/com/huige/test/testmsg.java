package com.huige.test;

import org.junit.Test;

import lxms.utils.sendsms;

public class testmsg {
	@Test
	public void testphoneMsg(){
		sendsms.SendMessage("15204504697");
	}
}
