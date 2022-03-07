package cn.edu.chain.config;

import lombok.AllArgsConstructor;
import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.precompiled.crud.TableCRUDService;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@Configuration
@AllArgsConstructor
public class ChainConfig {

    private final FISCOConfig fiscoConfig;

    @Bean
    public BcosSDK getBcosSDK() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        return context.getBean(BcosSDK.class);
    }

    @Bean
    public Client getClient(BcosSDK sdk) {
        Client client = sdk.getClient(fiscoConfig.getGroup());
        client.getCryptoSuite().setCryptoKeyPair(client.getCryptoSuite().createKeyPair(fiscoConfig.getPrivateKey()));
        return client;
    }

    @Bean
    public CryptoSuite getCryptoSuite(Client client) {
        return client.getCryptoSuite();
    }

    @Bean
    public CryptoKeyPair getCryptoKeyPair(CryptoSuite cryptoSuite) {
        return cryptoSuite.getCryptoKeyPair();
    }

    @Bean
    public TableCRUDService getTableCRUDService(Client client, CryptoKeyPair cryptoKeyPair) {
        return new TableCRUDService(client, cryptoKeyPair);
    }
}
