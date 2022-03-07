package cn.edu.server.pojo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KeyModel {

    private String address;
    private String privateKey;
    private String publicKey;
}
