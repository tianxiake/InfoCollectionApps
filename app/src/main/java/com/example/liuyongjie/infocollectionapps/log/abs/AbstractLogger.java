package com.example.liuyongjie.infocollectionapps.log.abs;


import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;

/**
 * 
 * @author NiBaoGang HuaYongWuXian 2013-5-22
 * 
 */
public abstract class AbstractLogger implements ILogger {
	private static final long START_TIME= System.nanoTime();
	private static final String REPLACE_STR = "{}";
	private final String tag;
	private static final int TAG_MAX=22;

	protected AbstractLogger(String tag) {
		if(tag.length()<TAG_MAX) {
			while (tag.length() < TAG_MAX) {
				tag += "_";
			}
		}
		this.tag = tag;
	}

	private StringBuilder createStringBuilder(Author author, Business business, Object param){
		long ms=(System.nanoTime()-START_TIME)/1000000;
		StringBuilder builder=new StringBuilder("[");
		builder.append(ms).append("][");
		builder.append(author.getCc());
		builder.append("]");
		if(business!=null) {
			builder.append("[").append(business);
			if(param!=null){
				builder.append(":").append(param);
			}
			builder.append("]");
		}
		builder.append("[tid_").append(Thread.currentThread().getId()).append("](").append(getLine()).append(")");
		return builder;
	}

	@Override
	public final void verbose(Author author, Business business, String message, Object... params) {
		StringBuilder builder=createStringBuilder(author,business,null);
		log(LOG_LEVEL.v,Author.nibaogang,tag,builder.toString(),message,null,params);
	}

	@Override
	public void debug(Author author, Business business, Object param, String message, Object... params) {
		StringBuilder builder=createStringBuilder(author,business,param);
		log(LOG_LEVEL.d,author,tag,builder.toString(),message,null,params);
	}

	@Override
	public void info(Author author, Business business, Object param, String message, Object... params) {
		StringBuilder builder=createStringBuilder(author,business,param);
		log(LOG_LEVEL.i,author,tag,builder.toString(),message,null,params);
	}

	@Override
	public void warn(Author author, Throwable t) {
		warn(author,null, t);
	}

	@Override
	public void warn(Author author, String message, Throwable t) {
		StringBuilder builder=createStringBuilder(author,null,null);
		log(LOG_LEVEL.w,author,tag,builder.toString(), message, t);
	}

	@Override
	public void warn(Author author, Business business, String message, Object... params) {
		StringBuilder builder=createStringBuilder(author,business,null);
		log(LOG_LEVEL.w,author,tag,builder.toString(),message,null,params);
	}

	@Override
	public final void error(Author author, Throwable t) {
		error(author, null, t);
	}

	@Override
	public final void error(Author author, String message, Throwable t) {
		StringBuilder builder=createStringBuilder(author,null,null);
		log(LOG_LEVEL.e, author,tag,builder.toString(), message, t);
	}

	@Override
	public final void error(Author author, String message, Object... params) {
		StringBuilder builder=createStringBuilder(author,null,null);
		log(LOG_LEVEL.e, author,tag, builder.toString(),message, null,params);
	}

	protected String toFullMessage(final String messagePattern, final Object[] array) {
		//Bug #41086 by jianghui 2015-01-25
		StringBuilder builder=new StringBuilder();
		if(messagePattern == null){
			if(array.length != 0) {
				return "log_format_error";
			}else {
				return "";
			}
		}
		try {
			int i = 0;
			int j;
			int l;
			for (l = 0; l < array.length; l++) {
				j = messagePattern.indexOf(REPLACE_STR, i);
				if (j != -1) {
					builder.append(messagePattern.substring(i, j));
					builder.append(array[l]);
					i = j + 2;
				} else {
					break;
				}
			}
			builder.append(messagePattern.substring(i, messagePattern.length()));
			return builder.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "log_format_error";
		}
	}

	private int getLine(){
		try {
			StackTraceElement[] elements = Thread.currentThread().getStackTrace();
			boolean find = false;
			for (StackTraceElement stackTraceElement : elements) {
				if (stackTraceElement.getClassName().equals(AbstractLogger.class.getName())) {
					find = true;
					continue;
				}
				if (find) {
					if (!stackTraceElement.getClassName().equals(AbstractLogger.class.getName())) {
						return stackTraceElement.getLineNumber();
					}
				}
			}
		}catch (Exception e){
			warn(Author.nibaogang, e);
		}
		return 0;
	}

	private void log(LOG_LEVEL level, Author author, String tag, String head, String message, Throwable throwable, Object... params){
		if (LoggerFactory.isEnable()) {
			handleLog(level, author, tag, head, message, throwable, params);
		} else {
			if(level == LOG_LEVEL.e) {
				verbose(Author.nibaogang, Business.dev_test, "error log");
				handleLog(level, author, tag, head, message, throwable, params);
			}
		}
	}

	/**
	 * 处理log
	 * Created by HanPeng on 2016.9.20
     */
	private void handleLog(LOG_LEVEL level, Author author, String tag, String head, String message, Throwable throwable, Object... params) {
		String fullMessage = toFullMessage(message, params);
		String author_name = author == null ? "author" : author.name();
		int index = 0;
		int maxLength = 3000;
		String sub;
		if (fullMessage.length()<maxLength){
			log(level, author_name, tag, head + fullMessage, throwable);
		}else {
			int i=0;
			while (index < fullMessage.length()) {
				i++;
				// java的字符不允许指定超过总的长度end
				if (fullMessage.length() <= index + maxLength) {
					sub = fullMessage.substring(index);
				} else {
					sub = fullMessage.substring(index, index + maxLength);
				}
				index += maxLength;
				log(level, author_name, tag, head+"**********("+i+")**********" + sub, throwable);
			}
		}
	}

	protected abstract void log(LOG_LEVEL level, String author, String tag, String message, Throwable t);
}
