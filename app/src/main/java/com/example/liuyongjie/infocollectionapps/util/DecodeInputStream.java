package com.example.liuyongjie.infocollectionapps.util;


import com.example.liuyongjie.infocollectionapps.log.LoggerFactory;
import com.example.liuyongjie.infocollectionapps.log.intf.ILogger;
import com.example.liuyongjie.infocollectionapps.log.util.Author;
import com.example.liuyongjie.infocollectionapps.log.util.Business;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by nibaogang on 16/9/2.
 */
public class DecodeInputStream extends InputStream {
    private ILogger log= LoggerFactory.getLogger("DecodeInputStream");
    private final DataDecode dataDecode=new DataDecode();
    private final InputStream inputStream;

    public DecodeInputStream(InputStream inputStream){
        this.inputStream=inputStream;
    }

    @Override
    public int read() throws IOException {
        int b = this.inputStream.read();
        log.verbose(Author.nibaogang, Business.dev_test,"read byte:{}",b);
        return dataDecode.switchData(b);
    }

    @Override
    public int read(byte[] buffer) throws IOException {
        int b = this.inputStream.read(buffer);
        this.dataDecode.switchData(buffer,0,b);
        log.verbose(Author.nibaogang, Business.dev_test,"read buffer:{} b:{}",buffer,b);
        return b;
    }

    @Override
    public int read(byte[] buffer, int byteOffset, int byteCount) throws IOException {
        int b = this.inputStream.read(buffer, byteOffset, byteCount);
        this.dataDecode.switchData(buffer,byteOffset,b);
        log.verbose(Author.nibaogang, Business.dev_test,"read buffer:{} offset:{} count:{} b:{}",buffer,byteOffset,byteCount,b);
        return b;
    }

    @Override
    public void close() throws IOException {
        this.inputStream.close();
    }

    @Override
    public int available() throws IOException {
        return this.inputStream.available();
    }

    @Override
    public void mark(int readlimit) {
    }

    @Override
    public boolean markSupported() {
        return false;
    }

    @Override
    public synchronized void reset() throws IOException {
    }

    @Override
    public long skip(long byteCount) throws IOException {
        long b = this.inputStream.skip(byteCount);
        this.dataDecode.skip(b);
        return b;
    }

}
