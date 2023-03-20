package com.vidaEsSalud;

import java.net.InetAddress;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.vidaEsSalud.service.MailServie;

@SpringBootTest
class VidaEsSaludApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
    void send() throws Exception {
        String hostname = InetAddress.getLocalHost().getCanonicalHostName();
      System.out.println("Hostname: " + hostname);
	}
       

}
