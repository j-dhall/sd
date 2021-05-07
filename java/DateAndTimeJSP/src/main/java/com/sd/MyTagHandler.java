package com.sd; //sd: system design //NOTE NOTE NOTE package name was needed for .tld file to find this class.

import java.io.IOException;
import java.util.Calendar;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;

public class MyTagHandler extends TagSupport {

	@Override
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		JspWriter out = pageContext.getOut(); //get the writer from TagSupport::pageContext
		try {
			out.print(Calendar.getInstance().getTime());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return super.doStartTag();
	}

	
	
}
