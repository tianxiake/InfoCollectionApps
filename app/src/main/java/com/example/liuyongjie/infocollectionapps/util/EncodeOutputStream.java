package com.example.liuyongjie.infocollectionapps.util;


import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by nibaogang on 16/9/2.
 */
public class EncodeOutputStream extends OutputStream {
    private ILogger log= LoggerFactory.getLogger("EncodeOutputStream");
    private final DataDecode dataDecode=new DataDecode();
    private final OutputStream outputStream;

    public EncodeOutputStream(OutputStream outputStream, long skip){
        this.outputStream=outputStream;
        this.dataDecode.skip(skip);
    }
    @Override
    public void write(int oneByte) throws IOException {
        oneByte=this.dataDecode.switchData(oneByte);
        this.outputStream.write(oneByte);
        log.verbose(Author.nibaogang, Business.dev_test,"write oneByte",oneByte);
    }

    @Override
    public void write(byte[] buffer) throws IOException {
        this.dataDecode.switchData(buffer,0,buffer.length);
        this.outputStream.write(buffer);
        log.verbose(Author.nibaogang, Business.dev_test,"write buffer:{}",buffer);
    }

    @Override
    public void write(byte[] buffer, int offset, int count) throws IOException {
        this.dataDecode.switchData(buffer,offset,count);
        this.outputStream.write(buffer, offset, count);
        log.verbose(Author.nibaogang, Business.dev_test,"write buffer:{} offset:{} count:{}",buffer,offset,count);
    }

    @Override
    public void close() throws IOException {
        this.outputStream.close();
    }

    @Override
    public void flush() throws IOException {
        this.outputStream.flush();
    }
}
