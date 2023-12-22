package com.example.spring2xmemshell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
    1. Front request đến httpserver, server return 301: http://back/ticket/" + ExploitUtils.genDeserPayload() + "?compress=true"
    2. Backend nhận request:
    2.1. Mục tiêu sẽ là chèn memshell, do bị giới hạn độ dài (2048) và khi gen CC4 + spring memshell length sẽ khoảng 2k8 bytes --> không thể. Do đó sẽ deser ProxyTemplatesImpl thực hiện load bytecode MemTemplatesImpl từ remote httpserver mà mình host
    2.2.  Backend deser CC4 chain chạy code trong ProxyTemplatesImpl.class
    3. Backend callback URL ra httpserver qua front để download MemTemplatesImpl bytecode chứa spring memshell và load
    4. Memshell đã được load, dùng tiếp httpserver để redirect con front và truy cập memshell
 */
@SpringBootApplication
public class MainApp {
    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }

}