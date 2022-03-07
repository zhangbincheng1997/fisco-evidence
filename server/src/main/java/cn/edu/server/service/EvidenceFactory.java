package cn.edu.server.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.fisco.bcos.sdk.abi.FunctionReturnDecoder;
import org.fisco.bcos.sdk.abi.TypeReference;
import org.fisco.bcos.sdk.abi.datatypes.Address;
import org.fisco.bcos.sdk.abi.datatypes.Bool;
import org.fisco.bcos.sdk.abi.datatypes.Event;
import org.fisco.bcos.sdk.abi.datatypes.Function;
import org.fisco.bcos.sdk.abi.datatypes.Type;
import org.fisco.bcos.sdk.abi.datatypes.Utf8String;
import org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32;
import org.fisco.bcos.sdk.abi.datatypes.generated.Uint8;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple1;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple5;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple6;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.contract.Contract;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.eventsub.EventCallback;
import org.fisco.bcos.sdk.model.CryptoType;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.model.callback.TransactionCallback;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;

@SuppressWarnings("unchecked")
public class EvidenceFactory extends Contract {
    public static final String[] BINARY_ARRAY = {};

    public static final String BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", BINARY_ARRAY);

    public static final String[] SM_BINARY_ARRAY = {"608060405234801561001057600080fd5b50611a01806100206000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806344bc24e9146100725780636dda0e52146101c85780637a2a1de8146102445780637a966897146102ad578063a368022e14610336575b600080fd5b34801561007e57600080fd5b506101c6600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035600019169060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803560ff169060200190929190803590602001908201803590602001908080601f016020809104026020016040519081016040528093929190818152602001838380828437820191505050505050919291929050505061051a565b005b3480156101d457600080fd5b506101f760048036038101908080356000191690602001909291905050506107c0565b60405180831515151581526020018273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff1681526020019250505060405180910390f35b34801561025057600080fd5b506102ab600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610811565b005b3480156102b957600080fd5b50610334600480360381019080803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610978565b005b34801561034257600080fd5b506103ab600480360381019080803590602001908201803590602001908080601f01602080910402602001604051908101604052809392919081815260200183838082843782019150505050505091929192908035600019169060200190929190505050610a3d565b60405180866000191660001916815260200180602001806020018560ff1660ff16815260200180602001848103845288818151815260200191508051906020019080838360005b8381101561040d5780820151818401526020810190506103f2565b50505050905090810190601f16801561043a5780820380516001836020036101000a031916815260200191505b50848103835287818151815260200191508051906020019080838360005b83811015610473578082015181840152602081019050610458565b50505050905090810190601f1680156104a05780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b838110156104d95780820151818401526020810190506104be565b50505050905090810190601f1680156105065780820380516001836020036101000a031916815260200191505b509850505050505050505060405180910390f35b600061052587610c22565b90508073ffffffffffffffffffffffffffffffffffffffff16637e575e2487878787876040518663ffffffff167c010000000000000000000000000000000000000000000000000000000002815260040180866000191660001916815260200180602001806020018560ff1660ff16815260200180602001848103845288818151815260200191508051906020019080838360005b838110156105d55780820151818401526020810190506105ba565b50505050905090810190601f1680156106025780820380516001836020036101000a031916815260200191505b50848103835287818151815260200191508051906020019080838360005b8381101561063b578082015181840152602081019050610620565b50505050905090810190601f1680156106685780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b838110156106a1578082015181840152602081019050610686565b50505050905090810190601f1680156106ce5780820380516001836020036101000a031916815260200191505b5098505050505050505050600060405180830381600087803b1580156106f357600080fd5b505af1158015610707573d6000803e3d6000fd5b505050507f1877152373c280f304d2b8017d87e464c6e83fc0059064ac74e39e98b20da851878760405180806020018360001916600019168152602001828103825284818151815260200191508051906020019080838360005b8381101561077c578082015181840152602081019050610761565b50505050905090810190601f1680156107a95780820380516001836020036101000a031916815260200191505b50935050505060405180910390a150505050505050565b60006020528060005260406000206000915090508060000160009054906101000a900460ff16908060000160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff16905082565b600061081c82610d15565b9050600080826000191660001916815260200190815260200160002060000160009054906101000a900460ff161515156108be576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260128152602001807fe5ba94e794a8e5b7b2e7bb8fe6b3a8e5868c000000000000000000000000000081525060200191505060405180910390fd5b6001600080836000191660001916815260200190815260200160002060000160006101000a81548160ff0219169083151502179055506108fc610d23565b604051809103906000f080158015610918573d6000803e3d6000fd5b50600080836000191660001916815260200190815260200160002060000160016101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff1602179055505050565b600061098383610c22565b90508073ffffffffffffffffffffffffffffffffffffffff1663da89dd38836040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff168152602001915050600060405180830381600087803b158015610a2057600080fd5b505af1158015610a34573d6000803e3d6000fd5b50505050505050565b6000606080600060606000610a5188610c22565b90508073ffffffffffffffffffffffffffffffffffffffff1663cad1a469886040518263ffffffff167c0100000000000000000000000000000000000000000000000000000000028152600401808260001916600019168152602001915050600060405180830381600087803b158015610aca57600080fd5b505af1158015610ade573d6000803e3d6000fd5b505050506040513d6000823e3d601f19601f8201168201806040525060a0811015610b0857600080fd5b81019080805190602001909291908051640100000000811115610b2a57600080fd5b82810190506020810184811115610b4057600080fd5b8151856001820283011164010000000082111715610b5d57600080fd5b50509291906020018051640100000000811115610b7957600080fd5b82810190506020810184811115610b8f57600080fd5b8151856001820283011164010000000082111715610bac57600080fd5b5050929190602001805190602001909291908051640100000000811115610bd257600080fd5b82810190506020810184811115610be857600080fd5b8151856001820283011164010000000082111715610c0557600080fd5b505092919050505095509550955095509550509295509295909350565b600080610c2e83610d15565b9050600080826000191660001916815260200190815260200160002060000160009054906101000a900460ff161515610ccf576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260128152602001807fe5ba94e794a8e5b09ae69caae6b3a8e5868c000000000000000000000000000081525060200191505060405180910390fd5b600080826000191660001916815260200190815260200160002060000160019054906101000a900473ffffffffffffffffffffffffffffffffffffffff16915050919050565b600060208201519050919050565b604051610ca280610d348339019056006080604052326000806101000a81548173ffffffffffffffffffffffffffffffffffffffff021916908373ffffffffffffffffffffffffffffffffffffffff160217905550610c4f806100536000396000f30060806040526004361061006d576000357c0100000000000000000000000000000000000000000000000000000000900463ffffffff16806328e914891461007257806356bea000146100c95780637e575e241461010c578063cad1a4691461021c578063da89dd38146103ba575b600080fd5b34801561007e57600080fd5b506100876103fd565b604051808273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200191505060405180910390f35b3480156100d557600080fd5b5061010a600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610422565b005b34801561011857600080fd5b5061021a6004803603810190808035600019169060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290803560ff169060200190929190803590602001908201803590602001908080601f0160208091040260200160405190810160405280939291908181526020018383808284378201915050505050509192919290505050610541565b005b34801561022857600080fd5b5061024b600480360381019080803560001916906020019092919050505061075e565b60405180866000191660001916","815260200180602001806020018560ff1660ff16815260200180602001848103845288818151815260200191508051906020019080838360005b838110156102ad578082015181840152602081019050610292565b50505050905090810190601f1680156102da5780820380516001836020036101000a031916815260200191505b50848103835287818151815260200191508051906020019080838360005b838110156103135780820151818401526020810190506102f8565b50505050905090810190601f1680156103405780820380516001836020036101000a031916815260200191505b50848103825285818151815260200191508051906020019080838360005b8381101561037957808201518184015260208101905061035e565b50505050905090810190601f1680156103a65780820380516001836020036101000a031916815260200191505b509850505050505050505060405180910390f35b3480156103c657600080fd5b506103fb600480360381019080803573ffffffffffffffffffffffffffffffffffffffff169060200190929190505050610a60565b005b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1681565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff161415156104e6576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260158152602001807fe794a8e688b7e4b88de698afe7aea1e79086e59198000000000000000000000081525060200191505060405180910390fd5b6000600160008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff02191690831515021790555050565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff1614806105ec575060011515600160003273ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060009054906101000a900460ff161515145b1515610660576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260158152602001807fe794a8e688b7e4b88de58581e8aeb8e6938de4bd9c000000000000000000000081525060200191505060405180910390fd5b8460026000876000191660001916815260200190815260200160002060000181600019169055508360026000876000191660001916815260200190815260200160002060010190805190602001906106b9929190610b7e565b508260026000876000191660001916815260200190815260200160002060020190805190602001906106ec929190610b7e565b508160026000876000191660001916815260200190815260200160002060030160006101000a81548160ff021916908360ff160217905550806002600087600019166000191681526020019081526020016000206004019080519060200190610756929190610b7e565b505050505050565b600060608060006060600060026000886000191660001916815260200190815260200160002090508660001916816000015460001916141515610855576040517fc703cb1200000000000000000000000000000000000000000000000000000000815260040180806020018281038252604b8152602001807fe697a0e6b395e58cb9e9858de588b0e4b88ee993bee4b88ae4b880e887b4e79a81526020017f84e4bfa1e681afefbc8ce8afb7e6a0b8e5afb9e8be93e585a5e4bfa1e681afe581526020017f908ee9878de8af95e3808200000000000000000000000000000000000000000081525060600191505060405180910390fd5b806000015481600101826002018360030160009054906101000a900460ff1684600401838054600181600116156101000203166002900480601f01602080910402602001604051908101604052809291908181526020018280546001816001161561010002031660029004801561090d5780601f106108e25761010080835404028352916020019161090d565b820191906000526020600020905b8154815290600101906020018083116108f057829003601f168201915b50505050509350828054600181600116156101000203166002900480601f0160208091040260200160405190810160405280929190818152602001828054600181600116156101000203166002900480156109a95780601f1061097e576101008083540402835291602001916109a9565b820191906000526020600020905b81548152906001019060200180831161098c57829003601f168201915b50505050509250808054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610a455780601f10610a1a57610100808354040283529160200191610a45565b820191906000526020600020905b815481529060010190602001808311610a2857829003601f168201915b50505050509050955095509550955095505091939590929450565b6000809054906101000a900473ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff163273ffffffffffffffffffffffffffffffffffffffff16141515610b24576040517fc703cb120000000000000000000000000000000000000000000000000000000081526004018080602001828103825260158152602001807fe794a8e688b7e4b88de698afe7aea1e79086e59198000000000000000000000081525060200191505060405180910390fd5b60018060008373ffffffffffffffffffffffffffffffffffffffff1673ffffffffffffffffffffffffffffffffffffffff16815260200190815260200160002060006101000a81548160ff02191690831515021790555050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f10610bbf57805160ff1916838001178555610bed565b82800160010185558215610bed579182015b82811115610bec578251825591602001919060010190610bd1565b5b509050610bfa9190610bfe565b5090565b610c2091905b80821115610c1c576000816000905550600101610c04565b5090565b905600a165627a7a72305820e102de93a9f3c4a88610782f724279d2224fa06d9c964a2bd9283d637dd629170029a165627a7a723058208e054deb75c3635f74ad605e3ccdce0f7e0e714ebe68440844f74e9e509df1060029"};

    public static final String SM_BINARY = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", SM_BINARY_ARRAY);

    public static final String[] ABI_ARRAY = {"[{\"constant\":false,\"inputs\":[{\"name\":\"appId\",\"type\":\"string\"},{\"name\":\"hash\",\"type\":\"bytes32\"},{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"dataUrl\",\"type\":\"string\"},{\"name\":\"dataType\",\"type\":\"uint8\"},{\"name\":\"ext\",\"type\":\"string\"}],\"name\":\"save\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"\",\"type\":\"bytes32\"}],\"name\":\"apps\",\"outputs\":[{\"name\":\"valid\",\"type\":\"bool\"},{\"name\":\"evidenceRepo\",\"type\":\"address\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"appId\",\"type\":\"string\"}],\"name\":\"createApp\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":false,\"inputs\":[{\"name\":\"appId\",\"type\":\"string\"},{\"name\":\"addr\",\"type\":\"address\"}],\"name\":\"createUser\",\"outputs\":[],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"},{\"constant\":true,\"inputs\":[{\"name\":\"appId\",\"type\":\"string\"},{\"name\":\"hash\",\"type\":\"bytes32\"}],\"name\":\"query\",\"outputs\":[{\"name\":\"\",\"type\":\"bytes32\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"string\"},{\"name\":\"\",\"type\":\"uint8\"},{\"name\":\"\",\"type\":\"string\"}],\"payable\":false,\"stateMutability\":\"view\",\"type\":\"function\"},{\"anonymous\":false,\"inputs\":[{\"indexed\":false,\"name\":\"appId\",\"type\":\"string\"},{\"indexed\":false,\"name\":\"hash\",\"type\":\"bytes32\"}],\"name\":\"EvidenceSave\",\"type\":\"event\"}]"};

    public static final String ABI = org.fisco.bcos.sdk.utils.StringUtils.joinAll("", ABI_ARRAY);

    public static final String FUNC_SAVE = "save";

    public static final String FUNC_APPS = "apps";

    public static final String FUNC_CREATEAPP = "createApp";

    public static final String FUNC_CREATEUSER = "createUser";

    public static final String FUNC_QUERY = "query";

    public static final Event EVIDENCESAVE_EVENT = new Event("EvidenceSave",
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bytes32>() {}));
    ;

    protected EvidenceFactory(String contractAddress, Client client, CryptoKeyPair credential) {
        super(getBinary(client.getCryptoSuite()), contractAddress, client, credential);
    }

    public static String getBinary(CryptoSuite cryptoSuite) {
        return (cryptoSuite.getCryptoTypeConfig() == CryptoType.ECDSA_TYPE ? BINARY : SM_BINARY);
    }

    public TransactionReceipt save(String appId, byte[] hash, String name, String dataUrl, BigInteger dataType, String ext) {
        final Function function = new Function(
                FUNC_SAVE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(appId),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32(hash),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(name),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(dataUrl),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint8(dataType),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(ext)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void save(String appId, byte[] hash, String name, String dataUrl, BigInteger dataType, String ext, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_SAVE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(appId),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32(hash),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(name),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(dataUrl),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint8(dataType),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(ext)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForSave(String appId, byte[] hash, String name, String dataUrl, BigInteger dataType, String ext) {
        final Function function = new Function(
                FUNC_SAVE,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(appId),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32(hash),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(name),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(dataUrl),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Uint8(dataType),
                        new org.fisco.bcos.sdk.abi.datatypes.Utf8String(ext)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple6<String, byte[], String, String, BigInteger, String> getSaveInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_SAVE,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint8>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple6<String, byte[], String, String, BigInteger, String>(

                (String) results.get(0).getValue(),
                (byte[]) results.get(1).getValue(),
                (String) results.get(2).getValue(),
                (String) results.get(3).getValue(),
                (BigInteger) results.get(4).getValue(),
                (String) results.get(5).getValue()
        );
    }

    public Tuple2<Boolean, String> apps(byte[] param0) throws ContractException {
        final Function function = new Function(FUNC_APPS,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32(param0)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}, new TypeReference<Address>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple2<Boolean, String>(
                (Boolean) results.get(0).getValue(),
                (String) results.get(1).getValue());
    }

    public TransactionReceipt createApp(String appId) {
        final Function function = new Function(
                FUNC_CREATEAPP,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(appId)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void createApp(String appId, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_CREATEAPP,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(appId)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForCreateApp(String appId) {
        final Function function = new Function(
                FUNC_CREATEAPP,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(appId)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple1<String> getCreateAppInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_CREATEAPP,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple1<String>(

                (String) results.get(0).getValue()
        );
    }

    public TransactionReceipt createUser(String appId, String addr) {
        final Function function = new Function(
                FUNC_CREATEUSER,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(appId),
                        new org.fisco.bcos.sdk.abi.datatypes.Address(addr)),
                Collections.<TypeReference<?>>emptyList());
        return executeTransaction(function);
    }

    public void createUser(String appId, String addr, TransactionCallback callback) {
        final Function function = new Function(
                FUNC_CREATEUSER,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(appId),
                        new org.fisco.bcos.sdk.abi.datatypes.Address(addr)),
                Collections.<TypeReference<?>>emptyList());
        asyncExecuteTransaction(function, callback);
    }

    public String getSignedTransactionForCreateUser(String appId, String addr) {
        final Function function = new Function(
                FUNC_CREATEUSER,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(appId),
                        new org.fisco.bcos.sdk.abi.datatypes.Address(addr)),
                Collections.<TypeReference<?>>emptyList());
        return createSignedTransaction(function);
    }

    public Tuple2<String, String> getCreateUserInput(TransactionReceipt transactionReceipt) {
        String data = transactionReceipt.getInput().substring(10);
        final Function function = new Function(FUNC_CREATEUSER,
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}, new TypeReference<Address>() {}));
        List<Type> results = FunctionReturnDecoder.decode(data, function.getOutputParameters());
        return new Tuple2<String, String>(

                (String) results.get(0).getValue(),
                (String) results.get(1).getValue()
        );
    }

    public Tuple5<byte[], String, String, BigInteger, String> query(String appId, byte[] hash) throws ContractException {
        final Function function = new Function(FUNC_QUERY,
                Arrays.<Type>asList(new org.fisco.bcos.sdk.abi.datatypes.Utf8String(appId),
                        new org.fisco.bcos.sdk.abi.datatypes.generated.Bytes32(hash)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Bytes32>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint8>() {}, new TypeReference<Utf8String>() {}));
        List<Type> results = executeCallWithMultipleValueReturn(function);
        return new Tuple5<byte[], String, String, BigInteger, String>(
                (byte[]) results.get(0).getValue(),
                (String) results.get(1).getValue(),
                (String) results.get(2).getValue(),
                (BigInteger) results.get(3).getValue(),
                (String) results.get(4).getValue());
    }

    public List<EvidenceSaveEventResponse> getEvidenceSaveEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(EVIDENCESAVE_EVENT, transactionReceipt);
        ArrayList<EvidenceSaveEventResponse> responses = new ArrayList<EvidenceSaveEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            EvidenceSaveEventResponse typedResponse = new EvidenceSaveEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.appId = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.hash = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public void subscribeEvidenceSaveEvent(String fromBlock, String toBlock, List<String> otherTopics, EventCallback callback) {
        String topic0 = eventEncoder.encode(EVIDENCESAVE_EVENT);
        subscribeEvent(ABI,BINARY,topic0,fromBlock,toBlock,otherTopics,callback);
    }

    public void subscribeEvidenceSaveEvent(EventCallback callback) {
        String topic0 = eventEncoder.encode(EVIDENCESAVE_EVENT);
        subscribeEvent(ABI,BINARY,topic0,callback);
    }

    public static EvidenceFactory load(String contractAddress, Client client, CryptoKeyPair credential) {
        return new EvidenceFactory(contractAddress, client, credential);
    }

    public static EvidenceFactory deploy(Client client, CryptoKeyPair credential) throws ContractException {
        return deploy(EvidenceFactory.class, client, credential, getBinary(client.getCryptoSuite()), "");
    }

    public static class EvidenceSaveEventResponse {
        public TransactionReceipt.Logs log;

        public String appId;

        public byte[] hash;
    }
}
