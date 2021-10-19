package vip.codehome.camel.examples.common;

import static ch.qos.logback.core.spi.FilterReply.ACCEPT;
import static ch.qos.logback.core.spi.FilterReply.DENY;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.filter.LevelFilter;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import java.io.File;
import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dsys
 * @version v1.0
 **/
public class LogHelper {
  public static String logHome=System.getProperty("user.home")+ File.separator +"weblogs"+File.separator;
  private static ConcurrentHashMap<String,Logger> container=new ConcurrentHashMap<>();
  public static Logger getLogger(String logName){
    Logger logger=container.get(logName);
    if(logger!=null){
      return logger;
    }
    synchronized (LogHelper.class){
      logger=container.get(logName);
      if(logger!=null){
        return logger;
      }
      logger=build(logName);
      container.put(logName,logger);
    }
    return logger;
  }
  public static Logger build(String loggerName){
    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    ch.qos.logback.classic.Logger logger=loggerContext.getLogger(loggerName);
    logger.setAdditive(false);
    RollingFileAppender appender=new RollingFileAppender();
    appender.addFilter(createLevelFilter(Level.INFO));
    appender.setContext(loggerContext);
    appender.setName(loggerName+"Appender");
    appender.setFile(logHome+loggerName+".log");
    appender.setAppend(true);
    appender.setPrudent(false);
    TimeBasedRollingPolicy policy = new TimeBasedRollingPolicy();
    // 设置父节点是appender
    policy.setContext(loggerContext);
    policy.setParent(appender);
    // 最大日志文件大小
    // 设置文件名模式
    policy.setFileNamePattern(logHome+"backup/"+loggerName+"-%d{yyyy-MM-dd}-%i.log");
    // 设置最大历史记录为30天
    policy.setMaxHistory(30);
    // 总大小限制
    policy.setTotalSizeCap(FileSize.valueOf("32GB"));

    SizeAndTimeBasedFNATP sizeAndTimeBasedFNATP= new SizeAndTimeBasedFNATP();
    sizeAndTimeBasedFNATP.setMaxFileSize(FileSize.valueOf("50MB"));
    policy.setTimeBasedFileNamingAndTriggeringPolicy(sizeAndTimeBasedFNATP);
    // 设置上下文，每个logger都关联到logger上下文，默认上下文名称为default。
    // 但可以使用<contextName>设置成其他名字，用于区分不同应用程序的记录。一旦设置，不能修改。
    policy.start();
    appender.setRollingPolicy(policy);
    PatternLayoutEncoder encoder = new PatternLayoutEncoder();
    // 设置上下文，每个logger都关联到logger上下文，默认上下文名称为default。
    // 但可以使用<contextName>设置成其他名字，用于区分不同应用程序的记录。一旦设置，不能修改。
    encoder.setContext(loggerContext);
    // 设置格式
    encoder.setPattern("%msg%n");
    encoder.setCharset(Charset.forName("UTF-8"));
    encoder.start();
    appender.setEncoder(encoder);
    logger.addAppender(appender);
    appender.start();
    return logger;
  }
  private static LevelFilter createLevelFilter(Level level) {
    LevelFilter levelFilter = new LevelFilter();
    levelFilter.setLevel(level);
    levelFilter.setOnMatch(ACCEPT);
    levelFilter.setOnMismatch(DENY);
    levelFilter.start();
    return levelFilter;
  }


}
