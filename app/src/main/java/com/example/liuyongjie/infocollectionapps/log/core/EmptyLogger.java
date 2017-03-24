package com.example.liuyongjie.infocollectionapps.log.core;


import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;

/**
 * 
 * @author NiBaoGang
 * HuaYongWuXian
 * 2013-5-22
 *
 */
public class EmptyLogger implements ILogger {

	@Override
	public void verbose(Author author, Business business, String message, Object... params) {
		
	}

	@Override
	public void debug(Author author, Business business, Object param, String message, Object... params) {

	}

	@Override
	public void info(Author author, Business business, Object param, String message, Object... params) {

	}

	@Override
	public void warn(Author author,Throwable t) {
	}

	@Override
	public void warn(Author author, String message, Throwable t) {
	}

	@Override
	public void warn(Author author, Business business, String message, Object... params) {

	}

	@Override
	public void error(Author author, Throwable t) {
	}

	@Override
	public void error(Author author, String message, Throwable t) {
	}

	@Override
	public void error(Author author, String message, Object... params) {
	}
}
