pragma solidity ^0.4.25;

import "./Authentication.sol";

contract EvidenceRepository is Authentication {
    struct EvidenceData {
        bytes32 hash;
        string name;
        string dataUrl;
        uint8 dataType;
        string ext;
    }
    mapping(bytes32=>EvidenceData) private _evidences;

    function setData(bytes32 hash, string name, string dataUrl, uint8 dataType, string ext) public auth {
        _evidences[hash].hash = hash;
        _evidences[hash].name = name;
        _evidences[hash].dataUrl = dataUrl;
        _evidences[hash].dataType = dataType;
        _evidences[hash].ext = ext;
    }

    function getData(bytes32 hash) public view returns(bytes32, string, string, uint8, string) {
        EvidenceData storage evidence = _evidences[hash];
        require(evidence.hash == hash, "无法匹配到与链上一致的信息，请核对输入信息后重试。");
        return (evidence.hash, evidence.name, evidence.dataUrl, evidence.dataType, evidence.ext);
    }
}