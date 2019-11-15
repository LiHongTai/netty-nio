package com.roger.nio.echo.main;

import com.roger.nio.echo.client.EchoClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoClientMain {

    public static void main(String[] args) throws Exception{
        if(args.length != 2){
            log.error("Echo Client Host And Port Not Allocate.");
            return;
        }
        EchoClient echoClient = new EchoClient(args[0],Integer.valueOf(args[1]));
        echoClient.start();
    }
}
