package com.roger.nio.echo.main;

import com.roger.nio.echo.server.EchoServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoServerMain {

    public static void main(String[] args) throws Exception{
        if(args.length != 1){
            log.error("Echo Server Port Not Allocate.");
            return;
        }
        EchoServer echoServer = new EchoServer(Integer.valueOf(args[0]));
        echoServer.start();
    }
}
